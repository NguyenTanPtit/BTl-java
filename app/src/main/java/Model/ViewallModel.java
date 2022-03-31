package Model;

import java.io.Serializable;

public class ViewallModel implements Serializable {
    String name;
    String description;
    String rate;
    String types;
    int price;
    String img_Url;

    public ViewallModel() {
    }

    public ViewallModel(String name, String description, String rate, String type, int price, String img_Url) {
        this.name = name;
        this.description = description;
        this.rate = rate;
        this.types = type;
        this.price = price;
        this.img_Url = img_Url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }


    public String getRate() {
        return rate;
    }


    public String getTypes() {
        return this.types;
    }


    public int getPrice() {
        return price;
    }


    public String getImg_Url() {
        return img_Url;
    }


}
