package br.dev.zancanela.taskboard.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@Entity(name = "card_bloqueio")
public class CardBloqueio implements Comparable<CardBloqueio> {
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

    @Override
    public int compareTo(CardBloqueio o) {
        if (this.dataBloqueio == null && o.dataBloqueio == null) return 0;
        if (this.dataBloqueio == null) return -1;
        if (o.dataBloqueio == null) return 1;
        return this.dataBloqueio.compareTo(o.dataBloqueio);
    }

}