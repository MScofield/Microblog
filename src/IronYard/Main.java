package IronYard;

import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by scofieldservices on 12/5/16.
 */
public class Main {
    public static User user;

    public static void main (String[]args){
        ArrayList<Messages> messageList = new ArrayList();

        Spark.init();
        // lambda to build an object for handing hashmap data to the html file
        Spark.get(
                "/",
                ((request, response) -> {
                    HashMap userHash = new HashMap();

                    if (user== null||!user.password.equalsIgnoreCase("wierdo"))
                    {

                        return new ModelAndView(userHash, "login.html");

                    }else {
                        userHash.put("name", user.name);
                        userHash.put("messageAdd", messageList);
                        return new ModelAndView(userHash, "messages.html");
                    }
                }),
                new MustacheTemplateEngine()
        );

        Spark.post(
                "/create-user",
                ((request, response) -> {
                    String name = request.queryParams("loginName");
                    String password = request.queryParams("loginPassword");
                    user = new User (name, password);
                    response.redirect("/");
                    return "";
                })
        );

        Spark.post(
                "/create-message",
                ((request, response) -> {

                    String messageString = request.queryParams("messageBox");
                    Messages messages = new Messages(messageString);
                    messageList.add(messages);
                    response.redirect("/");
                    return "";
                })
        );

//        Spark.post(
//                "/change-user",
//                ((request, response) -> {
//                    user = null;
//                    response.redirect("/");
//                    return "";
//                })
//        );
    }
}
