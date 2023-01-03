package com.jiripernik;

import javax.sound.sampled.*;
import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class VoiceTranscript extends Main {

    public void play() throws Exception {


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://voicerss-text-to-speech.p.rapidapi.com/?key=1b96146a424341839bf3154606788d71"))
                .header("content-type", "application/x-www-form-urlencoded")
                .header("X-RapidAPI-Key", "922f1b56b3mshe3feda7ece4558bp1586bejsn7d7aa314e322")
                .header("X-RapidAPI-Host", "voicerss-text-to-speech.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.ofString("V=Harry&src=" + sbJoke()))
                .build();
        HttpResponse<Path> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofFile(Paths.get("play.mp3")));

        File soundFile = new File("play.mp3");

        int EXTERNAL_BUFFER_SIZE = 524288;

        AudioInputStream audioInputStream = null;
        audioInputStream = AudioSystem.getAudioInputStream(soundFile);

        AudioFormat format = audioInputStream.getFormat();

        SourceDataLine auline = null;

        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        try {
            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        auline.start();

        int nBytesRead = 0;
        byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];

        try {
            while (nBytesRead != -1) {
                nBytesRead = audioInputStream.read(abData, 0, abData.length);
                if (nBytesRead >= 0) {
                    auline.write(abData, 0, nBytesRead);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        } finally {
            auline.drain();
            auline.close();
        }

    }

    public String sbJoke() throws Exception {
        StringBuilder sb_joke = new StringBuilder();

        String joke = randomizer();
        sb_joke.append(joke);
        sb_joke.insert(0,"\"");
        sb_joke.insert(sb_joke.length(), "\"");
        return String.valueOf(sb_joke);
    }
    public String randomizer() throws Exception {
        Scanner scanner = new Scanner(System.in);
        RandomJoke ranJoke = new RandomJoke();
        CategoryJoke catJoke = new CategoryJoke();

        System.out.println("Do you want to hear a random joke? Press Y/N: ");
        String choice = scanner.nextLine().toUpperCase();
        String joke = null;

        switch (choice) {
            case "Y":
                joke = ranJoke.getJoke();
                break;
            case "N":
                joke = catJoke.getJoke();
                break;
        }
        return joke;
    }
}
