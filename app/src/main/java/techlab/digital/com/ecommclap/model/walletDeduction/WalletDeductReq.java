package techlab.digital.com.ecommclap.model.walletDeduction;

import com.google.gson.annotations.SerializedName;

public class WalletDeductReq {

    @SerializedName("type")
    String type;

    @SerializedName("amount")
    String amount;

    @SerializedName("details")
    String details;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
