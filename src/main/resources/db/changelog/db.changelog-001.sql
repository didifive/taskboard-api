CREATE TABLE board (
    id BIGINT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

CREATE TABLE coluna (
    id BIGINT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    ordem INT NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    board_id BIGINT NOT NULL,
    CONSTRAINT fk_coluna_board FOREIGN KEY (board_id) REFERENCES board(id)
);

CREATE TABLE card (
    id BIGINT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT,
    data_criacao TIMESTAMP NOT NULL,
    bloqueado BOOLEAN NOT NULL,
    coluna_atual_id BIGINT NOT NULL,
    CONSTRAINT fk_card_coluna FOREIGN KEY (coluna_atual_id) REFERENCES coluna(id)
);

CREATE TABLE card_movimentacao (
    id BIGINT PRIMARY KEY,
    data_entrada TIMESTAMP NOT NULL,
    data_saida TIMESTAMP,
    card_id BIGINT NOT NULL,
    coluna_id BIGINT NOT NULL,
    CONSTRAINT fk_mov_card FOREIGN KEY (card_id) REFERENCES card(id),
    CONSTRAINT fk_mov_coluna FOREIGN KEY (coluna_id) REFERENCES coluna(id)
);

CREATE TABLE card_bloqueio (
    id BIGINT PRIMARY KEY,
    motivo_bloqueio VARCHAR(255),
    motivo_desbloqueio VARCHAR(255),
    data_bloqueio TIMESTAMP NOT NULL,
    data_desbloqueio TIMESTAMP,
    card_id BIGINT NOT NULL,
    CONSTRAINT fk_bloq_card FOREIGN KEY (card_id) REFERENCES card(id)
);