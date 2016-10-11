package org.alex859.commons.function.utils;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class FunctionUtils
{
    public static <T, A> Predicate<T> eq(final Function<T, A> attributeExtractor, final A attributeValue)
    {
      return obj -> Objects.equals(attributeValue, attributeExtractor.apply(obj));
    }
 }
