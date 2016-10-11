package org.alex859.commons.function.impl;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

/**
 * Checks a condition before throwing an exception.
 *
 * @param <INPUT>
 * @param <OUTPUT>
 */
public class ConditionalExceptionBiConsumer<INPUT, OUTPUT> implements BiConsumer<INPUT, OUTPUT>
{
    private final BiPredicate<INPUT, OUTPUT> predicate;
    private final Supplier<RuntimeException> exceptionSupplier;

    public ConditionalExceptionBiConsumer(final BiPredicate<INPUT, OUTPUT> predicate, final
    Supplier<RuntimeException> exceptionSupplier)
    {
        this.predicate = Objects.requireNonNull(predicate, "Predicate cannot be null");
        this.exceptionSupplier = Objects.requireNonNull(exceptionSupplier, "Exception supplier cannot be null");
    }

    @Override
    public void accept(final INPUT input, final OUTPUT output)
    {
        if (predicate.test(input, output))
        {
            throw exceptionSupplier.get();
        }
    }
}
