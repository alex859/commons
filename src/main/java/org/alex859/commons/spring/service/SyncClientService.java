package org.alex859.commons.spring.service;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.util.Objects;
import java.util.function.Function;

/**
 * Defines the action of sending a {@link RequestEntity} to a remote system synchronously.
 * The {@link SyncClientService#forResponseType(Class)} method can be used to get a
 * {@link SyncClientServiceBuilder} implementation for the specified response type {@link Class}.
 * <p>
 * For example:
 * <code>
 * SyncClientService<String> service = SyncClientService.forResponseType(String.class).withRestTemplate(restTemplate);
 * </code>
 *
 * @param <RESPONSE> The response type.
 */
@FunctionalInterface
public interface SyncClientService<RESPONSE> extends Function<RequestEntity<?>, ResponseEntity<RESPONSE>>
{
    /**
     * Sends the given {@link RequestEntity}.
     *
     * @param request The request to be sent.
     * @return The response entity.
     */
    ResponseEntity<RESPONSE> send(RequestEntity<?> request);

    default ResponseEntity<RESPONSE> apply(final RequestEntity<?> requestEntity)
    {
        Objects.requireNonNull(requestEntity, "Request Entity cannot be null");
        return send(requestEntity);
    }

    /**
     * Returns an {@link SyncClientServiceBuilder} for the provided response {@link Class} type.
     *
     * @param responseType The response {@link Class} type to use.
     * @param <T>          The response type.
     * @return The newly created {@link AsyncClientServiceBuilder}.
     */
    static <T> SyncClientServiceBuilder<T> forResponseType(final Class<T> responseType)
    {
        return restTemplate ->
                requestEntity ->
                {
                    Objects.requireNonNull(requestEntity, "Request Entity cannot be null");
                    return restTemplate.exchange(requestEntity, responseType);
                };
    }
}
