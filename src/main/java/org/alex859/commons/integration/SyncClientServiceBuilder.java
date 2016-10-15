package org.alex859.commons.integration;

/**
 * Defines the action of building a new {@link SyncClientService} for a given response {@link Class}.
 *
 * @param <T> The response type.
 */
@FunctionalInterface
public interface SyncClientServiceBuilder<T>
{
    /**
     * Builds a new {@link SyncClientService} that returns an object of the given {@link Class}.
     *
     * @param responseClass The response class.
     * @return The {@link SyncClientService}.
     */
    SyncClientService<T> buildForClass(Class<T> responseClass);
}
