package com.odedia.SpringBootVueApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootVueApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootVueApplication.class, args);
	}
    //Demo
// We actually don't need CORS configuration while running on PCF,
// Because we can map both frontend and backend to the same domain name
// and separate the applications based on path.
    
//    @Bean
//    public FilterRegistrationBean simpleCorsFilter() {  
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();  
//        CorsConfiguration config = new CorsConfiguration();  
//        config.setAllowCredentials(true); 
//        // *** URL below needs to match the Vue client URL and port ***
//        config.setAllowedOrigins(Collections.singletonList("http://localhost:8080")); 
//        config.setAllowedMethods(Collections.singletonList("*"));  
//        config.setAllowedHeaders(Collections.singletonList("*"));  
//        source.registerCorsConfiguration("/**", config);  
//        FilterRegistrationBean bean = new FilterRegistrationBean<>(new CorsFilter(source));
//        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);  
//        return bean;  
//    }   

}

