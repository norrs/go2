package no.norrs.go2.systemtests;


import no.norrs.go2.handlers.Go2;
import no.norrs.go2.handlers.Register;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.api.Response;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.junit.Assert;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class Go2Test extends AbstractHttpClientServerTest {

    public Go2Test(SslContextFactory sslContextFactory) {
        //super(sslContextFactory);
        super(null);
    }


    @Test
    public void test_NoRedirectTarget() throws Exception {
        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(Go2.class, "/*");
        start(handler);

        Response response = client.GET(scheme + "://localhost:" + connector.getLocalPort() + "/testrandomNoRedirectTarget" + new Date().getTime());

        Assert.assertNotNull(response);
        Assert.assertEquals(HttpServletResponse.SC_NOT_FOUND, response.getStatus());
    }

    @Test
    public void test_RegisterAndVisitShortName() throws Exception {
        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(Register.class, "/register");
        handler.addServletWithMapping(Go2.class, "/*");
        start(handler);

        String shortName = "testRegisterAndVisitShortName" + new Date().getTime();
        String redirectTarget = "http://github.com/norrs/go2";

        Request request = client.POST(scheme + "://localhost:" + connector.getLocalPort() + "/register");

        request.param("shortName", shortName);
        request.param("redirectTarget", redirectTarget);
        Response response =  request.send();


        Assert.assertNotNull(response);
        Assert.assertEquals(HttpServletResponse.SC_CREATED, response.getStatus());

        Request request2 = client.newRequest(scheme  + "://localhost:" + connector.getLocalPort() + "/"  + shortName);
        request2.followRedirects(false);
        Response response2 = request2.send();


        Assert.assertEquals(HttpServletResponse.SC_FOUND, response2.getStatus());
        Assert.assertEquals(redirectTarget, response2.getHeaders().get("Location"));
    }

    @Test
    public void test_RegisterAndVisitShortName_Option_ViewRedirectLink() throws Exception {
        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(Register.class, "/register");
        handler.addServletWithMapping(Go2.class, "/*");
        start(handler);

        String shortName = "testRegisterAndVisitShortNameViewRedirectLink" + new Date().getTime();
        String redirectTarget = "http://github.com/norrs/go2";

        Request request = client.POST(scheme + "://localhost:" + connector.getLocalPort() + "/register");

        request.param("shortName", shortName);
        request.param("redirectTarget", redirectTarget);
        Response response =  request.send();


        Assert.assertNotNull(response);
        Assert.assertEquals(HttpServletResponse.SC_CREATED, response.getStatus());

        Request request2 = client.newRequest(scheme  + "://localhost:" + connector.getLocalPort() + "/"  + shortName + "/view");
        request2.followRedirects(false);
        Response response2 = request2.send();

        Request request3 = client.newRequest(scheme  + "://localhost:" + connector.getLocalPort() + "/"  + shortName + "/v");
        request3.followRedirects(false);
        Response response3 = request3.send();


        Assert.assertEquals(HttpServletResponse.SC_FOUND, response2.getStatus());
        Assert.assertFalse(response2.getHeaders().containsKey("Location"));

        Assert.assertEquals(HttpServletResponse.SC_FOUND, response3.getStatus());
        Assert.assertFalse(response3.getHeaders().containsKey("Location"));
    }
}
