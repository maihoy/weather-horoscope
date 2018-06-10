package my.dxWeather;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Horoscope {
    private static final Logger LOG = Logger.getLogger(CurrentWeather.class);

    void parse (){
        try {
            Document doc = Jsoup.connect("http://1001goroskop.ru/?znak=aries").get();
            LOG.debug(doc.title());
            Elements newsHeadlines = doc.select("#eje_text");
            for (Element headline : newsHeadlines) {
                headline= headline.select("p").first();
                LOG.debug(
                        headline.ownText());
            }
        }catch (IOException e){
            LOG.error("",e);
        }

    }

}
