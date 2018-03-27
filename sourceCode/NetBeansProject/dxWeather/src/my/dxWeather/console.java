package my.dxWeather;
import java.io.*;
public class console
{
    public static String readLine() throws IOException
    {
        BufferedReader uiInput = new BufferedReader(new InputStreamReader(System.in));        
        return uiInput.readLine();
    }
    
    public static void p(String text)
    {
        System.out.print(text);
    }
    
    public static void pln(String text)
    {
        System.out.println(text);
    }
}