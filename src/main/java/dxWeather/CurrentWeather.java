package dxWeather;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrentWeather {

    String apiKey;
    String url;
    String data;
    char tempUnit;

    private static final Logger LOG = Logger.getLogger(CurrentWeather.class);

    public void setUnit() {
        tempUnit = 'C';
    }

    public void setApiKey(String apiKeyPassed) {
        apiKey = apiKeyPassed;
    }

    public void finalize() {

        JSONObject ipInfo = IpUtils.getInstance().getIPInfo();
        url = "http://api.openweathermap.org/data/2.5/weather?q=" + ipInfo.getString("city") + "," + ipInfo.getString("countryCode") + "&appid=" + apiKey + "&units=metric";
    }

    public boolean getData() {
        try {
            URL obj = new URL(url);
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
            data = response.toString();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String[] getAllData() {
        String weatherData[] = new String[6];
        try {
            JSONObject allData = new JSONObject(data);
            JSONObject mainObj = new JSONObject(allData.getJSONObject("main").toString());
            JSONObject sys = new JSONObject(allData.getJSONObject("sys").toString());
            LOG.debug(allData);
            JSONArray jsonarray = new JSONArray(allData.get("weather").toString());
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                weatherData[1] = jsonobject.getString("description"); //Weather Description..
            }

            weatherData[0] = allData.getString("name") + ", " + sys.getString("country");
            int tmpWeather = (int) (mainObj.getDouble("temp")); //Temperature
            int humidity = (int) (mainObj.getDouble("humidity")); //Humidity
            int minTemp = (int) (mainObj.getDouble("temp_min")); //Min Temp
            int maxTemp = (int) (mainObj.getDouble("temp_max")); //Max Temp
            weatherData[2] = String.valueOf(tmpWeather); //String Temperature
            weatherData[3] = String.valueOf(humidity) + " %"; //String Humidity

            weatherData[4] = String.valueOf(minTemp) + " °C"; //Min Temp in Celcius
            weatherData[5] = String.valueOf(maxTemp) + " °C"; //Max Temp in Celcius

        } catch (Exception e) {
            LOG.error("", e);
            LOG.error(data);
        }
        return weatherData;
    }
}