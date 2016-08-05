# Spotippos Code Challenge

Implementação do desafio [VivaReal Code Challenge](https://github.com/VivaReal/code-challenge)

## Deploy

[![Deploy](https://www.herokucdn.com/deploy/button.svg)](https://heroku.com/deploy)

## Execução local

Para executar localmente a aplicação, basta clonar o repositório e executar, na raíz do projeto: `mvn spring-boot:run`

## API

A API foi implementada de acordo com a especificação do [desafio de back-end](https://github.com/VivaReal/code-challenge/blob/master/backend.md). Como solução simples para uma futura necessidade de versionamento foi decidido adicionar o prefixo `/v1` em todos os end-points. Desta forma, as operações suportadas respondem nos seguintes end-points:

* Criar imóvel em Spotippos: `POST /v1/properties`
* Mostrar imóvel específico: `GET /v1/properties/{id}`
* Buscar imóveis por área: `GET /v1/properties?ax={integer}&ay={integer}&bx={integer}&by={integer}`

## Detalhes de implementação

### Criação de imóvel (_response_)

Retornar apenas o _status_ de sucesso (`200`) ao criar elementos utilizando APIs REST não é uma boa prática pois dificulta a integração com outros serviços/operações. Poderia ter sido feita a opção por retornar, além do _status_, o `id` do novo elemento mas, por uma questão de completude, este end-point retorna o objeto completo, incluindo o `id` gerado pelo banco e suas respectivas `provinces`.

### Respostas de erro

O tipo de erro, nesta implementação, é representado principalmente pelo _HTTP status_ das respostas. Não houve uma preocupação maior em detalhar o conteúdo das mensagens.

* `404 NOT FOUND`: Retornado quando não é encontrado um imóvel com o `id` solicitado
* `400 BAD REQUEST`: Retornado quando os parâmetros da requisição são inválidos, segundo qualquer uma das regras de validação.

### Validações adicionais

Algumas regras foram inferidas da especificação, enquanto outras foram introduzidas, principalmente pela ausência de maiores detalhes na especificação do desafio.

* Na busca por imóvel específico, `:id > 0`
* Na busca por área, foi aplicada a restrição: `ax <= bx && ay <= by`
* Apesar da API não prever end-points para a criação de _Provinces_, foi aplicada a seguinte restrição sobre o valor dos seus atributos: `x0 <= x1 && y0 <= y1`

### Testes

Todos os testes foram implementados como caixa-preta, tendo como entrada as requisições aos end-points dos controllers e validando as respostas. Por simplicidade, não foi feita a validação explícita dos dados salvos no banco de dados (no caso da operação de criação de imóveis), verificada, indiretamente, através do `id` atribuído ao novo imóvel.

Para validar também as _queries_ executadas no banco, todos os testes utilizam um banco H2 em memória para as operações de leitura/escrita. O lado negativo desta abordagem é a diminição na velocidade de execução dos testes.

Os testes podem (e devem) ser separados de forma que apenas alguns cenários específicos utilizem a infra-estrutura completa de _servlets/controllers/database_, de baixa performance, enquanto que os demais utilizem apenas _mocks_ destas entidades. Estas otimizações, contudo, não foram implementadas nesta versão.

## Tecnologias

* Java 8
* Maven
* Spring Boot
* Spring MVC
* Spring Data + JPA
* Spring Test
* Cucumber
* Bean Validation (JSR 303) / Hibernate Validation
* Jackson
* H2
* PostgreSQL
