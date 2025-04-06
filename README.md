 

# Java8SpringJWT

Um CRUD de Usuário com autenticação via Token JWT e endpoints RESTful em Spring Boot.

## Tecnologias Usadas

- **Java 8**  
- **Spring Boot** (Framework para construção de aplicações Java baseadas em microserviços)
- **JWT (JSON Web Token)** (Autenticação e autorização de usuários)
- **Mockito e JUnit** (Testes unitários com mocks para Service e Controller)
- **Swagger** (Documentação interativa da API)

## Funcionalidades

- Implementação de autenticação via JWT, permitindo autenticar usuários de forma segura.
- Endpoints RESTful que permitem o acesso e manipulação de dados.
- Testes automatizados utilizando Mockito e JUnit para garantir que os serviços e controladores funcionem corretamente.

## Como Rodar o Projeto

### Pré-requisitos

- **Java 8 ou superior**
- **Maven** (para gerenciamento de dependências e build do projeto)

### Passos

1. Clone o repositório:

   ```bash
   git clone https://github.com/facurymanoel/Java8SpringJWT
   ```

2. Acesse o diretório do projeto:

   ```bash
   cd Java8SpringJWT
   ```

3. Compile o projeto (Maven):

   ```bash
   mvn clean install
   ```

4. Execute a aplicação:

   ```bash
   mvn spring-boot:run
   ```

## Testes

O projeto utiliza **Mockito** para testes unitários de Service e Controller. Para rodar os testes, execute:

```bash
mvn test
```

## Como Contribuir

1. Fork o repositório.
2. Crie uma branch para sua feature:

   ```bash
   git checkout -b minha-feature
   ```

3. Faça suas alterações.
4. Comite suas mudanças:

   ```bash
   git commit -am 'Adicionando nova funcionalidade'
   ```

5. Faça o push para a branch:

   ```bash
   git push origin minha-feature
   ```

6. Abra um Pull Request.

---

 







