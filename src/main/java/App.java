import java.util.Map;
import java.util.HashMap;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

public class App {
    public static void main(String[] args){

        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }
        setPort(port);

        staticFileLocation("/public");
        String layout = "templates/layout.vtl";
        enableDebugScreen();

        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("stylists", Stylist.allStyle());
            model.put("template", "templates/index.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        //Route to add New Client That leads you to the Home Page
        get("/clients/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("stylists", Stylist.allStyle());
            model.put("template", "templates/index.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());
        //STYLIST
        post("/stylist", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String name = request.queryParams("name");
            String style = request.queryParams("style");
            Stylist newStylist = new Stylist(name,style);
            newStylist.save();
            model.put("template", "templates/style-success.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/stylist", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("stylists", Stylist.allStyle());
            model.put("template", "templates/stylist.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/stylist/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
            model.put("clients", Client.all());
            model.put("stylist", stylist);
            model.put("template", "templates/viewStylist.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        //Post method for deleting client
        post("/client/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            Client client = Client.find(Integer.parseInt(request.params(":id")));
            client.deleteClient();
            model.put("stylist", client);
            model.put("template", "templates/viewClients.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        //Post Method to delete stylist
        post("/stylist/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
            stylist.delete();
            model.put("stylist", stylist);
            model.put("template", "templates/stylist.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        //Client
        post("/client", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            Stylist stylist = Stylist.find(Integer.parseInt(request.queryParams(":id")));
            String clientName = request.queryParams("clientName");
            String clientStyle = request.queryParams("clientStyle");
            Client newClient = new Client(clientName, clientStyle, stylist.getId());
            newClient.save();
            model.put("stylist", stylist);
            model.put("template", "templates/client-success.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/about", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("clients", Client.all());
            model.put("template", "templates/AboutUs.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/stylist/:id/viewClients", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
            Client client = Client.find(Integer.parseInt(request.params(":id")));
            model.put("clients", stylist.getClients());
            model.put("client", client);
            model.put("stylist", stylist);
            model.put("template", "templates/viewClients.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/client/:client_id/update", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            Client client = Client.find(Integer.parseInt(request.params(":client_id")));
            String clientName = request.queryParams("clientName");
            String clientStyle = request.queryParams("clientStyle");
            client.update(clientName, clientStyle);
            model.put("template", "templates/index.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/client/:client_id/update", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            Client client = Client.find(Integer.parseInt(request.params(":client_id")));
            model.put("client", client);
            model.put("template", "templates/updateClient.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

    }

}
