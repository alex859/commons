package org.alex859.commons.function;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface MultiFunction<T, R> extends Function<T, Collection<R>>
{
    default <V> Function<T, List<V>> andThenToAll(Function<? super R, ? extends V> after)
    {
        return (T t) -> apply(t).stream().map(after).collect(Collectors.toList());
    }
}
