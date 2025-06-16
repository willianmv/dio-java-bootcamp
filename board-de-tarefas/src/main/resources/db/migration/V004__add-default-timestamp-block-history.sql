-- Adiciona valor padrão para blocked_at
ALTER TABLE tb_card_block_history
ALTER COLUMN block_at SET DEFAULT CURRENT_TIMESTAMP;

-- Adiciona valor padrão para unblocked_at (opcional)
ALTER TABLE tb_card_block_history
ALTER COLUMN unblock_at SET DEFAULT CURRENT_TIMESTAMP;
