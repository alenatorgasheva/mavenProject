import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        Scanner in = new Scanner(System.in);

        String answer = "+";
        while (answer.equals("+")) {
            System.out.println("Введите дату в формате dd/MM/yyyy, например, 21/02/2019: ");
            String data = in.nextLine();
            System.out.println("Введите идентификатор валюты, например, USD: ");
            String currencyName = in.nextLine();
            System.out.println();

            try {
                Currency currency = new Currency(data, currencyName);
                currency.getExchangeRate();
            } catch (EmptyInputDataException e) {
                e.printStackTrace();
            } catch (DataNotFoundException e) {
                e.printStackTrace();
            }
            System.out.print("Продолжить? (+/-): ");
            answer = in.nextLine();
            System.out.println();
        }
    }
}
