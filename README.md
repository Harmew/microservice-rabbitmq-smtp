# Projeto de Microserviços para Criação de Usuário e Envio de E-mail de Boas-vindas

Este projeto é composto por uma arquitetura de microserviços desenvolvida em Spring Boot, que realiza a criação de
usuários e o envio de e-mails de boas-vindas. A comunicação entre os microserviços é feita via RabbitMQ, e o envio de
e-mails utiliza o serviço de mensageria da Google.

### Visão Geral dos Microserviços

A aplicação possui dois principais microserviços:

1. **User Service**: Responsável pela criação do usuário. Este serviço recebe uma solicitação de criação de usuário e
   publica uma mensagem no RabbitMQ com os detalhes do usuário recém-criado.


2. **Email Service**: Responsável por consumir as mensagens enviadas pelo User Service e enviar um e-mail de boas-vindas ao
   usuário. Este serviço está integrado com o Google Messaging Service para o envio de e-mails.

### Estrutura dos Microserviços

1. **User Service**
   - **Propósito**: Gerenciar a criação de novos usuários e enviar uma mensagem para o RabbitMQ após a criação.
   - **Dependências**:
     - `spring-boot-starter-amqp`: Para comunicação com o RabbitMQ.
     - `spring-boot-starter-data-jpa`: Para interação com o banco de dados MySQL.
     - `spring-boot-starter-validation`: Para validação dos dados de entrada.
     - `spring-boot-starter-web`: Para expor a API REST de criação de usuários.
     - `mysql-connector-j`: Para conexão com o banco de dados MySQL.
   - **Endpoints**:
     - `POST /user`: Cria um novo usuário e envia os dados para o RabbitMQ.


2. **Email Service**
   - **Propósito**: Consumir mensagens do RabbitMQ e enviar e-mails de boas-vindas aos usuários.
   - **Dependências**:
     - `spring-boot-starter-amqp`: Para receber mensagens do RabbitMQ.
     - `spring-boot-starter-data-jpa`: Para interagir com o banco de dados MySQL (se necessário).
     - `spring-boot-starter-mail`: Para configuração e envio de e-mails.
     - `spring-boot-starter-validation`: Para validação dos dados recebidos.
     - `spring-boot-starter-web`: Para expor um endpoint de monitoramento (se necessário).
     - `mysql-connector-j: Para conexão com o banco de dados MySQL.

### Fluxo de Comunicação

1. O cliente faz uma requisição `POST /user` para o `User Service` para criar um novo usuário.
2. O `User Service` valida e salva o usuário no banco de dados MySQL e envia uma mensagem para o RabbitMQ contendo os dados do usuário.
3. O `Email Service` consome a mensagem do RabbitMQ, prepara o conteúdo do e-mail de boas-vindas e utiliza o Google Messaging Service para enviar o e-mail ao usuário.

### Tecnologias e Ferramentas Utilizadas

- **Spring Boot 3.3.5**: Framework principal para a construção de microserviços.
- **RabbitMQ**: Sistema de mensageria para comunicação assíncrona entre os microserviços.
- **MySQL**: Banco de dados relacional para persistência dos dados de usuários.
- **Google Messaging Service**: Serviço de envio de e-mails.
