package techlab.digital.com.ecommclap.model.cartModel.uploadDataCartModel;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddToCartResponse {

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

}