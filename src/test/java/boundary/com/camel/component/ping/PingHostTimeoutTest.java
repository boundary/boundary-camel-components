package boundary.com.camel.component.ping;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class PingHostTimeoutTest extends CamelTestSupport {

    @Test
    public void testPing() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMinimumMessageCount(1);
        mock.await(10, TimeUnit.SECONDS);
        
        mock.assertIsSatisfied();
        
        List <Exchange> receivedExchanges = mock.getReceivedExchanges();
        for(Exchange e: receivedExchanges) {
        	PingStatus status = e.getIn().getBody(PingStatus.class);
        	
        	assertTrue("check transmitted",status.getTransmitted() > 0);
        	assertTrue("check received",status.getReceived() == 0);
        	assertTrue(status.getStatus() == Status.FAIL);
        }
    }
    
    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                from("ping://?type=ping&delay=5&host=192.168.1.27")
                  .to("ping://bar")
                  .to("mock:result");
            }
        };
    }
}
