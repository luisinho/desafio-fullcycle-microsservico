# desafio-fullcycle-microsservico
Projeto sobre microsserviços e da arquitetura baseada em eventos
# Sobre o projeto
  Desenvolver um microsserviço para receber via Kafka os eventos gerados pelo microsserviço "Wallet Core" e persistir no banco de dados os balances atualizados para cada conta.<br>
  Criar um endpoint: "/balances/{account_id}" que exibe o balance atualizado.<br>
  Criar migrations para popular dados fictícios em ambos bancos de dados dos microserviços.<br>
  Gerar um "event-balance.http" para realizar a chamada do novo microsserviço "event-balance".

# Tecnologias utilizadas
  Linguagem de programação java versão 21 e golang:1.21

# Como executar o projeto

```bash
  # Clonar o repositório
  git clone ](https://github.com/luisinho/desafio-fullcycle-microsservico.git

  # Entrar na pasta do projeto
  cd desafio-fullcycle-microsservico

  # Executar os comandos docker abaixo
    docker compose build --no-cache
    docker compose up -d

  # Entrar no banco de dados do microserviço "Wallet Core"
    docker compose exec mysql-wallet mysql -uroot -proot
    USE wallet;

  # Entrar no banco de dados do microserviço "event-balance"
    docker compose exec mysql-balance mysql -uroot -proot
    USE balances;

  # Entrar no log do microserviço "Wallet Core"
    docker compose logs -f walletcore    

  # Entrar no log microserviço "event-balance"
    docker compose logs -f event-balance

  # Para para realizar a chamada do novo microsserviço "event-balance"
    O arquivo "event-balance.http" está dentro da pasta "api" do projeto.

# Autor

Luis Antonio Batista dos Santos

https://www.linkedin.com/in/luis-antonio-batista-dos-santos-5a37b781
