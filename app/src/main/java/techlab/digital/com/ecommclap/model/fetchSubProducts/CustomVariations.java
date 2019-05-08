package techlab.digital.com.ecommclap.model.fetchSubProducts;


import android.os.Parcel;
import android.os.Parcelable;

public class CustomVariations implements Parcelable {
    private String price,regular_price,weight,name,option;
    private Boolean in_stock;
    private int id;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRegular_price() {
        return regular_price;
    }

    public void setRegular_price(String regular_price) {
        this.regular_price = regular_price;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
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

    public Boolean getIn_stock() {
        return in_stock;
    }

    public void setIn_stock(Boolean in_stock) {
        this.in_stock = in_stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.price);
        dest.writeString(this.regular_price);
        dest.writeString(this.weight);
        dest.writeString(this.name);
        dest.writeString(this.option);
        dest.writeValue(this.in_stock);
        dest.writeInt(this.id);
    }

    public CustomVariations() {
    }

    protected CustomVariations(Parcel in) {
        this.price = in.readString();
        this.regular_price = in.readString();
        this.weight = in.readString();
        this.name = in.readString();
        this.option = in.readString();
        this.in_stock = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.id = in.readInt();
    }

    public static final Parcelable.Creator<CustomVariations> CREATOR = new Parcelable.Creator<CustomVariations>() {
        @Override
        public CustomVariations createFromParcel(Parcel source) {
            return new CustomVariations(source);
        }

        @Override
        public CustomVariations[] newArray(int size) {
            return new CustomVariations[size];
        }
    };
}
