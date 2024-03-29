package fr.epita.quiz.datamodel;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "QUESTIONS")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "QUEST_ID")
    private int id;
    @Column(name = "QUEST_TITLE")

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @OneToMany(targetEntity = Choice.class, mappedBy = "question", fetch = FetchType.LAZY)
    private List<Choice> choices;
}
