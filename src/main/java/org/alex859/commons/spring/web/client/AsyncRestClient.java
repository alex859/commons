package org.alex859.commons.spring.web.client;

import net.javacrumbs.futureconverter.springjava.FutureConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.AsyncRequestCallback;
import org.springframework.web.client.AsyncRestOperations;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;

import java.net.URI;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * Wrapper around {@link AsyncRestOperations} to use {@link CompletableFuture}s.
 */
public class AsyncRestClient
{
    private static final Logger LOG = LoggerFactory.getLogger(AsyncRestClient.class);

    private final AsyncRestOperations asyncRestOperation;

    public AsyncRestClient(final AsyncRestOperations asyncRestTemplate)
    {
        this.asyncRestOperation = asyncRestTemplate;
    }

    public RestOperations getRestOperations()
    {
        return asyncRestOperation.getRestOperations();
    }

    public <T> CompletableFuture<ResponseEntity<T>> getForEntity(final String url, final Class<T> responseType, final Object... uriVariables) throws RestClientException
    {
        return FutureConverter.toCompletableFuture(asyncRestOperation.getForEntity(url, responseType, uriVariables));
    }

    public <T> CompletableFuture<ResponseEntity<T>> getForEntity(final String url, final Class<T> responseType, final Map<String, ?> uriVariables) throws RestClientException
    {
        return FutureConverter.toCompletableFuture(asyncRestOperation.getForEntity(url, responseType, uriVariables));
    }

    public <T> CompletableFuture<ResponseEntity<T>> getForEntity(final URI url, final Class<T> responseType) throws RestClientException
    {
        return FutureConverter.toCompletableFuture(asyncRestOperation.getForEntity(url, responseType));
    }

    public CompletableFuture<HttpHeaders> headForHeaders(final String url, final Object... uriVariables) throws RestClientException
    {
        return FutureConverter.toCompletableFuture(asyncRestOperation.headForHeaders(url, uriVariables));
    }

    public CompletableFuture<HttpHeaders> headForHeaders(final String url, final Map<String, ?> uriVariables) throws RestClientException
    {
        return FutureConverter.toCompletableFuture(asyncRestOperation.headForHeaders(url, uriVariables));
    }

    public CompletableFuture<HttpHeaders> headForHeaders(final URI url) throws RestClientException
    {
        return FutureConverter.toCompletableFuture(asyncRestOperation.headForHeaders(url));
    }

    public CompletableFuture<URI> postForLocation(final String url, final HttpEntity<?> request, final Object... uriVariables) throws RestClientException
    {
        return FutureConverter.toCompletableFuture(asyncRestOperation.postForLocation(url, request, uriVariables));
    }

    public CompletableFuture<URI> postForLocation(final String url, final HttpEntity<?> request, final Map<String, ?> uriVariables) throws RestClientException
    {
        return FutureConverter.toCompletableFuture(asyncRestOperation.postForLocation(url, request, uriVariables));
    }

    public CompletableFuture<URI> postForLocation(final URI url, final HttpEntity<?> request) throws RestClientException
    {
        return FutureConverter.toCompletableFuture(asyncRestOperation.postForLocation(url, request));
    }

    public <T> CompletableFuture<ResponseEntity<T>> postForEntity(final String url, final HttpEntity<?> request, final Class<T> responseType, final Object... uriVariables) throws RestClientException
    {
        return FutureConverter.toCompletableFuture(asyncRestOperation.postForEntity(url, request, responseType, uriVariables));
    }

    public <T> CompletableFuture<ResponseEntity<T>> postForEntity(final String url, final HttpEntity<?> request, final Class<T> responseType, final Map<String, ?> uriVariables) throws RestClientException
    {
        return FutureConverter.toCompletableFuture(asyncRestOperation.postForEntity(url, request, responseType, uriVariables));
    }

    public <T> CompletableFuture<ResponseEntity<T>> postForEntity(final URI url, final HttpEntity<?> request, final Class<T> responseType) throws RestClientException
    {
        return FutureConverter.toCompletableFuture(asyncRestOperation.postForEntity(url, request, responseType));
    }

    public CompletableFuture<?> put(final String url, final HttpEntity<?> request, final Object... uriVariables) throws RestClientException
    {
        return FutureConverter.toCompletableFuture(asyncRestOperation.put(url, request, uriVariables));
    }

    public CompletableFuture<?> put(final String url, final HttpEntity<?> request, final Map<String, ?> uriVariables) throws RestClientException
    {
        return FutureConverter.toCompletableFuture(asyncRestOperation.put(url, request, uriVariables));
    }

    public CompletableFuture<?> put(final URI url, final HttpEntity<?> request) throws RestClientException
    {
        return FutureConverter.toCompletableFuture(asyncRestOperation.put(url, request));
    }

    public CompletableFuture<?> delete(final String url, final Object... uriVariables) throws RestClientException
    {
        return FutureConverter.toCompletableFuture(asyncRestOperation.delete(url, uriVariables));
    }

    public CompletableFuture<?> delete(final String url, final Map<String, ?> uriVariables) throws RestClientException
    {
        return FutureConverter.toCompletableFuture(asyncRestOperation.delete(url, uriVariables));
    }

    public CompletableFuture<?> delete(final URI url) throws RestClientException
    {
        return FutureConverter.toCompletableFuture(asyncRestOperation.delete(url));
    }

    public CompletableFuture<Set<HttpMethod>> optionsForAllow(final String url, final Object... uriVariables) throws RestClientException
    {
        return FutureConverter.toCompletableFuture(asyncRestOperation.optionsForAllow(url, uriVariables));
    }

    public CompletableFuture<Set<HttpMethod>> optionsForAllow(final String url, final Map<String, ?> uriVariables) throws RestClientException
    {
        return FutureConverter.toCompletableFuture(asyncRestOperation.optionsForAllow(url, uriVariables));
    }

    public CompletableFuture<Set<HttpMethod>> optionsForAllow(final URI url) throws RestClientException
    {
        return FutureConverter.toCompletableFuture(asyncRestOperation.optionsForAllow(url));
    }

    public <T> CompletableFuture<ResponseEntity<T>> exchange(final String url, final HttpMethod method, final HttpEntity<?> requestEntity, final Class<T> responseType, final Object... uriVariables) throws RestClientException
    {
        return FutureConverter.toCompletableFuture(asyncRestOperation.exchange(url, method, requestEntity, responseType, uriVariables));
    }

    public <T> CompletableFuture<ResponseEntity<T>> exchange(final String url, final HttpMethod method, final HttpEntity<?> requestEntity, final Class<T> responseType, final Map<String, ?> uriVariables) throws RestClientException
    {
        return FutureConverter.toCompletableFuture(asyncRestOperation.exchange(url, method, requestEntity, responseType, uriVariables));
    }

    public <T> CompletableFuture<ResponseEntity<T>> exchange(final URI url, final HttpMethod method, final HttpEntity<?> requestEntity, final Class<T> responseType) throws RestClientException
    {
        return FutureConverter.toCompletableFuture(asyncRestOperation.exchange(url, method, requestEntity, responseType));
    }

    public <T> CompletableFuture<ResponseEntity<T>> exchange(final String url, final HttpMethod method, final HttpEntity<?> requestEntity, final ParameterizedTypeReference<T> responseType, final Object... uriVariables) throws RestClientException
    {
        return FutureConverter.toCompletableFuture(asyncRestOperation.exchange(url, method, requestEntity, responseType, uriVariables));
    }

    public <T> CompletableFuture<ResponseEntity<T>> exchange(final String url, final HttpMethod method, final HttpEntity<?> requestEntity, final ParameterizedTypeReference<T> responseType, final Map<String, ?> uriVariables) throws RestClientException
    {
        return FutureConverter.toCompletableFuture(asyncRestOperation.exchange(url, method, requestEntity, responseType, uriVariables));
    }

    public <T> CompletableFuture<ResponseEntity<T>> exchange(final URI url, final HttpMethod method, final HttpEntity<?> requestEntity, final ParameterizedTypeReference<T> responseType) throws RestClientException
    {
        return FutureConverter.toCompletableFuture(asyncRestOperation.exchange(url, method, requestEntity, responseType));
    }

    public <T> CompletableFuture<T> execute(final String url, final HttpMethod method, final AsyncRequestCallback requestCallback, final ResponseExtractor<T> responseExtractor, final Object... uriVariables) throws RestClientException
    {
        return FutureConverter.toCompletableFuture(asyncRestOperation.execute(url, method, requestCallback, responseExtractor, uriVariables));
    }

    public <T> CompletableFuture<T> execute(final String url, final HttpMethod method, final AsyncRequestCallback requestCallback, final ResponseExtractor<T> responseExtractor, final Map<String, ?> uriVariables) throws RestClientException
    {
        return FutureConverter.toCompletableFuture(asyncRestOperation.execute(url, method, requestCallback, responseExtractor, uriVariables));
    }

    public <T> CompletableFuture<T> execute(final URI url, final HttpMethod method, final AsyncRequestCallback requestCallback, final ResponseExtractor<T> responseExtractor) throws RestClientException
    {
        return FutureConverter.toCompletableFuture(asyncRestOperation.execute(url, method, requestCallback, responseExtractor));
    }
}
