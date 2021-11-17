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
import java.sql.*;

public class Service {

    private static NodeList getRateByDate(Currency currency) {
        NodeList currenciesList = null;
        String url = "jdbc:mysql://localhost:3306/mydb";
        String user = "root";
        String password = "password";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM exchangeRates;");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("currencyName") + " " +
                        resultSet.getString("date") + " " +
                        resultSet.getDouble("rate")
                    );
               }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return currenciesList;
    }

    public static void updateDatabase(Currency currency) {
        String url = "jdbc:mysql://localhost:3306/mydb";
        String user = "root";
        String password = "password";
        Double rate = 66.3309;

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Statement statement = connection.createStatement();

            String query = "INSERT INTO exchangeRates (`currencyName`, `date`, `rate`) VALUES ('" + currency.getCurrencyName() + "', '" + currency.getDate() + "', '" + rate + "');";
            System.out.println(query);

            statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static NodeList getRateByDate1(Currency currency) throws IOException, SAXException, ParserConfigurationException {
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
        try {
            for (int i = 0; i < currenciesList.getLength(); i++) {
                Element cur = (Element) currenciesList.item(i);
                if (cur.getElementsByTagName("CharCode").item(0).getTextContent().equals(currency.getCurrencyName())) {
                    String name = cur.getElementsByTagName("Name").item(0).getTextContent();
                    String value = cur.getElementsByTagName("Value").item(0).getTextContent();
                    System.out.println("\n1 " + name + " = " + value + " Российских рублей");
                    return;
                }
            }
        } catch (NullPointerException e) {
            throw new DataNotFoundException();
        }
    }
}
