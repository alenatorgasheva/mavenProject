package Maven;

public class EmptyInputDataException extends Exception{
    @Override
    public void printStackTrace() {
        System.out.println("\nДанные о валюте и дате пустые. " +
                "\nПопробуйте ввести их еще раз.\n");
    }
}
