INSERT INTO papel (nome)
SELECT 'admin' WHERE NOT EXISTS (SELECT 1 FROM papel WHERE nome = 'admin');

INSERT INTO papel (nome)
SELECT 'aluno' WHERE NOT EXISTS (SELECT 1 FROM papel WHERE nome = 'aluno');

INSERT INTO papel (nome)
SELECT 'professor' WHERE NOT EXISTS (SELECT 1 FROM papel WHERE nome = 'professor');