# 🤖 Budgeting AI - Spring Boot + Spring AI

> Projeto final desenvolvido durante o curso de **Spring AI da DIO**, com o objetivo de aplicar conceitos de Inteligência Artificial em uma aplicação backend utilizando Java e Spring Boot.

A aplicação consiste em uma API para **gerenciamento de transações financeiras**, permitindo registrar e consultar gastos através de endpoints REST.

Além da interação tradicional via API, o projeto utiliza **Spring AI e OpenAI** para interpretar comandos enviados através de áudio. A aplicação realiza a transcrição da voz, utiliza um modelo de linguagem para interpretar a solicitação e pode executar operações da aplicação através de **Tool Calling**, retornando posteriormente uma resposta em áudio.

---

## 🚀 Funcionalidades

* Cadastro de transações financeiras
* Consulta de transações por categoria
* Integração com OpenAI através do Spring AI
* Transcrição de áudio para texto
* Interpretação de comandos utilizando modelo de linguagem
* Execução de operações através de Tool Calling
* Conversão da resposta da IA para áudio
* Persistência de transações em MySQL
* Execução do banco de dados utilizando Docker Compose
* Testes de integração com recursos da OpenAI

---

## 🛠️ Tecnologias

| Tecnologia          | Finalidade                              |
| ------------------- | --------------------------------------- |
| **Java 25**         | Linguagem principal                     |
| **Spring Boot 4**   | Framework backend                       |
| **Spring AI**       | Integração com Inteligência Artificial  |
| **OpenAI**          | Modelos de IA utilizados pela aplicação |
| **GPT-4o-mini**     | Processamento de linguagem              |
| **Whisper**         | Transcrição de áudio                    |
| **GPT-4o-mini-TTS** | Conversão de texto para áudio           |
| **Spring Web**      | Construção da API REST                  |
| **Spring Data JPA** | Persistência de dados                   |
| **Hibernate**       | ORM                                     |
| **MySQL**           | Banco de dados relacional               |
| **Docker Compose**  | Execução do banco de dados              |
| **Gradle**          | Gerenciamento de dependências e build   |
| **JUnit 5**         | Testes                                  |
| **AssertJ**         | Assertions nos testes                   |
| **Lombok**          | Redução de código boilerplate           |

---

## 📋 Pré-requisitos

Antes de executar o projeto, certifique-se de ter instalado:

* Java 25
* Docker
* Docker Compose
* Uma chave de API da OpenAI

---

## 🚀 Como executar

### 🔑 Variável de ambiente

A aplicação utiliza a variável de ambiente `OPENAI_API_KEY` para realizar as requisições à OpenAI.

Linux/macOS:

```bash
export OPENAI_API_KEY="your_api_key"
```

Windows PowerShell:

```powershell
$env:OPENAI_API_KEY="your_api_key"
```

> ⚠️ A chave da OpenAI é uma informação sensível e não deve ser enviada para o repositório.

---

### 🐳 Executando o banco de dados

O projeto utiliza Docker Compose para executar o MySQL.

Para iniciar o banco:

```bash
docker compose up -d
```

Para verificar os containers:

```bash
docker compose ps
```

Para parar o banco:

```bash
docker compose down
```

O banco utiliza as seguintes configurações:

```text
Database: transaction
Username: app
Password: app
Port: 3307
```

---

### ☕ Executando a aplicação

Utilizando o Gradle Wrapper:

Linux/macOS:

```bash
./gradlew bootRun
```

Windows:

```powershell
gradlew.bat bootRun
```

A aplicação estará disponível em:

```text
http://localhost:8080
```

---

## 💰 Transações

A API permite registrar e consultar transações financeiras.

Uma transação possui:

```text
Transaction
├── id
├── description
├── amount
└── category
```

As categorias disponíveis são:

```text
GROCERIES
PHARMA
AUTO
```

O valor da transação é armazenado em centavos.

Por exemplo:

```json
{
    "description": "Compras no supermercado",
    "category": "GROCERIES",
    "amount": 8000
}
```

Representa uma transação de **R$ 80,00**.

---

## 🌐 Endpoints

### Criar uma transação

```http
POST /transactions
```

**Exemplo de requisição:**

```json
{
    "description": "Compras no supermercado",
    "category": "GROCERIES",
    "amount": 8000
}
```

---

### Consultar transações por categoria

```http
GET /transactions/{category}
```

**Exemplo:**

```http
GET /transactions/GROCERIES
```

O endpoint retorna as transações cadastradas para a categoria informada.

---

## 🤖 Inteligência Artificial

O projeto utiliza o **Spring AI** para integrar a aplicação com os modelos da OpenAI.

A aplicação utiliza diferentes recursos de IA:

### Chat

```text
gpt-4o-mini
```

Utilizado para interpretar as mensagens recebidas e determinar qual operação deve ser realizada.

### Transcrição de áudio

```text
whisper-1
```

Utilizado para transformar o áudio enviado pelo usuário em texto.

### Text-to-Speech

```text
gpt-4o-mini-tts
```

Utilizado para transformar a resposta gerada pela IA novamente em áudio.

---

## 🎙️ Transações por áudio

A aplicação possui um endpoint específico para interação através de áudio:

```http
POST /transactions/ai
```

O endpoint recebe um arquivo utilizando `multipart/form-data`.

```text
file
```

O fluxo da aplicação funciona da seguinte maneira:

```text
Áudio
   ↓
Whisper
   ↓
Texto
   ↓
GPT-4o-mini
   ↓
Tool Calling
   ↓
Operação da aplicação
   ↓
Resposta
   ↓
Text-to-Speech
   ↓
Áudio MP3
```

Por exemplo, o usuário pode enviar um áudio informando um gasto.

A aplicação primeiro realiza a transcrição do áudio. O texto obtido é então enviado ao modelo de linguagem, que interpreta a solicitação e pode utilizar as ferramentas disponibilizadas pela aplicação.

A resposta final é convertida para `.mp3` e retornada pelo endpoint.

---

## 🔧 Tool Calling

O projeto também explora o recurso de **Tool Calling** do Spring AI.

Os casos de uso responsáveis pelas operações de transação são disponibilizados como ferramentas para o modelo de linguagem.

### Persistência de transação

```java
@Tool(
    name = "persist-transaction",
    description = "Persiste uma nova transação financeira"
)
```

### Consulta por categoria

```java
@Tool(
    name = "list-transactions-by-category",
    description = "Lista transações financeiras por categoria"
)
```

Dessa maneira, o modelo pode identificar a intenção do usuário e solicitar a execução da operação apropriada.

---

## 🧪 Testes

O projeto possui testes de integração para os principais recursos utilizados com a OpenAI.

### ChatClient

Testa a utilização do `ChatClient` com um modelo da OpenAI.

### ChatModel

Testa a utilização do `OpenAiChatModel`.

### Speech Model

Testa a geração de áudio utilizando o modelo de Text-to-Speech.

### Transcription Model

Testa a transcrição dos arquivos de áudio presentes no projeto.

Os testes utilizam arquivos localizados em:

```text
src/test/resources/audio/
```

### Tool Calling

Testa a utilização de ferramentas pelo modelo de linguagem através do Spring AI.

Os testes que dependem da OpenAI são executados somente quando a variável `OPENAI_API_KEY` está configurada.

---

## 📁 Estrutura do projeto

```text
SpringIA_Project/
│
├── src/
│   ├── main/
│   │   ├── java/dio/budgeting/
│   │   │   ├── application/
│   │   │   ├── domain/
│   │   │   └── infrastructure/
│   │   │
│   │   └── resources/
│   │       ├── application.properties
│   │       └── prompts/
│   │           └── system-message.st
│   │
│   └── test/
│       ├── java/dio/budgeting/
│       └── resources/
│           └── audio/
│
├── compose.yml
├── build.gradle
├── gradlew
├── gradlew.bat
└── settings.gradle
```

---

## 🎯 Objetivo do projeto

Este projeto foi desenvolvido como **projeto final do curso de Spring AI da DIO**, colocando em prática os conceitos apresentados durante a formação.

O principal objetivo foi explorar a integração entre uma aplicação **Java/Spring Boot** e serviços de **Inteligência Artificial**, especialmente:

* Spring AI
* OpenAI
* LLMs
* ChatClient
* Tool Calling
* Speech-to-Text
* Text-to-Speech
* APIs REST
* Persistência com JPA
* MySQL
* Docker Compose

---

## 👨‍💻 Autor

### Vitor Otavio dos Reis

[GitHub](https://github.com/vitorreis-dev)

Projeto desenvolvido como parte da formação em **Spring AI pela Digital Innovation One (DIO)**.

