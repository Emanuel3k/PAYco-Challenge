# Sistema de Envios e Processamento de E-mails

Este é um sistema desenvolvido em Kotlin e Quarkus para envio e processamento de e-mails, utilizando RabbitMQ para a comunicação entre os serviços. O sistema é empacotado em containers Docker para facilitar a implantação e execução.

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

## Tecnologias Utilizadas

- Kotlin
- Quarkus
- RabbitMQ
- Docker