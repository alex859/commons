package org.alex859.commons.function.impl;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

/**
 * Checks a condition before applying the logic.
 *
 * @param <INPUT>
 * @param <OUTPUT>
 */
public class ConditionalBiConsumer<INPUT, OUTPUT> implements BiConsumer<INPUT, OUTPUT>
{
    private final BiPredicate<INPUT, OUTPUT> predicate;
    private final BiConsumer<INPUT, OUTPUT> consumer;

    public ConditionalBiConsumer(final BiPredicate<INPUT, OUTPUT> predicate, final BiConsumer<INPUT, OUTPUT> consumer)
    {
        this.predicate = Objects.requireNonNull(predicate, "Predicate cannot be null");
        this.consumer = Objects.requireNonNull(consumer, "Consumer cannot be null");
    }

    @Override
    public void accept(final INPUT input, final OUTPUT output)
    {
        if (predicate.test(input, output))
        {
            consumer.accept(input, output);
        }
    }
}
