package tn.esprit.esprithub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.esprithub.services.WeatherService;

@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/{city}/{country}")
    public String getWeatherForCity(@PathVariable String city, @PathVariable String country) {
        String apiKey = "d0f0a438761295bcde444344fac2472a";
        return weatherService.getWeatherForCity(city, country);
    }

    @GetMapping("/tomorrowAriana")
    public String getTomorrowWeatherForAriana() throws Exception {
        return weatherService.getTomorrowWeatherForecastForAriana();
    }

    @GetMapping("/forecast")
    public String getWeatherForecasttt() throws Exception {
        return weatherService.getWeatherForecast();
    }

    @GetMapping("/critical")
    public String checkCriticalWeather(
            @RequestParam String city,
            @RequestParam String country
    ) {
        boolean isCritical = weatherService.isWeatherCritical(city, country);
        if (isCritical) {
            return "Critical weather conditions detected in " + city + ", " + country;
        } else {
            return "No critical weather conditions detected in " + city + ", " + country;
        }
    }
}
