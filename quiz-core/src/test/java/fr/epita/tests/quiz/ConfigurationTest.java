package fr.epita.tests.quiz;

import fr.epita.quiz.services.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ConfigurationTest {
    private Configuration configuration;
    @BeforeAll
    public static void globalSetup(){
        System.setProperty("conf.location", "src/test/resources/conf.properties");
    }
    @BeforeEach
    public void setup(){
        this.configuration = Configuration.getInstance();
    }
    @Test
    public void test() throws IOException {
        // given
        System.setProperty("conf.location", "src/test/resources/conf.properties");
        Configuration configuration = Configuration.getInstance();

        // when
        String dbUrl = configuration.getDBUrl();

        // then
        assertThat(dbUrl).isNotNull();
        System.out.println(dbUrl);
    }
    @Test
    public void test2(){

    }
}
