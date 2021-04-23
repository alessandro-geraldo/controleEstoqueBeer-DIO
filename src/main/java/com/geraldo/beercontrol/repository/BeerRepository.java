package com.geraldo.beercontrol.repository;

import com.geraldo.beercontrol.DTO.BeerDTO;
import com.geraldo.beercontrol.entity.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BeerRepository extends JpaRepository<Beer, Long> {
    Optional<Beer> findByName(String nome);
}
