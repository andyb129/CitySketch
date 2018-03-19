package uk.co.barbuzz.urbancanvas.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import uk.co.barbuzz.zigzagrecyclerview.ZigzagImage;

public class Photo implements Parcelable, ZigzagImage {

    public enum ImageViewType {
        IMAGE_VIEW_ODD_TYPE(0),
        IMAGE_VIEW_EVEN_TYPE(1),
        IMAGE_FOOTER_EVEN_TYPE(2);

        private int order;

        ImageViewType(int order) {
            this.order = order;
        }

        public int getOrder() {
            return order;
        }
    }

    public static final int PHOTO_THUMB = 111;
    public static final int PHOTO_LARGE = 222;
    private static final String STATIC = ".staticflickr.com/";
    private static final String SLASH = "/";
    private static final String UNDERSCORE = "_";
    private static final String THUMBNAIL_PREFIX = "_t";
    private static final String LARGE_PIC_PREFIX = "_z";
    private static final String JPG_EXTENSION = ".jpg";

    private String id;
    private String owner;
    private String secret;
    private String server;
    private int farm;
    private String title;
    private int ispublic;
    private int isfriend;
    private int isfamily;
    private String thumbPhotoUrl;
    private String largePhotoUrl;

    private Description description;
    private Integer accuracy;
    private String media;
    private String datetaken;
    private Integer context;
    private Integer views;
    private String place_id;
    private Double longitude;
    private Double latitude;
    private String ownername;
    private Integer woeid;
    private Integer dateupload;
    private String tags;
    private Integer lastupdate;
    private Integer datetakenunknown;

    private ImageViewType imageViewType = ImageViewType.IMAGE_VIEW_ODD_TYPE;

    public ImageViewType getImageViewType() {
        return imageViewType;
    }

    public void setImageViewType(ImageViewType imageViewType) {
        this.imageViewType = imageViewType;
    }

    @SerializedName("geo_is_public")
    @Expose
    private Integer geoIsPublic;
    @SerializedName("geo_is_contact")
    @Expose
    private Integer geoIsContact;
    @SerializedName("geo_is_family")
    @Expose
    private Integer geoIsFamily;

    //values for display in staggered grid view
    private int width;
    private int height;
    private float ratio;

    @Override
    public String getZigzagImageUrl() {
        return getLargePhotoUrl();
    }

    @Override
    public int getZigzagImageResourceId() {
        return 0;
    }

    /**
     * populate the photo urls for this photo
     */
    public void populatePhotoUrls()
    {
        thumbPhotoUrl = getPhotoURL(PHOTO_THUMB);
        largePhotoUrl = getPhotoURL(PHOTO_LARGE);
    }

    public String getPhotoURL(int photoType) {
        StringBuilder sb = new StringBuilder("http://farm");
        sb.append(farm);
        sb.append(STATIC);
        sb.append(server);
        sb.append(SLASH);
        sb.append(id);
        sb.append(UNDERSCORE);
        sb.append(secret);
        switch (photoType) {
            case PHOTO_THUMB:
                sb.append(THUMBNAIL_PREFIX);
                break;
            case PHOTO_LARGE:
                sb.append(LARGE_PIC_PREFIX);
                break;
        }
        sb.append(JPG_EXTENSION);
        return sb.toString();
    }

    public String getThumbPhotoUrl() {
        return getPhotoURL(PHOTO_THUMB);
    }

    public void setThumbPhotoUrl(String thumbPhotoUrl) {
        this.thumbPhotoUrl = thumbPhotoUrl;
    }

    public String getLargePhotoUrl() {
        return getPhotoURL(PHOTO_LARGE);
    }

    public void setLargePhotoUrl(String largePhotoUrl) {
        this.largePhotoUrl = largePhotoUrl;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getFarm() {
        return farm;
    }

    public void setFarm(int farm) {
        this.farm = farm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIspublic() {
        return ispublic;
    }

    public void setIspublic(int ispublic) {
        this.ispublic = ispublic;
    }

    public int getIsfriend() {
        return isfriend;
    }

    public void setIsfriend(int isfriend) {
        this.isfriend = isfriend;
    }

    public int getIsfamily() {
        return isfamily;
    }

    public void setIsfamily(int isfamily) {
        this.isfamily = isfamily;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public Integer getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getDatetaken() {
        return datetaken;
    }

    public void setDatetaken(String datetaken) {
        this.datetaken = datetaken;
    }

    public Integer getContext() {
        return context;
    }

    public void setContext(Integer context) {
        this.context = context;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getOwnername() {
        return ownername;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    public Integer getWoeid() {
        return woeid;
    }

    public void setWoeid(Integer woeid) {
        this.woeid = woeid;
    }

    public Integer getDateupload() {
        return dateupload;
    }

    public void setDateupload(Integer dateupload) {
        this.dateupload = dateupload;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(Integer lastupdate) {
        this.lastupdate = lastupdate;
    }

    public Integer getDatetakenunknown() {
        return datetakenunknown;
    }

    public void setDatetakenunknown(Integer datetakenunknown) {
        this.datetakenunknown = datetakenunknown;
    }

    public Integer getGeoIsPublic() {
        return geoIsPublic;
    }

    public void setGeoIsPublic(Integer geoIsPublic) {
        this.geoIsPublic = geoIsPublic;
    }

    public Integer getGeoIsContact() {
        return geoIsContact;
    }

    public void setGeoIsContact(Integer geoIsContact) {
        this.geoIsContact = geoIsContact;
    }

    public Integer getGeoIsFamily() {
        return geoIsFamily;
    }

    public void setGeoIsFamily(Integer geoIsFamily) {
        this.geoIsFamily = geoIsFamily;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.owner);
        dest.writeString(this.secret);
        dest.writeString(this.server);
        dest.writeInt(this.farm);
        dest.writeString(this.title);
        dest.writeInt(this.ispublic);
        dest.writeInt(this.isfriend);
        dest.writeInt(this.isfamily);
        dest.writeString(this.thumbPhotoUrl);
        dest.writeString(this.largePhotoUrl);
        dest.writeParcelable(this.description, 0);
        dest.writeValue(this.accuracy);
        dest.writeString(this.media);
        dest.writeString(this.datetaken);
        dest.writeValue(this.context);
        dest.writeValue(this.views);
        dest.writeString(this.place_id);
        dest.writeValue(this.longitude);
        dest.writeValue(this.latitude);
        dest.writeString(this.ownername);
        dest.writeValue(this.woeid);
        dest.writeValue(this.dateupload);
        dest.writeString(this.tags);
        dest.writeValue(this.lastupdate);
        dest.writeValue(this.datetakenunknown);
        dest.writeValue(this.geoIsPublic);
        dest.writeValue(this.geoIsContact);
        dest.writeValue(this.geoIsFamily);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeFloat(this.ratio);
    }

    public Photo() {
    }

    protected Photo(Parcel in) {
        this.id = in.readString();
        this.owner = in.readString();
        this.secret = in.readString();
        this.server = in.readString();
        this.farm = in.readInt();
        this.title = in.readString();
        this.ispublic = in.readInt();
        this.isfriend = in.readInt();
        this.isfamily = in.readInt();
        this.thumbPhotoUrl = in.readString();
        this.largePhotoUrl = in.readString();
        this.description = in.readParcelable(Description.class.getClassLoader());
        this.accuracy = (Integer) in.readValue(Integer.class.getClassLoader());
        this.media = in.readString();
        this.datetaken = in.readString();
        this.context = (Integer) in.readValue(Integer.class.getClassLoader());
        this.views = (Integer) in.readValue(Integer.class.getClassLoader());
        this.place_id = in.readString();
        this.longitude = (Double) in.readValue(Double.class.getClassLoader());
        this.latitude = (Double) in.readValue(Double.class.getClassLoader());
        this.ownername = in.readString();
        this.woeid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.dateupload = (Integer) in.readValue(Integer.class.getClassLoader());
        this.tags = in.readString();
        this.lastupdate = (Integer) in.readValue(Integer.class.getClassLoader());
        this.datetakenunknown = (Integer) in.readValue(Integer.class.getClassLoader());
        this.geoIsPublic = (Integer) in.readValue(Integer.class.getClassLoader());
        this.geoIsContact = (Integer) in.readValue(Integer.class.getClassLoader());
        this.geoIsFamily = (Integer) in.readValue(Integer.class.getClassLoader());
        this.width = in.readInt();
        this.height = in.readInt();
        this.ratio = in.readFloat();
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        public Photo createFromParcel(Parcel source) {
            return new Photo(source);
        }

        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
}
