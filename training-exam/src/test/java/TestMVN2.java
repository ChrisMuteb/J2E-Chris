import fr.epita.training.datamodel.Passenger;
import fr.epita.training.services.PassengerCSVReader;

import java.io.IOException;
import java.util.List;

public class TestMVN2 {
    public static void test() throws IOException {
        PassengerCSVReader p = new PassengerCSVReader();
        List<Passenger> list = p.read("./training-exam/data.csv");
        p.sortOnPClass(list);
        System.out.println(list);
    }
}
