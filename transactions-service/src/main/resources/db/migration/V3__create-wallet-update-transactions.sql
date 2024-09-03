CREATE TABLE tb_wallet (
    id BINARY(16) NOT NULL,
    user_id BINARY(16) NOT NULL UNIQUE,
    balance DECIMAL(19, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    PRIMARY KEY (id)
);

ALTER TABLE tb_transactions ADD COLUMN updated_at TIMESTAMP NOT NULL;