-- Criação da tabela board
CREATE TABLE tb_board (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criação do tipo ENUM para column_type
CREATE TYPE column_type_enum AS ENUM ('TODO', 'DONE', 'CANCELED');

-- Tabela board_column com enum
CREATE TABLE tb_board_column (
    id BIGSERIAL PRIMARY KEY,
    board_id BIGINT NOT NULL REFERENCES tb_board(id),
    name VARCHAR(50) NOT NULL,
    position INT NOT NULL,
    column_type column_type_enum NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela card
CREATE TABLE tb_card (
    id BIGSERIAL PRIMARY KEY,
    board_column_id BIGINT NOT NULL REFERENCES tb_board_column(id),
    title VARCHAR(50) NOT NULL,
    description TEXT NOT NULL,
    blocked BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de histórico de bloqueios
CREATE TABLE tb_card_block_history (
    id BIGSERIAL PRIMARY KEY,
    card_id BIGINT NOT NULL REFERENCES tb_card(id),
    block_at TIMESTAMP NOT NULL,
    block_reason TEXT NOT NULL,
    unblock_at TIMESTAMP,
    unblock_reason TEXT
);

-- Tabela de transições de colunas
CREATE TABLE tb_card_transition (
    id BIGSERIAL PRIMARY KEY,
    card_id BIGINT NOT NULL REFERENCES tb_card(id),
    origin_column_id BIGINT NOT NULL REFERENCES tb_board_column(id),
    destination_column_id BIGINT NOT NULL REFERENCES tb_board_column(id),
    moved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP

);