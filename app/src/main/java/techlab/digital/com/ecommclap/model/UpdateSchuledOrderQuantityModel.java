package techlab.digital.com.ecommclap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateSchuledOrderQuantityModel {
    @SerializedName("product_id")
    @Expose
    private String product_id;

    @SerializedName("order_id")
    @Expose
    private String order_id;

    @SerializedName("quantity")
    @Expose
    private String quantity;


    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}

