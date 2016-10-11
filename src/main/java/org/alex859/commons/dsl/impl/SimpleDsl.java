package org.alex859.commons.dsl.impl;

import org.alex859.commons.function.impl.ConditionalBiConsumer;
import org.alex859.commons.function.impl.ConditionalExceptionBiConsumer;
import org.alex859.commons.dsl.AfterCondition;
import org.alex859.commons.dsl.BeforeCondition;
import org.alex859.commons.dsl.BeforeConditionAlternative;
import org.alex859.commons.dsl.SimpleDslBuilder;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public class SimpleDsl<INPUT, OUTPUT> implements BiConsumer<INPUT, OUTPUT>
{
    private static final BiConsumer NO_OP_BI_CONSUMER = (input, output) ->
    {
    };

    private final BiConsumer<INPUT, OUTPUT> consumerAccumulator;

    private SimpleDsl(final Builder<INPUT, OUTPUT> builder)
    {
        this.consumerAccumulator = builder.consumerAccumulator;
    }

    @Override
    public void accept(final INPUT input, final OUTPUT output)
    {
        consumerAccumulator.accept(input, output);
    }

    public static <A, B> AfterCondition<A, B> when(final BiPredicate<A, B> predicate)
    {
        return new Builder<A, B>().when(predicate);
    }

    public static <A, B> AfterCondition<A, B> always()
    {
        return new Builder<A, B>().always();
    }

    private static class Builder<INPUT, OUTPUT> implements SimpleDslBuilder<INPUT, OUTPUT>,
            AfterCondition<INPUT, OUTPUT>
    {
        private BiConsumer<INPUT, OUTPUT> consumerAccumulator = NO_OP_BI_CONSUMER;
        private BiPredicate<INPUT, OUTPUT> currentPredicate;

        @Override
        public AfterCondition<INPUT, OUTPUT> when(final BiPredicate<INPUT, OUTPUT> predicate)
        {
            Objects.requireNonNull(predicate, "Predicate cannot be null");
            currentPredicate = predicate;
            return this;
        }

        @Override
        public AfterCondition<INPUT, OUTPUT> when(final Predicate<INPUT> predicate)
        {
            return when((input, output) -> predicate.test(input));
        }

        @Override
        public AfterCondition always()
        {
            return when(((input, output) -> true));
        }

        @Override
        public BeforeConditionAlternative<INPUT, OUTPUT> thenUse(final BiConsumer<INPUT, OUTPUT> consumer)
        {
            consumerAccumulator = consumerAccumulator.andThen(new ConditionalBiConsumer<>(currentPredicate,
                    consumer));
            return this;
        }

        @Override
        public BeforeConditionAlternative<INPUT, OUTPUT> thenUse(final Consumer<INPUT> consumer)
        {
            return thenUse((input, output) -> consumer.accept(input));
        }

        @Override
        public BeforeConditionAlternative<INPUT, OUTPUT> thenThrow(final Supplier<RuntimeException> exceptionSupplier)
        {
            consumerAccumulator = consumerAccumulator.andThen(new ConditionalExceptionBiConsumer<>(currentPredicate,
                    exceptionSupplier));
            return this;
        }

        @Override
        public AfterCondition<INPUT, OUTPUT> and(final BiPredicate<INPUT, OUTPUT> predicate)
        {
            Objects.requireNonNull(predicate, "Predicate cannot be null");
            currentPredicate = currentPredicate.and(predicate);
            return this;
        }

        @Override
        public AfterCondition<INPUT, OUTPUT> or(final BiPredicate<INPUT, OUTPUT> predicate)
        {
            Objects.requireNonNull(predicate, "Predicate cannot be null");
            currentPredicate = currentPredicate.or(predicate);
            return this;
        }

        @Override
        public AfterCondition<INPUT, OUTPUT> and(final Predicate<INPUT> predicate)
        {
            return and((input, output) -> predicate.test(input));
        }

        @Override
        public AfterCondition<INPUT, OUTPUT> or(final Predicate<INPUT> predicate)
        {
            return or((input, output) -> predicate.test(input));
        }

        @Override
        public AfterCondition<INPUT, OUTPUT> negate()
        {
            currentPredicate = currentPredicate.negate();
            return null;
        }

        @Override
        public SimpleDsl<INPUT, OUTPUT> build()
        {
            return new SimpleDsl<>(this);
        }

        @Override
        public BeforeCondition<INPUT, OUTPUT> orElse(final BiConsumer<INPUT, OUTPUT> consumer)
        {
            Objects.requireNonNull(currentPredicate, "Current predicate cannot be null");
            return when(currentPredicate.negate()).thenUse(consumer);
        }

        @Override
        public BeforeCondition<INPUT, OUTPUT> orElseThrow(final Supplier<RuntimeException> exceptionSupplier)
        {
            Objects.requireNonNull(currentPredicate, "Current predicate cannot be null");
            return when(currentPredicate.negate()).thenThrow(exceptionSupplier);
        }
    }
}
