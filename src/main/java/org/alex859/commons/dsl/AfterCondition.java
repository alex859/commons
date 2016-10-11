package org.alex859.commons.dsl;

import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface AfterCondition<INPUT, OUTPUT>
{
    BeforeConditionAlternative<INPUT, OUTPUT> thenUse(BiConsumer<INPUT, OUTPUT> consumer);

    BeforeConditionAlternative<INPUT, OUTPUT> thenUse(Consumer<INPUT> consumer);

    BeforeConditionAlternative<INPUT, OUTPUT> thenThrow(Supplier<RuntimeException> exceptionSupplier);

    AfterCondition<INPUT, OUTPUT> and(BiPredicate<INPUT, OUTPUT> predicate);

    AfterCondition<INPUT, OUTPUT> or(BiPredicate<INPUT, OUTPUT> predicate);

    AfterCondition<INPUT, OUTPUT> and(Predicate<INPUT> predicate);

    AfterCondition<INPUT, OUTPUT> or(Predicate<INPUT> predicate);

    AfterCondition<INPUT, OUTPUT> negate();
}
