package techlab.digital.com.ecommclap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.annotations.PrimaryKey;

public class BannerModel {
    @SerializedName("id")
    @Expose
    @PrimaryKey
    private int id =0;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("image")
    @Expose
    private String image;

    private int product_position;


    public int getProduct_position() {
        return product_position;
    }

    public void setProduct_position(int product_position) {
        this.product_position = product_position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
