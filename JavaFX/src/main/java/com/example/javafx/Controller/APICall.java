package com.example.javafx.Controller;

import com.example.javafx.model.Model;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


public class APICall {


    public static void delete(String apiURL, String ID) throws Exception {
        apiConnectionDelete(apiURL + ID);
    }

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
    public static List<Model.supplier> retrieveSupplierById(String apiURL,String id) throws Exception{

        String response = apiConnection(apiURL+id);
        response = checkBracket(response);

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Model.supplier>>(){}.getType();

        return gson.fromJson(response, listType);
    }
    public static void editSupplier(String apiURL,String id , String name, String phone, String address) throws Exception {
        apiConnection(apiURL+id+"/"+name+"/"+phone+"/"+address);
    }
    public static void createSupplier(String apiURL, String name, String phone, String address) throws Exception {
        String jsonInputString = String.format(
                "{\"supplier_name\":\"%s\",\"supplier_phone\":\"%s\",\"supplier_address\":\"%s\"}",
                name, phone, address
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/" + apiURL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonInputString))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Create Supplier Response: " + response.body());
    }


    public static List<Model.user> retrieveUser(String apiURL) throws Exception{

        String response = apiConnection(apiURL);
        response = checkBracket(response);

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Model.user>>(){}.getType();

        return gson.fromJson(response, listType);
    }

    public static List<Model.Instance> retrieveInstance(String apiURL) throws Exception{

        String response = apiConnection(apiURL);
        response = checkBracket(response);

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Model.Instance>>(){}.getType();

        return gson.fromJson(response, listType);
    }

    public List<Model.log> retrieveLog(String apiURL) throws Exception{

        String response = apiConnection(apiURL);
        response = checkBracket(response);

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Model.log>>(){}.getType();

        return gson.fromJson(response, listType);
    }


    public static String apiConnection(String apiURL) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/" + apiURL)).build();
        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
    private static String apiConnectionDelete(String apiURL) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/" + apiURL)).DELETE().build();
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
    public static boolean addStock(Model.stock newStock) throws Exception {
        String jsonBody = getString(newStock);

        System.out.println("Envoi vers l'API : " + jsonBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/createStock"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Réponse API : " + response.statusCode() + " - " + response.body());

        return response.statusCode() == 200 || response.statusCode() == 201;
    }
    /**
     * Supprime un stock via l'API
     */
    /**
     * Met à jour la quantité d'un stock via l'API
     */
    public static boolean updateStockQuantity(int stockId, int newQuantity) throws Exception {
        System.out.println("Mise à jour de la quantité du stock ID " + stockId + " : " + newQuantity);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/updateStock/" + stockId + "/available_quantity/" + newQuantity))
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Réponse API : " + response.statusCode());

        return response.statusCode() == 200;
    }

    /**
     * Supprime un stock via l'API
     */
    public static boolean deleteStock(int stockId) throws Exception {
        System.out.println("Suppression du stock ID " + stockId);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/deleteStockById/" + stockId))
                .DELETE()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Réponse API : " + response.statusCode() + " - " + response.body());

        return response.statusCode() == 200;
    }

    private static String getString(Model.stock newStock) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("label", newStock.label);
        jsonObject.addProperty("years", newStock.years);
        jsonObject.addProperty("genre", newStock.genre);
        jsonObject.addProperty("area", newStock.area);
        jsonObject.addProperty("available_quantity", newStock.available_quantity);
        jsonObject.addProperty("price", newStock.price);
        jsonObject.addProperty("supplier_id", newStock.supplier_id);

        String jsonBody = jsonObject.toString();
        return jsonBody;
    }
}
