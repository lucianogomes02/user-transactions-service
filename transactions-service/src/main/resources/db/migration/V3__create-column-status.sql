ALTER TABLE tb_transactions
ADD COLUMN status ENUM('PROCESSING', 'SUCCEEDED', 'FAILED') NOT NULL DEFAULT 'PROCESSING';