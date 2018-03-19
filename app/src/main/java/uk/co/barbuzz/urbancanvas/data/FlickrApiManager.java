package uk.co.barbuzz.urbancanvas.data;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import uk.co.barbuzz.urbancanvas.data.model.FlickrViewData;
import uk.co.barbuzz.urbancanvas.data.model.Photo;
import uk.co.barbuzz.urbancanvas.data.remote.FlickrApi;

/**
 * Created by andyb129 on 10/07/2017.
 */

public class FlickrApiManager {

    private static final String SEARCH_METHOD = "flickr.photos.search";
    private static final String API_KEY = "ce18a2bd84252f4bd0ab175dd483235e";
    private static final String FORMAT = "json";
    private static final String MEDIA = "photos";
    private static final String HAS_GEO = "1";
    private static final String NOJSONCALLBACKFUNCTION = "1";
    private static final String TAGMODE = "all";
    private static final String EXTRAS = "description, date_upload, date_taken, owner_name, " +
            "last_update, geo, tags, views, media";

    private FlickrApi mFlickrApi;

    public FlickrApiManager(FlickrApi flickrApi) {
        this.mFlickrApi = flickrApi;
    }

    /**
     * method to return flickr image data
     * @param tags
     * @param resultsPerPage
     * @return
     */
    public Observable<FlickrViewData> getFlickrImages(String tags, int resultsPerPage, int page) {
        return mFlickrApi.getImages(SEARCH_METHOD, API_KEY, tags, TAGMODE, resultsPerPage,
                MEDIA, EXTRAS, HAS_GEO, FORMAT, NOJSONCALLBACKFUNCTION, page)
                .map(flickrData -> FlickrViewData.getFlickrViewData(flickrData));
    }

    /**
     * load individual image from flickr photo
     * @param photo
     * @return
     */
    public Observable<ResponseBody> getFlickrImage(Photo photo) {
        String url = photo.getPhotoURL(Photo.PHOTO_THUMB);
        return mFlickrApi.getImageThumbnail(url);
    }
}
