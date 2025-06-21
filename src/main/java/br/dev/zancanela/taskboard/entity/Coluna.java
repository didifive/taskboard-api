package br.dev.zancanela.taskboard.entity;

import br.dev.zancanela.taskboard.enums.ColunaTipo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "coluna")
public class Coluna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer ordem;

    @Enumerated(EnumType.STRING)
    private ColunaTipo tipo;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "colunaAtual")
    private List<Card> cards;
}
