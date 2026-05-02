package com.vitorbetmann.restaurant.repositories;

import com.vitorbetmann.restaurant.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByNameContaining(String name);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndIdNot(String email);
}
