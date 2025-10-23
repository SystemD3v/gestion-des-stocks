package com.example.javafx.Controller;

import com.example.javafx.model.Model;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


public class Controller {

    public static List<Model.stock> retrieveStock(String apiURL) throws Exception{

        String response = apiConnection(apiURL);
        response = checkBracket(response);

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Model.stock>>(){}.getType();

        return gson.fromJson(response, listType);
    }

    public static List<Model.supplier> retrieveSupplier(String apiURL) throws Exception{

        String response = apiConnection(apiURL);
        response = checkBracket(response);

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Model.supplier>>(){}.getType();

        return gson.fromJson(response, listType);
    }

    public List<Model.user> retrieveUser(String apiURL) throws Exception{

        String response = apiConnection(apiURL);
        response = checkBracket(response);

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Model.user>>(){}.getType();

        return gson.fromJson(response, listType);
    }

    public List<Model.instance> retrieveInstance(String apiURL) throws Exception{

        String response = apiConnection(apiURL);
        response = checkBracket(response);

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Model.instance>>(){}.getType();

        return gson.fromJson(response, listType);
    }

    public List<Model.log> retrieveLog(String apiURL) throws Exception{

        String response = apiConnection(apiURL);
        response = checkBracket(response);

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Model.log>>(){}.getType();

        return gson.fromJson(response, listType);
    }


    private static String apiConnection(String apiURL) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/" + apiURL)).build();
        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    private static String checkBracket(String str){
        char[] chars = str.toCharArray();
        if (chars.length > 0 && chars[0] == '[' && chars[chars.length-1] == ']') {
            return str;
        }
        else{
            str = "[" + str + "]";
            return str;
        }

    }
}
