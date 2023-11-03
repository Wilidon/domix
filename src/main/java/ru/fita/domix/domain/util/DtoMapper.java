package ru.fita.domix.domain.util;

public interface DtoMapper<T, D> {
    D toDto(T model);
//    default Set<D> toDtoSet(Set<T> modelSet) {
//        Set<D> dSet = new LinkedHashSet<>();
//        for (T t : modelSet) {
//            dSet.add(toDto(t));
//        }
//        return dSet;
//    }
}
