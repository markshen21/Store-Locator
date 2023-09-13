import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;

public class WebScraper {

    public static ArrayList<Store> scrapeStoreData(String url) throws IOException {
        ArrayList<Store> storeList = new ArrayList<>();
        Document doc = Jsoup.connect(url).get();
        
        // Parse the HTML using Jsoup selectors and extract store data
        Elements storeElements = doc.select(".store"); // Adjust selector based on HTML structure
        for (Element storeElement : storeElements) {
            String storeName = storeElement.select(".name").text();
            String storeAddress = storeElement.select(".address").text();
            String storeCity = storeElement.select(".city").text();
            String storeState = storeElement.select(".state").text();
            String storeZipCode = storeElement.select(".zipcode").text();
            double storeLatitude = Double.parseDouble(storeElement.select(".latitude").text());
            double storeLongitude = Double.parseDouble(storeElement.select(".longitude").text());
            Store store = new Store(storeName, storeAddress, storeCity, storeState, storeZipCode, storeLatitude, storeLongitude);
            storeList.add(store);
        }
        return storeList;
    }
}
