package ru.otus.health;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
public class ClientController {
    private static final Logger log = LoggerFactory.getLogger(ClientController.class);

    private final ClientRepository clientRepository;

    public ClientController(ClientRepository repo) {
        clientRepository = repo;
    }

    @RequestMapping("/clients")
    public List<Client> clients() {
        log.info("Getting full client list...");
        ArrayList<Client> clients = new ArrayList<>();
        for (Client client: clientRepository.findAll()) {
            clients.add(client);
        }
        log.info("Found "+ clients.size() +" client(s)");
        return clients;
    }
    @RequestMapping("/clients/{id}")
    public Client client(@PathVariable("id") long id) {
        log.info("Searching client with id=" + id + "...");

        Client client = clientRepository.findById(id);
        log.info((client != null ? "Found client" : "No client found") + " with id=" + id);
        return client;
    }
}
