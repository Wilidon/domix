package ru.fita.domix.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fita.domix.data.model.Component;

public interface ComponentRepository extends JpaRepository<Component, Long> {

}
