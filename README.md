# Projeto de Transações Financeiras - Microserviços com Spring Boot

## Propósito
Este projeto consiste em uma aplicação de microserviços desenvolvida com Spring Boot. A arquitetura é baseada em quatro serviços principais:

- Auth Service - Gerencia autenticação e segurança.
- Transactions Service - Processa transações financeiras.
- Users Service - Gerencia os dados dos usuários e suas transações.
- MySQL - Banco de dados para armazenar dados das transações e dos usuários.
- RabbitMQ - Sistema de mensagens para comunicação entre os serviços.

Os serviços se comunicam entre si através de RabbitMQ, e a aplicação utiliza Docker Compose para orquestrar os contêineres.

## Como Rodar a Aplicação com Docker Compose

Para rodar a aplicação, você precisa ter o Docker e o Docker Compose instalados na sua máquina. Siga os passos abaixo para iniciar os serviços:

### 1 - Clone o Repositório

```sh
git clone <URL_DO_REPOSITORIO>
cd <NOME_DO_REPOSITORIO>
```

### 2 - Crie um Arquivo .env

Crie um arquivo .env na raiz do projeto e adicione as seguintes variáveis:

```env
Copiar código
AUTH_SERVICE_PORT=8082
TRANSACTIONS_SERVICE_PORT=8081
USERS_SERVICE_PORT=8080

USERS_SERVICE_URL=http://users:8080

SERVER_HOST=localhost
SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/user-transactions
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=root
SPRING_DATASOURCE_DATABASE=user-transactions
SPRING_JPA_HIBERNATE_DDL_AUTO=update

RABBITMQ_HOST=amqp://guest:guest@rabbitmq:5672/
RABBITMQ_PORT=5672
RABBITMQ_USERNAME=guest
RABBITMQ_PASSWORD=guest
RABBITMQ_TRANSACTION_TO_USER_QUEUE_NAME=default.transaction-queue
RABBITMQ_USER_TO_TRANSACTION_QUEUE_NAME=default.user-queue

SECURITY_JWT_SECRET_KEY_FILE=classpath:security.key
SECURITY_JWT_PUBLIC_KEY_FILE=classpath:security.pub

SECURITY_API_KEY=daf75259-2374-417b-b86b-27556f0ab933
```
### 3 - Gere as Chaves de Segurança
Para isso, é necesário ter o OpenSSL instalado na sua máquina. Execute os seguintes comandos:

```sh
openssl genrsa -out security.key 2048
openssl rsa -in security.key -pubout -out security.pub
```
Após geradas as chaves, copie as mesmas para o arquivo de configurações ```application.properties``` de
todos os serviços.

### 4 - Build e Suba os Contêineres

Na raiz do projeto, execute o comando:

```sh
docker-compose up --build # ou
docker-compose up -d # para rodar em background
```

Isso criará e iniciará os contêineres para todos os serviços definidos no arquivo docker-compose.yml.
Cada serviço será construído com base no Dockerfile correspondente e terá seu próprio contêiner.

### 5 - Acesse os Serviços

- Auth Service: http://localhost:8082
- Transactions Service: http://localhost:8081
- Users Service: http://localhost:8080
- RabbitMQ Management Console: http://localhost:15672 (usuário: guest, senha: guest)


## Estrutura Básica da Arquitetura
Estrutura de Pastas

```css
.
├── auth-service
│   ├── src 
│   └── Dockerfile-auth
├── transactions-service
│   ├── src
│       └── main
│           ├── java
│               └── com
│                   └── transactions
│                       └── application # Camada de configuração da aplicação
│                       └── controllers # Camada de endpoints REST
│                       └── domain # Camada de domínio, responsável por armazenar toda a regra e contexto de negócio
│                       └── repositories # Camada de acesso ao banco de dados
│                       └── services # Camada de serviços, responsável por orquestrar as regras de negócio e fazer uso dos repositórios
│   └── Dockerfile-transactions
├── users-service
│   ├── src
│   └── Dockerfile-users
├── docker-compose.yml
└── .env
```

## Arquitetura em Microserviços

- Auth Service: Responsável pela autenticação e autorização. Comunica-se com os outros serviços usando RabbitMQ para garantir a segurança das transações.

- Transactions Service: Gerencia e processa as transações financeiras. Envia e recebe mensagens de transação através do RabbitMQ.

- Users Service: Gerencia as informações dos usuários e atualiza suas carteiras com base nas transações. Também utiliza RabbitMQ para comunicação com o Transactions Service.

- MySQL: Banco de dados utilizado por todos os serviços para armazenar dados.

- RabbitMQ: Sistema de mensageria que facilita a comunicação assíncrona entre os serviços.

Abaixo temos um modelo gráfico de como funciona a arquitetura da aplicação:

![img.png](docs/img.png)

Observações:
Certifique-se de que todos os arquivos de configuração e dependências estão corretamente configurados antes de iniciar os contêineres. Verifique os logs dos serviços para identificar qualquer problema durante a inicialização.