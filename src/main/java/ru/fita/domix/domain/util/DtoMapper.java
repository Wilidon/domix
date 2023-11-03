package ru.fita.domix.domain.util;

public interface DtoMapper<T, D> {
    D toDto(T model);
}
