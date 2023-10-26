package app.controllers;

import io.javalin.http.Context;

import java.util.HashMap;


public class OrderController {
    // Vi ved godt det ikke er den rigtige måde og vi forbinder med databasen istedet . skal muligvis slettes
    private static HashMap<String, Integer> selectTop = new HashMap<>();
    private static HashMap<String, Integer> selectBottom = new HashMap<>();

    public static void getPrice(Context ctx) {

        selectTop.put("Chocolate", 5);
        selectTop.put("Bluebarry", 5);
        selectTop.put("Rasberry", 5);
        selectTop.put("Crispy", 6);
        selectTop.put("Strawberry", 6);
        selectTop.put("Rum/Raisin", 7);
        selectTop.put("Orange", 8);
        selectTop.put("Lemon", 8);
        selectTop.put("Blue cheese", 9);

        selectBottom.put("Chocolate", 5);
        selectBottom.put("Vanilla", 5);
        selectBottom.put("Nutmeg", 5);
        selectBottom.put("Pistacio", 6);
        selectBottom.put("Almond", 7);


        String topType = ctx.formParam("top_type");
        String bottomType = ctx.formParam("bottom_type");

        // Hent priserne fra HashMaps
        int topPrice = selectTop.get(topType);
        int bottomPrice = selectBottom.get(bottomType);


        // Beregn den samlede pris
        int totalPrice = topPrice + bottomPrice;


        //Gem topPrice i Thymeleaf-konteksten
        ctx.attribute("price", totalPrice);

        ctx.render("cart.html");

    }
}