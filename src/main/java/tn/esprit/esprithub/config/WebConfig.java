package tn.esprit.esprithub.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
   @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/photos/**")
                .addResourceLocations("file:C:/Users/HP/IdeaProjects/EspritHub/src/main/resources/static/photos/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:4200") // Utiliser allowedOriginPatterns
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .exposedHeaders("Access-Control-Allow-Origin")
                .allowCredentials(true)
                .maxAge(3600);
    }

}






