package dxWeather;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Horoscope {
    private static Horoscope horoscope = new Horoscope();

    public static Horoscope getInstance() {
        return horoscope;
    }
    private static final Logger LOG = Logger.getLogger(CurrentWeather.class);
    public static String horoscopeText;

    private List<Sign> list = Arrays.asList(Sign.values());
    private Iterator<Sign> it = list.iterator();

    void parse() {

        Sign sign = Sign.Aquarius;

        if (it.hasNext()) {
            sign = it.next();
        } else {
            it = list.iterator();
        }
        System.out.println(sign.getName());
        try {
            Document doc = Jsoup.connect("http://1001goroskop.ru/?znak=" + sign.getName()).get();
            LOG.debug(doc.title());
            Elements newsHeadlines = doc.select("#eje_text");
            for (Element headline : newsHeadlines) {
                headline = headline.select("p").first();
                horoscopeText = headline.ownText();
                LOG.debug(
                        headline.ownText());
            }
        } catch (IOException e) {
            LOG.error("", e);
        }

    }

    enum Sign {
        Aquarius,
        Pisces,
        Aries,
        Taurus,
        Gemini,
        Cancer,
        Leo,
        Virgo,
        Libra,
        Scorpio,
        Capricorn;
        private String name;

        Sign() {
            this.name = this.name().toLowerCase();
        }

        public String getName() {
            return name;
        }

    }
}
