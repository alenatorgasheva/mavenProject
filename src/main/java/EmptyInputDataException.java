public class EmptyInputDataException extends Exception{
    @Override
    public void printStackTrace() {
        System.out.println("""
                ------------------------------------------------------------------
                Данные о валюте и дате пустые.\s
                Попробуйте ввести их еще раз.
                ------------------------------------------------------------------
                """);
    }
}
