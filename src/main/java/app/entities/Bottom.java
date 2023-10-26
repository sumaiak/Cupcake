package app.entities;

public class Bottom {
    int bottomId;
    String bottom;
    int price;

    public Bottom(String bottom, int price) {
        this.bottom = bottom;
        this.price = price;
    }

    public String getBottom() {
        return bottom;
    }

    public void setBottom(String bottom) {
        this.bottom = bottom;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
