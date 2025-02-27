CREATE TABLE IF NOT EXISTS soldado_ativo (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    idade INT NOT NULL,
    criado_por VARCHAR(255),
    modificado_por VARCHAR(255),
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_modificacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS missao_terrestre (
    id SERIAL PRIMARY KEY,
    nome_missao VARCHAR(255) NOT NULL,
    criado_por VARCHAR(255),
    modificado_por VARCHAR(255),
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_modificacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS missao_soldado (
    missao_id INT NOT NULL,
    soldado_id INT NOT NULL,
    PRIMARY KEY (missao_id, soldado_id),
    FOREIGN KEY (missao_id) REFERENCES missao_terrestre(id),
    FOREIGN KEY (soldado_id) REFERENCES soldado_ativo(id)
);
