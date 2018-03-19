package uk.co.barbuzz.urbancanvas.injection;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.barbuzz.urbancanvas.BuildConfig;
import uk.co.barbuzz.urbancanvas.data.FlickrApiManager;
import uk.co.barbuzz.urbancanvas.data.remote.FlickrApi;

public class Injection {

    public static String BASE_URL = "https://api.flickr.com/services/";
    public static final String FLICKR_SEARCH_KEYWORDS = "bristol,grafitti";
    public static final int RESULTS_PER_PAGE = 30;

    private static OkHttpClient okHttpClient;
    private static Retrofit retrofitInstance;
    private static FlickrApi flickrApi;

    public static FlickrApiManager provideFlickrApiManager() {
        return new FlickrApiManager(provideFlickrApi());
    }

    static FlickrApi provideFlickrApi() {
        if (flickrApi == null) {
            flickrApi = getRetrofitInstance().create(FlickrApi.class);
        }
        return flickrApi;
    }

    static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            if (BuildConfig.DEBUG) {
                okHttpClientBuilder.addInterceptor(interceptor);
            }
            okHttpClient = okHttpClientBuilder.build();
        }

        return okHttpClient;
    }

    static Retrofit getRetrofitInstance() {
        if (retrofitInstance == null) {
            Retrofit.Builder retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getOkHttpClient())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());
            retrofitInstance = retrofit.build();

        }
        return retrofitInstance;
    }
}
