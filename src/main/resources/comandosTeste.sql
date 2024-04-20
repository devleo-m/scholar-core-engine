--deletar todas as tabelas
DO $$ DECLARE
tabela_name RECORD;
BEGIN
FOR tabela_name IN (SELECT tablename FROM pg_tables WHERE schemaname = 'public') LOOP
        EXECUTE 'DROP TABLE IF EXISTS ' || tabela_name.tablename || ' CASCADE;';
END LOOP;
END $$;

--selecionar os valores das tabelas
SELECT * FROM papel;
select * from usuario;
select * from docente;
select * from aluno;
select * from curso;
select * from turma;
select * from materia;
select * from nota;
