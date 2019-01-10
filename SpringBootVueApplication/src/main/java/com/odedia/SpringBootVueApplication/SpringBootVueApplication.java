package com.odedia.SpringBootVueApplication;

import java.util.stream.Stream;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootVueApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootVueApplication.class, args);
	}
    
	// Bootstrap some test data into the in-memory database
    @Bean  
    ApplicationRunner init(TodoRepository repository) {  
        return args -> {  
            Stream.of("Buy milk", "Eat pizza", "Write tutorial", "Study Vue.js", "Go kayaking").forEach(name -> {  
                    Todo todo = new Todo();  
                    todo.setTitle(name);  
                    repository.save(todo);  
            });  
            repository.findAll().forEach(System.out::println);  
        };  
    }  

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

