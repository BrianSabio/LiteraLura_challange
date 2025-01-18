package com.example.literalura.LiterAlura;

import com.example.literalura.LiterAlura.principal.Principal;
import com.example.literalura.LiterAlura.repository.IAutoresRepository;
import com.example.literalura.LiterAlura.repository.ILibrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

	@Autowired
	private IAutoresRepository autoresRepository;
	@Autowired
	private ILibrosRepository librosRepository;
	public static void main(String[] args) {
		SpringApplication.run(com.example.literalura.LiterAlura.LiterAluraApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(autoresRepository, librosRepository);
		principal.muestraElMenu();
	}
}
