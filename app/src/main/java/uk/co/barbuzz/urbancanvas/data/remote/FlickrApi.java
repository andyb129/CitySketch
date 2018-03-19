package uk.co.barbuzz.urbancanvas.data.remote;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import uk.co.barbuzz.urbancanvas.data.model.FlickrData;

/**
 * Created by andy.barber on 16/06/2017.
 */

public interface FlickrApi {
        @GET("rest/")
        Observable<FlickrData> getImages(@Query("method") String method,
                                         @Query("api_key") String apiKey,
                                         @Query("tags") String tags,
                                         @Query("tag_mode") String tagsMode,
                                         @Query("per_page") int numImages,
                                         @Query("media") String media,
                                         @Query("extras") String extras,
                                         @Query("has_geo") String hasGeo,
                                         @Query("format") String format,
                                         @Query("nojsoncallback") String noJsonCallback,
                                         @Query("page") int page);

        @GET
        Observable<ResponseBody> getImageThumbnail(@Url String url);
}
