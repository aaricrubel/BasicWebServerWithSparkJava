import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        HashMap<String, Data> map = new HashMap<>();
        Gson gson = new GsonBuilder().setPrettyPrinting().create(); // For printing a more readable JSON, use new Gson() to get minified JSON in return
        get("/hello", (req, res) -> "Hello World");

        get("/", (request, response) -> "This is a Test Server");

        post("/postman", (request, response) -> {
            var data = request.body();
            return "Hello World " + request.requestMethod() + " " + (String) data;
        });

        post("/postman2", (request, response) -> {
            String jsonUser = request.body();
            Data data = gson.fromJson(jsonUser, Data.class);
            JsonElement jsonElement = gson.toJsonTree(data);
            jsonElement.getAsJsonObject().addProperty("abhinav", "mukherjee");
            return gson.toJson(jsonElement);
            /*HashMap<String, String> New =new HashMap<>();
              New.put("abhinav", "mukherjee");
              map.put("user", data);
              return gson.toJson(map);
              return gson.toJson(data);
              return gson.toJson(New);*/
        });

        get("/hello/:name", (request, response) -> {
            return "Hello, " + request.params(":name");
        });

        get("/greet/:number", (request, response) -> {
            int x = Integer.parseInt(request.params(":number"));
            x++;
            return x;
        });

        get("/goBack", (request, response) -> {
            response.redirect("/");
            return "Status Ok";
        });

        get("/checkPal/:string", (request, response) -> {
            String string = request.params(":string");
            StringBuilder stringBuilder = new StringBuilder(string);
            return stringBuilder.reverse().toString().equals(string) ? "Yes" : "No";
        });

        //THA1 ->
        post("/tha", (request, response) -> {
            String str = request.body();
            Data tha1 = gson.fromJson(str, Data.class);
            String str1 = tha1.getName();
            str1 = str1 + "abc";
            tha1.setName(str1);
            return gson.toJson(tha1);
        });

        //THA2 ->
        AtomicReference<String> result = new AtomicReference<>("");
        get("/tha2/input/:string", (request, response) -> {
            var s = request.params(":string");
            result.set(result + s);
            return s;
        });

        get("/tha2/getAll", (request, response) -> {
            return result;
        });
    }
}
