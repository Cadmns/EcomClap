package techlab.digital.com.ecommclap.model.cartModel.uploadDataCartModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddToCartWithVariationReq extends AddToCartReq {
    @SerializedName("variation_id")
    @Expose
    private String variations_id;

    public String getVariations_id() {
        return variations_id;
    }

    public void setVariations_id(String variations_id) {
        this.variations_id = variations_id;
    }

    public AddToCartWithVariationReq(String product_id, String variations_id, String quantity) {
        super(product_id, quantity);
        this.variations_id = variations_id;
    }
}
