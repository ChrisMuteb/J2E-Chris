import com.sun.net.httpserver.HttpServer;
import fr.epita.quiz.datamodel.User;
import fr.epita.quiz.web.client.UserHttpClient;
import fr.epita.quiz.web.server.Microserver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
public class ClientServerCommunicationTest {
    @Inject
    private Microserver server;
    @Inject
    private UserHttpClient client;
    @Test
    public void test() throws IOException {
        //given
        server.start();
        client.createUser(new User("test", "paris", "0123"));

        // when
        List<User> users = client.getUsers();

        // then
        assertThat(users).hasSize(2);
        server.stop();
    }
}
