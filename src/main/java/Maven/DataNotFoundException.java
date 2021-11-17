package Maven;

public class DataNotFoundException extends Exception{
    @Override
    public void printStackTrace() {
        System.out.println("\nДанные о курсе валюты не найдены." +
                "\nВозможно допущены ошибки при вводе данных. Попробуйте еще раз.");
    }
}
