package org.alex859.commons.function;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public class ChainableBiFunctionTest
{
    private final ChainableBiFunction<Integer, Integer, Integer> add = (n, m) -> n + m;
    private final Function<Integer, Integer> multiplyBy2 = n -> n * 2;
    private final Function<Integer, String> toString = Object::toString;
    private final List<String> result = new ArrayList<>();
    private final Consumer<String> addToList = result::add;

    @Test
    public void chain_with_functions()
    {
        final BiConsumer<Integer, Integer> integerConsumer = add.andThen(multiplyBy2).andThen(toString).andThen
                (addToList);
        // (2 + 3) * 2
        integerConsumer.accept(2, 3);

        assertEquals(1, result.size());
        assertEquals("10", result.get(0));
    }
}