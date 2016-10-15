package org.alex859.commons.integration;

/**
 * Defines the action of building a new {@link AsyncClientService} for a given response {@link Class}.
 *
 * @param <T> The response type.
 */
@FunctionalInterface
public interface AsyncClientServiceBuilder<T>
{
    /**
     * Builds a new {@link AsyncClientService} that returns an object of the given {@link Class}.
     *
     * @param responseClass The response class.
     *
     * @return The {@link AsyncClientService}.
     */
    AsyncClientService<T> buildForClass(Class<T> responseClass);
}
