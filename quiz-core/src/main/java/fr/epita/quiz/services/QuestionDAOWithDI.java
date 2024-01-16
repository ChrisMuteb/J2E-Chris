package fr.epita.quiz.services;

import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.services.api.IQuestionDAO;
import jakarta.inject.Inject;


import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAOWithDI implements IQuestionDAO {
    @Inject
    DataSource ds;
    private static final String INSERT_STMT = "INSERT INTO QUESTIONS(title) values (?)";
    private static final String SELECT_STMT = "SELECT id, title from QUESTIONS WHERE title like ?";
    public void create(Question question) {
        try{
            Connection connection = ds.getConnection();
            PreparedStatement stmt = connection.prepareStatement(INSERT_STMT);
            stmt.setString(1, question.getTitle());
            stmt.execute();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    @Override
    public void update(Question question) {

    }
    @Override
    public void delete(Question question) {

    }
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(Configuration.getInstance().getDBUrl());
    }

    public List<Question> search(Question question) {
        Connection connection = null;
        List<Question> questions = new ArrayList<>();

        try {
            connection = getConnection();
            PreparedStatement stat = connection.prepareStatement(SELECT_STMT);
            stat.setString(1, question.getTitle() + "%");
            ResultSet resultSet = stat.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                Question currentQuestion = new Question();
                currentQuestion.setTitle(title);
                currentQuestion.setId(id);
                questions.add(currentQuestion);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return questions;
    }

    @Override
    public Question getById(Object id) {
        return null;
    }
}
