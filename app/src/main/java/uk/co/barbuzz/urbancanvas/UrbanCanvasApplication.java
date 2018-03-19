package uk.co.barbuzz.urbancanvas;

import android.app.Application;

import cn.refactor.multistatelayout.MultiStateConfiguration;
import cn.refactor.multistatelayout.MultiStateLayout;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by andy.barber on 14/12/2017.
 */

public class UrbanCanvasApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Chunkfive, Pacifico.ttf, FFF_Tusj.ttf, Capture_it.ttf
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Chunkfive.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        //multi-state layout config
        MultiStateConfiguration.Builder builder = new MultiStateConfiguration.Builder();
        builder.setCommonEmptyLayout(R.layout.layout_empty)
                .setCommonErrorLayout(R.layout.layout_error)
                .setCommonLoadingLayout(R.layout.layout_loading);
        MultiStateLayout.setConfiguration(builder);
    }
}
