package techlab.digital.com.ecommclap.model.cartModel.FetechCart;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Parcelable {

    @SerializedName("product_type")
    @Expose
    private String productType;
    @SerializedName("product_custom_fields")
    @Expose
    private ProductCustomFields productCustomFields;

    protected Data(Parcel in) {
        productType = in.readString();
        productCustomFields = in.readParcelable(ProductCustomFields.class.getClassLoader());
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public ProductCustomFields getProductCustomFields() {
        return productCustomFields;
    }

    public void setProductCustomFields(ProductCustomFields productCustomFields) {
        this.productCustomFields = productCustomFields;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productType);
        dest.writeParcelable(productCustomFields, flags);
    }
}