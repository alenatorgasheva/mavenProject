package Maven;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class ServiceDB extends Service {
    private String url = "jdbc:mysql://localhost:3306/mydb";
    private String user = "root";
    private String password = "password";

    @Override
    public boolean getExchangeRate(Currency currency) throws DataNotFoundException {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM exchangeRates WHERE (`currency_name` = '" + currency.getCurrencyName() + "') AND (`date` = '" + currency.getDate() + "');";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                currency.setRate(resultSet.getDouble("rate"));
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public void updateDatabase(String date) {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {
            List<Currency> currencyList = ServiceAPI.getRatesByDate(date);

            for (Currency currency : currencyList) {
                // если есть в бд, то обновляем курс
                String query = "SELECT * FROM exchangeRates WHERE (`currency_name` = '" + currency.getCurrencyName() + "') AND (`date` = '" + currency.getDate() + "');";
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    query = "UPDATE `mydb`.`exchangerates` SET `rate` = '" + currency.getRate() + "' WHERE (`id` = '" + id + "');";
                    statement.executeUpdate(query);
                    return;
                }
                // если нет в бд, то добавляем запись
                query = "INSERT INTO exchangeRates (`currency_name`, `date`, `rate`) VALUES ('" + currency.getCurrencyName() + "', '" + currency.getDate() + "', '" + currency.getRate() + "');";
                statement.executeUpdate(query);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
