package dxWeather;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Enumeration;

public class IpUtils {

    private static IpUtils ipUtils = new IpUtils();
    private final static Logger LOG = Logger.getLogger(IpUtils.class);

    public static IpUtils getInstance() {
        return ipUtils;
    }

    public JSONObject getIPInfo() {
        try {
            URL obj = new URL("http://ip-api.com/json");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            LOG.debug(response.toString());
            return new JSONObject(response.toString());
        } catch (Exception e) {
            LOG.error("",e);
        }
        return new JSONObject();
    }


}
