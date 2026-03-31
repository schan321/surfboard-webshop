package com.saltyplank.webshop.repository;
import com.saltyplank.webshop.models.Gebruiker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface GebruikerRepository extends JpaRepository<Gebruiker, Long> {
    Optional<Gebruiker> findByEmail(String email);
    boolean existsByEmail(String email);
}
