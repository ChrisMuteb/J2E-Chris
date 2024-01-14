package fr.epita.tests.quiz;

import org.junit.jupiter.api.Test;

import static fr.epita.core.maths.services.CommonUtils.factorial;

public class QuizCoreTests {
    @Test
    public void test(){
        // given
        int value = 5;

        // when
        int result = factorial(value);

        // then
        if(result != 120)
            throw new AssertionError("expected 120 but got " + result);
        else
            System.out.println("success!");
    }


}
