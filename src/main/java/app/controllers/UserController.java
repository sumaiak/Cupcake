package app.controllers;

import app.Exception.DatabaseException;
import app.entities.User;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import io.javalin.http.Context;

import java.util.List;

public class UserController {

    public static void createUser(Context ctx, ConnectionPool connectionPool) {

        //her fisker man de oplysninger der er indtastet i formularen
        String username = ctx.formParam("username");
        String name = ctx.formParam("name");
        String password1 = ctx.formParam("password1");
        String password2 = ctx.formParam("password2");

        //validering af passwords -  at de matcher
        if (password1.equals(password2)) {
            try {
                UserMapper.createuser(username,name, password1, connectionPool);
                ctx.attribute("message", "Your account is created. You can now login");
                ctx.render("mangler html side");
            } catch (DatabaseException e) {
                ctx.attribute("message", e.getMessage());
                ctx.render("createuser.html");
            }
        } else {
            ctx.attribute("message", "Your passwords are not identical");
            ctx.render("createuser.html");
        }
    }

    public static void login(Context ctx , ConnectionPool connectionPool){

        String username  = ctx.formParam("username");
        String password  = ctx.formParam("password");
        try {
            User user = UserMapper.login(username, password, connectionPool);
            ctx.sessionAttribute("currentUser", user);


            //I attribute gemmer vi et listen over tasks i varibale "tasks"
         //   ctx.attribute();
            ctx.render("login.html");
        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("index.html");
        }



    }
}