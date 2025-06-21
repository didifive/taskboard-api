package br.dev.zancanela.taskboard.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private LocalDateTime dataCriacao;
    private Boolean bloqueado;

    @ManyToOne
    @JoinColumn(name = "coluna_atual_id")
    private Coluna colunaAtual;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CardMovimentacao> movimentacoes;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CardBloqueio> bloqueios;
}
