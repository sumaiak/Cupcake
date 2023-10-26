package app;

import app.config.ThymeleafConfig;
import app.controllers.CartController;
import app.controllers.UserController;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

public class Main {

    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=public";
    private static final String DB = "Cupcake";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);




    public static void main(String[] args)

    {
        // Initializing Javalin and Jetty webserver

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            JavalinThymeleaf.init(ThymeleafConfig.templateEngine());
        }).start(7078);

        // Routing


        //Når man besøger hjemmesiden skal index siden renderes.
        app.get("/", ctx -> ctx.render("index.html"));

        app.get("/createuser", ctx -> ctx.render("createuser.html"));
        // Når der klikkes på createuser, følges denne rute:
        app.post("/createuser", ctx -> UserController.createUser(ctx, connectionPool));
        app.post("/cart",ctx-> CartController.addToCart(ctx, connectionPool));
    }
}