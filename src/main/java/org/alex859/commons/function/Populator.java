package org.alex859.commons.function;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Functional definition of a populator, can be used together with {@link Converter}.
 * Defines the action of populating a target object from a given source object.
 *
 * @author alex859 (alessandro.ciccimarra@gmail.com).
 */
@FunctionalInterface
public interface Populator<S, T> extends BiConsumer<S, T>
{
    /**
     * Populates the target object from the given source object.
     *
     * @param source The source object.
     * @param target The target object.
     */
    void populate(final S source, final T target);

    @Override
    default void accept(final S source, final T target)
    {
        Objects.requireNonNull(source, "Source cannot be null.");
        Objects.requireNonNull(target, "Target cannot be null.");

        populate(source, target);
    }

    /**
     * Creates a {@link Consumer} able to populate a target object. The source object is passed as a parameter.
     *
     * @param source The source object.
     * @return The partially applied populator.
     */
    default Consumer<T> withSource(final S source)
    {
        Objects.requireNonNull(source, "Source cannot be null");
        return target -> populate(source, target);
    }

    /**
     * Creates a "future" {@link Consumer} (defined as {@link Function} T -> {@link CompletableFuture})able to
     * populate a target object. The source object will be available in the future.
     *
     * @param sourceFuture The source object available in future.
     * @return The partially applied populator.
     */
    default Function<T, CompletableFuture<Void>> withFutureSource(final CompletableFuture<S> sourceFuture)
    {
        Objects.requireNonNull(sourceFuture, "Source future cannot be null.");
        return target ->
                sourceFuture.thenAccept(
                        source ->
                                populate(source, target)
                );
    }

    /**
     * Creates a "future" {@link Consumer} (defined as {@link Function} T -> {@link CompletableFuture})able to
     * populate a target object. The source object will be available in the future as the result of the provided
     * {@link Function}.
     *
     * @param sourceFutureFunction The function that will generate the source object available in future.
     * @return The partially applied populator.
     */
    default Function<T, CompletableFuture<Void>> withFutureSource(final Function<T, CompletableFuture<S>>
                                                                          sourceFutureFunction)
    {
        Objects.requireNonNull(sourceFutureFunction, "Source future function cannot be null.");
        return target ->
                sourceFutureFunction.apply(target)
                        .thenAccept(source ->
                                populate(source, target));
    }
}
