package boundary.com.camel.component.ping;

import java.util.concurrent.TimeUnit;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class PingHostTest extends CamelTestSupport {

    @Test
    public void testPing() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMinimumMessageCount(1);
        mock.await(10, TimeUnit.SECONDS);
        
        assertMockEndpointsSatisfied();
    }
    
    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                from("ping://?type=ping&delay=5&host=foo.bar.com")
                  .to("ping://bar")
                  .to("mock:result");
            }
        };
    }
}
