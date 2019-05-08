package techlab.digital.com.ecommclap.model.complaints_model.get_complain_replies;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Collection {
    @SerializedName("href")
    @Expose
    private String href;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
