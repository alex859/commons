package org.alex859.commons.integration;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.function.Function;

/**
 * Defines the action of sending a {@link RequestEntity} to a remote system synchronously.
 * The {@link SyncClientService#withRestTemplate(RestTemplate)} can be used to get a
 * {@link SyncClientServiceBuilder} implementation based on the {@link RestTemplate}.
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
     * Returns an {@link SyncClientServiceBuilder} based on the provided {@link RestTemplate}
     *
     * @param restTemplate The {@link RestTemplate} to use.
     * @param <T>          The response type
     * @return The newly created {@link AsyncClientServiceBuilder}.
     */
    static <T> SyncClientServiceBuilder<T> withRestTemplate(final RestTemplate restTemplate)
    {
        return responseType ->
                requestEntity ->
                {
                    Objects.requireNonNull(requestEntity, "Request Entity cannot be null");
                    return restTemplate.exchange(requestEntity, responseType);
                };
    }
}
