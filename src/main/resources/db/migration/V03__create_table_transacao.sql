CREATE TABLE tb_transacao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    valor DECIMAL (10, 2),
    carteiraPagante_id BIGINT,
    carteiraRecebedor_id BIGINT,
    
    FOREIGN KEY (carteiraPagante_id) REFERENCES tb_carteira(id),
    FOREIGN KEY (carteiraRecebedor_id) REFERENCES tb_carteira(id)
);