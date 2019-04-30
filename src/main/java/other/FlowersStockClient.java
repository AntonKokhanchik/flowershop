package other;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FlowersStockClient {
    private static String flowersStockServiceUrl = "http://localhost:8080/ws/FlowersStock";

    public static void main(String[] args) {
        int i = 0;
        while (i < 50) {
            i++;
            try {
                // random([10,30])
                sendRequestIncreaseFlowersStock((int) (Math.random() * 21 + 10));
                // 10 sec
                Thread.sleep(10 * 1000L);
                // 10 min - *60
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void sendRequestIncreaseFlowersStock(int count) throws IOException {
        String soapXML = generateIncreaseFlowersStockSoapXML(count);
        HttpURLConnection conn = (HttpURLConnection) new URL(flowersStockServiceUrl).openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        // insert xml in POST
        OutputStream os = conn.getOutputStream();
        os.write(soapXML.getBytes());
        os.flush();

        //read response
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String str;
        while ((str = br.readLine())!= null) {
            System.out.println(str);
        }
    }

    private static String generateIncreaseFlowersStockSoapXML(int count) {
        // подглядел у SoapUI
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webService.flowershop/\">\n" +
               "   <soapenv:Header/>\n" +
               "   <soapenv:Body>\n" +
               "      <web:increaseFlowersStockSize>\n" +
               "         <count>"+count+"</count>\n" +
               "      </web:increaseFlowersStockSize>\n" +
               "   </soapenv:Body>\n" +
               "</soapenv:Envelope>";
    }
}
