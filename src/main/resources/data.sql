CREATE TABLE IF NOT EXISTS papel (
    id_papel BIGSERIAL PRIMARY KEY,
    nome_papel VARCHAR(150) NOT NULL
);

CREATE TABLE IF NOT EXISTS usuario (
    id_usuario BIGSERIAL PRIMARY KEY,
    nome_usuario VARCHAR(100) UNIQUE NOT NULL,
    senha_usuario VARCHAR(100) NOT NULL,
    id_papel BIGINT UNIQUE NOT NULL,
    FOREIGN KEY (id_papel) REFERENCES papel(id_papel)
);

CREATE TABLE IF NOT EXISTS docente (
    id_docente BIGSERIAL PRIMARY KEY,
    nome_docente VARCHAR(150) NOT NULL,
    data_entrada DATE NOT NULL DEFAULT NOW(),
    id_usuario BIGINT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

CREATE TABLE IF NOT EXISTS curso (
    id_curso BIGSERIAL PRIMARY KEY,
    nome_curso VARCHAR(150) NOT NULL
);

CREATE TABLE IF NOT EXISTS turma (
    id_turma BIGSERIAL PRIMARY KEY,
    nome_turma VARCHAR(150) NOT NULL,
    id_docente BIGINT NOT NULL,
    id_curso BIGINT NOT NULL,
    FOREIGN KEY (id_docente) REFERENCES docente(id_docente),
    FOREIGN KEY (id_curso) REFERENCES curso(id_curso)
);

CREATE TABLE IF NOT EXISTS aluno (
    id_aluno BIGSERIAL PRIMARY KEY,
    nome_aluno VARCHAR(150) NOT NULL,
    data_nascimento DATE NOT NULL,
    id_usuario BIGINT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

CREATE TABLE IF NOT EXISTS materia (
    id_materia BIGSERIAL PRIMARY KEY,
    nome_materia VARCHAR(100) NOT NULL,
    id_curso BIGINT NOT NULL,
    FOREIGN KEY (id_curso) REFERENCES curso(id_curso)
);

CREATE TABLE IF NOT EXISTS nota (
    id_nota BIGSERIAL PRIMARY KEY,
    valor_nota NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    data_nota DATE NOT NULL DEFAULT NOW(),
    id_docente BIGINT NOT NULL,
    id_aluno BIGINT NOT NULL,
    id_materia BIGINT NOT NULL,
    FOREIGN KEY (id_docente) REFERENCES docente(id_docente),
    FOREIGN KEY (id_aluno) REFERENCES aluno(id_aluno),
    FOREIGN KEY (id_materia) REFERENCES materia(id_materia)
);