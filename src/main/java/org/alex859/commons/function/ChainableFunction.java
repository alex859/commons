package org.alex859.commons.function;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

@FunctionalInterface
public interface ChainableFunction<T, R> extends Function<T, R>
{
    @Override
    default <V> ChainableFunction<T, V> andThen(final Function<? super R, ? extends V> after)
    {
        Objects.requireNonNull(after, "After function cannot be null");
        return (T t) -> after.apply(apply(t));
    }

    default Consumer<T> andThen(final Consumer<? super R> after)
    {
        Objects.requireNonNull(after, "Consumer cannot be null");
        return (T t) -> after.accept(apply(t));
    }
}
