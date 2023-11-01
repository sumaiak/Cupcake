package app.entities;

public class Topping {
    int topID;
    String name;
    int price;

    public Topping(int topID, String name, int price) {
        this.topID = topID;
        this.name = name;
        this.price = price;
    }

    public int getTopID() {
        return topID;
    }

    public void setTopID(int topID) {
        this.topID = topID;
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
