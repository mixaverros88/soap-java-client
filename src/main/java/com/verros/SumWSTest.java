package com.verros;



import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.junit.Test;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import com.verros.ws.SumRequest;
import com.verros.ws.SumWSService;
import com.verros.ws.SumResponse;
import com.verros.ws.SumWS;

public class SumWSTest {

    @Test
    public void calculateResult() {
        try {
            SumWSService service = new SumWSService(new URL("http://localhost:8080/sumws/services/sumService?wsdl"));
            SumWS port = service.getSumWSPort();

            Client client = ClientProxy.getClient(port);
            Endpoint endpoint = client.getEndpoint();

            Map<String, Object> outProps = new HashMap<>();
            outProps.put(WSHandlerConstants.ACTION, "UsernameToken");
            outProps.put(WSHandlerConstants.USER, "test");
            outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
            outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS, PasswordCallbackHandler.class.getName());

            WSS4JOutInterceptor wss4JOutInterceptor = new WSS4JOutInterceptor(outProps);

            endpoint.getOutInterceptors().add(wss4JOutInterceptor);

            SumRequest sumRequest = new SumRequest();
            sumRequest.setNum1(1);
            sumRequest.setNum2(12);
            SumResponse response = port.calculateSum(sumRequest);

            System.out.println(response.getResult());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
