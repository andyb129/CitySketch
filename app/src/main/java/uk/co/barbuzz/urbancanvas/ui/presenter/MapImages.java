package uk.co.barbuzz.urbancanvas.ui.presenter;

import android.graphics.drawable.BitmapDrawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy.barber on 07/02/2018.
 */

class MapImages {

    private List<BitmapDrawable> mapBitmapDrawablesList = new ArrayList<>();
    private int indexCount;

    public List<BitmapDrawable> getMapBitmapDrawablesList() {
        return mapBitmapDrawablesList;
    }

    public void setMapBitmapDrawablesList(List<BitmapDrawable> mapBitmapDrawablesList) {
        this.mapBitmapDrawablesList = mapBitmapDrawablesList;
    }

    public int getIndexCount() {
        return indexCount;
    }

    public void setIndexCount(int indexCount) {
        this.indexCount = indexCount;
    }
}
