package com.jiripernik;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Categories extends RandomJoke {

    List<String> categories = new ArrayList<>();
    public Categories() {
    }

    public String categoryRequest() {

        String resp = null;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.chucknorris.io/jokes/categories"))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            resp = response.body();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return resp;
    }
    public List<String> makeCategories() {
        String request = categoryRequest();
        String[] categ = request.substring(request.indexOf("\""), request.indexOf("]")).replaceAll("\"","").split(",");
        Collections.addAll(categories, categ);
        return categories;
    }
    public List<String> getCategories() {
        makeCategories();
        return categories;
    }

    @Override
    public String toString() {
        return "Categories{" +
                "categories=" + categories +
                '}';
    }
}
