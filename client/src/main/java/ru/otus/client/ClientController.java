package ru.otus.client;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/client")
public class ClientController {

    private final ClientRepository repository;

    public ClientController(ClientRepository repo) {
        repository = repo;
    }

    @GetMapping("")
    public List<Client> getClients() {
        ArrayList<Client> clients = new ArrayList<>();
        for (Client client: repository.findAll()) {
            clients.add(client);
        }
        return clients;
    }

    @PostMapping("")
    public Client createClient(@RequestBody Client newClient) {
        return repository.save(newClient);
    }

    @GetMapping("/{id}")
    public Client getClient(@PathVariable("id") long id) {
        return repository.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable("id") long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            ; //no action
        }
    }

    @PutMapping("/{id}")
    public Client updateClient(@RequestBody Client clientData, @PathVariable Long id) {
        return repository.findById(id)
                .map(client -> {
                    client.setFirstName(clientData.getFirstName());
                    client.setLastName(clientData.getLastName());
                    client.setEmail(clientData.getEmail());
                    client.setPhone(clientData.getPhone());
                    client = repository.save(client);
                    return client;
                })
                .orElseGet(() -> {
                    clientData.setId(id);
                    Client client = repository.save(clientData);
                    return client;
                });
    }
}
