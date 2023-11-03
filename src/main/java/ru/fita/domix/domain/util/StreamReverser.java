package ru.fita.domix.domain.util;

import java.util.LinkedList;
import java.util.stream.Stream;

public class StreamReverser {
    public static <T> Stream<T> reverse(Stream<T> stream) {
        LinkedList<T> stack = new LinkedList<>();
        stream.forEach(stack::push);

        return stack.stream();
    }
}
