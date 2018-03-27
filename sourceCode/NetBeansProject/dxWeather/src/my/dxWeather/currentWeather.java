package my.dxWeather;

import java.io.*;
import java.net.*; 
import org.json.JSONObject;
import org.json.JSONArray;

public class currentWeather
{
    String lat;
    String lon;
    String apiKey;
    String url;
    String data;
    char tempUnit;
    public void setLan(String lanPassed)
    {
        lat = lanPassed;
    }
    
    public void setLon(String lonPassed)
    {
        lon = lonPassed;
    }
    
    public void setUnit(int valuePassed)
    {
        if(valuePassed == 0)
        {
            //Celcius
            tempUnit = 'C';
        }
        else if(valuePassed == 1)
        {
            //Fahrenheit
            tempUnit = 'F';
        }
    }
    
    public void setApiKey(String apiKeyPassed)
    {
        apiKey = apiKeyPassed;
    }
    
    public void finalize()
    {
        if(tempUnit == 'C')
        {
            url = "http://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&appid="+apiKey+"&units=metric";
        }
        else if(tempUnit == 'F')
        {
            url = "http://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&appid="+apiKey+"&units=imperial";
        }
    }
    
    public boolean getData()
    {
        try{
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
        }
        catch(Exception e)
        {
            return false;
        }
    }
    
    public String[] getAllData()
    {
        String weatherData[] = new String[6];
        try
        {
            JSONObject allData  = new JSONObject(data);
            JSONObject mainObj = new JSONObject(allData.getJSONObject("main").toString());
            JSONObject sys = new JSONObject(allData.getJSONObject("sys").toString());
            
            JSONArray jsonarray = new JSONArray(allData.get("weather").toString());
            for (int i = 0; i < jsonarray.length(); i++)
            {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                weatherData[1] = jsonobject.getString("description"); //Weather Description..
            }
            
            weatherData[0] = allData.getString("name")+", "+sys.getString("country");
            int tmpWeather = (int) (mainObj.getDouble("temp")); //Temperature
            int humidity = (int) (mainObj.getDouble("humidity")); //Humidity
            int minTemp = (int) (mainObj.getDouble("temp_min")); //Min Temp
            int maxTemp = (int) (mainObj.getDouble("temp_max")); //Max Temp
            weatherData[2] = String.valueOf(tmpWeather); //String Temperature
            weatherData[3] = String.valueOf(humidity)+" %"; //String Humidity
            
            
            
            //Temp Data
            if(tempUnit == 'C')
            {
                weatherData[4] = String.valueOf(minTemp)+" °C"; //Min Temp in Celcius
                weatherData[5] = String.valueOf(maxTemp)+" °C"; //Max Temp in Celcius
            }
            else if(tempUnit == 'F')
            {
                weatherData[4] = String.valueOf(minTemp)+" °F"; //Min Temp in Fahrenheit
                weatherData[5] = String.valueOf(maxTemp)+" °F"; //Max Temp in Fahrenheit
            }
        }
        catch(Exception e)
        {
            console.pln(e.toString());
            console.pln(data);
        }
        return weatherData;
    }
}