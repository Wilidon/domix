package ru.fita.domix.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fita.domix.data.model.Calculator;

import java.util.Optional;

public interface CalculatorRepository extends JpaRepository<Calculator, Long> {
    Optional<Calculator> findByStatus(String string);
}
