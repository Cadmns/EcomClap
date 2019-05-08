package techlab.digital.com.ecommclap.model.walletDeduction;

import com.google.gson.annotations.SerializedName;

public class WalletDeductedResponse {

    @SerializedName("response")
    String response;

    @SerializedName("id")
    String id;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
