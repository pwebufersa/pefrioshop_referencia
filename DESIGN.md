# Sistema visual - PéFrioShop

Sistema visual do projeto de referência. Vale para todas as telas.

## Onde o sistema é usado

O sistema é operado no balcão da loja, à luz do dia, em tela de desktop. A
superfície é clara, e o azul da navegação ancora a página.

## Paleta

| Papel | Valor | Uso |
|---|---|---|
| Cal | `#f5f2ec` | Fundo da página |
| Cal escuro | `#ebe6dc` | Cabeçalho de tabela e rodapé de total |
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

A paleta parte da foto colorida de autoria do aluno;
`src/main/resources/static/imagens/foto-referencia.svg` é o substituto.

## Tipografia

- Títulos: Archivo, pesos 500, 600 e 700.
- Corpo: Source Sans 3, pesos 400 e 600.

Corpo em 1rem, h1 entre 1,7rem e 2,5rem, h2 entre 1,2rem e 1,5rem, e medida de
leitura de 68ch.

## Espaçamento

Escala de 0,25rem a 3,5rem, em passos nomeados `--passo-1` a `--passo-7`. O
espaço anterior a um título é maior que o posterior.

## Componentes

- Navegação: barra azulejo, com borda inferior de areia.
- Tabela: superfície branca, cabeçalho em versalete, numeral tabular
  (`tabular-nums`), cantos de 10px e sombra suave.
- Rodapé de totalização: borda superior forte, fundo cal escuro, e o rótulo
  encostado nos valores da própria coluna.
- Botão: couro, com texto branco; desabilitado em borda forte.
- Formulário: rótulos em tinta suave, campos com borda forte e foco azulejo,
  todos com a mesma largura. O campo de CEP traz o botão de busca na mesma
  linha, que ocupa a largura dos demais campos.
- Painel: superfície branca, para os últimos pedidos e para o resumo do pedido.
- Lista de atalhos: linhas com divisória, sem grade de cartões.

## Estados dos elementos interativos

Hover em linhas e botões; foco visível no que é interativo; erro em vermelho;
vazio com aviso em tinta suave.

## Detalhes desenhados pelo próprio navegador

Três recursos desenhados pelo próprio navegador foram ajustados à paleta:

- Seleção de texto (`::selection`): fundo em areia.
- Anel de foco (`:focus-visible`): contorno em azulejo claro, para a navegação
  por teclado.
- Numeral tabular (`font-variant-numeric`): dígitos de largura fixa, o que
  alinha dinheiro, peso e contagem na vertical.

## Movimento

Uma transição curta de cor em botões e linhas, com curva exponencial. Sob
`prefers-reduced-motion`, todo movimento é desligado.

## Responsividade

Abaixo de 820px, as tabelas de listagem viram cartões, com um rótulo por célula,
o painel do balcão passa a uma coluna, e as tabelas de relatório rolam na
horizontal.

## Restrições

- Texto em português, voz passiva.
- Nenhum cálculo no template; os valores chegam prontos do serviço.
- Valores de negócio em `application.properties`.
