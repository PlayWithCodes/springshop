package myshop.springshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringshopApplication.class, args);
		Hello h = new Hello();
		h.setName("test");
		System.out.println("h.getName() = " + h.getName());
	}

}
