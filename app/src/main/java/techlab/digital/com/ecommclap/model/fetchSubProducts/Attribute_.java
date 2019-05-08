package techlab.digital.com.ecommclap.model.fetchSubProducts;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attribute_ implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("option")
    @Expose
    private String option;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.option);
    }

    public Attribute_() {
    }

    protected Attribute_(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.option = in.readString();
    }

    public static final Parcelable.Creator<Attribute_> CREATOR = new Parcelable.Creator<Attribute_>() {
        @Override
        public Attribute_ createFromParcel(Parcel source) {
            return new Attribute_(source);
        }

        @Override
        public Attribute_[] newArray(int size) {
            return new Attribute_[size];
        }
    };
}