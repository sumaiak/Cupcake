package app.controllers;

import app.Exception.DatabaseException;
import app.entities.*;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import app.persistence.UserMapper;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CartController {


    public static void addToCart(Context ctx, ConnectionPool connectionPool) {
        int topId = Integer.parseInt(ctx.formParam("toppings"));
        int bottomId = Integer.parseInt(ctx.formParam("bottoms"));
        int quantity = Integer.parseInt(ctx.formParam("quantity"));
        System.out.println("topId: " + topId);
        System.out.println("bottomId: " + bottomId);
        System.out.println("quantity: " + quantity);
        OrderLine orderLine = new OrderLine(bottomId, topId, quantity);
        // gem orderline i kurven
        Cart cart = (Cart) ctx.sessionAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }
        cart.addtocart(orderLine);
        ctx.sessionAttribute("cart", cart);
        ctx.render("index.html");

    }

    public static void showcart(Context ctx, ConnectionPool connectionPool) {
        User user = ctx.sessionAttribute("login");

        List<Bottom> bottoms = ctx.sessionAttribute("bottoms");
        List<Topping> toppings = ctx.sessionAttribute("toppings");

        Map<Integer, Topping> toppingMap = toppings.stream()
                .collect(Collectors.toMap(Topping::getTopID, topping -> topping));
        Map<Integer, Bottom> bottomMap = bottoms.stream()
                .collect(Collectors.toMap(Bottom::getBottomId, topping -> topping));

        ctx.sessionAttribute("toppings", toppings);
        ctx.sessionAttribute("bottoms", bottoms);
        ctx.sessionAttribute("toppingmap", toppingMap);
        ctx.sessionAttribute("bottomMap", bottomMap);

        ctx.render("cart.html");
    }
}