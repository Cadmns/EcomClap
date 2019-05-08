package techlab.digital.com.ecommclap.model.complaints_model.add_complain_replies;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Content {
    @SerializedName("raw")
    @Expose
    private String raw;
    @SerializedName("rendered")
    @Expose
    private String rendered;
    @SerializedName("protected")
    @Expose
    private Boolean _protected;
    @SerializedName("block_version")
    @Expose
    private Integer blockVersion;

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getRendered() {
        return rendered;
    }

    public void setRendered(String rendered) {
        this.rendered = rendered;
    }

    public Boolean getProtected() {
        return _protected;
    }

    public void setProtected(Boolean _protected) {
        this._protected = _protected;
    }

    public Integer getBlockVersion() {
        return blockVersion;
    }

    public void setBlockVersion(Integer blockVersion) {
        this.blockVersion = blockVersion;
    }

}
