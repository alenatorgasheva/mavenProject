import kong.unirest.Unirest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

public class Service {
    private static NodeList getRateByDate(Currency currency) throws IOException, SAXException, ParserConfigurationException {
        NodeList currenciesList = null;
        String url = "https://www.cbr.ru/scripts/XML_daily.asp?date_req=" + currency.getDate();

        String data = Unirest.get(url).asString().getBody();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = null;
        try (StringReader reader = new StringReader(data)) {
            doc = builder.parse(new InputSource(reader));
            currenciesList = doc.getElementsByTagName("Valute");
        }
        return currenciesList;
    }

    public static void printExchangeRate(Currency currency) throws DataNotFoundException, IOException, ParserConfigurationException, SAXException {
        NodeList currenciesList = getRateByDate(currency);
        for (int i = 0; i < currenciesList.getLength(); i++) {
            Element cur = (Element) currenciesList.item(i);
            if (cur.getElementsByTagName("CharCode").item(0).getTextContent().equals(currency.getCurrencyName())) {
                String name = cur.getElementsByTagName("Name").item(0).getTextContent();
                String value = cur.getElementsByTagName("Value").item(0).getTextContent();
                System.out.println("\n1 " + name + " = " + value + " Российских рублей");
                return;
            }
        }
        throw new DataNotFoundException();
    }
}
