import fr.epita.training.datamodel.Passenger;
import fr.epita.training.services.PassengerCSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class TestJUN2 {
    PassengerCSVReader reader;

    @BeforeEach
    public void init(){
        this.reader = new PassengerCSVReader();
    }
    @Test
    public void test() throws IOException {
        // given

        // when
        List<Passenger> list = reader.read("./data.csv");

        // then
        Assertions.assertTrue(list.get(0).getpClass().equals("1st"));
    }
}
