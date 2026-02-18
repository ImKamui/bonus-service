CREATE INDEX IF NOT EXISTS idx_id_card_num ON bonus_cards(id, card_num);
CREATE INDEX IF NOT EXISTS idx_card_num_operation_time ON operations(card_num, operation_time);