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

public class Currency {
    private String date;
    private String currencyName;

    public Currency(String data, String name) throws EmptyInputDataException {
        if ((data.isBlank()) || (name.isBlank())) {
            throw new EmptyInputDataException();
        }
        this.date = data;
        this.currencyName = name.toUpperCase();
        }

    public String getDate() {
        return date;
    }

    public String getCurrencyName() {
        return currencyName;
    }


}
