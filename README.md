# PéFrioShop - implementação de referência

Sistema de informação para loja de calçados, com ficha clínica do pé do cliente.
Projeto de referência da disciplina **Programação Web 2026.2** - UFERSA Angicos.

Repositório do projeto: https://github.com/pwebufersa/pefrioshop_referencia

## Como executar

```
./mvnw spring-boot:run
```

A aplicação sobe em http://localhost:8080. O console do H2 fica em
`/h2-console`, com usuário `admin` e senha `admin`.

No Windows, `mvnw.cmd` pode ser usado no lugar de `./mvnw`.

## Pilha

- Spring Boot 4.1.1 e Java 25.
- Thymeleaf como motor de template.
- Spring Data JPA, com consultas derivadas e uma `Specification`.
- H2 em memória, no modo MySQL.
- Maven, com o wrapper incluído.

## User stories

**Milestone 1**

1. Manter e exibir informações de um cliente.
2. Manter e exibir informações de produtos.
3. Manter e exibir a ficha clínica do pé, com exclusão em cascata.
4. Manter e exibir pedidos, com itens, pagamento, imposto e total.

**Milestone 2**

5. Faturamento dos últimos doze meses.
6. Busca de produto pelo nome.
7. Busca de produto por qualquer campo.
8. Contato pelo WhatsApp.
9. E-mail promocional, com 5% no tênis mais caro do último pedido.
10. Preenchimento do endereço pelo CEP, na BrasilAPI.
11. Aniversariantes do mês.
12. Foto do produto guardada no banco.
13. Frete estimado para Marte.
14. Desconto de 7,3% nos quatro produtos mais novos.
15. Contagem de produtos por tipo.
16. Novatos do mês e do ano.

## Organização

- `domain`: entidades e enums.
- `repository`: acesso a dados.
- `service`: regras de negócio e cálculos.
- `web`: controladores e formulários.
- `config`: parâmetros da loja, cliente HTTP e carga inicial.

Os valores de negócio ficam em `application.properties`, sob o prefixo
`pefrihop`, e são lidos por `ParametrosLoja`. Nenhum cálculo é feito no
template.

A carga inicial fica em `import.sql`: três clientes, uma ficha, cinco produtos,
dois pagamentos, dois pedidos e três itens.

## Design system

O sistema visual está em [DESIGN.md](DESIGN.md), e o contexto do produto em
[PRODUCT.md](PRODUCT.md). A paleta usa cal, azulejo, couro e areia; os títulos
usam Archivo, e o corpo usa Source Sans 3.

## Publicação

O PéFrioShop é uma aplicação Java, e não roda no GitHub Pages. O que é
publicado é a documentação estática da pasta `docs/`, em
https://pwebufersa.github.io/pefrioshop_referencia/, a partir da pasta `/docs`
da branch `main`.

Para executar de fato, a aplicação precisa de um serviço que rode Java, como
Railway, Render ou um servidor próprio.
