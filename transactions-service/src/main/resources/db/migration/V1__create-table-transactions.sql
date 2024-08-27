CREATE TABLE tb_transactions (
    id BINARY(16) NOT NULL,
    sender_id BINARY(16) NOT NULL,
    receiver_id BINARY(16) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
);