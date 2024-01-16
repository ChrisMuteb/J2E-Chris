package fr.epita.tests.quiz;

import fr.epita.quiz.services.ChoiceJPADAO;
import fr.epita.quiz.services.QuestionDAOWithDI;
import fr.epita.quiz.services.QuestionJPADAO;
import fr.epita.quiz.services.api.IChoiceDAO;
import fr.epita.quiz.services.api.IQuestionDAO;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class BeanConfiguration {
    static {
        System.setProperty("conf.location", "src/test/resources/conf.properties");
    }
    @Bean("questionDAO")
    public IQuestionDAO questionJPADAO(SessionFactory factory){
        return new QuestionJPADAO(factory);
    }
    @Bean("choiceDAO")
    public IChoiceDAO choiceDAO(SessionFactory factory){
        return  new ChoiceJPADAO(factory);
    }
    @Bean("questionJPADAO")
    QuestionDAOWithDI questionDAOWithDI(){
        return new QuestionDAOWithDI();
    }
    @Bean("defaultString")
    public String getTestStringValue(){
        return "testFromDI";
    }
    @Bean("testStringInjection")
    public String getTestStringValue2(){
        return "testFromDI";
    }
    @Bean("test")
    public fr.epita.quiz.services.Configuration configuration(){
        return fr.epita.quiz.services.Configuration.getInstance();
    }

    @Bean
    DataSource dataSource(@Autowired fr.epita.quiz.services.Configuration configuration){
        return new DriverManagerDataSource(configuration.getDBUrl());
    }
    @Bean
    LocalSessionFactoryBean getSessionFactory(@Autowired DataSource ds){
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setPackagesToScan("fr.epita.quiz.datamodel");
        sessionFactoryBean.setDataSource(ds);
        Properties extraproperties = new Properties();
        extraproperties.put("hibernate.hbm2ddl.auto", "update");
        extraproperties.put("hibernate.show_sql", "true");

        sessionFactoryBean.setHibernateProperties(extraproperties);
        return sessionFactoryBean;
    }
}
