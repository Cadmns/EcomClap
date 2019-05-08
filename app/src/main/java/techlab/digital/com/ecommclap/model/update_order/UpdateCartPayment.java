package techlab.digital.com.ecommclap.model.update_order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateCartPayment {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("transaction_id")
    @Expose
    private String transaction_id;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }
}
