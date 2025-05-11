
-- Renomear colunas existentes, se necessário
ALTER TABLE Doctors
    RENAME COLUMN login TO tell,
    RENAME COLUMN password TO cpf;

-- Adicionar novas colunas
ALTER TABLE Doctors
    ADD COLUMN data_nascimento DATE;

-- Remover colunas que não são mais necessárias
ALTER TABLE Doctors
    DROP COLUMN role;


-- Adicionar novas colunas
ALTER TABLE Patients
    ADD COLUMN data_nascimento DATE;

-- Remover colunas que não são mais necessárias
ALTER TABLE Patients
    DROP COLUMN login,
    DROP COLUMN password,
    DROP COLUMN role;

