package org.alex859.commons.function.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;
import java.util.function.Function;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FunctionUtilsTest
{
    private static final String S1 = "YAAY1";
    private static final String S2 = "YAAY2";

    @Mock
    private Function<Integer, String> f1;

    @Mock
    private Function<Integer, String> f2;

    @Test
    public void firstNotNull_given_bothFunctionsReturnNotNull_then_firstOnesResultsIsReturnedAndSecondNeverCalled()
            throws Exception
    {

        given(f1.apply(any())).willReturn(S1);
        given(f2.apply(any())).willReturn(S2);

        final Optional<String> result = FunctionUtils.firstNotNull(1, f1, f2);
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(S1, result.get());
        verify(f2, never()).apply(any());
    }

    @Test
    public void firstNotNull_given_secondReturnNotNull_then_secondOnesResultsIsReturnedAndBothAreCalled()
            throws Exception
    {

        given(f1.apply(any())).willReturn(null);
        given(f2.apply(any())).willReturn(S2);

        final Optional<String> result = FunctionUtils.firstNotNull(1, f1, f2);
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(S2, result.get());
        verify(f1, times(1)).apply(any());
        verify(f2, times(1)).apply(any());
    }

    @Test
    public void firstNotNull_given_bothReturnNull_then_emptyOptionalIsReturned()
            throws Exception
    {

        given(f1.apply(any())).willReturn(null);
        given(f2.apply(any())).willReturn(null);

        final Optional<String> result = FunctionUtils.firstNotNull(1, f1, f2);
        assertNotNull(result);
        assertFalse(result.isPresent());
        verify(f1, times(1)).apply(any());
        verify(f2, times(1)).apply(any());
    }

}