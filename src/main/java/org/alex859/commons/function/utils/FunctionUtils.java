package org.alex859.commons.function.utils;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Set of {@link Function} related utility methods.
 */
public class FunctionUtils
{
    public static <T, A> Predicate<T> eq(final Function<T, A> attributeExtractor, final A attributeValue)
    {
      return obj -> Objects.equals(attributeValue, attributeExtractor.apply(obj));
    }

    public static <I, O> Optional<O> firstNotNull(final I input, final Function<I, O>... functions)
    {
        return Stream.of(functions)
                .map(f -> f.apply(input))
                .filter(Objects::nonNull)
                .findFirst();
    }

    public static <O> Optional<O> firstNotNull(final Supplier<O>... suppliers)
    {
        return Stream.of(suppliers)
                .map(Supplier::get)
                .filter(Objects::nonNull)
                .findFirst();
    }

    public static <O> Optional<O> firstNotNull(final O... objects)
    {
        return Stream.of(objects)
                .filter(Objects::nonNull)
                .findFirst();
    }
 }
