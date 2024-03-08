CREATE TABLE tabela_notas (
    cod SERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    texto TEXT,
    data_criacao DATE
);
