package ru.fita.domix.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fita.domix.data.model.Step;

public interface StepRepository extends JpaRepository<Step, Long> {

}
