package uk.co.barbuzz.urbancanvas.data.model;

/**
 * Created by 1andbarb on 13/10/2015.
 */

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * class to represent a cluster item on the map
 */
public class PhotoClusterItem implements ClusterItem {
    private final LatLng position;
    private final String photoId;
    private final String photoName;
    private final String owner;
    private final String photoThumbUrl;

    public PhotoClusterItem(String photoId, String photoName, String owner, double lat, double lng,
                            String photoThumbUrl) {
        this.photoId = photoId;
        this.photoName = photoName;
        this.owner = owner;
        this.photoThumbUrl = photoThumbUrl;
        this.position = new LatLng(lat, lng);
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    @Override
    public String getTitle() {
        return photoName;
    }

    @Override
    public String getSnippet() {
        return "Owner: " + owner;
    }

    public String getPhotoId() {
        return photoId;
    }

    public String getPhotoName() {
        return photoName;
    }

    public String getPhotoThumbUrl() {
        return photoThumbUrl;
    }

}
