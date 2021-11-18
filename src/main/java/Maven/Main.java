package Maven;

public class Main {
    public static void main(String[] args) {
        ServiceAPI serviceAPI = new ServiceAPI();
        ServiceDB serviceDB = new ServiceDB();

        if (!args[0].equals("updateDB")) {
            String data = serviceAPI.correctDate(args[0]);
            String currencyName = args[1];

            try {
                Currency currency = new Currency(data, currencyName);
                if (serviceDB.getExchangeRate(currency)) {
                    serviceAPI.getExchangeRate(currency);
                }
                currency.printInfo();
            } catch (EmptyInputDataException e) {
                e.printStackTrace();
            } catch (DataNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            String date = serviceAPI.correctDate(args[1]);

            serviceDB.updateDatabase(date);
        }
    }
}
