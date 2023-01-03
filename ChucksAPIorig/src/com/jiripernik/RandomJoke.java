package com.jiripernik;

import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RandomJoke {

     public HttpResponse<String> httpCall() throws Exception {

          HttpRequest request = HttpRequest.newBuilder()
          .uri(new URI("https://api.chucknorris.io/jokes/random"))
          .build();

          HttpClient client = HttpClient.newHttpClient();

          HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
          return response;
     }

     public RandomJokeTranscript getTranscript() throws Exception {

          RandomJokeTranscript jSonRandomJokeTranscript = new RandomJokeTranscript();
          Gson gson = new Gson();

          HttpResponse<String> response = httpCall();
          jSonRandomJokeTranscript = gson.fromJson(response.body(), RandomJokeTranscript.class);
          return jSonRandomJokeTranscript;
     }

     public String getJoke() throws Exception {
          String joke = getTranscript().getValue();
          System.out.println(joke);
          return joke;
     }
}
