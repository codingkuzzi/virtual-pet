package com.epicodus.ak.virtualpet;

import java.util.Map;
import java.util.HashMap;

import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.Spark;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App{

    public  static void main(String[] args){

        // Use Heroku environment variables, if detected
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.parseInt(System.getenv("PORT")));
        }

        if (System.getenv("JDBC_DATABASE_URL") != null) {
            DB.sql2o  = new Sql2o(
                    System.getenv("JDBC_DATABASE_URL"),
                    System.getenv("JDBC_DATABASE_USERNAME"),
                    System.getenv("JDBC_DATABASE_PASSWORD"));
        } else {
            // TODO: move local database setting to some config file
            DB.sql2o  = new Sql2o(
                    "jdbc:postgresql://localhost:5432/virtual_pet",
                    "postgres",
                    "postgres");
        }

        String layout = "templates/layout.vtl";

        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", "templates/index.vtl");
            model.put("persons", Person.all());
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/persons", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String name = request.queryParams("name");
            String email = request.queryParams("email");
            Person newPerson = new Person(name, email);
            newPerson.save();
            model.put("template", "templates/person-success.vtl");
            model.put("newPerson", newPerson);
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());


        get("/monsters", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", "templates/monsters.vtl");
            model.put("watermonsters", FireMonster.all());
            model.put("firemonsters", WaterMonster.all());
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());



    }
}