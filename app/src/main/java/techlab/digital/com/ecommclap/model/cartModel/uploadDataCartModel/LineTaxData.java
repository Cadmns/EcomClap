package techlab.digital.com.ecommclap.model.cartModel.uploadDataCartModel;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LineTaxData {

    @SerializedName("subtotal")
    @Expose
    private List<Object> subtotal = null;
    @SerializedName("total")
    @Expose
    private List<Object> total = null;

    public List<Object> getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(List<Object> subtotal) {
        this.subtotal = subtotal;
    }

    public List<Object> getTotal() {
        return total;
    }

    public void setTotal(List<Object> total) {
        this.total = total;
    }

}