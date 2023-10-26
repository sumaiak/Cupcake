package app.entities;

public class Topping {
    int topID;
    String topping;
    int price;

    public Topping(int topID, String topping, int price) {
        this.topID = topID;
        this.topping = topping;
        this.price = price;
    }

    public int getTopID() {
        return topID;
    }

    public void setTopID(int topID) {
        this.topID = topID;
    }

    public String getTopping() {
        return topping;
    }

    public void setTopping(String topping) {
        this.topping = topping;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
