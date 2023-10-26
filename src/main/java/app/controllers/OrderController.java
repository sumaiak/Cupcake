package app.controllers;

import app.Exception.DatabaseException;
import app.entities.Order;
import app.entities.OrderLine;
import app.entities.User;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import io.javalin.http.Context;

import java.util.List;


public class OrderController {
    public static void OrderLine(int bottom, int top, Context ctx, ConnectionPool connectionpool) {
        User user = ctx.sessionAttribute("currentUser");


    }
}