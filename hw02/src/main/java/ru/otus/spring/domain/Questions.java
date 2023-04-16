package ru.otus.spring.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class Questions {
    private String questionName;

    private List<String> questionsAnswers;
}
