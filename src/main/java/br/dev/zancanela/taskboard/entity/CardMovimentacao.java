package br.dev.zancanela.taskboard.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@Entity(name = "card_movimentacao")
public class CardMovimentacao implements Comparable<CardMovimentacao> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @ManyToOne
    @JoinColumn(name = "coluna_id")
    private Coluna coluna;

    @Override
    public int compareTo(CardMovimentacao o) {
        if (this.dataEntrada == null && o.dataEntrada == null) return 0;
        if (this.dataEntrada == null) return -1;
        if (o.dataEntrada == null) return 1;
        return this.dataEntrada.compareTo(o.dataEntrada);
    }
}
