# Spotippos Code Challenge

Implementação do desafio [VivaReal Code Challenge](https://github.com/VivaReal/code-challenge)

## Deploy

[![Deploy](https://www.herokucdn.com/deploy/button.svg)](https://heroku.com/deploy)

## API

A API foi implementada de acordo com a especificação do [desafio de back-end](https://github.com/VivaReal/code-challenge/blob/master/backend.md). Como solução simples para uma futura necessidade de versionamento, decidi, contudo, adicionar o prefixo `/v1` em todos os end-points. Desta forma, as operações suportadas respondem nos seguintes end-points:

* Criar imóvel em Spotippos: `POST /v1/properties`
* Mostrar imóvel específico: `GET /v1/properties/{id}`
* Buscar imóveis por área: `GET /v1/properties?ax={integer}&ay={integer}&bx={integer}&by={integer}`

## Detalhes de implementação

### Validação

Algumas regras foram inferidas da especificação, enquanto outras foram introduzidas, principalmente pela ausência de maiores detalhes na especificação do desafio.

* Na busca por área, foi aplicada a restrição: `ax <= bx && ay <= by`
* Apesar da API não prever end-points para a criação de _Provinces_, foi aplicada a seguinte restrição sobre o valor dos seus atributos: `x0 <= x1 && y0 <= y1`

## Tecnologias

* Spring Boot
* Spring MVC
* Spring Data + JPA
* Spring Test
* Cucumber
* Bean Validation (JSR 303) / Hibernate Validation
* Jackson
