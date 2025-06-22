-- Changeset: 002
-- Description: Create sequences for tables with auto-incrementing IDs

-- Board
ALTER TABLE board ALTER COLUMN id DROP DEFAULT;
DROP SEQUENCE IF EXISTS board_id_seq;
CREATE SEQUENCE board_id_seq START 1;
ALTER TABLE board ALTER COLUMN id SET DEFAULT nextval('board_id_seq');

-- Coluna
ALTER TABLE coluna ALTER COLUMN id DROP DEFAULT;
DROP SEQUENCE IF EXISTS coluna_id_seq;
CREATE SEQUENCE coluna_id_seq START 1;
ALTER TABLE coluna ALTER COLUMN id SET DEFAULT nextval('coluna_id_seq');

-- Card
ALTER TABLE card ALTER COLUMN id DROP DEFAULT;
DROP SEQUENCE IF EXISTS card_id_seq;
CREATE SEQUENCE card_id_seq START 1;
ALTER TABLE card ALTER COLUMN id SET DEFAULT nextval('card_id_seq');

-- Card Movimentacao
ALTER TABLE card_movimentacao ALTER COLUMN id DROP DEFAULT;
DROP SEQUENCE IF EXISTS card_movimentacao_id_seq;
CREATE SEQUENCE card_movimentacao_id_seq START 1;
ALTER TABLE card_movimentacao ALTER COLUMN id SET DEFAULT nextval('card_movimentacao_id_seq');

-- Card Bloqueio
ALTER TABLE card_bloqueio ALTER COLUMN id DROP DEFAULT;
DROP SEQUENCE IF EXISTS card_bloqueio_id_seq;
CREATE SEQUENCE card_bloqueio_id_seq START 1;
ALTER TABLE card_bloqueio ALTER COLUMN id SET DEFAULT nextval('card_bloqueio_id_seq');