# Product

<!-- impeccable:product-schema 1 -->

## Platform

web

## Users

Operador de balcão da loja PéFrioShop. Trabalha em pé, no balcão, durante o
expediente, com pressa e interrupções. A tarefa é cadastrar cliente, produto e
pedido, e consultar a ficha clínica do pé antes de vender. Em segundo plano, o
gestor consulta os relatórios (faturamento, aniversariantes, novatos, frete,
desconto, contagem por tipo). O sistema também é lido como referência didática
pelos alunos da disciplina Programação Web 2026.2, que o usam como modelo de
projeto Spring Boot.

## Product Purpose

O sistema mantém clientes, produtos e pedidos de uma loja de calçados, com a
ficha clínica do pé do cliente. Existe para demonstrar, de forma completa e
executável, um sistema de informação incremental em Spring Boot, Thymeleaf e
JPA. O êxito é o professor conseguir executar `./mvnw spring-boot:run` sem
ajuda e cada user story ser encontrada em segundos.

## Positioning

A ficha clínica do pé, com comprimento, pisada, temperatura e odor, transforma
um CRUD de loja em um domínio que nenhum cadastro genérico de pessoa copiaria.
O tom lúdico sustenta o conteúdo sem infantilizar.

## Operating Context

- Uso no balcão, em tela de desktop, com luz de ambiente.
- Vendas registradas durante o expediente; relatórios consultados ao fim do dia.
- A correção da disciplina é oral e presencial, no dia da entrega do milestone.
- Os dados de exemplo são carregados quando o banco está vazio.

## Capabilities and Constraints

- 16 user stories em dois milestones, mais os desafios opcionais.
- Spring Boot 4, Thymeleaf, Spring Data JPA, H2, Maven.
- `./mvnw spring-boot:run` deve funcionar sem intervenção.
- Lógica de negócio no serviço; nenhum cálculo no template.
- Valores de negócio (imposto, descontos, frete, prazo) em configuração, não no
  código.
- Texto em português, voz passiva, sem travessão.
- Um framework JavaScript de front é aceito, embora a referência permaneça em
  Thymeleaf com JavaScript tradicional.
- A foto do design system é de autoria do aluno; a referência traz apenas um
  substituto marcado.

## Brand Commitments

- Nome: PéFrioShop.
- Voz: português brasileiro, impessoal, voz passiva, sem anglicismos
  desnecessários.
- Tom: lúdico e visual, sem infantilizar.

## Evidence on Hand

- Carga inicial de clientes, produtos e pedidos em `DadosIniciais`.
- `src/main/resources/static/imagens/foto-referencia.svg`, substituto que deve
  ser trocado pela foto colorida de autoria do aluno.
- Ausência a não preencher: dados comerciais reais, preços de mercado e marcas
  verdadeiras.

## Product Principles

1. A tarefa do balcão tem prioridade sobre a expressão visual.
2. Cada user story é encontrável em segundos pela navegação.
3. O sistema é lido como material didático; o código e a tela contam a mesma
   história.
4. Nenhum valor de negócio fica solto no código nem no template.
5. O tom é leve, e o domínio permanece verificável.

## Accessibility & Inclusion

- Contraste mínimo de 4.5:1 no texto corrente.
- Navegação por teclado e foco visível.
- HTML semântico, com rótulos em todos os campos.
