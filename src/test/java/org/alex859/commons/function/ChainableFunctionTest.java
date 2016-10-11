package org.alex859.commons.function;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.junit.Assert.*;

public class ChainableFunctionTest
{
    private final ChainableFunction<Integer, Integer> add3 = n -> n + 3;
    private final Function<Integer, Integer> multiplyBy2 = n -> n * 2;
    private final Function<Integer, String> toString = Object::toString;
    private final List<String> result = new ArrayList<>();
    private final Consumer<String> addToList = result::add;

    @Test
    public void chain_with_functions()
    {
        final Consumer<Integer> integerConsumer = add3.andThen(multiplyBy2).andThen(toString).andThen(addToList);
        // (2 + 3) * 2
        integerConsumer.accept(2);

        assertEquals(1, result.size());
        assertEquals("10", result.get(0));
    }
}