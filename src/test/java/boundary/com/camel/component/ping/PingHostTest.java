package boundary.com.camel.component.ping;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class PingHostTest extends CamelTestSupport {

    @Test
    public void testPing() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMessageCount(1);
        mock.await(10, TimeUnit.SECONDS);
        
        mock.assertIsSatisfied();

        List <Exchange> receivedExchanges = mock.getReceivedExchanges();
        for(Exchange e: receivedExchanges) {
        	PingStatus status = e.getIn().getBody(PingStatus.class);
        	
        	assertTrue("check transmitted",status.getTransmitted() > 0);
        	assertEquals("check transmitted/received", status.getTransmitted(),status.getReceived());
        	assertTrue("check ping satus",status.getStatus() == Status.SUCCESS);
        }
    }
    
    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                from("ping://?type=ping&delay=5&host=localhost")
                  .to("ping://bar")
                  .to("mock:result");
            }
        };
    }
}
