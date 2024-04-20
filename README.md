# Sistema Educacional

## Descrição
O Sistema Educacional é uma API Rest back-end robusta desenvolvida em Java, utilizando o framework Spring Boot. Ela foi projetada para gerenciar eficientemente as operações acadêmicas de instituições de ensino, oferecendo uma solução completa para o gerenciamento de usuários, docentes, turmas, cursos, matérias e notas.

## Tecnologias
- **Java**: Linguagem de programação para o desenvolvimento back-end.
- **Spring Boot**: Framework utilizado para a construção da API Rest.
- **Spring Security**: Para adicionar controle de segurança.
- **DTO**: Classes DTO para padronização das requisições e respostas.
- **PostgreSQL**: Banco de dados utilizado, com suporte para Docker ou conexão direta.
- **GitFlow**: Estratégia de versionamento de código no GitHub.
- **Trello**: Ferramenta para organização e gerenciamento de tarefas.

## Entidades e Atributos
O sistema conta com diversas entidades, como `Papel`, `Usuário`, `Docente`, `Turma`, `Aluno`, `Curso`, `Matéria` e `Notas`, cada uma com seus atributos específicos e regras de negócio associadas.

![sistema-educacional](https://github.com/devleo-m/api-sistema-educacional/assets/149954966/7e0b0454-14ba-44d9-ad38-744f0cf6d19a)

## Como Executar
Para executar o Sistema Educacional, siga os passos abaixo:
1. Clone o repositório do projeto: https://github.com/devleo-m/api-sistema-educacional.git
2. Configure o banco de dados PostgreSQL criando uma Database com o nome `sistema_educacional`

## Guia de Uso do Postman

Este guia irá ajudá-lo a interagir com a API utilizando o Postman.

# Autenticação

O sistema possui um usuário administrador padrão com as seguintes credenciais:
- **Login**: admin
- **Senha**: admin

Para entrar no sistema e receber um token de acesso, siga estes passos:

1. Utilize o Postman para criar uma requisição POST direcionada para: localhost:8081/login.
2. No corpo da requisição, escolha o formato raw e defina o tipo como JSON.
3. Adicione as seguintes credenciais do administrador:

```json
{
    "login": "admin",
    "senha": "admin"
}
```
### Configuração do Token no Postman
#### No Postman:
- Clique em Authorization.
- Selecione Bearer Token da lista.
- Insira o token JWT no campo disponível.

Após o login, você receberá uma resposta similar a esta:
```json
{
    "name": "admin",
    "role": "ADMIN",
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJsb2dpbi1hdXRoLWFwaSIsInN1YiI6ImFkbWluIiwiZXhwIjoxNzEzOTIzODAwfQ.yDuFxmkoMltQgzoahksHnt_i2_dSqSEkZS4c5SVmoMU"
}
```

Copie e use este token para acessar outros endpoints do sistema.

### Criação de Novos Usuários
#### Somente um administrador pode criar novos usuários e deletar. Para isso, utilize o token obtido e siga os passos:
1. Crie uma requisição POST para: localhost:8081/registrar.
2. No cabeçalho Authorization, adicione o token JWT.
3. No corpo da requisição, insira os dados do novo usuário. Por exemplo:

```json
{
    "login": "leonardo",
    "senha": "root",
    "tipoUsuario": "ALUNO"
}
```
```json
{
    "login": "joao",
    "senha": "root",
    "tipoUsuario": "PROFESSOR"
}
```

Repita o processo para cada novo usuário, alterando os dados conforme necessário.

### Tipos de Usuários
#### No sistema, você pode atribuir um dos cinco papéis aos usuários: ADMIN, ALUNO, PROFESSOR, PEDAGOGICO e RECRUITER.
Com essas informações, você pode registrar novos usuários e gerenciar o acesso ao sistema de forma eficiente.

#Docentes
Para criar, visualizar ou deletar informações de docentes, siga os exemplos abaixo:

## Criar Docente

1. Crie uma requisição POST com a URL: localhost:8081/api/docentes

**Resposta de Sucesso**:
Status: `201 Created`
```json
{
    "usuarioId": 3,
    "nome": "Joao da Costa",
    "dataEntrada": "14/02/2018"
}
```
Resposta:
```json
[
    {
        "id": 1,
        "nome": "Joao da Costa",
        "dataEntrada": "14/02/2018",
        "login": "joao",
        "papel": "PROFESSOR"
    }
]
```

## Listar Docentes

1. Crie uma requisição GET com a URL: localhost:8081/api/docentes

**Resposta de Sucesso**:
#### Status: `200 OK`

Resposta:
```json
[
    {
        "id": 1,
        "nome": "Joao da Costa",
        "dataEntrada": "14/02/2018",
        "login": "joao",
        "papel": "PROFESSOR"
    }
]
```

## Listar Docentes por ID

1. Crie uma requisição GET com a URL: localhost:8081/api/docentes/1

**Resposta de Sucesso**:
#### Status: `200 OK`

Resposta:
```json
[
    {
        "id": 1,
        "nome": "Joao da Costa",
        "dataEntrada": "14/02/2018",
        "login": "joao",
        "papel": "PROFESSOR"
    }
]
```
## Atualizar Docente

1. Crie uma requisição PUT com a URL: localhost:8081/api/docentes/1

**Resposta de Sucesso**:
#### Status: `200 OK`

```json
{
    "nome": "Joao Nascimento",
    "dataEntrada": "13/01/2017",
    "usuarioId": 3
}
```
Resposta:
```json
{
    "id": 2,
    "nome": "Joao Nascimento",
    "dataEntrada": "13/01/2017",
    "login": "joao",
    "papel": "PROFESSOR"
}
```
## Deletar Docentes

1. Crie uma requisição DELETE com a URL: localhost:8081/api/docentes/1

**Resposta de Sucesso**:
#### Status `204 No Content`

#Cursos
Para criar, visualizar ou deletar informações de cursos, siga os exemplos abaixo:

## Criar Curso

1. Crie uma requisição POST com a URL: localhost:8081/api/cursos

**Resposta de Sucesso**:
#### Status: `201 Created`
```json
{
    "nome": "Java"
}  
```
Resposta:
```json
{
    "id": 1,
    "nome": "Java"
}
```

## Listar Cursos

1. Crie uma requisição GET com a URL: localhost:8081/api/cursos

**Resposta de Sucesso**:
#### Status: `200 OK`

Resposta:
```json
[
    {
        "id": 1,
        "nome": "Java"
    }
]
```

## Listar cursos por ID

1. Crie uma requisição GET com a URL: localhost:8081/api/cursos/1

**Resposta de Sucesso**:
#### Status: `200 OK`

Resposta:
```json
{
    "id": 1,
    "nome": "Java"
}
```

## Atualizar Cursos

1. Crie uma requisição PUT com a URL: localhost:8081/api/cursos/1

**Resposta de Sucesso**:
#### Status: `200 OK`

```json
{
    "nome": "Lab365"
}
```
Resposta:
```json
{
    "id": 1,
    "nome": "Lab365"
}
```
## Deletar Cursos

1. Crie uma requisição DELETE com a URL: localhost:8081/api/cursos/1

**Resposta de Sucesso**:
#### Status `204 No Content`

#Turmas
Para criar, visualizar ou deletar informações de Turmas, siga os exemplos abaixo:

## Criar Turmas

1. Crie uma requisição POST com a URL: localhost:8081/api/turmas

**Resposta de Sucesso**:
#### Status: `201 Created`
```json
{
    "nome": "FullStack [EDUCATION]",
    "docenteId": 1,
    "cursoId": 1
}
```
Resposta:
```json
{
    "id": 1,
    "turma": "FullStack [EDUCATION]",
    "docente": "Joao Nascimento",
    "curso": "Lab365"
}
```
## Listar Turmas

1. Crie uma requisição GET com a URL: localhost:8081/api/turmas

**Resposta de Sucesso**:
#### Status: `200 OK`

Resposta:
```json
[
    {
        "id": 1,
        "turma": "FullStack [EDUCATION]",
        "docente": "Joao Nascimento",
        "curso": "Lab365"
    }
]
```

## Listar Turmas por ID

1. Crie uma requisição GET com a URL: localhost:8081/api/turmas/1

**Resposta de Sucesso**:
#### Status: `200 OK`

Resposta:
```json
{
    "id": 1,
    "turma": "FullStack [EDUCATION]",
    "docente": "Joao Nascimento",
    "curso": "Lab365",
    "totalAlunos": 0
}
```

## Atualizar Turma

1. Crie uma requisição PUT com a URL: localhost:8081/api/turmas/1

**Resposta de Sucesso**:
#### Status: `200 OK`

```json
{
    "nome": "FullStack [EDUCATION] MEGA",
    "docenteId": 1,
    "cursoId": 1
}
```
Resposta:
```json
{
    "id": 1,
    "turma": "FullStack [EDUCATION] MEGA",
    "docente": "Joao Nascimento",
    "curso": "Lab365"
}
```
## Deletar Turmas

1. Crie uma requisição DELETE com a URL: localhost:8081/api/turmas/1

**Resposta de Sucesso**:
#### Status `204 No Content`

#Aluno
Para criar, visualizar ou deletar informações de Alunos, siga os exemplos abaixo:

## Criar Alunos

1. Crie uma requisição POST com a URL: localhost:8081/api/alunos

**Resposta de Sucesso**:
#### Status: `201 Created`
```json
{
    "nome": "Leonardo Madeira",
    "nascimento": "01/01/1996",
    "turmaId": 1,
    "usuarioId": 2
}
```
Resposta:
```json
{
    "id": 1,
    "nome": "Leonardo Madeira",
    "nascimento": "01/01/1996",
    "turma": "FullStack [EDUCATION] MEGA",
    "login": "leonardo",
    "papel": "ALUNO"
}
```
## Listar Alunos

1. Crie uma requisição GET com a URL: localhost:8081/api/alunos

**Resposta de Sucesso**:
#### Status: `200 OK`

Resposta:
```json
[
    {
        "id": 1,
        "nome": "Leonardo Madeira",
        "nascimento": "07/10/1996",
        "turma": "FullStack [EDUCATION] MEGA",
        "login": "leonardo",
        "papel": "ALUNO"
    }
]
```

## Listar Alunos por ID

1. Crie uma requisição GET com a URL: localhost:8081/api/alunos/1

**Resposta de Sucesso**:
#### Status: `200 OK`

Resposta:
```json
{
    "id": 1,
    "nome": "Leonardo Madeira",
    "nascimento": "07/10/1996",
    "turma": "FullStack [EDUCATION] MEGA",
    "login": "leonardo",
    "papel": "ALUNO"
}
```

## Atualizar Alunos

1. Crie uma requisição PUT com a URL: localhost:8081/api/alunos/1

**Resposta de Sucesso**:
#### Status: `200 OK`

```json
{
    "nome": "Leonardo Madeira B Silva",
    "nascimento": "10/10/1996",
    "turmaId": 1,
    "usuarioId": 2
}
```
Resposta:
```json
{
    "id": 1,
    "nome": "Leonardo Madeira B Silva",
    "nascimento": "10/10/1996",
    "turma": "FullStack [EDUCATION] MEGA",
    "login": "leonardo",
    "papel": "ALUNO"
}
```
## Deletar Alunos

1. Crie uma requisição DELETE com a URL: localhost:8081/api/alunos/1

**Resposta de Sucesso**:
#### Status `204 No Content`

#Materia
Para criar, visualizar ou deletar informações de Materia, siga os exemplos abaixo:

## Criar Materia

1. Crie uma requisição POST com a URL: localhost:8081/api/materias

**Resposta de Sucesso**:
#### Status: `201 Created`
```json
{
    "nomeMateria": "Java Spring",
    "cursoId": 1
}
```
Resposta:
```json
{
    "materiaId": 1,
    "nomeMateria": "Java Spring",
    "nomeCurso": "Lab365"
}
```
## Listar Materias

1. Crie uma requisição GET com a URL: localhost:8081/api/materias

**Resposta de Sucesso**:
#### Status: `200 OK`

Resposta:
```json
[
    {
        "materiaId": 1,
        "nomeMateria": "Java Spring",
        "nomeCurso": "Lab365"
    }
]
```

## Listar Materia por ID

1. Crie uma requisição GET com a URL: localhost:8081/api/materias/1

**Resposta de Sucesso**:
#### Status: `200 OK`

Resposta:
```json
{
    "materiaId": 1,
    "nomeMateria": "Java Spring",
    "nomeCurso": "Lab365"
}
```

## Atualizar Materia

1. Crie uma requisição PUT com a URL: localhost:8081/api/materias/1

**Resposta de Sucesso**:
#### Status: `200 OK`

```json
{
    "nomeMateria": "Spring Boot",
    "cursoId": 1
}
```
Resposta:
```json
{
    "materiaId": 1,
    "nomeMateria": "Spring Boot",
    "nomeCurso": "Lab365"
}
```
## Deletar Materia

1. Crie uma requisição DELETE com a URL: localhost:8081/api/materias/1

**Resposta de Sucesso**:
#### Status `204 No Content`

#Notas
Para criar, visualizar ou deletar informações de Notas, siga os exemplos abaixo:

## Criar Notas

1. Crie uma requisição POST com a URL: localhost:8081/api/notas

**Resposta de Sucesso**:
#### Status: `201 Created`

`OBS` crie pelo menos 4 notas para fazer o calculo total no final do programa.

```json
{
    "valorNota": 10,
    "alunoId": 1,
    "docenteId": 1,
    "materiaId": 1
}
```
```json
{
    "valorNota": 7,
    "alunoId": 1,
    "docenteId": 1,
    "materiaId": 1
}
```
```json
{
    "valorNota": 6,
    "alunoId": 1,
    "docenteId": 1,
    "materiaId": 1
}
```
```json
{
    "valorNota": 2,
    "alunoId": 1,
    "docenteId": 1,
    "materiaId": 1
}
```
Respostas:
```json
{
    "notaId": 1,
    "valorNota": 10.0,
    "dataNota": "20/04/2024",
    "alunoNome": "Leonardo Madeira B Silva",
    "docenteNome": "Joao Nascimento",
    "materiaNome": "Spring Boot"
}
```
```json
{
    "notaId": 2,
    "valorNota": 7.0,
    "dataNota": "20/04/2024",
    "alunoNome": "Leonardo Madeira B Silva",
    "docenteNome": "Joao Nascimento",
    "materiaNome": "Spring Boot"
}
```
```json
{
    "notaId": 3,
    "valorNota": 6.0,
    "dataNota": "20/04/2024",
    "alunoNome": "Leonardo Madeira B Silva",
    "docenteNome": "Joao Nascimento",
    "materiaNome": "Spring Boot"
}
```
```json
{
    "notaId": 4,
    "valorNota": 2.0,
    "dataNota": "20/04/2024",
    "alunoNome": "Leonardo Madeira B Silva",
    "docenteNome": "Joao Nascimento",
    "materiaNome": "Spring Boot"
}
```
## Listar Notas

1. Crie uma requisição GET com a URL: localhost:8081/api/notas/aluno/1

**Resposta de Sucesso**:
#### Status: `200 OK`

Resposta:
```json
[
    {
        "valorNota": 10.0,
        "alunoNome": "Leonardo Madeira B Silva",
        "docenteNome": "Joao Nascimento",
        "materiaNome": "Spring Boot"
    },
    {
        "valorNota": 7.0,
        "alunoNome": "Leonardo Madeira B Silva",
        "docenteNome": "Joao Nascimento",
        "materiaNome": "Spring Boot"
    },
    {
        "valorNota": 6.0,
        "alunoNome": "Leonardo Madeira B Silva",
        "docenteNome": "Joao Nascimento",
        "materiaNome": "Spring Boot"
    },
    {
        "valorNota": 2.0,
        "alunoNome": "Leonardo Madeira B Silva",
        "docenteNome": "Joao Nascimento",
        "materiaNome": "Spring Boot"
    }
]
```
## Listar Notas por ID

1. Crie uma requisição GET com a URL: localhost:8081/api/notas/4

**Resposta de Sucesso**:
#### Status: `200 OK`

Resposta:
```json
{
    "notaId": 4,
    "valorNota": 2.0,
    "dataNota": "20/04/2024",
    "alunoNome": "Leonardo Madeira B Silva",
    "docenteNome": "Joao Nascimento",
    "materiaNome": "Spring Boot"
}
```
## Atualizar Notas

1. Crie uma requisição PUT com a URL: localhost:8081/api/notas/4
   
**Resposta de Sucesso**:
#### Status: `200 OK`

`OBS`: A nota do id 4, ela tinha valor de 2.0, com essa atualização passou a valer 10.0 pontos.

```json
{
    "valorNota": 10,
    "alunoId": 1,
    "docenteId": 1,
    "materiaId": 1
}
```
Resposta:
```json
{
    "notaId": 4,
    "valorNota": 10.0,
    "dataNota": "20/04/2024",
    "alunoNome": "Leonardo Madeira B Silva",
    "docenteNome": "Joao Nascimento",
    "materiaNome": "Spring Boot"
}
```
## Deletar Notas

1. Crie uma requisição DELETE com a URL: localhost:8081/api/notas/5

**Resposta de Sucesso**:
#### Status `204 No Content`

## Pontuação total do aluno por ID

1. Crie uma requisição GET com a URL: localhost:8081/api/notas/aluno/1/pontuacao

**Resposta de Sucesso**:
#### Status: `200 OK`

`OBS`: o ALUNO não tem acesso a esse metodo, apenas ADMIN e PROFESSOR
O calculo é feito com a seguinte formula:
`NOTA + NOTA + NOTA + NOTA / QUANTIDADE DE MATERIAS * 10`
`10 + 7 + 6 + 10 / 1 * 10` = `330`

Resposta:
```json
{
    "idAluno": 1,
    "nomeAluno": "Leonardo Madeira B Silva",
    "notaTotal": 330.0
}
```

Agora para você ter acesso exclusivo a esses dois proximos metodos, voce precisa fazer login na conta do aluno, por exemplo:

1. Volte na requisição POST no inicio do guia na URL: localhost:8081/login

```json
{
    "login": "leonardo",
    "senha": "root"
}
```
Resposta:
```json
{
    "name": "leonardo",
    "papel": "ALUNO",
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJsb2dpbi1hdXRoLWFwaSIsInN1YiI6Imxlb25hcmRvIiwiZXhwIjoxNzEzOTMwMDk0fQ.SfX9PBvaWN4RDlPb6u8lXvRp9LJ7nshybWMXq0HCvUU"
}
```

`OBS`: Utilize o token do aluno e acesse a esses dois metodos GET:

## Aluno nota total

1. Crie uma requisição GET com a URL: localhost:8081/api/notas/aluno/pontuacao/total

**Resposta de Sucesso**:
#### Status: `200 OK`

Resposta:
```json
{
    "idAluno": 1,
    "nomeAluno": "Leonardo Madeira B Silva",
    "notaTotal": 330.0
}
```

## Aluno lista de notas

1. Crie uma requisição GET com a URL: localhost:8081/api/notas/aluno/lista

**Resposta de Sucesso**:
#### Status: `200 OK`

Resposta:
```json
[
    {
        "valorNota": 10.0,
        "alunoNome": "Leonardo Madeira B Silva",
        "docenteNome": "Joao Nascimento",
        "materiaNome": "Spring Boot"
    },
    {
        "valorNota": 7.0,
        "alunoNome": "Leonardo Madeira B Silva",
        "docenteNome": "Joao Nascimento",
        "materiaNome": "Spring Boot"
    },
    {
        "valorNota": 6.0,
        "alunoNome": "Leonardo Madeira B Silva",
        "docenteNome": "Joao Nascimento",
        "materiaNome": "Spring Boot"
    },
    {
        "valorNota": 10.0,
        "alunoNome": "Leonardo Madeira B Silva",
        "docenteNome": "Joao Nascimento",
        "materiaNome": "Spring Boot"
    }
]
```
   
## Melhorias Futuras
- Implementação de testes automatizados.
- Integração com sistemas de front-end.
