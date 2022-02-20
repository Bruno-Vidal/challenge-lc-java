# challenge-lc-java

Projeto para avaliação das minhas competências com o desenvolvimento de aplicações Java 

Para execultar o projeto, verifique se a maquina possui as dependencias :
JAVA 11
MAVEN

Execute o vuild do JAR e inicio o projeto 

acesse: http://localhost:8080

Será redirecionado para a documentação do projeto 

Existem 3 Controladores de fluxo;

Neles é possivel realizar 
  - Criação de um Jogador novo 
  - Login para acesso ao token
  - Fluxo para realizar as partidas dos jogos

O sistema utiliza JWT para realizar sua autenticação o que significa que precisa adicionar nos Headers os parametros
key="Authorization" value="Bearer " + token

Para obter o token realize a requisição no controlador de login

Exsite por padrão dois jogadores criados: 
  Jogador 1 :
    email: player1@test.com
    senha: 12345678
  Jogador 2 :
    email: player2@test.com
    senha: 12345678
    
O serviço de Ranking é publico

