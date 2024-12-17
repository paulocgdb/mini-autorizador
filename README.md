# mini-autorizador
Projeto prático


Este projeto tem como foco ser um mini-autorizador, onde precisa
respeitar certas condições de sucesso e falhas.

O projeto utiliza o JAVA 21

Tentei seguir as orientações à risca, evitando o uso de if nos fluxos
de código e aplicando conceitos de programação funcional com Optional
para tornar o código mais expressivo e conciso. Além disso, tratei a 
condição de corrida em múltiplas requisições conflitantes utilizando 
lock a nível de banco de dados, delegando a responsabilidade de 
gerenciamento de concorrência ao banco. Isso garante a consistência 
dos dados, independentemente da quantidade de instâncias da aplicação 
em execução.

Deixei a cobertura de testes em 100%

Utilizei o Strategy Pattern visando também a eliminação dos ifs
e tornar a solução mais elegante também.


# Rodar a aplicação

### 1 - Inicie o Docker localmente
### 2 - Execute o Docker Compose na pasta raiz do projeto
#### docker-compose up --build
### 3 - Compile e inicie a aplicação
### 4 - Teste as requisições