# Design System - PéFrioShop

Sistema visual do projeto de referência. Vale para todas as telas.

## Cena

O sistema é operado no balcão da loja, à luz do dia, em tela de desktop. Por
isso a superfície é clara, e o azul da navegação ancora a página.

## Paleta

| Papel | Valor | Uso |
|---|---|---|
| Cal | `#f5f2ec` | Fundo da página |
| Cal escuro | `#ebe6dc` | Cabeçalho de tabela |
| Superfície | `#ffffff` | Tabelas, painéis, formulários |
| Tinta | `#14202a` | Texto principal |
| Tinta suave | `#46545f` | Texto secundário |
| Azulejo | `#14425f` | Navegação e foco |
| Azulejo claro | `#2f6f96` | Anel de foco |
| Couro | `#a2542a` | Ação principal |
| Couro escuro | `#7f3f1f` | Ação principal em `:hover` |
| Areia | `#d99a2b` | Apoio e seleção de texto |
| Borda | `#d5cec0` | Divisórias |
| Erro | `#a3281c` | Mensagens de erro |

A paleta do projeto parte da foto colorida de autoria do aluno. O arquivo
`src/main/resources/static/imagens/foto-referencia.svg` é um substituto e deve
ser trocado pela foto.

## Tipografia

- Títulos: Archivo, pesos 500, 600 e 700.
- Corpo: Source Sans 3, pesos 400 e 600.

Escala: corpo 1rem; h1 entre 1,7rem e 2,5rem; h2 entre 1,2rem e 1,5rem. A
medida de leitura é de 68ch.

## Espaçamento

Escala de 0,25rem a 3,5rem, em passos nomeados `--passo-1` a `--passo-7`. O
espaço acima de um título é maior que o espaço a seguir.

## Componentes

- Navegação: barra azulejo com borda inferior de areia.
- Tabela: superfície branca, cabeçalho em versalete, numeral tabular, sombra
  suave e cantos de 10px.
- Botão: couro, com texto branco; estado desabilitado em borda forte.
- Formulário: rótulos em tinta suave, campos com borda forte e foco azulejo.
- Painel: superfície branca para os últimos pedidos e para o resumo do pedido.
- Lista de atalhos: linhas com divisória, sem grade de cartões.

## Estados

Hover em linhas e botões; foco visível em todo elemento interativo; erro com
borda e texto em vermelho; vazio com aviso em tinta suave.

## Superfícies do navegador

Seleção em areia, anel de foco em azulejo claro, numeral tabular em dinheiro e
comprimento.

## Movimento

Uma transição curta de cor em botões e linhas, com curva exponencial. Sob
`prefers-reduced-motion`, todo movimento é desligado.

## Responsividade

Abaixo de 780px, o painel do balcão passa a uma coluna e as tabelas rolam na
horizontal.

## Restrições

- Texto em português, voz passiva.
- Nenhum cálculo no template; os valores chegam prontos do serviço.
- Valores de negócio em `application.properties`.
