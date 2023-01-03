package com.jiripernik;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class CategoryJoke {

    public void displayCategories() {
        System.out.println("\s Choose category: \n".toUpperCase() +
                "1) animal \n" +
                "2) career\n" +
                "3) celebrity\n" +
                "4) dev\n" +
                "5) explicit\n" +
                "6) fashion\n" +
                "7) food\n" +
                "8) history\n" +
                "9) money\n" +
                "10) movie\n" +
                "11) music\n" +
                "12) political\n" +
                "13) religion\n" +
                "14) science\n" +
                "15) sport\n" +
                "16) travel");
    }

    public HttpResponse<String> categoryHttpCall()  throws Exception {

        Scanner scanner = new Scanner(System.in);
        displayCategories();

        int choice = 0;
        String s = null;
        choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {

            case 1:
                s = "animal";
                break;
            case 2:
                s = "career";
                break;
            case 3:
                s = "celebrity";
                break;
            case 4:
                s = "dev";
                break;
            case 5:
                s = "explicit";
                break;
            case 6:
                s = "fashion";
                break;
            case 7:
                s = "food";
                break;
            case 8:
                s = "history";
                break;
            case 9:
                s = "money";
                break;
            case 10:
                s = "movie";
                break;
            case 11:
                s = "music";
                break;
            case 12:
                s = "political";
                break;
            case 13:
                s = "religion";
                break;
            case 14:
                s = "science";
                break;
            case 15:
                s = "sport";
                break;
            case 16:
                s = "travel";
                break;
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.chucknorris.io/jokes/random?category=" + s ))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    public CategoryJokeTranscript getCategTranscript() throws Exception {
        CategoryJokeTranscript categJokeTranscript = new CategoryJokeTranscript();
        Gson gson = new Gson();

        HttpResponse<String> response = categoryHttpCall();
        categJokeTranscript = gson.fromJson(response.body(), CategoryJokeTranscript.class);
        return categJokeTranscript;
    }
    public String getJoke() throws Exception {

        String joke = getCategTranscript().getValue();
        System.out.println(joke);
        return joke;
    }
}
