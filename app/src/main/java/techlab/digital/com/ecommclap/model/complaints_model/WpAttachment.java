package techlab.digital.com.ecommclap.model.complaints_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WpAttachment {
    @SerializedName("embeddable")
    @Expose
    private Boolean embeddable;
    @SerializedName("href")
    @Expose
    private String href;

    public Boolean getEmbeddable() {
        return embeddable;
    }

    public void setEmbeddable(Boolean embeddable) {
        this.embeddable = embeddable;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}