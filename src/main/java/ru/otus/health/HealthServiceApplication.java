package ru.otus.health;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses=ClientRepository.class)
public class HealthServiceApplication {
	private static final Logger log = LoggerFactory.getLogger(HealthServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(HealthServiceApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner loadData(ClientRepository repository) {
//		return (args) -> {
//			// save a couple of Clients
//			repository.save(new Client("Jack", "Bauer"));
//			repository.save(new Client("Chloe", "O'Brian"));
//			repository.save(new Client("Kim", "Bauer"));
//			repository.save(new Client("David", "Palmer"));
//			repository.save(new Client("Michelle", "Dessler"));
//
//			// fetch all Clients
//			log.info("Clients found with findAll():");
//			log.info("-------------------------------");
//			for (Client Client : repository.findAll()) {
//				log.info(Client.toString());
//			}
//			log.info("");
//
//			// fetch an individual Client by ID
//			Client client = repository.findById(1L);
//			log.info("Client found with findOne(1L):");
//			log.info("--------------------------------");
//			log.info(client.toString());
//			log.info("");
//
//			// fetch Clients by last name
//			log.info("Client found with findByLastNameStartsWithIgnoreCase('Bloch'):");
//			log.info("--------------------------------------------");
//			for (Client cl : repository.findByLastNameStartsWithIgnoreCase("Bloch")) {
//				log.info(cl.toString());
//			}
//			log.info("");
//		};
//	}
//	@Autowired
//	JdbcTemplate jdbcTemplate;

//	@Override
//	public void run(String[] args) {
//
//		log.info("Creating tables");
//
//		jdbcTemplate.execute("DROP TABLE IF EXISTS client");
//		jdbcTemplate.execute("CREATE TABLE client(id BIGSERIAL PRIMARY KEY, first_name VARCHAR(255), last_name VARCHAR(255))");
//
//		// Split up the array of whole names into an array of first/last names
//		List<Object[]> splitUpNames = Stream.of("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long")
//				.map(name -> name.split(" "))
//				.collect(Collectors.toList());
//
//		// Use a Java 8 stream to print out each tuple of the list
//		splitUpNames.forEach(name -> log.info(String.format("Inserting client record for %s %s", name[0], name[1])));
//
//		// Uses JdbcTemplate's batchUpdate operation to bulk load data
//		jdbcTemplate.batchUpdate("INSERT INTO client(first_name, last_name) VALUES (?,?)", splitUpNames);
//
//		log.info("" + splitUpNames.size() + " clients inserted");
//	}
}
