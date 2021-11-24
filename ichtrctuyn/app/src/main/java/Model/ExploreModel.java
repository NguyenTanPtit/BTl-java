package Model;

public class ExploreModel {
    String name;
    String type;
    String img_Url;

    public ExploreModel() {
    }

    public ExploreModel(String name, String type, String img_Url) {
        this.name = name;
        this.type = type;
        this.img_Url = img_Url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImg_Url() {
        return img_Url;
    }

    public void setImg_Url(String img_Url) {
        this.img_Url = img_Url;
    }
}
