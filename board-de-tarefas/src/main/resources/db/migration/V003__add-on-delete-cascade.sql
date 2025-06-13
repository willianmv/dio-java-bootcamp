-- 1. tb_board_column → tb_board
ALTER TABLE tb_board_column DROP CONSTRAINT IF EXISTS tb_board_column_board_id_fkey;
ALTER TABLE tb_board_column
ADD CONSTRAINT fk_board_column_board
FOREIGN KEY (board_id) REFERENCES tb_board(id) ON DELETE CASCADE;


-- 2. tb_card → tb_board_column
ALTER TABLE tb_card DROP CONSTRAINT IF EXISTS tb_card_board_column_id_fkey;
ALTER TABLE tb_card
ADD CONSTRAINT fk_card_column
FOREIGN KEY (board_column_id) REFERENCES tb_board_column(id) ON DELETE CASCADE;


-- 3. tb_card_block_history → tb_card
ALTER TABLE tb_card_block_history DROP CONSTRAINT IF EXISTS tb_card_block_history_card_id_fkey;
ALTER TABLE tb_card_block_history
ADD CONSTRAINT fk_card_block_card
FOREIGN KEY (card_id) REFERENCES tb_card(id) ON DELETE CASCADE;


-- 4. tb_card_transition → tb_card
ALTER TABLE tb_card_transition DROP CONSTRAINT IF EXISTS tb_card_transition_card_id_fkey;
ALTER TABLE tb_card_transition
ADD CONSTRAINT fk_transition_card
FOREIGN KEY (card_id) REFERENCES tb_card(id) ON DELETE CASCADE;


-- 5. tb_card_transition → origin_column_id
ALTER TABLE tb_card_transition DROP CONSTRAINT IF EXISTS tb_card_transition_origin_column_id_fkey;
ALTER TABLE tb_card_transition
ADD CONSTRAINT fk_transition_origin_column
FOREIGN KEY (origin_column_id) REFERENCES tb_board_column(id) ON DELETE CASCADE;


-- 6. tb_card_transition → destination_column_id
ALTER TABLE tb_card_transition DROP CONSTRAINT IF EXISTS tb_card_transition_destination_column_id_fkey;
ALTER TABLE tb_card_transition
ADD CONSTRAINT fk_transition_dest_column
FOREIGN KEY (destination_column_id) REFERENCES tb_board_column(id) ON DELETE CASCADE;