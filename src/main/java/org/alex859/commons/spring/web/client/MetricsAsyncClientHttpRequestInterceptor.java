package org.alex859.commons.spring.web.client;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.AsyncClientHttpRequestExecution;
import org.springframework.http.client.AsyncClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.concurrent.ListenableFuture;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * {@link AsyncClientHttpRequestInterceptor} to add Dropwizard metrics to the current request for:
 * Timers:
 * <ul>
 * <li>Total requests</li>
 * <li>Successful requests</li>
 * <li>Failed requests</li>
 * </ul>
 * <p>
 * Counters:
 * <ul>
 * <li>HTTP statuses</li>
 * </ul>
 */
public class MetricsAsyncClientHttpRequestInterceptor implements AsyncClientHttpRequestInterceptor
{
    private static final Logger LOG = LoggerFactory.getLogger(MetricsAsyncClientHttpRequestInterceptor.class);

    private static final String METRIC_DELIMITER = ".";
    private static final String REQUESTS = "requests";
    private static final String STATUS = "status";
    private static final String TOTAL = "total";
    private static final String SUCCESSFUL = "success";
    private static final String FAILED = "failed";

    private final MetricRegistry metricRegistry;
    private final Function<String, MetricNames> metricsNameProvider;

    /**
     * Creates a new {@link MetricsAsyncClientHttpRequestInterceptor} for the given service using the provided
     * {@link MetricRegistry}.
     *
     * @param service        Name of the service the request is sent to.
     * @param metricRegistry The metrics registry.
     */
    public MetricsAsyncClientHttpRequestInterceptor(final String service, final MetricRegistry metricRegistry)
    {
        this.metricRegistry = Objects.requireNonNull(metricRegistry, "MetricRegistry cannot be null");
        Objects.requireNonNull(service, "Service name cannot be null");

        final MetricNames metricNames = new MetricNames(service);
        metricsNameProvider = uriPath -> metricNames;
    }

    /**
     * Creates a new {@link MetricsAsyncClientHttpRequestInterceptor} using the provided {@link MetricRegistry} for
     * more than one service. A mapping from service host to service name will be used to get the metric names.
     *
     * @param hostToServiceMap A mapping from service host to service name.
     * @param metricRegistry The metrics registry.
     */
    public MetricsAsyncClientHttpRequestInterceptor(final Map<String, String> hostToServiceMap, final MetricRegistry
            metricRegistry)
    {
        this.metricRegistry = Objects.requireNonNull(metricRegistry, "MetricRegistry cannot be null");
        Objects.requireNonNull(hostToServiceMap, "URLToService map cannot be null");

        final Map<String, MetricNames> urlToMetricNamesMap = new HashMap<>(hostToServiceMap.size());
        hostToServiceMap.forEach((url, service) -> urlToMetricNamesMap.put(url, new MetricNames(service)));

        metricsNameProvider = urlToMetricNamesMap::get;
    }

    @Override
    public ListenableFuture<ClientHttpResponse> intercept(final HttpRequest request,
                                                          final byte[] body,
                                                          final AsyncClientHttpRequestExecution execution) throws IOException
    {
        final String host = request.getURI().getHost();
        final MetricNames metricNames = metricsNameProvider.apply(host);

        final ListenableFuture<ClientHttpResponse> responseFuture = execution.executeAsync(request, body);
        if (metricNames != null)
        {
            final Timer.Context timerContext = metricRegistry.timer(metricNames.total).time();
            responseFuture.addCallback(
                    result ->
                    {
                        final long recordedTime = timerContext.stop();
                        recordTime(metricNames.success, recordedTime);
                        recordHttpStatus(metricNames.status, result);
                    },
                    ex ->
                    {
                        final long recordedTime = timerContext.stop();
                        recordTime(metricNames.failure, recordedTime);
                    });
        }
        else
        {
            LOG.warn("No service defined for request host: [{}]", host);
        }

        return responseFuture;
    }

    private void recordTime(final String metric, final long elapsed)
    {
        metricRegistry.timer(metric).update(elapsed, TimeUnit.NANOSECONDS);
    }

    private void recordHttpStatus(final String metric, final ClientHttpResponse response)
    {
        try
        {
            final String name = MetricRegistry.name(metric, String.valueOf(response.getStatusCode()));
            metricRegistry.meter(name).mark();
        }
        catch (final IOException e)
        {
            LOG.error("Error retrieving response status", e);
        }
    }

    private static class MetricNames
    {
        private final String total;
        private final String success;
        private final String failure;
        private final String status;

        private MetricNames(final String service)
        {
            this.total = String.join(METRIC_DELIMITER, service, REQUESTS, TOTAL);
            this.success = String.join(METRIC_DELIMITER, service, REQUESTS, SUCCESSFUL);
            this.failure = String.join(METRIC_DELIMITER, service, REQUESTS, FAILED);
            this.status = String.join(METRIC_DELIMITER, service, REQUESTS, STATUS);
        }
    }
}
