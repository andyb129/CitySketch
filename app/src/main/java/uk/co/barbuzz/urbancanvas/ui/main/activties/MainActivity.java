package uk.co.barbuzz.urbancanvas.ui.main.activties;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.polyak.iconswitch.IconSwitch;
import com.polyak.iconswitch.IconSwitch.Checked;

import java.util.ArrayList;
import java.util.List;

import cn.refactor.multistatelayout.MultiStateLayout;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import uk.co.barbuzz.urbancanvas.R;
import uk.co.barbuzz.urbancanvas.data.model.FlickrViewData;
import uk.co.barbuzz.urbancanvas.data.model.Photo;
import uk.co.barbuzz.urbancanvas.injection.Injection;
import uk.co.barbuzz.urbancanvas.ui.base.BaseActivity;
import uk.co.barbuzz.urbancanvas.ui.main.listener.EndlessRecyclerViewScrollListener;
import uk.co.barbuzz.urbancanvas.ui.presenter.MainPresenter;
import uk.co.barbuzz.urbancanvas.ui.presenter.MainView;
import uk.co.barbuzz.zigzagrecyclerview.ZigzagGridRecyclerViewAdapter;
import uk.co.barbuzz.zigzagrecyclerview.ZigzagImage;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends BaseActivity implements MainView,
        OnMapReadyCallback, ZigzagGridRecyclerViewAdapter.ZigzagListOnClickListener,
        IconSwitch.CheckedChangeListener, ValueAnimator.AnimatorUpdateListener {

    private static final int DURATION_COLOR_CHANGE_MS = 400;

    private int[] toolbarColors;
    private int[] statusBarColors;
    private ValueAnimator statusBarAnimator;
    private Interpolator contentInInterpolator;
    private Interpolator contentOutInterpolator;
    private Point revealCenter;

    private Window window;
    private View toolbar;
    private View content;
    private IconSwitch iconSwitch;
    private MainPresenter mainPresenter;
    private RecyclerView recycleview;
    private ZigzagGridRecyclerViewAdapter zigzagGridRecyclerViewAdapter;
    private MultiStateLayout multiStateView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView numberOfImagesText;
    private GoogleMap googleMap;
    private int currentPage = 1, totalImages, currentImageNum;

    private GoogleMap.OnInfoWindowClickListener infoWindowClickListener =
            new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
            String photoName = marker.getTitle();
            //TODO - implement map detail open of detail

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animator) {
        if (animator == statusBarAnimator) {
            int color = (Integer) animator.getAnimatedValue();
            window.setStatusBarColor(color);
        }
    }

    @Override
    public void onCheckChanged(Checked current) {
        updateColors(true);
        changeContentVisibility();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        mainPresenter.getImages(this, googleMap, infoWindowClickListener, currentPage);

        googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.blue_gmap_style));
        //uk center - 54.844430, -3.916004 - zoom 5.5f
        googleMap.moveCamera(CameraUpdateFactory
                .newLatLngZoom(new LatLng(51.468406, -2.589343), 11f));
    }

    @Override
    public void onUnknownError(String error) {

    }

    @Override
    public void onTimeout() {

    }

    @Override
    public void onNetworkError() {

    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public void onConnectionError() {

    }

    @Override
    public void displayImages(FlickrViewData flickrViewData) {
        totalImages = flickrViewData.getTotal();
        currentImageNum += flickrViewData.getImagesPerPage();

        //set row type so we can display last image as progressbar (except last page)
        if (currentImageNum != totalImages) {
            flickrViewData.getPhotoList().get(flickrViewData.getPhotoList().size() - 1)
                    .setImageViewType(Photo.ImageViewType.IMAGE_FOOTER_EVEN_TYPE);
        }

        //set number of images title
        numberOfImagesText.setText(
                String.format(getString(R.string.number_of_images),
                        currentImageNum, totalImages));

        //add images to RecyclerView here
        //TODO - modify library so we can add as well as set data here
        List<Photo> currentImages = (List<Photo>) zigzagGridRecyclerViewAdapter.getData();
        if (currentImages != null && currentImages.size() > 0) {
            currentImages.addAll(flickrViewData.getPhotoList());
            zigzagGridRecyclerViewAdapter.setData(currentImages);
        } else {
            zigzagGridRecyclerViewAdapter.setData(flickrViewData.getPhotoList());
        }

        swipeRefreshLayout.setRefreshing(false);
        multiStateView.setState(MultiStateLayout.State.CONTENT);
    }

    @Override
    public void onError(String errorMsg) {
        swipeRefreshLayout.setRefreshing(false);
        multiStateView.setState(MultiStateLayout.State.CONTENT);
        Snackbar.make(findViewById(android.R.id.content), errorMsg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onZigzagImageClicked(int position, ZigzagImage zigzagImage) {
        //TODO - fix shared element by implementing as library feature
        Photo photo = (Photo) zigzagImage;
        mainPresenter.onPhotoClicked(MainActivity.this, null, position, photo, null);
    }

    private void initViews() {
        mainPresenter = new MainPresenter(
                Injection.provideFlickrApiManager(),
                Schedulers.io(),
                AndroidSchedulers.mainThread());
        mainPresenter.attachView(this);

        multiStateView = findViewById(R.id.multi_state_view);
        multiStateView.setState(MultiStateLayout.State.LOADING);

        //icon switch transition setup
        window = getWindow();
        initColors();
        initAnimationRelatedFields();
        content = findViewById(R.id.content);
        toolbar = findViewById(R.id.toolbar);
        TextView title = (TextView) findViewById(R.id.toolbar_title);
        title.setText(R.string.app_name);
        iconSwitch = (IconSwitch) findViewById(R.id.icon_switch);
        iconSwitch.setCheckedChangeListener(this);
        updateColors(false);

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment fragment = (SupportMapFragment) fm.findFragmentById(R.id.map_container);
        if (fragment == null) {
            fragment = new SupportMapFragment();
            fm.beginTransaction().replace(R.id.map_container, fragment).commit();
        }
        fragment.getMapAsync(this);

        numberOfImagesText = (TextView) findViewById(R.id.number_of_images_text);

        //init recycler view of images
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycleview = (RecyclerView) findViewById(R.id.recycleview);
        zigzagGridRecyclerViewAdapter = new ZigzagGridRecyclerViewAdapter(this, null, this);
        zigzagGridRecyclerViewAdapter.setPlaceholderDrawableResId(R.drawable.graffiti_black_white);
        recycleview.setLayoutManager(linearLayoutManager);
        recycleview.setAdapter(zigzagGridRecyclerViewAdapter);
        final EndlessRecyclerViewScrollListener scrollEndlessListener =
                new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (currentImageNum < totalImages) {
                    currentPage++;
                    loadImages(currentPage);
                }
            }
        };
        recycleview.addOnScrollListener(scrollEndlessListener);

        swipeRefreshLayout = findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            zigzagGridRecyclerViewAdapter.setData(new ArrayList<>());
            currentPage = 1;
            currentImageNum = 0;
            scrollEndlessListener.resetState();
            loadImages(currentPage);
        });
    }

    private void loadImages(int page) {
        mainPresenter.getImages(this, googleMap, infoWindowClickListener, page);
    }




    /** custom toolbar methods */

    private void updateColors(boolean animated) {
        int colorIndex = iconSwitch.getChecked().ordinal();
        toolbar.setBackgroundColor(toolbarColors[colorIndex]);
        if (animated) {
            switch (iconSwitch.getChecked()) {
                case LEFT:
                    statusBarAnimator.reverse();
                    break;
                case RIGHT:
                    statusBarAnimator.start();
                    break;
            }
            revealToolbar();
        } else {
            window.setStatusBarColor(statusBarColors[colorIndex]);
        }
    }

    private void revealToolbar() {
        iconSwitch.getThumbCenter(revealCenter);
        moveFromSwitchToToolbarSpace(revealCenter);
        ViewAnimationUtils.createCircularReveal(toolbar,
                revealCenter.x, revealCenter.y,
                iconSwitch.getHeight(), toolbar.getWidth())
                .setDuration(DURATION_COLOR_CHANGE_MS)
                .start();
    }

    private void changeContentVisibility() {
        int targetTranslation = 0;
        Interpolator interpolator = null;
        switch (iconSwitch.getChecked()) {
            case LEFT:
                targetTranslation = 0;
                interpolator = contentInInterpolator;
                break;
            case RIGHT:
                targetTranslation = content.getHeight();
                interpolator = contentOutInterpolator;
                break;
        }
        content.animate().cancel();
        content.animate()
                .translationY(targetTranslation)
                .setInterpolator(interpolator)
                .setDuration(DURATION_COLOR_CHANGE_MS)
                .start();
    }

    private void initAnimationRelatedFields() {
        revealCenter = new Point();
        statusBarAnimator = createArgbAnimator(
                statusBarColors[Checked.LEFT.ordinal()],
                statusBarColors[Checked.RIGHT.ordinal()]);
        contentInInterpolator = new OvershootInterpolator(1f);
        contentOutInterpolator = new DecelerateInterpolator();
    }

    private void initColors() {
        toolbarColors = new int[Checked.values().length];
        statusBarColors = new int[toolbarColors.length];
        toolbarColors[Checked.LEFT.ordinal()] = color(R.color.informationPrimary);
        statusBarColors[Checked.LEFT.ordinal()] = color(R.color.informationPrimaryDark);
        toolbarColors[Checked.RIGHT.ordinal()] = color(R.color.mapPrimary);
        statusBarColors[Checked.RIGHT.ordinal()] = color(R.color.mapPrimaryDark);
    }

    private ValueAnimator createArgbAnimator(int leftColor, int rightColor) {
        ValueAnimator animator = ValueAnimator.ofArgb(leftColor, rightColor);
        animator.setDuration(DURATION_COLOR_CHANGE_MS);
        animator.addUpdateListener(this);
        return animator;
    }

    private void moveFromSwitchToToolbarSpace(Point point) {
        point.set(point.x + iconSwitch.getLeft(), point.y + iconSwitch.getTop());
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

}
