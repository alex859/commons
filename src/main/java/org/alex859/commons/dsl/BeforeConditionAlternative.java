package org.alex859.commons.dsl;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public interface BeforeConditionAlternative<INPUT, OUTPUT> extends BeforeCondition<INPUT, OUTPUT>
{
    BeforeCondition<INPUT, OUTPUT> orElse(BiConsumer<INPUT, OUTPUT> consumer);

    BeforeCondition<INPUT, OUTPUT> orElseThrow(Supplier<RuntimeException> exceptionSupplier);
}
