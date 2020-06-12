package ru.otus.client;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    //Client findByLogin(String login);
    Client findById(long id);
    //List<Client> findByLastNameStartsWithIgnoreCase(String lastName);
}
