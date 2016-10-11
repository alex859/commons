import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import org.alex859.commons.spring.web.client.MetricsAsyncClientHttpRequestInterceptor;
import org.springframework.web.client.AsyncRestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Application
{
    private static MetricRegistry metrics = new MetricRegistry();

    public static void main(String[] args)
    {
        startReport();

        final AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();
        final HashMap<String, String> map = new HashMap<>();
        map.put("www.google.co.uk", "google");
        map.put("www.amazon.co.uk", "amazon");
        map.put("www.foodandwine.com", "foodandwine");
        map.put("www.cazzo.com", "cazzo");
        asyncRestTemplate.setInterceptors(Collections.singletonList(new MetricsAsyncClientHttpRequestInterceptor(map, metrics)));

        asyncRestTemplate.getForEntity("http://www.foodandwine.com/articles/affordable-paris", String.class);
//        asyncRestTemplate.getForEntity("https://www.google.co.uk/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#q=spring+boot+", String.class);
//        asyncRestTemplate.getForEntity("https://www.amazon.co.uk/gp/product/B01EN63SSC/ref=br_dig_pdt-1?pf_rd_m=A3P5ROKL5A1OLE&pf_rd_s=&pf_rd_r=BG2HEMWMBTKKSEEKXN0S&pf_rd_t=36701&pf_rd_p=036fde10-b66d-4e7f-8d6b-dd9047910524&pf_rd_i=desktop", String.class);
//        asyncRestTemplate.getForEntity("https://www.cazzo.com/ciccino", String.class);
//        asyncRestTemplate.getForEntity("http://www.lespapillesparis.fr/#about-2-2-1-2", String.class);

        wait5Seconds();
    }

    private static void startReport()
    {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(4, TimeUnit.SECONDS);
    }


    private static void wait5Seconds()
    {
        try
        {
            Thread.sleep(6 * 1000);
        }
        catch (InterruptedException e)
        {
        }
    }
}
