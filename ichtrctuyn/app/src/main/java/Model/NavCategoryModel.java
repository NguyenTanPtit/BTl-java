package Model;

import java.io.Serializable;

public class NavCategoryModel implements Serializable {
    private String name;
    private String price;
    private String img_Url;
    private String type;
    private String description;
    private String rate;

    public NavCategoryModel() {
    }

    public NavCategoryModel(String name, String price, String img_Url, String type) {
        this.name = name;
        this.price = price;
        this.img_Url = img_Url;
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public String getRate() {
        return rate;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getImg_Url() {
        return img_Url;
    }

    public String getType() {
        return type;
    }
}
