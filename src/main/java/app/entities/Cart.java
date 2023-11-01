package app.entities;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<OrderLine> cart = new ArrayList<>();

    public void addtocart( OrderLine item){
        cart.add(item);
        System.out.println(cart);

    }

    public Cart() {

    }

    public List<OrderLine> getCart() {
        return cart;
    }

    public void setCart(List<OrderLine> cart) {
        this.cart = cart;
    }

    public int cartSize( ){

        return cart.size();

    }
}
