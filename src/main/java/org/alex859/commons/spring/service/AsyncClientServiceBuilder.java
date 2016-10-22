package org.alex859.commons.spring.service;

import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

/**
 * Defines the action of building a new {@link AsyncClientService} that uses the provided {@link RestTemplate}.
 *
 * @param <T> The response type.
 */
@FunctionalInterface
public interface AsyncClientServiceBuilder<T>
{
    /**
     * Builds a new {@link AsyncClientService} that uses the provided {@link RestTemplate}.
     *
     * @param asyncRestTemplate The rest template.
     * @return The {@link SyncClientService}.
     */
    AsyncClientService<T> withRestTemplate(final AsyncRestTemplate asyncRestTemplate);
}
