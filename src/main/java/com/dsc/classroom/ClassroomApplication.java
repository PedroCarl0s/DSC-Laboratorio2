package com.dsc.classroom;

import com.dsc.classroom.models.Discipline;
import com.dsc.classroom.services.DisciplineService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class ClassroomApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClassroomApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(DisciplineService disciplineService) {
		return args -> {
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<Discipline>> mapType = new TypeReference<List<Discipline>>(){};
			InputStream input = TypeReference.class.getResourceAsStream("/json/disciplines.json");
			try {
				List<Discipline> disciplines = mapper.readValue(input, mapType);
				disciplineService.addDisciplines(disciplines);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		};
	}
}
