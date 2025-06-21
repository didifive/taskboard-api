package br.dev.zancanela.taskboard.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "card_bloqueio")
public class CardBloqueio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String motivoBloqueio;
    private String motivoDesbloqueio;
    private LocalDateTime dataBloqueio;
    private LocalDateTime dataDesbloqueio;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

}