# AppointmentScheduling-Backend
![Java](https://img.shields.io/badge/Java-21-blue) 
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-green) 
![JWT](https://img.shields.io/badge/JWT-auth-orange) 
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14-blue)
![Docker](https://img.shields.io/badge/Docker-container-blue)

Visão Geral

O AppointmentScheduling-Backend é um serviço HTTP de agendamento de consultas, desenvolvido em Java 21 com Spring Boot, que expõe uma API para operações CRUD em agendamentos, usuários e disponibilidade. Adota autenticação estateless via JWT, persistência de dados com PostgreSQL e é containerizado com Docker para facilitar build e deploy



## Exemplo de `docker-compose.yml` 

```yaml
version: '3.8'

services:
  db:
    image: postgres:13
    container_name: appointmentSchedulingDB
    environment:
      POSTGRES_DB: appointmentScheduling
      POSTGRES_USER: example_user       # usuário de exemplo
      POSTGRES_PASSWORD: example_password # senha de exemplo
    volumes:
      - postgres-data:/var/lib/postgresql/data
    ports:
      - "5432:5432"

  app:
    build:
      context: .
      dockerfile: Dockerfile
    image: appointment-backend
    container_name: appointmentSchedulingApp
    depends_on:
      - db
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/appointmentScheduling
      SPRING_DATASOURCE_USERNAME: example_user
      SPRING_DATASOURCE_PASSWORD: example_password
      JWT_SECRET: example_jwt_secret

volumes:
  postgres-data:
```


---

## Passo a passo para build e deploy com Docker Compose

1. **Crie um arquivo `.env` na raiz do projeto** 

   ```dotenv
   POSTGRES_PASSWORD=example_password
   SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/appointmentScheduling
   SPRING_DATASOURCE_USERNAME=example_user
   SPRING_DATASOURCE_PASSWORD=example_password
   JWT_SECRET=example_jwt_secret
   ```

   Adicione `.env` ao seu `.gitignore`:

   ```gitignore
   .env
   ```

2. **Construa a imagem da aplicação**

   * Usando Docker diretamente:

     ```bash
     docker build -t appointment-backend .
     ```
   * Ou via Docker Compose:

     ```bash
     docker-compose build app
     ```

3. **Suba os containers em background**

   ```bash
   docker-compose up -d
   ```

   * `-d` inicia em background, mantendo o terminal livre.

4. **Verifique se está tudo rodando**

   ```bash
   docker ps
   ```

   Você deve ver dois containers ativos: `appointmentSchedulingDB` e `appointmentSchedulingApp`.

5. **Para parar e remover containers, redes e volumes**

   ```bash
   docker-compose down
   ```

---

