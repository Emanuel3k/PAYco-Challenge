# Sistema de Envios e Processamento de E-mails

Este é um sistema desenvolvido em Kotlin e Quarkus para envio e processamento de e-mails, utilizando RabbitMQ para a comunicação entre os serviços. O sistema é empacotado em containers Docker para facilitar a implantação e execução.

## Tecnologias Utilizadas

- Kotlin
- Quarkus
- RabbitMQ
- Docker

## Requisitos

- Docker instalado no seu PC.
- Conta no Brevo para utilizar o sistema de envios SMTP.

## Configuração

Após o download do código-fonte, siga as etapas abaixo para configurar a aplicação:

1. **Brevo SMTP Keys:**
   - Acesse [https://app.brevo.com/settings/keys/smtp](https://app.brevo.com/settings/keys/smtp) e obtenha as informações necessárias.
   - Insira as informações obtidas no arquivo `api/email-producer/src/main/resources/application.properties`.

2. **Compilação e Construção do Container:**
   - Abra o terminal na pasta raiz de cada projeto e execute o seguinte comando:
     ```bash
     ./mvnw package
     ```
   - Volte para a pasta raiz do projeto e execute:
     ```bash
     docker-compose up -d --build
     ```

3. **Testes**

Para testar a aplicação, você pode utilizar o Insomnia, Postman ou outros clientes HTTP.

Aqui estão alguns exemplos de testes que você pode realizar:

    Teste se o endpoint GET /api/emails/ retorna uma lista vazia quando a aplicação é inicializada.

GET 127.0.0.1:8080/api/emails/

GET 127.0.0.1:8080/api/emails/{id}

POST 127.0.0.1:8080/api/emails/
Content-Type: application/json

{
  "recipient": "email@email.com",
  "subject": "Assunto do email",
  "body": "corpo do email"
}