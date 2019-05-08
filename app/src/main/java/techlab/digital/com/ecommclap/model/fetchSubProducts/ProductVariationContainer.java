package techlab.digital.com.ecommclap.model.fetchSubProducts;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

public class ProductVariationContainer implements Parcelable {

    private CustomVariations variations;
    private HashMap<String,CustomVariations> quantity_attributes;

    public CustomVariations getVariations() {
        return variations;
    }

    public void setVariations(CustomVariations variations) {
        this.variations = variations;
    }

    public HashMap<String, CustomVariations> getQuantity_attributes() {
        return quantity_attributes;
    }

    public void setQuantity_attributes(HashMap<String, CustomVariations> quantity_attributes) {
        this.quantity_attributes = quantity_attributes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.variations, flags);
        dest.writeMap(quantity_attributes);
    }

    public ProductVariationContainer() {
    }

    protected ProductVariationContainer(Parcel in) {
        this.variations = in.readParcelable(CustomVariations.class.getClassLoader());
        quantity_attributes = new HashMap<String, CustomVariations>();
        in.readMap(quantity_attributes,CustomVariations.class.getClassLoader());
    }

    public static final Parcelable.Creator<ProductVariationContainer> CREATOR = new Parcelable.Creator<ProductVariationContainer>() {
        @Override
        public ProductVariationContainer createFromParcel(Parcel source) {
            return new ProductVariationContainer(source);
        }

        @Override
        public ProductVariationContainer[] newArray(int size) {
            return new ProductVariationContainer[size];
        }
    };
}
