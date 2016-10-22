package org.alex859.commons.spring.service;

import net.javacrumbs.futureconverter.springjava.FutureConverter;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * Defines the action of sending a {@link RequestEntity} to a remote system asynchronously.
 * The {@link AsyncClientService#forResponseType(Class)} can be used to get a
 * {@link SyncClientServiceBuilder} implementation for the specified response type {@link Class}.
 * <p>
 * For example:
 * <code>
 * AsyncClientService<String> service = AsyncClientService.forResponseType(String.class).withRestTemplate(null);
 * </code>
 *
 * @param <RESPONSE> The response type.
 */
@FunctionalInterface
public interface AsyncClientService<RESPONSE> extends Function<RequestEntity<?>, CompletableFuture<ResponseEntity<RESPONSE>>>
{
    /**
     * Sends the given {@link RequestEntity}.
     *
     * @param request The request to be sent.
     * @return A {@link CompletableFuture} of the response entity.
     */
    CompletableFuture<ResponseEntity<RESPONSE>> send(RequestEntity<?> request);

    default CompletableFuture<ResponseEntity<RESPONSE>> apply(final RequestEntity<?> requestEntity)
    {
        Objects.requireNonNull(requestEntity, "Request Entity cannot be null");
        return send(requestEntity);
    }

    /**
     * Returns an {@link AsyncClientServiceBuilder} for the provided response {@link Class} type.
     *
     * @param responseType The response {@link Class} type to use.
     * @param <T>          The response type
     * @return The newly created {@link AsyncClientServiceBuilder}.
     */
    static <T> AsyncClientServiceBuilder<T> forResponseType(final Class<T> responseType)
    {
        return asyncRestTemplate ->
                requestEntity ->
                {
                    Objects.requireNonNull(requestEntity, "Request Entity cannot be null");
                    return FutureConverter.toCompletableFuture(
                            asyncRestTemplate.exchange(
                                    requestEntity.getUrl(),
                                    requestEntity.getMethod(),
                                    requestEntity,
                                    responseType)
                    );
                };
    }
}
