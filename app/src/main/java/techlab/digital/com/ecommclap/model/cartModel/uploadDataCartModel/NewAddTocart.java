package techlab.digital.com.ecommclap.model.cartModel.uploadDataCartModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewAddTocart {

    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("quantity")
    @Expose
    private String quantity;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
