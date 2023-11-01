package app.controllers;

import app.Exception.DatabaseException;
import app.entities.*;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;


public class OrderController {
    //session  der lever i programet

    public static void allBottomsAndToppings(Context ctx, ConnectionPool connectionpool) {

        try {

            List<Bottom> bottoms = new ArrayList<>(OrderMapper.bottoms(connectionpool));
            ctx.sessionAttribute("bottoms", bottoms);
            List<Topping> toppings = new ArrayList<>(OrderMapper.toppings(connectionpool));
            ctx.sessionAttribute("toppings", toppings);

            ctx.render("index.html");


        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());

        }
    }


}