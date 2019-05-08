package techlab.digital.com.ecommclap.model.cartModel.FetechCart;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FetchCartResponse implements Parcelable {

    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("variation_id")
    @Expose
    private Integer variationId;
    @SerializedName("variation")
    @Expose
    private List<Object> variation = null;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("data_hash")
    @Expose
    private String dataHash;
    @SerializedName("line_tax_data")
    @Expose
    private LineTaxData lineTaxData;
    @SerializedName("line_subtotal")
    @Expose
    private Integer lineSubtotal;
    @SerializedName("line_subtotal_tax")
    @Expose
    private Integer lineSubtotalTax;
    @SerializedName("line_total")
    @Expose
    private Integer lineTotal;
    @SerializedName("line_tax")
    @Expose
    private Integer lineTax;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("product_name")
    @Expose
    private String productName;

    protected FetchCartResponse(Parcel in) {
        key = in.readString();
        if (in.readByte() == 0) {
            productId = null;
        } else {
            productId = in.readInt();
        }
        if (in.readByte() == 0) {
            variationId = null;
        } else {
            variationId = in.readInt();
        }
        if (in.readByte() == 0) {
            quantity = null;
        } else {
            quantity = in.readInt();
        }
        dataHash = in.readString();
        lineTaxData = in.readParcelable(LineTaxData.class.getClassLoader());
        if (in.readByte() == 0) {
            lineSubtotal = null;
        } else {
            lineSubtotal = in.readInt();
        }
        if (in.readByte() == 0) {
            lineSubtotalTax = null;
        } else {
            lineSubtotalTax = in.readInt();
        }
        if (in.readByte() == 0) {
            lineTotal = null;
        } else {
            lineTotal = in.readInt();
        }
        if (in.readByte() == 0) {
            lineTax = null;
        } else {
            lineTax = in.readInt();
        }
        data = in.readParcelable(Data.class.getClassLoader());
        productName = in.readString();
        productImage = in.readString();
    }

    public static final Creator<FetchCartResponse> CREATOR = new Creator<FetchCartResponse>() {
        @Override
        public FetchCartResponse createFromParcel(Parcel in) {
            return new FetchCartResponse(in);
        }

        @Override
        public FetchCartResponse[] newArray(int size) {
            return new FetchCartResponse[size];
        }
    };

    public String getProductImage() {
        return this.productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    @SerializedName("product_image")
    @Expose
    private String productImage;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getVariationId() {
        return variationId;
    }

    public void setVariationId(Integer variationId) {
        this.variationId = variationId;
    }

    public List<Object> getVariation() {
        return variation;
    }

    public void setVariation(List<Object> variation) {
        this.variation = variation;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDataHash() {
        return dataHash;
    }

    public void setDataHash(String dataHash) {
        this.dataHash = dataHash;
    }

    public LineTaxData getLineTaxData() {
        return lineTaxData;
    }

    public void setLineTaxData(LineTaxData lineTaxData) {
        this.lineTaxData = lineTaxData;
    }

    public Integer getLineSubtotal() {
        return lineSubtotal;
    }

    public void setLineSubtotal(Integer lineSubtotal) {
        this.lineSubtotal = lineSubtotal;
    }

    public Integer getLineSubtotalTax() {
        return lineSubtotalTax;
    }

    public void setLineSubtotalTax(Integer lineSubtotalTax) {
        this.lineSubtotalTax = lineSubtotalTax;
    }

    public Integer getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(Integer lineTotal) {
        this.lineTotal = lineTotal;
    }

    public Integer getLineTax() {
        return lineTax;
    }

    public void setLineTax(Integer lineTax) {
        this.lineTax = lineTax;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        if (productId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(productId);
        }
        if (variationId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(variationId);
        }
        if (quantity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(quantity);
        }
        dest.writeString(dataHash);
        dest.writeParcelable(lineTaxData, flags);
        if (lineSubtotal == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(lineSubtotal);
        }
        if (lineSubtotalTax == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(lineSubtotalTax);
        }
        if (lineTotal == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(lineTotal);
        }
        if (lineTax == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(lineTax);
        }
        dest.writeParcelable(data, flags);
        dest.writeString(productName);
        dest.writeString(productImage);
    }
}