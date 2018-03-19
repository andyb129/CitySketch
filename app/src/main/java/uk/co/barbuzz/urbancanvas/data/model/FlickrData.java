package uk.co.barbuzz.urbancanvas.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FlickrData implements Parcelable {

    private Photos photos;
    private String stat;

    public Photos getPhotos() {
        return photos;
    }

    public void setPhotos(Photos photos) {
        this.photos = photos;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.photos, flags);
        dest.writeString(this.stat);
    }

    public FlickrData() {
    }

    protected FlickrData(Parcel in) {
        this.photos = in.readParcelable(Photos.class.getClassLoader());
        this.stat = in.readString();
    }

    public static final Creator<FlickrData> CREATOR = new Creator<FlickrData>() {
        @Override
        public FlickrData createFromParcel(Parcel source) {
            return new FlickrData(source);
        }

        @Override
        public FlickrData[] newArray(int size) {
            return new FlickrData[size];
        }
    };
}
