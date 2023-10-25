package app.controllers;

import app.entities.Cart;
import app.entities.OrderLine;
import app.persistence.ConnectionPool;
import io.javalin.http.Context;

public class CartController {


    public static void addToCart(Context ctx, ConnectionPool connectionPool) {
        int topId = Integer.parseInt(ctx.formParam("top"));
        int bottomId = Integer.parseInt(ctx.formParam("bottom"));
        OrderLine orderLine = new OrderLine(topId, bottomId);
         // gem orderline i kurven
        Cart cart = (Cart) ctx.sessionAttribute("cart");
        if (cart == null)
        {
            cart = new Cart();
        }
        cart.addtocart(orderLine);
        ctx.sessionAttribute("cart", cart);
        ctx.render("index.html");

    }
}
