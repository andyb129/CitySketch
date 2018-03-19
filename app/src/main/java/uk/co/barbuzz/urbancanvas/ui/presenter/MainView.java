package uk.co.barbuzz.urbancanvas.ui.presenter;

import uk.co.barbuzz.urbancanvas.data.model.FlickrViewData;
import uk.co.barbuzz.urbancanvas.ui.base.BaseView;

public interface MainView extends BaseView {

    void displayImages(FlickrViewData flickrViewData);

    void onError(String errorMsg);
}
