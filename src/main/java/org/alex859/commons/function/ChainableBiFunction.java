package org.alex859.commons.function;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@FunctionalInterface
public interface ChainableBiFunction<T, U, R> extends BiFunction<T, U, R>
{
    @Override
    default <V> ChainableBiFunction<T, U, V> andThen(final Function<? super R, ? extends V> after)
    {
        Objects.requireNonNull(after, "After function cannot be null");
        return (T t, U u) -> after.apply(apply(t, u));
    }

    default BiConsumer<T, U> andThen(final Consumer<? super R> after)
    {
        Objects.requireNonNull(after, "BiConsumer cannot be null");
        return (T t, U u) -> after.accept(apply(t, u));
    }
}
