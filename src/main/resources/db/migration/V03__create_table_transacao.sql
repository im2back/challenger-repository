CREATE TABLE tb_transacao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    carteiraRaiz_id INT,
    carteiraDestino_id INT,
    FOREIGN KEY (carteiraRaiz_id) REFERENCES tb_user(id),
    FOREIGN KEY (carteiraDestino_id) REFERENCES tb_user(id)
);