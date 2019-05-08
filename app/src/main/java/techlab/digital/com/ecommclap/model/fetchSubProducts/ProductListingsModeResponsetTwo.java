package techlab.digital.com.ecommclap.model.fetchSubProducts;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductListingsModeResponsetTwo implements Parcelable {
    @SerializedName("quantity")
    @Expose
    private String quantity;

    protected ProductListingsModeResponsetTwo(Parcel in) {
        quantity = in.readString();
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        slug = in.readString();
        permalink = in.readString();
        dateCreated = in.readString();
        dateModified = in.readString();
        type = in.readString();
        status = in.readString();
        byte tmpFeatured = in.readByte();
        featured = tmpFeatured == 0 ? null : tmpFeatured == 1;
        catalogVisibility = in.readString();
        description = in.readString();
        shortDescription = in.readString();
        sku = in.readString();
        price = in.readString();
        regularPrice = in.readString();
        salePrice = in.readString();
        dateOnSaleFrom = in.readString();
        dateOnSaleTo = in.readString();
        priceHtml = in.readString();
        byte tmpOnSale = in.readByte();
        onSale = tmpOnSale == 0 ? null : tmpOnSale == 1;
        byte tmpPurchasable = in.readByte();
        purchasable = tmpPurchasable == 0 ? null : tmpPurchasable == 1;
        if (in.readByte() == 0) {
            totalSales = null;
        } else {
            totalSales = in.readInt();
        }
        byte tmpVirtual = in.readByte();
        virtual = tmpVirtual == 0 ? null : tmpVirtual == 1;
        byte tmpDownloadable = in.readByte();
        downloadable = tmpDownloadable == 0 ? null : tmpDownloadable == 1;
        if (in.readByte() == 0) {
            downloadLimit = null;
        } else {
            downloadLimit = in.readInt();
        }
        if (in.readByte() == 0) {
            downloadExpiry = null;
        } else {
            downloadExpiry = in.readInt();
        }
        downloadType = in.readString();
        externalUrl = in.readString();
        buttonText = in.readString();
        taxStatus = in.readString();
        taxClass = in.readString();
        byte tmpManageStock = in.readByte();
        manageStock = tmpManageStock == 0 ? null : tmpManageStock == 1;
        byte tmpInStock = in.readByte();
        inStock = tmpInStock == 0 ? null : tmpInStock == 1;
        backorders = in.readString();
        byte tmpBackordersAllowed = in.readByte();
        backordersAllowed = tmpBackordersAllowed == 0 ? null : tmpBackordersAllowed == 1;
        byte tmpBackordered = in.readByte();
        backordered = tmpBackordered == 0 ? null : tmpBackordered == 1;
        byte tmpSoldIndividually = in.readByte();
        soldIndividually = tmpSoldIndividually == 0 ? null : tmpSoldIndividually == 1;
        weight = in.readString();
        dimensions = in.readParcelable(Dimensions.class.getClassLoader());
        byte tmpShippingRequired = in.readByte();
        shippingRequired = tmpShippingRequired == 0 ? null : tmpShippingRequired == 1;
        byte tmpShippingTaxable = in.readByte();
        shippingTaxable = tmpShippingTaxable == 0 ? null : tmpShippingTaxable == 1;
        shippingClass = in.readString();
        if (in.readByte() == 0) {
            shippingClassId = null;
        } else {
            shippingClassId = in.readInt();
        }
        byte tmpReviewsAllowed = in.readByte();
        reviewsAllowed = tmpReviewsAllowed == 0 ? null : tmpReviewsAllowed == 1;
        averageRating = in.readString();
        if (in.readByte() == 0) {
            ratingCount = null;
        } else {
            ratingCount = in.readInt();
        }
        if (in.readByte() == 0) {
            parentId = null;
        } else {
            parentId = in.readInt();
        }
        purchaseNote = in.readString();
        categories = in.createTypedArrayList(Category.CREATOR);
        images = in.createTypedArrayList(Image.CREATOR);
        attributes = in.createTypedArrayList(Attribute.CREATOR);
        variations = in.createTypedArrayList(Variation.CREATOR);
        if (in.readByte() == 0) {
            menuOrder = null;
        } else {
            menuOrder = in.readInt();
        }
        links = in.readParcelable(Links.class.getClassLoader());
    }

    public static final Creator<ProductListingsModeResponsetTwo> CREATOR = new Creator<ProductListingsModeResponsetTwo>() {
        @Override
        public ProductListingsModeResponsetTwo createFromParcel(Parcel in) {
            return new ProductListingsModeResponsetTwo(in);
        }

        @Override
        public ProductListingsModeResponsetTwo[] newArray(int size) {
            return new ProductListingsModeResponsetTwo[size];
        }
    };

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

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
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(quantity);
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(name);
        parcel.writeString(slug);
        parcel.writeString(permalink);
        parcel.writeString(dateCreated);
        parcel.writeString(dateModified);
        parcel.writeString(type);
        parcel.writeString(status);
        parcel.writeByte((byte) (featured == null ? 0 : featured ? 1 : 2));
        parcel.writeString(catalogVisibility);
        parcel.writeString(description);
        parcel.writeString(shortDescription);
        parcel.writeString(sku);
        parcel.writeString(price);
        parcel.writeString(regularPrice);
        parcel.writeString(salePrice);
        parcel.writeString(dateOnSaleFrom);
        parcel.writeString(dateOnSaleTo);
        parcel.writeString(priceHtml);
        parcel.writeByte((byte) (onSale == null ? 0 : onSale ? 1 : 2));
        parcel.writeByte((byte) (purchasable == null ? 0 : purchasable ? 1 : 2));
        if (totalSales == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(totalSales);
        }
        parcel.writeByte((byte) (virtual == null ? 0 : virtual ? 1 : 2));
        parcel.writeByte((byte) (downloadable == null ? 0 : downloadable ? 1 : 2));
        if (downloadLimit == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(downloadLimit);
        }
        if (downloadExpiry == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(downloadExpiry);
        }
        parcel.writeString(downloadType);
        parcel.writeString(externalUrl);
        parcel.writeString(buttonText);
        parcel.writeString(taxStatus);
        parcel.writeString(taxClass);
        parcel.writeByte((byte) (manageStock == null ? 0 : manageStock ? 1 : 2));
        parcel.writeByte((byte) (inStock == null ? 0 : inStock ? 1 : 2));
        parcel.writeString(backorders);
        parcel.writeByte((byte) (backordersAllowed == null ? 0 : backordersAllowed ? 1 : 2));
        parcel.writeByte((byte) (backordered == null ? 0 : backordered ? 1 : 2));
        parcel.writeByte((byte) (soldIndividually == null ? 0 : soldIndividually ? 1 : 2));
        parcel.writeString(weight);
        parcel.writeParcelable(dimensions, i);
        parcel.writeByte((byte) (shippingRequired == null ? 0 : shippingRequired ? 1 : 2));
        parcel.writeByte((byte) (shippingTaxable == null ? 0 : shippingTaxable ? 1 : 2));
        parcel.writeString(shippingClass);
        if (shippingClassId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(shippingClassId);
        }
        parcel.writeByte((byte) (reviewsAllowed == null ? 0 : reviewsAllowed ? 1 : 2));
        parcel.writeString(averageRating);
        if (ratingCount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(ratingCount);
        }
        if (parentId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(parentId);
        }
        parcel.writeString(purchaseNote);
        parcel.writeTypedList(categories);
        parcel.writeTypedList(images);
        parcel.writeTypedList(attributes);
        parcel.writeTypedList(variations);
        if (menuOrder == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(menuOrder);
        }
        parcel.writeParcelable(links, i);
    }
}