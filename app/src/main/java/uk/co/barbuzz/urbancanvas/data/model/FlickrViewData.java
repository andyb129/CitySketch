package uk.co.barbuzz.urbancanvas.data.model;

import java.util.List;

import uk.co.barbuzz.urbancanvas.data.BaseResponse;

/**
 * Created by andy.barber on 22/01/2018.
 */

public class FlickrViewData extends BaseResponse {

    private int imagesPerPage;
    private int total;
    private int pages;
    private List<Photo> photoList;

    public static FlickrViewData getFlickrViewData(FlickrData flickrData) {
        FlickrViewData flickrViewData = new FlickrViewData();
        flickrViewData.imagesPerPage = flickrData.getPhotos().getPerpage();
        flickrViewData.total = Integer.valueOf(flickrData.getPhotos().getTotal());
        flickrViewData.pages = flickrData.getPhotos().getPages();
        flickrViewData.photoList = flickrData.getPhotos().getPhoto();
        return flickrViewData;
    }

    public int getImagesPerPage() {
        return imagesPerPage;
    }

    public void setImagesPerPage(int imagesPerPage) {
        this.imagesPerPage = imagesPerPage;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Photo> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<Photo> photoList) {
        this.photoList = photoList;
    }

}
