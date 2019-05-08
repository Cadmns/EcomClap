package techlab.digital.com.ecommclap.model.fetchSubProducts;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Variation implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;
    @SerializedName("date_modified")
    @Expose
    private String dateModified;
    @SerializedName("permalink")
    @Expose
    private String permalink;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("regular_price")
    @Expose
    private String regularPrice;
    @SerializedName("sale_price")
    @Expose
    private String salePrice;
    @SerializedName("date_on_sale_from")
    @Expose
    private String dateOnSaleFrom;
    @SerializedName("date_on_sale_to")
    @Expose
    private String dateOnSaleTo;
    @SerializedName("on_sale")
    @Expose
    private Boolean onSale;
    @SerializedName("purchasable")
    @Expose
    private Boolean purchasable;
    @SerializedName("visible")
    @Expose
    private Boolean visible;
    @SerializedName("virtual")
    @Expose
    private Boolean virtual;
    @SerializedName("downloadable")
    @Expose
    private Boolean downloadable;
    @SerializedName("downloads")
    @Expose
    private List<Object> downloads = null;
    @SerializedName("download_limit")
    @Expose
    private Integer downloadLimit;
    @SerializedName("download_expiry")
    @Expose
    private Integer downloadExpiry;
    @SerializedName("tax_status")
    @Expose
    private String taxStatus;
    @SerializedName("tax_class")
    @Expose
    private String taxClass;
    @SerializedName("manage_stock")
    @Expose
    private Boolean manageStock;
    @SerializedName("stock_quantity")
    @Expose
    private Object stockQuantity;
    @SerializedName("in_stock")
    @Expose
    private Boolean inStock;
    @SerializedName("backorders")
    @Expose
    private String backorders;
    @SerializedName("backorders_allowed")
    @Expose
    private Boolean backordersAllowed;
    @SerializedName("backordered")
    @Expose
    private Boolean backordered;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("dimensions")
    @Expose
    private Dimensions_ dimensions;
    @SerializedName("shipping_class")
    @Expose
    private String shippingClass;
    @SerializedName("shipping_class_id")
    @Expose
    private Integer shippingClassId;
    @SerializedName("image")
    @Expose
    private List<Image_> image = null;
    @SerializedName("attributes")
    @Expose
    private List<Attribute_> attributes = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(String regularPrice) {
        this.regularPrice = regularPrice;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getDateOnSaleFrom() {
        return dateOnSaleFrom;
    }

    public void setDateOnSaleFrom(String dateOnSaleFrom) {
        this.dateOnSaleFrom = dateOnSaleFrom;
    }

    public String getDateOnSaleTo() {
        return dateOnSaleTo;
    }

    public void setDateOnSaleTo(String dateOnSaleTo) {
        this.dateOnSaleTo = dateOnSaleTo;
    }

    public Boolean getOnSale() {
        return onSale;
    }

    public void setOnSale(Boolean onSale) {
        this.onSale = onSale;
    }

    public Boolean getPurchasable() {
        return purchasable;
    }

    public void setPurchasable(Boolean purchasable) {
        this.purchasable = purchasable;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Boolean getVirtual() {
        return virtual;
    }

    public void setVirtual(Boolean virtual) {
        this.virtual = virtual;
    }

    public Boolean getDownloadable() {
        return downloadable;
    }

    public void setDownloadable(Boolean downloadable) {
        this.downloadable = downloadable;
    }

    public List<Object> getDownloads() {
        return downloads;
    }

    public void setDownloads(List<Object> downloads) {
        this.downloads = downloads;
    }

    public Integer getDownloadLimit() {
        return downloadLimit;
    }

    public void setDownloadLimit(Integer downloadLimit) {
        this.downloadLimit = downloadLimit;
    }

    public Integer getDownloadExpiry() {
        return downloadExpiry;
    }

    public void setDownloadExpiry(Integer downloadExpiry) {
        this.downloadExpiry = downloadExpiry;
    }

    public String getTaxStatus() {
        return taxStatus;
    }

    public void setTaxStatus(String taxStatus) {
        this.taxStatus = taxStatus;
    }

    public String getTaxClass() {
        return taxClass;
    }

    public void setTaxClass(String taxClass) {
        this.taxClass = taxClass;
    }

    public Boolean getManageStock() {
        return manageStock;
    }

    public void setManageStock(Boolean manageStock) {
        this.manageStock = manageStock;
    }

    public Object getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Object stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    public String getBackorders() {
        return backorders;
    }

    public void setBackorders(String backorders) {
        this.backorders = backorders;
    }

    public Boolean getBackordersAllowed() {
        return backordersAllowed;
    }

    public void setBackordersAllowed(Boolean backordersAllowed) {
        this.backordersAllowed = backordersAllowed;
    }

    public Boolean getBackordered() {
        return backordered;
    }

    public void setBackordered(Boolean backordered) {
        this.backordered = backordered;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Dimensions_ getDimensions() {
        return dimensions;
    }

    public void setDimensions(Dimensions_ dimensions) {
        this.dimensions = dimensions;
    }

    public String getShippingClass() {
        return shippingClass;
    }

    public void setShippingClass(String shippingClass) {
        this.shippingClass = shippingClass;
    }

    public Integer getShippingClassId() {
        return shippingClassId;
    }

    public void setShippingClassId(Integer shippingClassId) {
        this.shippingClassId = shippingClassId;
    }

    public List<Image_> getImage() {
        return image;
    }

    public void setImage(List<Image_> image) {
        this.image = image;
    }

    public List<Attribute_> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute_> attributes) {
        this.attributes = attributes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.dateCreated);
        dest.writeString(this.dateModified);
        dest.writeString(this.permalink);
        dest.writeString(this.sku);
        dest.writeString(this.price);
        dest.writeString(this.regularPrice);
        dest.writeString(this.salePrice);
        dest.writeString(this.dateOnSaleFrom);
        dest.writeString(this.dateOnSaleTo);
        dest.writeValue(this.onSale);
        dest.writeValue(this.purchasable);
        dest.writeValue(this.visible);
        dest.writeValue(this.virtual);
        dest.writeValue(this.downloadable);
        dest.writeList(this.downloads);
        dest.writeValue(this.downloadLimit);
        dest.writeValue(this.downloadExpiry);
        dest.writeString(this.taxStatus);
        dest.writeString(this.taxClass);
        dest.writeValue(this.manageStock);
        dest.writeParcelable((Parcelable) this.stockQuantity, flags);
        dest.writeValue(this.inStock);
        dest.writeString(this.backorders);
        dest.writeValue(this.backordersAllowed);
        dest.writeValue(this.backordered);
        dest.writeString(this.weight);
        dest.writeParcelable(this.dimensions, flags);
        dest.writeString(this.shippingClass);
        dest.writeValue(this.shippingClassId);
        dest.writeList(this.image);
        dest.writeList(this.attributes);
    }

    public Variation() {
    }

    protected Variation(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.dateCreated = in.readString();
        this.dateModified = in.readString();
        this.permalink = in.readString();
        this.sku = in.readString();
        this.price = in.readString();
        this.regularPrice = in.readString();
        this.salePrice = in.readString();
        this.dateOnSaleFrom = in.readString();
        this.dateOnSaleTo = in.readString();
        this.onSale = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.purchasable = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.visible = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.virtual = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.downloadable = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.downloads = new ArrayList<Object>();
        in.readList(this.downloads, Object.class.getClassLoader());
        this.downloadLimit = (Integer) in.readValue(Integer.class.getClassLoader());
        this.downloadExpiry = (Integer) in.readValue(Integer.class.getClassLoader());
        this.taxStatus = in.readString();
        this.taxClass = in.readString();
        this.manageStock = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.stockQuantity = in.readParcelable(Object.class.getClassLoader());
        this.inStock = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.backorders = in.readString();
        this.backordersAllowed = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.backordered = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.weight = in.readString();
        this.dimensions = in.readParcelable(Dimensions_.class.getClassLoader());
        this.shippingClass = in.readString();
        this.shippingClassId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.image = new ArrayList<Image_>();
        in.readList(this.image, Image_.class.getClassLoader());
        this.attributes = new ArrayList<Attribute_>();
        in.readList(this.attributes, Attribute_.class.getClassLoader());
    }

    public static final Parcelable.Creator<Variation> CREATOR = new Parcelable.Creator<Variation>() {
        @Override
        public Variation createFromParcel(Parcel source) {
            return new Variation(source);
        }

        @Override
        public Variation[] newArray(int size) {
            return new Variation[size];
        }
    };
}