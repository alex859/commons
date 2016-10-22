package org.alex859.commons.spring.service;

import org.springframework.web.client.RestTemplate;

/**
 * Defines the action of building a new {@link SyncClientService} that uses the provided {@link RestTemplate}.
 *
 * @param <T> The response type.
 */
@FunctionalInterface
public interface SyncClientServiceBuilder<T>
{
    /**
     * Builds a new {@link SyncClientService} that uses the provided {@link RestTemplate}.
     *
     * @param restTemplate The rest template.
     * @return The {@link SyncClientService}.
     */
    SyncClientService<T> withRestTemplate(RestTemplate restTemplate);
}
