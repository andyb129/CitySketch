package uk.co.barbuzz.urbancanvas.ui.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.maps.android.clustering.ClusterManager;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import uk.co.barbuzz.urbancanvas.data.CallbackWrapper;
import uk.co.barbuzz.urbancanvas.data.FlickrApiManager;
import uk.co.barbuzz.urbancanvas.data.model.FlickrViewData;
import uk.co.barbuzz.urbancanvas.data.model.Photo;
import uk.co.barbuzz.urbancanvas.injection.Injection;
import uk.co.barbuzz.urbancanvas.ui.base.BasePresenter;
import uk.co.barbuzz.urbancanvas.data.model.PhotoClusterItem;
import uk.co.barbuzz.urbancanvas.ui.main.activties.DetailActivity;

public class MainPresenter extends BasePresenter<MainView> {

    private static final String TAG = "MainPresenter";
    public static final String EXTRA_PHOTO = TAG + "_extra_photo";
    public static final String EXTRA_PHOTO_IMAGE_TRANSITION_NAME = TAG + "_extra_photo_transition_name";

    private FlickrApiManager mFlickrApiManager;
    private Scheduler mSubscribeScheduer;
    private Scheduler mObserveSceduler;
    private Disposable mSubscription;
    private ClusterManager<PhotoClusterItem> clusterManager;

    public MainPresenter(
            FlickrApiManager flickrApiManager,
            Scheduler subscribeScheduer,
            Scheduler observeSceduler) {

        mFlickrApiManager = flickrApiManager;
        mSubscribeScheduer = subscribeScheduer;
        mObserveSceduler = observeSceduler;
    }

    @Override
    public void attachView(MainView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) {
            mSubscription.dispose();
        }
    }

    /**
     * retrieves photo data & populates map locations
     * @param context
     * @param googleMap
     * @param infoWindowClickListener
     * @param page
     */
    public void getImages(final Context context, GoogleMap googleMap,
                          GoogleMap.OnInfoWindowClickListener infoWindowClickListener, int page) {

        checkViewAttached();

        setupMapPinClusters(context, googleMap, infoWindowClickListener);

        mSubscription = mFlickrApiManager.getFlickrImages(Injection.FLICKR_SEARCH_KEYWORDS, Injection.RESULTS_PER_PAGE, page)
                .subscribeOn(mSubscribeScheduer)
                .observeOn(mObserveSceduler)
                .doOnNext(flickrViewData -> addMapClusterPins(flickrViewData.getPhotoList()))
                .subscribeWith(new CallbackWrapper<FlickrViewData>(getView()) {
                    @Override
                    protected void onSuccess(FlickrViewData flickrViewData) {
                        getView().displayImages(flickrViewData);
                    }
                });
    }

    public void onPhotoClicked(Activity activity, View toolbar, int position,
                               Photo photo, ImageView sharedImageView) {

        checkViewAttached();

        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(EXTRA_PHOTO, photo);

        /*intent.putExtra(EXTRA_PHOTO_IMAGE_TRANSITION_NAME, ViewCompat.getTransitionName(sharedImageView));

        View statusBar = activity.findViewById(android.R.id.statusBarBackground);
        Pair<View, String> imagePair = Pair.create((View) sharedImageView, ViewCompat.getTransitionName(sharedImageView));

        Pair<View, String> statusPair = Pair.create(statusBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME);
        //Pair<View, String> toolbarPair = Pair.create((View) toolbar, "tActionBar");

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                imagePair, statusPair);//, toolbarPair);

        activity.startActivity(intent, options.toBundle());*/

        activity.startActivity(intent);

    }

    private void setupMapPinClusters(Context context, GoogleMap googleMap, GoogleMap.OnInfoWindowClickListener infoWindowClickListener) {
        clusterManager = new ClusterManager<PhotoClusterItem>(context, googleMap);
        googleMap.setOnCameraIdleListener(clusterManager);
        googleMap.setOnMarkerClickListener(clusterManager);
        googleMap.setOnInfoWindowClickListener(infoWindowClickListener);
    }

    private void addMapClusterPins(List<Photo> photoList) {
        for (int i = 0; i < photoList.size(); i++) {
            Photo photo = photoList.get(i);
            PhotoClusterItem photoClusterItem =
                    new PhotoClusterItem(photo.getId(), photo.getTitle(), photo.getOwner(),
                            photo.getLatitude(), photo.getLongitude(), photo.getThumbPhotoUrl());
            clusterManager.addItem(photoClusterItem);
        }
        clusterManager.cluster();
    }

}
