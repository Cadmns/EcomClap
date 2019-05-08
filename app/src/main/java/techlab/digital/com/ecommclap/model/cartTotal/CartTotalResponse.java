package techlab.digital.com.ecommclap.model.cartTotal;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartTotalResponse {

    @SerializedName("subtotal")
    @Expose
    private String subtotal;
    @SerializedName("subtotal_tax")
    @Expose
    private Integer subtotalTax;
    @SerializedName("shipping_total")
    @Expose
    private String shippingTotal;
    @SerializedName("shipping_tax")
    @Expose
    private Integer shippingTax;
    @SerializedName("shipping_taxes")
    @Expose
    private List<Object> shippingTaxes = null;
    @SerializedName("discount_total")
    @Expose
    private Integer discountTotal;
    @SerializedName("discount_tax")
    @Expose
    private Integer discountTax;
    @SerializedName("cart_contents_total")
    @Expose
    private String cartContentsTotal;
    @SerializedName("cart_contents_tax")
    @Expose
    private Integer cartContentsTax;
    @SerializedName("cart_contents_taxes")
    @Expose
    private List<Object> cartContentsTaxes = null;
    @SerializedName("fee_total")
    @Expose
    private String feeTotal;
    @SerializedName("fee_tax")
    @Expose
    private Integer feeTax;
    @SerializedName("fee_taxes")
    @Expose
    private List<Object> feeTaxes = null;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("total_tax")
    @Expose
    private Integer totalTax;

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public Integer getSubtotalTax() {
        return subtotalTax;
    }

    public void setSubtotalTax(Integer subtotalTax) {
        this.subtotalTax = subtotalTax;
    }

    public String getShippingTotal() {
        return shippingTotal;
    }

    public void setShippingTotal(String shippingTotal) {
        this.shippingTotal = shippingTotal;
    }

    public Integer getShippingTax() {
        return shippingTax;
    }

    public void setShippingTax(Integer shippingTax) {
        this.shippingTax = shippingTax;
    }

    public List<Object> getShippingTaxes() {
        return shippingTaxes;
    }

    public void setShippingTaxes(List<Object> shippingTaxes) {
        this.shippingTaxes = shippingTaxes;
    }

    public Integer getDiscountTotal() {
        return discountTotal;
    }

    public void setDiscountTotal(Integer discountTotal) {
        this.discountTotal = discountTotal;
    }

    public Integer getDiscountTax() {
        return discountTax;
    }

    public void setDiscountTax(Integer discountTax) {
        this.discountTax = discountTax;
    }

    public String getCartContentsTotal() {
        return cartContentsTotal;
    }

    public void setCartContentsTotal(String cartContentsTotal) {
        this.cartContentsTotal = cartContentsTotal;
    }

    public Integer getCartContentsTax() {
        return cartContentsTax;
    }

    public void setCartContentsTax(Integer cartContentsTax) {
        this.cartContentsTax = cartContentsTax;
    }

    public List<Object> getCartContentsTaxes() {
        return cartContentsTaxes;
    }

    public void setCartContentsTaxes(List<Object> cartContentsTaxes) {
        this.cartContentsTaxes = cartContentsTaxes;
    }

    public String getFeeTotal() {
        return feeTotal;
    }

    public void setFeeTotal(String feeTotal) {
        this.feeTotal = feeTotal;
    }

    public Integer getFeeTax() {
        return feeTax;
    }

    public void setFeeTax(Integer feeTax) {
        this.feeTax = feeTax;
    }

    public List<Object> getFeeTaxes() {
        return feeTaxes;
    }

    public void setFeeTaxes(List<Object> feeTaxes) {
        this.feeTaxes = feeTaxes;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Integer getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(Integer totalTax) {
        this.totalTax = totalTax;
    }

}