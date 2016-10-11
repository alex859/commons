package org.alex859.commons.dsl;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public interface BeforeCondition<INPUT, OUTPUT>
{
    AfterCondition<INPUT, OUTPUT> when(BiPredicate<INPUT, OUTPUT> predicate);

    AfterCondition<INPUT, OUTPUT> when(Predicate<INPUT> predicate);

    AfterCondition<INPUT, OUTPUT> always();
}
