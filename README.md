# PéFrioShop - implementação de referência

Sistema de informação para loja de calçados, com ficha clínica do pé. Projeto de
referência da disciplina **Programação Web 2026.2** - UFERSA Angicos.

Repositório: https://github.com/pwebufersa/pefrioshop_referencia

## Como executar

No Windows:

```
cd C:\repos_github_2026\peFrioShop_referencia
mvnw.cmd spring-boot:run
```

No bash do Windows, no Linux e no macOS, o comando é `./mvnw spring-boot:run`, a
partir da mesma pasta.

A aplicação sobe em http://localhost:8080, e o console do H2 fica em
http://localhost:8080/h2-console, com usuário `admin` e senha `admin`. O
encerramento é feito com Ctrl+C. Para gerar e executar o jar:

```
mvnw.cmd -DskipTests clean package
java -jar target\pefrioshop-0.0.1-SNAPSHOT.jar
```

## Tecnologias usadas

Spring Boot 4.1.1, Java 21, Thymeleaf, Spring Data JPA, validação com Bean
Validation, H2 em memória no modo MySQL e Maven com invólucro (wrapper).

## User stories

**Milestone 1**: manter cliente, produto, ficha clínica do pé e pedido, este com
itens, pagamento, imposto e total.

**Milestone 2**: faturamento dos últimos doze meses; busca de produto pelo nome
e por qualquer campo; contato pelo WhatsApp; e-mail promocional, com 5% no tênis
mais caro do último pedido; endereço pelo CEP, na BrasilAPI; aniversariantes do
mês; foto do produto no banco; frete para Marte, pela viagem e pelo peso;
desconto de 7,3% nos quatro produtos mais novos; contagem por tipo; e novatos do
mês e do ano.

## Organização do código

- `domain`: entidades e enums.
- `repository`: acesso a dados.
- `service`: regras de negócio e cálculos.
- `web`: controladores e formulários.
- `config`: parâmetros da loja e cliente HTTP.
- `ferramenta`: minificação do CSS e do JavaScript, executada no empacotamento.

Os valores de negócio ficam em `application.properties`, sob o prefixo
`pefrioshop`, e são lidos por `ParametrosLoja`. Nenhum cálculo é feito no
template.

## Frete para Marte

O frete soma a viagem, de `pefrioshop.frete-marte-baseline`, ao peso
transportado, pelo preço do quilo de `pefrioshop.frete-marte-por-quilo-dolar`,
convertido pelo `pefrioshop.cambio-dolar`. O preço do quilo é o publicado pela
SpaceX para carga ao solo marciano, a partir de 2028: US$ 100 milhões por
tonelada métrica.

O peso do par é guardado em gramas, e não em quilo. A conversão é feita em
`BigDecimal`, com duas casas e arredondamento, porque a divisão inteira de
gramas por mil daria zero em qualquer par com menos de um quilo, e o frete
sairia sem o peso. O caso está fixado em `ResumoFreteMarteTest`.

## Dados e foto

A carga inicial (seed) fica em `import.sql`, com sete clientes, cinco fichas,
nove produtos, seis pagamentos, seis pedidos e nove itens. Um produto é um tênis
de coleção, com preço na casa do milhão. A foto fica em `produto_foto`, e a
miniatura (thumbnail) de 160 pixels é gerada no envio pelo próprio JDK, sem
biblioteca externa.

## Sistema visual e modelo de dados

O sistema visual está em [DESIGN.md](DESIGN.md), o contexto do produto em
[PRODUCT.md](PRODUCT.md) e o modelo de dados em [MER.md](MER.md).

## Publicação (deploy)

No GitHub Pages é publicada a documentação estática de `docs/`, em
https://pwebufersa.github.io/pefrioshop_referencia/. A aplicação precisa de um
serviço que rode Java, como o Railway, em que a porta é lida de `PORT`, por
`server.port=${PORT:8080}`.

O CSS e o JavaScript são minificados por `ferramenta.Minificador`, na fase
`prepare-package` da construção (build), e gravados em `target/classes`, sem que
os originais sejam tocados: o desenvolvimento permanece com os arquivos
legíveis, e apenas a publicação recebe os compactos. A ferramenta não acompanha
o jar, por exclusão no `maven-jar-plugin`.

Cada arquivo é entregue com o resumo (hash) do conteúdo no endereço (URL), por
`spring.web.resources.chain.strategy.content.enabled`. É uma soma de verificação
(checksum): quando o conteúdo muda, o endereço muda, e o navegador busca a
versão nova, sem que uma versão precise ser escrita à mão no template.
