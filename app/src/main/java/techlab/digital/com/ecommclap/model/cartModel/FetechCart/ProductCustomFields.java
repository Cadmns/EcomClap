package techlab.digital.com.ecommclap.model.cartModel.FetechCart;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductCustomFields implements Parcelable {

    @SerializedName("_wc_review_count")
    @Expose
    private List<String> wcReviewCount = null;
    @SerializedName("_wc_rating_count")
    @Expose
    private List<String> wcRatingCount = null;
    @SerializedName("_wc_average_rating")
    @Expose
    private List<String> wcAverageRating = null;
    @SerializedName("_edit_last")
    @Expose
    private List<String> editLast = null;
    @SerializedName("_edit_lock")
    @Expose
    private List<String> editLock = null;
    @SerializedName("_thumbnail_id")
    @Expose
    private List<String> thumbnailId = null;
    @SerializedName("_sku")
    @Expose
    private List<String> sku = null;
    @SerializedName("_regular_price")
    @Expose
    private List<String> regularPrice = null;
    @SerializedName("_sale_price")
    @Expose
    private List<String> salePrice = null;
    @SerializedName("_sale_price_dates_from")
    @Expose
    private List<String> salePriceDatesFrom = null;
    @SerializedName("_sale_price_dates_to")
    @Expose
    private List<String> salePriceDatesTo = null;
    @SerializedName("total_sales")
    @Expose
    private List<String> totalSales = null;
    @SerializedName("_tax_status")
    @Expose
    private List<String> taxStatus = null;
    @SerializedName("_tax_class")
    @Expose
    private List<String> taxClass = null;
    @SerializedName("_manage_stock")
    @Expose
    private List<String> manageStock = null;
    @SerializedName("_backorders")
    @Expose
    private List<String> backorders = null;
    @SerializedName("_sold_individually")
    @Expose
    private List<String> soldIndividually = null;
    @SerializedName("_weight")
    @Expose
    private List<String> weight = null;
    @SerializedName("_length")
    @Expose
    private List<String> length = null;
    @SerializedName("_width")
    @Expose
    private List<String> width = null;
    @SerializedName("_height")
    @Expose
    private List<String> height = null;
    @SerializedName("_upsell_ids")
    @Expose
    private List<String> upsellIds = null;
    @SerializedName("_crosssell_ids")
    @Expose
    private List<String> crosssellIds = null;
    @SerializedName("_purchase_note")
    @Expose
    private List<String> purchaseNote = null;
    @SerializedName("_default_attributes")
    @Expose
    private List<String> defaultAttributes = null;
    @SerializedName("_virtual")
    @Expose
    private List<String> virtual = null;
    @SerializedName("_downloadable")
    @Expose
    private List<String> downloadable = null;
    @SerializedName("_product_image_gallery")
    @Expose
    private List<String> productImageGallery = null;
    @SerializedName("_download_limit")
    @Expose
    private List<String> downloadLimit = null;
    @SerializedName("_download_expiry")
    @Expose
    private List<String> downloadExpiry = null;
    @SerializedName("_stock")
    @Expose
    private List<Object> stock = null;
    @SerializedName("_stock_status")
    @Expose
    private List<String> stockStatus = null;
    @SerializedName("_product_version")
    @Expose
    private List<String> productVersion = null;
    @SerializedName("_price")
    @Expose
    private List<String> price = null;
    @SerializedName("_phive_book_price")
    @Expose
    private List<String> phiveBookPrice = null;
    @SerializedName("_phive_book_interval")
    @Expose
    private List<String> phiveBookInterval = null;
    @SerializedName("_phive_book_interval_type")
    @Expose
    private List<String> phiveBookIntervalType = null;
    @SerializedName("_phive_book_interval_period")
    @Expose
    private List<String> phiveBookIntervalPeriod = null;
    @SerializedName("_phive_book_working_hour_start")
    @Expose
    private List<String> phiveBookWorkingHourStart = null;
    @SerializedName("_phive_book_working_hour_end")
    @Expose
    private List<String> phiveBookWorkingHourEnd = null;
    @SerializedName("booking_availability_rules")
    @Expose
    private List<String> bookingAvailabilityRules = null;

    protected ProductCustomFields(Parcel in) {
        wcReviewCount = in.createStringArrayList();
        wcRatingCount = in.createStringArrayList();
        wcAverageRating = in.createStringArrayList();
        editLast = in.createStringArrayList();
        editLock = in.createStringArrayList();
        thumbnailId = in.createStringArrayList();
        sku = in.createStringArrayList();
        regularPrice = in.createStringArrayList();
        salePrice = in.createStringArrayList();
        salePriceDatesFrom = in.createStringArrayList();
        salePriceDatesTo = in.createStringArrayList();
        totalSales = in.createStringArrayList();
        taxStatus = in.createStringArrayList();
        taxClass = in.createStringArrayList();
        manageStock = in.createStringArrayList();
        backorders = in.createStringArrayList();
        soldIndividually = in.createStringArrayList();
        weight = in.createStringArrayList();
        length = in.createStringArrayList();
        width = in.createStringArrayList();
        height = in.createStringArrayList();
        upsellIds = in.createStringArrayList();
        crosssellIds = in.createStringArrayList();
        purchaseNote = in.createStringArrayList();
        defaultAttributes = in.createStringArrayList();
        virtual = in.createStringArrayList();
        downloadable = in.createStringArrayList();
        productImageGallery = in.createStringArrayList();
        downloadLimit = in.createStringArrayList();
        downloadExpiry = in.createStringArrayList();
        stockStatus = in.createStringArrayList();
        productVersion = in.createStringArrayList();
        price = in.createStringArrayList();
        phiveBookPrice = in.createStringArrayList();
        phiveBookInterval = in.createStringArrayList();
        phiveBookIntervalType = in.createStringArrayList();
        phiveBookIntervalPeriod = in.createStringArrayList();
        phiveBookWorkingHourStart = in.createStringArrayList();
        phiveBookWorkingHourEnd = in.createStringArrayList();
        bookingAvailabilityRules = in.createStringArrayList();
    }

    public static final Creator<ProductCustomFields> CREATOR = new Creator<ProductCustomFields>() {
        @Override
        public ProductCustomFields createFromParcel(Parcel in) {
            return new ProductCustomFields(in);
        }

        @Override
        public ProductCustomFields[] newArray(int size) {
            return new ProductCustomFields[size];
        }
    };

    public List<String> getWcReviewCount() {
        return wcReviewCount;
    }

    public void setWcReviewCount(List<String> wcReviewCount) {
        this.wcReviewCount = wcReviewCount;
    }

    public List<String> getWcRatingCount() {
        return wcRatingCount;
    }

    public void setWcRatingCount(List<String> wcRatingCount) {
        this.wcRatingCount = wcRatingCount;
    }

    public List<String> getWcAverageRating() {
        return wcAverageRating;
    }

    public void setWcAverageRating(List<String> wcAverageRating) {
        this.wcAverageRating = wcAverageRating;
    }

    public List<String> getEditLast() {
        return editLast;
    }

    public void setEditLast(List<String> editLast) {
        this.editLast = editLast;
    }

    public List<String> getEditLock() {
        return editLock;
    }

    public void setEditLock(List<String> editLock) {
        this.editLock = editLock;
    }

    public List<String> getThumbnailId() {
        return thumbnailId;
    }

    public void setThumbnailId(List<String> thumbnailId) {
        this.thumbnailId = thumbnailId;
    }

    public List<String> getSku() {
        return sku;
    }

    public void setSku(List<String> sku) {
        this.sku = sku;
    }

    public List<String> getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(List<String> regularPrice) {
        this.regularPrice = regularPrice;
    }

    public List<String> getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(List<String> salePrice) {
        this.salePrice = salePrice;
    }

    public List<String> getSalePriceDatesFrom() {
        return salePriceDatesFrom;
    }

    public void setSalePriceDatesFrom(List<String> salePriceDatesFrom) {
        this.salePriceDatesFrom = salePriceDatesFrom;
    }

    public List<String> getSalePriceDatesTo() {
        return salePriceDatesTo;
    }

    public void setSalePriceDatesTo(List<String> salePriceDatesTo) {
        this.salePriceDatesTo = salePriceDatesTo;
    }

    public List<String> getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(List<String> totalSales) {
        this.totalSales = totalSales;
    }

    public List<String> getTaxStatus() {
        return taxStatus;
    }

    public void setTaxStatus(List<String> taxStatus) {
        this.taxStatus = taxStatus;
    }

    public List<String> getTaxClass() {
        return taxClass;
    }

    public void setTaxClass(List<String> taxClass) {
        this.taxClass = taxClass;
    }

    public List<String> getManageStock() {
        return manageStock;
    }

    public void setManageStock(List<String> manageStock) {
        this.manageStock = manageStock;
    }

    public List<String> getBackorders() {
        return backorders;
    }

    public void setBackorders(List<String> backorders) {
        this.backorders = backorders;
    }

    public List<String> getSoldIndividually() {
        return soldIndividually;
    }

    public void setSoldIndividually(List<String> soldIndividually) {
        this.soldIndividually = soldIndividually;
    }

    public List<String> getWeight() {
        return weight;
    }

    public void setWeight(List<String> weight) {
        this.weight = weight;
    }

    public List<String> getLength() {
        return length;
    }

    public void setLength(List<String> length) {
        this.length = length;
    }

    public List<String> getWidth() {
        return width;
    }

    public void setWidth(List<String> width) {
        this.width = width;
    }

    public List<String> getHeight() {
        return height;
    }

    public void setHeight(List<String> height) {
        this.height = height;
    }

    public List<String> getUpsellIds() {
        return upsellIds;
    }

    public void setUpsellIds(List<String> upsellIds) {
        this.upsellIds = upsellIds;
    }

    public List<String> getCrosssellIds() {
        return crosssellIds;
    }

    public void setCrosssellIds(List<String> crosssellIds) {
        this.crosssellIds = crosssellIds;
    }

    public List<String> getPurchaseNote() {
        return purchaseNote;
    }

    public void setPurchaseNote(List<String> purchaseNote) {
        this.purchaseNote = purchaseNote;
    }

    public List<String> getDefaultAttributes() {
        return defaultAttributes;
    }

    public void setDefaultAttributes(List<String> defaultAttributes) {
        this.defaultAttributes = defaultAttributes;
    }

    public List<String> getVirtual() {
        return virtual;
    }

    public void setVirtual(List<String> virtual) {
        this.virtual = virtual;
    }

    public List<String> getDownloadable() {
        return downloadable;
    }

    public void setDownloadable(List<String> downloadable) {
        this.downloadable = downloadable;
    }

    public List<String> getProductImageGallery() {
        return productImageGallery;
    }

    public void setProductImageGallery(List<String> productImageGallery) {
        this.productImageGallery = productImageGallery;
    }

    public List<String> getDownloadLimit() {
        return downloadLimit;
    }

    public void setDownloadLimit(List<String> downloadLimit) {
        this.downloadLimit = downloadLimit;
    }

    public List<String> getDownloadExpiry() {
        return downloadExpiry;
    }

    public void setDownloadExpiry(List<String> downloadExpiry) {
        this.downloadExpiry = downloadExpiry;
    }

    public List<Object> getStock() {
        return stock;
    }

    public void setStock(List<Object> stock) {
        this.stock = stock;
    }

    public List<String> getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(List<String> stockStatus) {
        this.stockStatus = stockStatus;
    }

    public List<String> getProductVersion() {
        return productVersion;
    }

    public void setProductVersion(List<String> productVersion) {
        this.productVersion = productVersion;
    }

    public List<String> getPrice() {
        return price;
    }

    public void setPrice(List<String> price) {
        this.price = price;
    }

    public List<String> getPhiveBookPrice() {
        return phiveBookPrice;
    }

    public void setPhiveBookPrice(List<String> phiveBookPrice) {
        this.phiveBookPrice = phiveBookPrice;
    }

    public List<String> getPhiveBookInterval() {
        return phiveBookInterval;
    }

    public void setPhiveBookInterval(List<String> phiveBookInterval) {
        this.phiveBookInterval = phiveBookInterval;
    }

    public List<String> getPhiveBookIntervalType() {
        return phiveBookIntervalType;
    }

    public void setPhiveBookIntervalType(List<String> phiveBookIntervalType) {
        this.phiveBookIntervalType = phiveBookIntervalType;
    }

    public List<String> getPhiveBookIntervalPeriod() {
        return phiveBookIntervalPeriod;
    }

    public void setPhiveBookIntervalPeriod(List<String> phiveBookIntervalPeriod) {
        this.phiveBookIntervalPeriod = phiveBookIntervalPeriod;
    }

    public List<String> getPhiveBookWorkingHourStart() {
        return phiveBookWorkingHourStart;
    }

    public void setPhiveBookWorkingHourStart(List<String> phiveBookWorkingHourStart) {
        this.phiveBookWorkingHourStart = phiveBookWorkingHourStart;
    }

    public List<String> getPhiveBookWorkingHourEnd() {
        return phiveBookWorkingHourEnd;
    }

    public void setPhiveBookWorkingHourEnd(List<String> phiveBookWorkingHourEnd) {
        this.phiveBookWorkingHourEnd = phiveBookWorkingHourEnd;
    }

    public List<String> getBookingAvailabilityRules() {
        return bookingAvailabilityRules;
    }

    public void setBookingAvailabilityRules(List<String> bookingAvailabilityRules) {
        this.bookingAvailabilityRules = bookingAvailabilityRules;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(wcReviewCount);
        dest.writeStringList(wcRatingCount);
        dest.writeStringList(wcAverageRating);
        dest.writeStringList(editLast);
        dest.writeStringList(editLock);
        dest.writeStringList(thumbnailId);
        dest.writeStringList(sku);
        dest.writeStringList(regularPrice);
        dest.writeStringList(salePrice);
        dest.writeStringList(salePriceDatesFrom);
        dest.writeStringList(salePriceDatesTo);
        dest.writeStringList(totalSales);
        dest.writeStringList(taxStatus);
        dest.writeStringList(taxClass);
        dest.writeStringList(manageStock);
        dest.writeStringList(backorders);
        dest.writeStringList(soldIndividually);
        dest.writeStringList(weight);
        dest.writeStringList(length);
        dest.writeStringList(width);
        dest.writeStringList(height);
        dest.writeStringList(upsellIds);
        dest.writeStringList(crosssellIds);
        dest.writeStringList(purchaseNote);
        dest.writeStringList(defaultAttributes);
        dest.writeStringList(virtual);
        dest.writeStringList(downloadable);
        dest.writeStringList(productImageGallery);
        dest.writeStringList(downloadLimit);
        dest.writeStringList(downloadExpiry);
        dest.writeStringList(stockStatus);
        dest.writeStringList(productVersion);
        dest.writeStringList(price);
        dest.writeStringList(phiveBookPrice);
        dest.writeStringList(phiveBookInterval);
        dest.writeStringList(phiveBookIntervalType);
        dest.writeStringList(phiveBookIntervalPeriod);
        dest.writeStringList(phiveBookWorkingHourStart);
        dest.writeStringList(phiveBookWorkingHourEnd);
        dest.writeStringList(bookingAvailabilityRules);
    }
}