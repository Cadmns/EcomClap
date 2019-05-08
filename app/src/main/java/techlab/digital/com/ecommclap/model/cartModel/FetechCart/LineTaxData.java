package techlab.digital.com.ecommclap.model.cartModel.FetechCart;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LineTaxData implements Parcelable {

    @SerializedName("subtotal")
    @Expose
    private List<Object> subtotal = null;
    @SerializedName("total")
    @Expose
    private List<Object> total = null;

    protected LineTaxData(Parcel in) {
    }

    public static final Creator<LineTaxData> CREATOR = new Creator<LineTaxData>() {
        @Override
        public LineTaxData createFromParcel(Parcel in) {
            return new LineTaxData(in);
        }

        @Override
        public LineTaxData[] newArray(int size) {
            return new LineTaxData[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}