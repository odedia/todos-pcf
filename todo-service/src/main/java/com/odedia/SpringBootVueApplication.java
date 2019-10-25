package com.odedia;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@RestController
public class SpringBootVueApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootVueApplication.class, args);
	}

	@GetMapping("processing")
	public @ResponseBody String processData(HttpServletRequest request) {
		return ("Remote address is " + request.getRemoteAddr());
	}

    @GetMapping("amit")
    public String hello(){
        return "Amit";
    }

}

@Entity
@Data
@NoArgsConstructor
class Todo {
	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	private String title;

	private Boolean completed = false;
}

@RepositoryRestResource(collectionResourceRel = "todos", path = "todos")
interface TodoRepository extends JpaRepository<Todo, Long> {
}

@Slf4j
@RepositoryEventHandler(Todo.class)
@EnableBinding(Source.class)
class TaskEventHandler {
	@Autowired Source source;

	@HandleBeforeSave
	public void handleBeforeSave(Todo todo) {
		if (todo.getCompleted()) {

			log.info("Sending completed message for {}", todo.getTitle());
			source.output().send(MessageBuilder.withPayload(todo.getTitle()).build());
		}
	}
}

@Component
class RestRepositoryConfigurator implements RepositoryRestConfigurer {

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config.exposeIdsFor(Todo.class);
	}
}


@Component
@Endpoint(id = "todos")
@RequiredArgsConstructor
class AccessLogActuator {
	@Autowired
	TodoRepository repo;

	@ReadOperation
	public AccessLogActuatorValues get() {
		return new AccessLogActuatorValues(repo.count());
	}
}

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
class AccessLogActuatorValues {
	private final long total;
}

/**
 * Export metrics to Micrometer.
 */
@Configuration
@RequiredArgsConstructor
class AccessLogMicrometer {
	@Autowired
	TodoRepository repo;

	@Bean
	public Gauge accessLogCounter(MeterRegistry registry) {
		return Gauge.builder("todos.total", () -> repo.count()).tag("kind", "performance")
				.description("Todos total count").register(registry);
	}
}

