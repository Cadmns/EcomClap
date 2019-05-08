package techlab.digital.com.ecommclap.model.fetchSubProducts;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductListingsModeResponse implements Parcelable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("permalink")
    @Expose
    private String permalink;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;
    @SerializedName("date_modified")
    @Expose
    private String dateModified;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("featured")
    @Expose
    private Boolean featured;
    @SerializedName("catalog_visibility")
    @Expose
    private String catalogVisibility;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("short_description")
    @Expose
    private String shortDescription;
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
    @SerializedName("price_html")
    @Expose
    private String priceHtml;
    @SerializedName("on_sale")
    @Expose
    private Boolean onSale;
    @SerializedName("purchasable")
    @Expose
    private Boolean purchasable;
    @SerializedName("total_sales")
    @Expose
    private Integer totalSales;
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
    @SerializedName("download_type")
    @Expose
    private String downloadType;
    @SerializedName("external_url")
    @Expose
    private String externalUrl;
    @SerializedName("button_text")
    @Expose
    private String buttonText;
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
    @SerializedName("sold_individually")
    @Expose
    private Boolean soldIndividually;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("dimensions")
    @Expose
    private Dimensions dimensions;
    @SerializedName("shipping_required")
    @Expose
    private Boolean shippingRequired;
    @SerializedName("shipping_taxable")
    @Expose
    private Boolean shippingTaxable;
    @SerializedName("shipping_class")
    @Expose
    private String shippingClass;
    @SerializedName("shipping_class_id")
    @Expose
    private Integer shippingClassId;
    @SerializedName("reviews_allowed")
    @Expose
    private Boolean reviewsAllowed;
    @SerializedName("average_rating")
    @Expose
    private String averageRating;
    @SerializedName("rating_count")
    @Expose
    private Integer ratingCount;
    @SerializedName("related_ids")
    @Expose
    private List<Integer> relatedIds = null;
    @SerializedName("upsell_ids")
    @Expose
    private List<Object> upsellIds = null;
    @SerializedName("cross_sell_ids")
    @Expose
    private List<Object> crossSellIds = null;
    @SerializedName("parent_id")
    @Expose
    private Integer parentId;
    @SerializedName("purchase_note")
    @Expose
    private String purchaseNote;
    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;
    @SerializedName("tags")
    @Expose
    private List<Object> tags = null;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("attributes")
    @Expose
    private List<Attribute> attributes = null;
    @SerializedName("default_attributes")
    @Expose
    private List<Object> defaultAttributes = null;
    @SerializedName("variations")
    @Expose
    private List<Variation> variations = null;
    @SerializedName("grouped_products")
    @Expose
    private List<Object> groupedProducts = null;
    @SerializedName("menu_order")
    @Expose
    private Integer menuOrder;
    @SerializedName("_links")
    @Expose
    private Links links;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }

    public String getCatalogVisibility() {
        return catalogVisibility;
    }

    public void setCatalogVisibility(String catalogVisibility) {
        this.catalogVisibility = catalogVisibility;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
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

    public String getPriceHtml() {
        return priceHtml;
    }

    public void setPriceHtml(String priceHtml) {
        this.priceHtml = priceHtml;
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

    public Integer getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(Integer totalSales) {
        this.totalSales = totalSales;
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

    public String getDownloadType() {
        return downloadType;
    }

    public void setDownloadType(String downloadType) {
        this.downloadType = downloadType;
    }

    public String getExternalUrl() {
        return externalUrl;
    }

    public void setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
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

    public Boolean getSoldIndividually() {
        return soldIndividually;
    }

    public void setSoldIndividually(Boolean soldIndividually) {
        this.soldIndividually = soldIndividually;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }

    public void setDimensions(Dimensions dimensions) {
        this.dimensions = dimensions;
    }

    public Boolean getShippingRequired() {
        return shippingRequired;
    }

    public void setShippingRequired(Boolean shippingRequired) {
        this.shippingRequired = shippingRequired;
    }

    public Boolean getShippingTaxable() {
        return shippingTaxable;
    }

    public void setShippingTaxable(Boolean shippingTaxable) {
        this.shippingTaxable = shippingTaxable;
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

    public Boolean getReviewsAllowed() {
        return reviewsAllowed;
    }

    public void setReviewsAllowed(Boolean reviewsAllowed) {
        this.reviewsAllowed = reviewsAllowed;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(Integer ratingCount) {
        this.ratingCount = ratingCount;
    }

    public List<Integer> getRelatedIds() {
        return relatedIds;
    }

    public void setRelatedIds(List<Integer> relatedIds) {
        this.relatedIds = relatedIds;
    }

    public List<Object> getUpsellIds() {
        return upsellIds;
    }

    public void setUpsellIds(List<Object> upsellIds) {
        this.upsellIds = upsellIds;
    }

    public List<Object> getCrossSellIds() {
        return crossSellIds;
    }

    public void setCrossSellIds(List<Object> crossSellIds) {
        this.crossSellIds = crossSellIds;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getPurchaseNote() {
        return purchaseNote;
    }

    public void setPurchaseNote(String purchaseNote) {
        this.purchaseNote = purchaseNote;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Object> getTags() {
        return tags;
    }

    public void setTags(List<Object> tags) {
        this.tags = tags;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public List<Object> getDefaultAttributes() {
        return defaultAttributes;
    }

    public void setDefaultAttributes(List<Object> defaultAttributes) {
        this.defaultAttributes = defaultAttributes;
    }

    public List<Variation> getVariations() {
        return variations;
    }

    public void setVariations(List<Variation> variations) {
        this.variations = variations;
    }

    public List<Object> getGroupedProducts() {
        return groupedProducts;
    }

    public void setGroupedProducts(List<Object> groupedProducts) {
        this.groupedProducts = groupedProducts;
    }

    public Integer getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.slug);
        dest.writeString(this.permalink);
        dest.writeString(this.dateCreated);
        dest.writeString(this.dateModified);
        dest.writeString(this.type);
        dest.writeString(this.status);
        dest.writeValue(this.featured);
        dest.writeString(this.catalogVisibility);
        dest.writeString(this.description);
        dest.writeString(this.shortDescription);
        dest.writeString(this.sku);
        dest.writeString(this.price);
        dest.writeString(this.regularPrice);
        dest.writeString(this.salePrice);
        dest.writeString(this.dateOnSaleFrom);
        dest.writeString(this.dateOnSaleTo);
        dest.writeString(this.priceHtml);
        dest.writeValue(this.onSale);
        dest.writeValue(this.purchasable);
        dest.writeValue(this.totalSales);
        dest.writeValue(this.virtual);
        dest.writeValue(this.downloadable);
        dest.writeList(this.downloads);
        dest.writeValue(this.downloadLimit);
        dest.writeValue(this.downloadExpiry);
        dest.writeString(this.downloadType);
        dest.writeString(this.externalUrl);
        dest.writeString(this.buttonText);
        dest.writeString(this.taxStatus);
        dest.writeString(this.taxClass);
        dest.writeValue(this.manageStock);
        dest.writeParcelable((Parcelable) this.stockQuantity, flags);
        dest.writeValue(this.inStock);
        dest.writeString(this.backorders);
        dest.writeValue(this.backordersAllowed);
        dest.writeValue(this.backordered);
        dest.writeValue(this.soldIndividually);
        dest.writeString(this.weight);
        dest.writeParcelable(this.dimensions, flags);
        dest.writeValue(this.shippingRequired);
        dest.writeValue(this.shippingTaxable);
        dest.writeString(this.shippingClass);
        dest.writeValue(this.shippingClassId);
        dest.writeValue(this.reviewsAllowed);
        dest.writeString(this.averageRating);
        dest.writeValue(this.ratingCount);
        dest.writeList(this.relatedIds);
        dest.writeList(this.upsellIds);
        dest.writeList(this.crossSellIds);
        dest.writeValue(this.parentId);
        dest.writeString(this.purchaseNote);
        dest.writeTypedList(this.categories);
        dest.writeList(this.tags);
        dest.writeTypedList(this.images);
        dest.writeTypedList(this.attributes);
        dest.writeList(this.defaultAttributes);
        dest.writeTypedList(this.variations);
        dest.writeList(this.groupedProducts);
        dest.writeValue(this.menuOrder);
        dest.writeParcelable(this.links, flags);
    }

    public ProductListingsModeResponse() {
    }

    protected ProductListingsModeResponse(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.slug = in.readString();
        this.permalink = in.readString();
        this.dateCreated = in.readString();
        this.dateModified = in.readString();
        this.type = in.readString();
        this.status = in.readString();
        this.featured = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.catalogVisibility = in.readString();
        this.description = in.readString();
        this.shortDescription = in.readString();
        this.sku = in.readString();
        this.price = in.readString();
        this.regularPrice = in.readString();
        this.salePrice = in.readString();
        this.dateOnSaleFrom = in.readString();
        this.dateOnSaleTo = in.readString();
        this.priceHtml = in.readString();
        this.onSale = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.purchasable = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.totalSales = (Integer) in.readValue(Integer.class.getClassLoader());
        this.virtual = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.downloadable = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.downloads = new ArrayList<Object>();
        in.readList(this.downloads, Object.class.getClassLoader());
        this.downloadLimit = (Integer) in.readValue(Integer.class.getClassLoader());
        this.downloadExpiry = (Integer) in.readValue(Integer.class.getClassLoader());
        this.downloadType = in.readString();
        this.externalUrl = in.readString();
        this.buttonText = in.readString();
        this.taxStatus = in.readString();
        this.taxClass = in.readString();
        this.manageStock = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.stockQuantity = in.readParcelable(Object.class.getClassLoader());
        this.inStock = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.backorders = in.readString();
        this.backordersAllowed = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.backordered = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.soldIndividually = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.weight = in.readString();
        this.dimensions = in.readParcelable(Dimensions.class.getClassLoader());
        this.shippingRequired = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.shippingTaxable = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.shippingClass = in.readString();
        this.shippingClassId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.reviewsAllowed = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.averageRating = in.readString();
        this.ratingCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.relatedIds = new ArrayList<Integer>();
        in.readList(this.relatedIds, Integer.class.getClassLoader());
        this.upsellIds = new ArrayList<Object>();
        in.readList(this.upsellIds, Object.class.getClassLoader());
        this.crossSellIds = new ArrayList<Object>();
        in.readList(this.crossSellIds, Object.class.getClassLoader());
        this.parentId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.purchaseNote = in.readString();
        this.categories = in.createTypedArrayList(Category.CREATOR);
        this.tags = new ArrayList<Object>();
        in.readList(this.tags, Object.class.getClassLoader());
        this.images = in.createTypedArrayList(Image.CREATOR);
        this.attributes = in.createTypedArrayList(Attribute.CREATOR);
        this.defaultAttributes = new ArrayList<Object>();
        in.readList(this.defaultAttributes, Object.class.getClassLoader());
        this.variations = in.createTypedArrayList(Variation.CREATOR);
        this.groupedProducts = new ArrayList<Object>();
        in.readList(this.groupedProducts, Object.class.getClassLoader());
        this.menuOrder = (Integer) in.readValue(Integer.class.getClassLoader());
        this.links = in.readParcelable(Links.class.getClassLoader());
    }

    public static final Parcelable.Creator<ProductListingsModeResponse> CREATOR = new Parcelable.Creator<ProductListingsModeResponse>() {
        @Override
        public ProductListingsModeResponse createFromParcel(Parcel source) {
            return new ProductListingsModeResponse(source);
        }

        @Override
        public ProductListingsModeResponse[] newArray(int size) {
            return new ProductListingsModeResponse[size];
        }
    };
}