package Maven;

import kong.unirest.Unirest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class ServiceAPI extends Service {
    private static String url = "https://www.cbr.ru/scripts/XML_daily.asp?date_req=";

    public static List<Currency> getRatesByDate(String date) throws DataNotFoundException {
        List<Currency> curList = new ArrayList<>();
        String fullUrl = url + date;

        String data = Unirest.get(fullUrl).asString().getBody();

        try (StringReader reader = new StringReader(data)) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = null;
            doc = builder.parse(new InputSource(reader));
            NodeList currenciesList = doc.getElementsByTagName("Valute");
            for (int i = 0; i < currenciesList.getLength(); i++) {
                Element cur = (Element) currenciesList.item(i);
                String currencyName = cur.getElementsByTagName("CharCode").item(0).getTextContent();
                String value = cur.getElementsByTagName("Value").item(0).getTextContent();
                Currency currency = new Currency(date, currencyName);
                currency.setRate(Double.parseDouble(value.replace(",",".")));
                curList.add(currency);
            }
            return curList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new DataNotFoundException();
    }

    @Override
    public boolean getExchangeRate(Currency currency) throws DataNotFoundException {
        NodeList currenciesList = null;
        String fullUrl = url + currency.getDate();

        String data = Unirest.get(fullUrl).asString().getBody();

        try (StringReader reader = new StringReader(data)) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = null;
            doc = builder.parse(new InputSource(reader));
            currenciesList = doc.getElementsByTagName("Valute");
            for (int i = 0; i < currenciesList.getLength(); i++) {
                Element cur = (Element) currenciesList.item(i);
                if (cur.getElementsByTagName("CharCode").item(0).getTextContent().equals(currency.getCurrencyName())) {
                    String value = cur.getElementsByTagName("Value").item(0).getTextContent();
                    currency.setRate(Double.parseDouble(value.replace(",",".")));
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new DataNotFoundException();
    }
}
