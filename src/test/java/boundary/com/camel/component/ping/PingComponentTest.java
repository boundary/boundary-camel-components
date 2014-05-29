package boundary.com.camel.component.ping;

import java.util.concurrent.TimeUnit;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class PingComponentTest extends CamelTestSupport {

    @Test
    public void testPing() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMinimumMessageCount(1);
        mock.await(20, TimeUnit.SECONDS);

        
        assertMockEndpointsSatisfied();
    }
    
    @Test
    public void testMultiplePing() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMinimumMessageCount(3);
        mock.await(30, TimeUnit.SECONDS);
        
        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                from("ping://foo?scheduler=quartz2&scheduler.cron=0/5+*+*+*+*+*")
                  .to("ping://bar")
                  .to("mock:result");
            }
        };
    }
}
