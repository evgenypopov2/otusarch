package ru.otus.health;

import io.micrometer.core.annotation.Timed;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController

@Timed(value = "client.controller.requests", histogram = true, percentiles = { 0.5, 0.95, 0.99 })
public class ClientController {
    //private static final Logger log = LoggerFactory.getLogger(ClientController.class);

    private final ClientRepository repository;

    public ClientController(ClientRepository repo) {
        repository = repo;
    }

    @Timed(value = "client.controller.requests", histogram = true, percentiles = { 0.5, 0.95, 0.99 })
    @GetMapping("/client")
    public List<Client> getClients() {
        //log.info("Getting full client list...");
        ArrayList<Client> clients = new ArrayList<>();
        for (Client client: repository.findAll()) {
            clients.add(client);
        }
        //log.info("Found "+ clients.size() +" client(s)");
        return clients;
    }

    @Timed(value = "client.controller.requests", histogram = true, percentiles = { 0.5, 0.95, 0.99 })
    @PostMapping("/client")
    public Client createClient(@RequestBody Client newClient) {
        //log.info("Creating new client...");
        Client client = repository.save(newClient);
        //log.info("New client created: " + client.toString());
        return client;
    }

    @Timed(value = "client.controller.requests", histogram = true, percentiles = { 0.5, 0.95, 0.99 })
    @GetMapping("/client/{id}")
    public Client getClient(@PathVariable("id") long id) {
        //log.info("Searching client with id=" + id + "...");
        Client client = repository.findById(id);
        //log.info((client != null ? "Found client" : "No client found") + " with id=" + id);
        return client;
    }

    @Timed(value = "client.controller.requests", histogram = true, percentiles = { 0.5, 0.95, 0.99 })
    @DeleteMapping("/client/{id}")
    public void deleteClient(@PathVariable("id") long id) {
        //log.info("Deleting client with id=" + id + "...");
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            ; //no action
        }
        //log.info("Deleted client with id=" + id);
    }

    @Timed(value = "client.controller.requests", histogram = true, percentiles = { 0.5, 0.95, 0.99 })
    @PutMapping("/client/{id}")
    public Client updateClient(@RequestBody Client clientData, @PathVariable Long id) {
        //log.info("Updating client with id=" + id + "...");
        return repository.findById(id)
                .map(client -> {
                    client.setFirstName(clientData.getFirstName());
                    client.setLastName(clientData.getLastName());
                    client.setEmail(clientData.getEmail());
                    client.setPhone(clientData.getPhone());
                    client = repository.save(client);
                    //log.info("Updated client with id=" + id + ": " + client.toString());
                    return client;
                })
                .orElseGet(() -> {
                    //log.info("No client found to update with id=" + id + ", will be created instead");
                    clientData.setId(id);
                    Client client = repository.save(clientData);
                    //log.info("New client created: " + client.toString());
                    return client;
                });
    }

}
