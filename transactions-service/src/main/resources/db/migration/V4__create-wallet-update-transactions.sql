CREATE TABLE tb_wallet (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    balance DECIMAL(19, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL

    PRIMARY KEY (id),
);

ALTER TABLE tb_wallet CREATE COLUMN updated_at TIMESTAMP NOT NULL;