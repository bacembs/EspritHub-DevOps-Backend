package tn.esprit.esprithub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.esprithub.services.WeatherService;

@RestController
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/weather/{city}/{country}")
    public String getWeatherForCity(@PathVariable String city, @PathVariable String country) {
        String apiKey = "d0f0a438761295bcde444344fac2472a";
        return weatherService.getWeatherForCity(city, country);
    }

    @GetMapping("/weather/tomorrowAriana")
    public String getTomorrowWeatherForAriana() throws Exception {
        return weatherService.getTomorrowWeatherForecastForAriana();
    }
}
