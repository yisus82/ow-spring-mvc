package com.liceolapaz.secondhandmarket;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.liceolapaz.secondhandmarket.models.Product;
import com.liceolapaz.secondhandmarket.models.User;
import com.liceolapaz.secondhandmarket.services.ProductService;
import com.liceolapaz.secondhandmarket.services.UserService;
import com.liceolapaz.secondhandmarket.storage.StorageService;

@SpringBootApplication
public class SecondHandMarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecondHandMarketApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(UserService userService, ProductService productService) {
		return args -> {
			User user1 = new User("Luis Miguel", "López Magaña", null, "luismi.lopez@openwebinars.net", "luismi");
			user1 = userService.register(user1);

			User user2 = new User("Jesús Ángel", "Pérez-Roca Fernández", null, "jprf.liceo@gmail.com", "yisus");
			user2 = userService.register(user2);

			List<Product> productList = Arrays.asList(new Product("Mountain Bike", 100.0f,
					"https://images-na.ssl-images-amazon.com/images/I/61TiSCBcWaL._SX425_.jpg", user1),
					new Product("Golf GTI Serie 2", 2500.0f,
							"https://www.minicar.es/large/Volkswagen-Golf-GTi-G60-Serie-II-%281990%29-Norev-1%3A18-i22889.jpg",
							user1),
					new Product("Tennis Racket", 10.5f,
							"https://imgredirect.milanuncios.com/fg/2311/04/tenis/Raqueta-tenis-de-segunda-mano-en-Madrid-231104755_1.jpg?VersionId=T9dPhTf.3ZWiAFjnB7CvGKsvbdfPLHht",
							user1),
					new Product("Xbox One X", 425.0f, "https://images.vibbo.com/635x476/860/86038583196.jpg", user2),
					new Product("Flexible tripod", 10.0f, "https://images.vibbo.com/635x476/860/86074256163.jpg",
							user2),
					new Product("Iphone 7 128 GB", 350.0f,
							"https://store.storeimages.cdn-apple.com/4667/as-images.apple.com/is/image/AppleInc/aos/published/images/i/ph/iphone7/rosegold/iphone7-rosegold-select-2016?wid=470&hei=556&fmt=jpeg&qlt=95&op_usm=0.5,0.5&.v=1472430205982",
							user2));

			productList.forEach(productService::insert);
		};
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}

}
