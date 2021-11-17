package Maven;

public class Service {
    boolean getExchangeRate(Currency currency) throws DataNotFoundException {
        return false;
    }

    public String correctDate (String date) {
        String[] splitedDate = date.split("-");
        return splitedDate[2] + "." + splitedDate[1] + "." + splitedDate[0];
    }
}
