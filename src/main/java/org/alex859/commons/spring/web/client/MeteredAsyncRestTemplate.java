package org.alex859.commons.spring.web.client;

import com.codahale.metrics.MetricRegistry;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.http.client.AsyncClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class MeteredAsyncRestTemplate extends AsyncRestTemplate
{
    public MeteredAsyncRestTemplate(final String service, final MetricRegistry metricRegistry)
    {
        super();
        setInterceptors(Collections.singletonList(new MetricsAsyncClientHttpRequestInterceptor(service, metricRegistry)));
    }

    public MeteredAsyncRestTemplate(final AsyncListenableTaskExecutor taskExecutor, final String service, final MetricRegistry metricRegistry)
    {
        super(taskExecutor);
        setInterceptors(Collections.singletonList(new MetricsAsyncClientHttpRequestInterceptor(service, metricRegistry)));
    }

    public MeteredAsyncRestTemplate(final AsyncClientHttpRequestFactory asyncRequestFactory, final String service, final MetricRegistry metricRegistry)
    {
        super(asyncRequestFactory);
        setInterceptors(Collections.singletonList(new MetricsAsyncClientHttpRequestInterceptor(service, metricRegistry)));
    }

    public MeteredAsyncRestTemplate(final AsyncClientHttpRequestFactory asyncRequestFactory, final ClientHttpRequestFactory syncRequestFactory, final String service, final MetricRegistry metricRegistry)
    {
        super(asyncRequestFactory, syncRequestFactory);
        setInterceptors(Collections.singletonList(new MetricsAsyncClientHttpRequestInterceptor(service, metricRegistry)));
    }

    public MeteredAsyncRestTemplate(final AsyncClientHttpRequestFactory requestFactory, final RestTemplate restTemplate, final String service, final MetricRegistry metricRegistry)
    {
        super(requestFactory, restTemplate);
        setInterceptors(Collections.singletonList(new MetricsAsyncClientHttpRequestInterceptor(service, metricRegistry)));
    }
}
