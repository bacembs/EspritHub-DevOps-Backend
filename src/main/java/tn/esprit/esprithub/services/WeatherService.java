package tn.esprit.esprithub.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class WeatherService {

    private String apiKey = "d0f0a438761295bcde444344fac2472a";
    private final String CITY = "Ariana";
    private final String COUNTRY = "TN";



    public boolean isRainyToday() {
        try {
            LocalDate currentDate = LocalDate.now();

            String weatherResponse = getWeatherForCity(CITY, COUNTRY);

            if (weatherResponse.contains("rain")) {
                return true;
            }

            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }




    public String getWeatherForCity(String city, String country) {
        try {
            String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "," + country + "&appid=" + apiKey;
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }




    //Zeydin
    public String getTomorrowWeatherForecastForAriana() throws IOException, InterruptedException {

        LocalDate tomorrow = LocalDate.now().plusDays(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String tomorrowDate = tomorrow.format(formatter);


        String url = "http://api.openweathermap.org/data/2.5/forecast?q=" + CITY + "," + COUNTRY +
                "&appid=" + apiKey;


        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


        String responseBody = response.body();

        return "Rainy";
    }


    public String getWeatherForecast() throws IOException, InterruptedException {
        String url = "http://api.openweathermap.org/data/2.5/forecast?q=Ariana,TN&appid=" + apiKey;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public boolean isWeatherCritical(String city, String country) {
        try {
            String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "," + country + "&appid=" + apiKey;
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> weatherMap = objectMapper.readValue(response.toString(), Map.class);

            // Access the weather details from the map and check if it's critical
            // Example: Check wind speed and weather conditions for rain
            Map<String, Object> wind = (Map<String, Object>) weatherMap.get("wind");
            double windSpeed = (double) wind.get("speed"); // Wind speed in meter/sec
            if (windSpeed > 15) { // Example threshold, adjust as needed
                return true;
            }

            // Check weather conditions for rain
            Object weatherObject = weatherMap.get("weather");
            if (weatherObject instanceof Iterable) {
                for (Object item : (Iterable<?>) weatherObject) {
                    if (item instanceof Map) {
                        Map<String, Object> weatherItem = (Map<String, Object>) item;
                        String description = (String) weatherItem.get("description");
                        if (description != null && description.contains("rain")) {
                            return true;
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}

