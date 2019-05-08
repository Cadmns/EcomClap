package techlab.digital.com.ecommclap.model.cartModel.uploadDataCartModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddToCartReq {


    @SerializedName("product_id")
    @Expose
    private String product_id;



    public AddToCartReq(String product_id, String quantity) {

        this.product_id = product_id;
        this.quantity = quantity;
    }



    @SerializedName("quantity")
    @Expose
    private String quantity;

    public String getProduct_id() {
        return this.product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getQuantity() {
        return this.quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
