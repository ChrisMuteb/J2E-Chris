package fr.epita.core.maths.services;

public class CommonUtils {
    public static int factorial(int value) {
        int result = 1;
        // when
        while (value > 0){
            result *= value;
            value--;
        }
        return result;
    }
}
