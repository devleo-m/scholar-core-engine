CREATE TABLE IF NOT EXISTS papel (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS usuario (
    id BIGSERIAL PRIMARY KEY,
    login VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(100) NOT NULL,
    papel_id BIGINT NOT NULL,
    FOREIGN KEY (papel_id) REFERENCES papel(id)
);

CREATE TABLE IF NOT EXISTS docente (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    data_entrada DATE DEFAULT NOW(),
    id_usuario BIGINT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);

CREATE TABLE IF NOT EXISTS aluno (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    data_nascimento DATE NOT NULL,
    id_usuario BIGINT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);

CREATE TABLE IF NOT EXISTS curso (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL
);

CREATE TABLE IF NOT EXISTS turma (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    id_docente BIGINT NOT NULL,
    id_curso BIGINT NOT NULL,
    FOREIGN KEY (id_docente) REFERENCES docente(id),
    FOREIGN KEY (id_curso) REFERENCES curso(id)
);

CREATE TABLE IF NOT EXISTS materia (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    id_curso BIGINT NOT NULL,
    FOREIGN KEY (id_curso) REFERENCES curso(id)
);

CREATE TABLE IF NOT EXISTS nota (
    id BIGSERIAL PRIMARY KEY,
    valor NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    data_nota DATE DEFAULT NOW(),
    id_docente BIGINT NOT NULL,
    id_aluno BIGINT NOT NULL,
    id_materia BIGINT NOT NULL,
    FOREIGN KEY (id_docente) REFERENCES docente(id),
    FOREIGN KEY (id_aluno) REFERENCES aluno(id),
    FOREIGN KEY (id_materia) REFERENCES materia(id)
);