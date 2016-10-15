package org.alex859.commons.function.impl;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class Validator<T> implements UnaryOperator<T>
{
    private final Predicate<T> predicate;
    private final Supplier<? extends RuntimeException> exceptionSupplier;

    public Validator(final Predicate<T> predicate, final Supplier<? extends RuntimeException> exceptionSupplier)
    {
        this.predicate = Objects.requireNonNull(predicate, "Predicate cannot be null");
        this.exceptionSupplier = Objects.requireNonNull(exceptionSupplier, "Exception supplier cannot be null");
    }

    @Override
    public T apply(final T t)
    {
        if (predicate.test(t))
        {
            return t;
        }

        throw exceptionSupplier.get();

    }

    public static <T> Validator<T> validate(final Predicate<T> predicate, final Supplier<? extends RuntimeException>
            exceptionSupplier)
    {
        return new Validator<>(predicate, exceptionSupplier);
    }
}
