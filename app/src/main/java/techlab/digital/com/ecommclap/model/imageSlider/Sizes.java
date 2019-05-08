package techlab.digital.com.ecommclap.model.imageSlider;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sizes {

    @SerializedName("estore-product-grid")
    @Expose
    private EstoreProductGrid estoreProductGrid;
    @SerializedName("woocommerce_gallery_thumbnail")
    @Expose
    private WoocommerceGalleryThumbnail woocommerceGalleryThumbnail;
    @SerializedName("shop_thumbnail")
    @Expose
    private ShopThumbnail shopThumbnail;
    @SerializedName("full")
    @Expose
    private Full full;

    public EstoreProductGrid getEstoreProductGrid() {
        return estoreProductGrid;
    }

    public void setEstoreProductGrid(EstoreProductGrid estoreProductGrid) {
        this.estoreProductGrid = estoreProductGrid;
    }

    public WoocommerceGalleryThumbnail getWoocommerceGalleryThumbnail() {
        return woocommerceGalleryThumbnail;
    }

    public void setWoocommerceGalleryThumbnail(WoocommerceGalleryThumbnail woocommerceGalleryThumbnail) {
        this.woocommerceGalleryThumbnail = woocommerceGalleryThumbnail;
    }

    public ShopThumbnail getShopThumbnail() {
        return shopThumbnail;
    }

    public void setShopThumbnail(ShopThumbnail shopThumbnail) {
        this.shopThumbnail = shopThumbnail;
    }

    public Full getFull() {
        return full;
    }

    public void setFull(Full full) {
        this.full = full;
    }

}