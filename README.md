# PéFrioShop - implementação de referência

Sistema de informação para loja de calçados, com ficha clínica do pé. Projeto de
referência da disciplina **Programação Web 2026.2** - UFERSA Angicos.

Repositório: https://github.com/pwebufersa/pefrioshop_referencia

## Como executar

No Windows, a partir da raiz do projeto:

```
cd C:\repos_github_2026\peFrioShop_referencia
mvnw.cmd spring-boot:run
```

No Linux, no macOS e no bash do Windows:

```
cd /c/repos_github_2026/peFrioShop_referencia
./mvnw spring-boot:run
```

A aplicação sobe em http://localhost:8080, e o console do H2 fica em
http://localhost:8080/h2-console, com usuário `admin` e senha `admin`. A
execução é encerrada com Ctrl+C.

Para gerar o jar, com o CSS e o JavaScript minificados, que é o que a
hospedagem usa:

```
mvnw.cmd -DskipTests clean package
java -jar target\pefrioshop-0.0.1-SNAPSHOT.jar
```

## Tecnologias usadas

Spring Boot 4.1.1, Java 21, Thymeleaf, Spring Data JPA, H2 em memória no modo
MySQL e Maven com wrapper.

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
13. Frete estimado para Marte, pela viagem e pelo peso do par.
14. Desconto de 7,3% nos quatro produtos mais novos.
15. Contagem de produtos por tipo.
16. Novatos do mês e do ano.

## Organização do código

- `domain`: entidades e enums.
- `repository`: acesso a dados.
- `service`: regras de negócio e cálculos.
- `web`: controladores e formulários.
- `config`: parâmetros da loja, cliente HTTP e carga inicial.
- `ferramenta`: minificação dos assets, executada no empacotamento.

Os valores de negócio ficam em `application.properties`, sob o prefixo
`pefrioshop`, e são lidos por `ParametrosLoja`. Nenhum cálculo é feito no
template.

## Frete para Marte

O frete soma a viagem, de `pefrioshop.frete-marte-baseline`, ao peso
transportado, pelo preço do quilo de `pefrioshop.frete-marte-por-quilo-dolar`,
convertido pelo `pefrioshop.cambio-dolar`. O preço do quilo é o publicado pela
SpaceX para carga ao solo marciano, a partir de 2028: US$ 100 milhões por
tonelada métrica, o equivalente a US$ 100.000,00 por quilo.

O peso do par é guardado em gramas, e não em quilo. A conversão para quilo é
feita em `BigDecimal`, com duas casas e arredondamento, porque a divisão
inteira de gramas por mil daria zero em qualquer par abaixo de um quilo: o
número pequeno desapareceria dentro do número grande, e o frete sairia sem o
peso. O caso está fixado em `ResumoFreteMarteTest`.

## Dados de exemplo e foto do produto

A carga inicial fica em `import.sql`: sete clientes, cinco fichas, nove
produtos, seis pagamentos, seis pedidos e nove itens. Um dos produtos é um tênis
de coleção, com preço na casa do milhão, para exercitar a formatação de valores
altos.

A foto do produto fica em `produto_foto`, e a miniatura de 160 pixels é gerada
no envio pelo próprio JDK, por `ImageIO` e `Graphics2D`, sem ImageMagick e sem
biblioteca externa. Sem foto, o endereço da miniatura devolve `sem-foto.svg`.

## Sistema visual e contexto do produto

O sistema visual está em [DESIGN.md](DESIGN.md), e o contexto do produto em
[PRODUCT.md](PRODUCT.md).

## Como o sistema é publicado

No GitHub Pages é publicada a documentação estática de `docs/`, em
https://pwebufersa.github.io/pefrioshop_referencia/. A aplicação precisa de um
serviço que rode Java, como o Railway.

Na hospedagem, a construção executa `./mvnw package`, e a porta é lida da
variável `PORT`, por `server.port=${PORT:8080}`, com 8080 como valor local.

A minificação do CSS e do JavaScript é feita por `ferramenta.Minificador`,
executado pelo `exec-maven-plugin` na fase `prepare-package`, e gravada em
`target/classes`, sem que os originais de `src/main/resources/static` sejam
tocados. Como `spring-boot:run` não chega ao empacotamento, o desenvolvimento
permanece com os arquivos legíveis, e apenas a publicação recebe os compactos:
o CSS cai de 13.960 para 11.250 bytes, e o JavaScript, de 3.116 para 2.916. A
ferramenta não acompanha o jar, por exclusão no `maven-jar-plugin`.

O CSS, o JavaScript e as imagens são entregues com o resumo do conteúdo no
endereço, por `spring.web.resources.chain.strategy.content.enabled`. Assim, a
versão escrita à mão no template é dispensada: quando o arquivo muda, o endereço
muda junto, e o navegador busca o arquivo novo. Sem isso, a folha de estilo
antiga permaneceria no cache do navegador depois de uma alteração.
