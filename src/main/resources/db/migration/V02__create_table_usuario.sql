CREATE TABLE tb_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(255) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    carteira_id BIGINT,
    
    UNIQUE (cpf),
    UNIQUE (email),
    
    FOREIGN KEY (carteira_id) REFERENCES tb_carteira (id)
);