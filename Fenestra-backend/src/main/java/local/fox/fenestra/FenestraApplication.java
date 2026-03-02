package local.fox.fenestra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.resilience.annotation.EnableResilientMethods;

@EnableResilientMethods
@SpringBootApplication
public class FenestraApplication {

	public static void main(String[] args) {
		SpringApplication.run(FenestraApplication.class, args);
	}

}