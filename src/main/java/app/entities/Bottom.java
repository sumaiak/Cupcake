package app.entities;

public class Bottom {
    int bottomId;
    String name;
    int price;

    public Bottom(int bottomId,String bottom, int price) {
        this.bottomId = bottomId;
        this.name = bottom;
        this.price = price;
    }

    public int getBottomId() {
        return bottomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
