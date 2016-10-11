package org.alex859.commons.dsl;

import org.alex859.commons.dsl.impl.SimpleDsl;

public interface SimpleDslBuilder<INPUT, OUTPUT> extends BeforeConditionAlternative<INPUT, OUTPUT>
{
    SimpleDsl<INPUT, OUTPUT> build();
}
