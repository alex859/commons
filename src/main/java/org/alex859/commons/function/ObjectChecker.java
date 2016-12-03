package org.alex859.commons.function;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * It might be necessary doing something like:
 * <code>
 *      if (condition) {
 *              return Optional.of(object).map(someFunction);
 *      }
 *
 *      return Optional.empty();
 * </code>
 *
 *  The same can be done in a cleaner way using:
 *
 *  <code>
 *      ObjectChecker.check(object).on(condition).map(someFunction)
 *  </code>
 *
 * @param <T>
 */
public interface ObjectChecker<T>
{
    public static <T> ObjectChecker<T> check(final T object)
    {
        return predicate ->
                Objects.nonNull(object) && predicate.test(object) ? Optional.of(object) : Optional.empty();
    }

    Optional<T> on(Predicate<T> predicate);
}
