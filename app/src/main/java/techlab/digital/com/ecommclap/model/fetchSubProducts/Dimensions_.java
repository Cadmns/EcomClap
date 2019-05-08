package techlab.digital.com.ecommclap.model.fetchSubProducts;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dimensions_ implements Parcelable {

    @SerializedName("length")
    @Expose
    private String length;
    @SerializedName("width")
    @Expose
    private String width;
    @SerializedName("height")
    @Expose
    private String height;

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.length);
        dest.writeString(this.width);
        dest.writeString(this.height);
    }

    public Dimensions_() {
    }

    protected Dimensions_(Parcel in) {
        this.length = in.readString();
        this.width = in.readString();
        this.height = in.readString();
    }

    public static final Parcelable.Creator<Dimensions_> CREATOR = new Parcelable.Creator<Dimensions_>() {
        @Override
        public Dimensions_ createFromParcel(Parcel source) {
            return new Dimensions_(source);
        }

        @Override
        public Dimensions_[] newArray(int size) {
            return new Dimensions_[size];
        }
    };
}