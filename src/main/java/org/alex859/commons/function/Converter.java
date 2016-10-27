package org.alex859.commons.function;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Defines the action ov converting an object of class INPUT to an object of class OUTPUT.
 *
 * @param <INPUT>  The input class type.
 * @param <OUTPUT> The output class type.
 */
@FunctionalInterface
public interface Converter<INPUT, OUTPUT> extends Function<INPUT, OUTPUT>
{
    /**
     * Converts the input object in the output object.
     *
     * @param input The input object.
     * @return The output object.
     */
    OUTPUT convert(final INPUT input);

    @Override
    default OUTPUT apply(final INPUT input)
    {
        Objects.requireNonNull(input, "Converter input cannot be null");
        return convert(input);
    }

    /**
     * Adds a population step to the given {@link Converter}.
     *
     * @param populator The populator to run after this converter.
     * @return A new {@link Converter} that also runs the provided {@link Populator}.
     */
    default Converter<INPUT, OUTPUT> populatingWith(final Populator<INPUT, OUTPUT> populator)
    {
        Objects.requireNonNull(populator, "Populator cannot be null");
        return source ->
        {
            final OUTPUT result = apply(source);
            populator.accept(source, result);

            return result;
        };
    }

    /**
     * Adds a population step to the given {@link Converter}.
     * A partially applied {@link Populator} can be can from {@link Populator#withSource(Object)}.
     *
     * @param populator The partial populator to run after this converter.
     * @return A new {@link Converter} that also runs the provided {@link Populator}.
     */
    default Converter<INPUT, OUTPUT> populatingWith(final Consumer<OUTPUT> populator)
    {
        Objects.requireNonNull(populator, "Consumer cannot be null");
        return source ->
        {
            final OUTPUT result = apply(source);
            populator.accept(result);

            return result;
        };
    }

    /**
     * Adds a population step to the given {@link Converter}. The additional step will be available in the future as
     * a result of the provided function: the returned {@link Converter} will convert the source into a {@link CompletableFuture}.
     *
     * @param futurePopulatorFunction The future populator to run after this converter.
     * @return A new {@link Converter} that also runs the provided {@link Populator}
     */
    default Converter<INPUT, CompletableFuture<OUTPUT>> populatingWithFuture(final Function<OUTPUT, CompletableFuture<Void>>
                                                                                     futurePopulatorFunction)
    {
        Objects.requireNonNull(futurePopulatorFunction, "Future consumer function cannot be null");
        return source ->
                CompletableFuture.supplyAsync(() -> apply(source))
                        .thenCompose(o -> futurePopulatorFunction.apply(o).thenApply(aVoid -> o));
    }

    /**
     * Helper method to be able to create a {@link Converter} like:
     * Example:
     * <code>
     * Converter<String, Integer> converter = Converter.from(Pojo1.class).to(Pojo2.class);
     * </code>
     *
     * @param sourceClass The source class.
     * @param <S>         The source class type.
     * @return An {@link AfterFrom} object to be used to finish the {@link Converter} creation.
     */
    static <S> AfterFrom<S> from(final Class<S> sourceClass)
    {
        return new AfterFrom<S>()
        {
            @Override
            public <T> Converter<S, T> to(final Class<T> targetClass)
            {
                Objects.requireNonNull(targetClass, "Target class cannot be null");

                return source ->
                {
                    try
                    {
                        return targetClass.newInstance();
                    }
                    catch (InstantiationException | IllegalAccessException e)
                    {
                        throw new IllegalStateException("Unable to instantiate class.", e);
                    }
                };
            }

            @Override
            public <T> Converter<S, T> to(final Supplier<T> targetSupplier)
            {
                Objects.requireNonNull(targetSupplier, "Target supplier cannot be null");

                return source -> targetSupplier.get();
            }
        };
    }

    /**
     * Helper interface to create a new {@link Converter}.
     *
     * @param <S> The source type.
     */
    interface AfterFrom<S>
    {
        <T> Converter<S, T> to(Class<T> targetClass);

        <T> Converter<S, T> to(Supplier<T> targetSupplier);
    }
}
