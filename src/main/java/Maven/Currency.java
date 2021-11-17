package Maven;

public class Currency {
    private String date;
    private String currencyName;
    private Double rate = null;

    public Currency(String data, String name) throws EmptyInputDataException {
        if (!data.isBlank() && !name.isBlank()) {
            date = data;
            currencyName = name.toUpperCase();
        } else {
            throw new EmptyInputDataException();
        }
    }

    public void setRate(Double exchangeRate) {
        rate = exchangeRate;
    }

    public Double getRate() {
        return rate;
    }

    public String getDate() {
        return date;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void printInfo() {
        System.out.println("\n1 " + currencyName + " = " + rate + " RUB");
    }
}
