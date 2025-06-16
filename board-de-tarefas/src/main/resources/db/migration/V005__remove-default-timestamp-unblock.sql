-- Remove o valor padrão da coluna unblock_at
ALTER TABLE tb_card_block_history
ALTER COLUMN unblock_at DROP DEFAULT;