# MER - modelo entidade-relacionamento do PéFrioShop

Diagrama gerado a partir das entidades JPA. É o esquema que o Hibernate cria no
banco, com as chaves, os tipos e as relações.

```mermaid
erDiagram
    CLIENTE ||--|| ENDERECO : possui
    CLIENTE ||--o| FICHA_CLINICA_DO_PE : possui
    CLIENTE ||--o{ PEDIDO : realiza
    PEDIDO ||--|{ ITEM_PEDIDO : contem
    PEDIDO ||--|| PAGAMENTO_DO_PEDIDO : registra
    PRODUTO ||--o| PRODUTO_FOTO : tem
    PRODUTO ||--o{ ITEM_PEDIDO : compoe

    CLIENTE {
        bigint id PK
        varchar nome_completo
        enum genero
        varchar email
        varchar telefone
        date data_nascimento
        timestamp data_cadastro
    }

    ENDERECO {
        bigint id PK
        bigint cliente_id FK
        varchar rua
        varchar bairro
        varchar numero
        varchar cidade
        varchar cep
        enum pais
    }

    FICHA_CLINICA_DO_PE {
        bigint id PK
        bigint cliente_id FK
        numeric comprimento_cm
        enum largura
        enum pisada
        int numero_preferido_br
        varchar marca_preferida
        enum temperatura
        enum odor
        date data_ultima_visita_podologo
        boolean tem_joanete
    }

    PEDIDO {
        bigint id PK
        bigint cliente_id FK
        timestamp data_pedido
        bigint pagamento_id FK
    }

    ITEM_PEDIDO {
        bigint id PK
        bigint pedido_id FK
        bigint produto_id FK
        int quantidade
        numeric valor_unitario
    }

    PAGAMENTO_DO_PEDIDO {
        bigint id PK
        enum forma
        varchar cartao_ultimos_digitos
        varchar chave_pix
        numeric valor_pago
    }

    PRODUTO {
        bigint id PK
        varchar nome
        varchar marca
        enum tipo
        enum genero_alvo
        int numero_br
        numeric peso_kg
        numeric preco_compra
        numeric preco_venda
        date data_cadastro
    }

    PRODUTO_FOTO {
        bigint id PK
        bigint produto_id FK
        blob conteudo
        varchar content_type
        blob miniatura
        varchar miniatura_content_type
    }
```

## Relações e cardinalidades

| Relação | Cardinalidade | Regra |
|---|---|---|
| Cliente e endereço | 1 para 1 | Cada cliente tem um endereço, em tabela própria. Excluir o cliente exclui o endereço. |
| Cliente e ficha clínica | 1 para 0 ou 1 | Cada cliente tem no máximo uma ficha. Excluir o cliente exclui a ficha em cascata. |
| Cliente e pedido | 1 para 0 ou muitos | Excluir o cliente não é permitido enquanto houver pedido. |
| Pedido e item | 1 para 1 ou muitos | Excluir o pedido exclui os itens em cascata. |
| Pedido e pagamento | 1 para 1 | Cada pedido tem um pagamento. Excluir o pedido exclui o pagamento. |
| Produto e foto | 1 para 0 ou 1 | A foto fica em tabela própria. Excluir o produto exclui a foto. |
| Produto e item | 1 para 0 ou muitos | Excluir o produto não é permitido enquanto houver item. Excluir o pedido não afeta o produto. |

## Observações

- O endereço tem tabela e identidade próprias, com a chave estrangeira
  `cliente_id` única. Assim, campos como `pais` entram sem inchar a linha do
  cliente.
- O campo `pais` é um enum, e não texto livre, para evitar grafias divergentes.
- A foto do produto fica em `produto_foto` para a linha do produto permanecer
  leve; a listagem não carrega os bytes.
- Chaves únicas: `endereco.cliente_id`, `ficha_clinica_do_pe.cliente_id`,
  `produto_foto.produto_id` e `pedido.pagamento_id`.
- O `item_pedido.valor_unitario` é uma cópia do preço no momento da compra, e
  não o preço atual do produto.
- Nenhum valor calculável é guardado: o total do pedido, o imposto e o desconto
  são obtidos em tempo de execução.

## Valores dos enums

| Enum | Valores |
|---|---|
| Genero | MASCULINO, FEMININO, NAO_INFORMADO |
| Pais | BRASIL, PORTUGAL, ESTADOS_UNIDOS, ESPANHA, ARGENTINA, OUTRO |
| TipoProduto | TENIS, SANDALIA, BOTA, SAPATENIS, CHINELO |
| GeneroAlvo | MASCULINO, FEMININO, UNISSEX, INFANTIL |
| LarguraPe | ESTREITO, NORMAL, LARGO |
| TipoPisada | PRONADA, SUPINADA, NEUTRA |
| TemperaturaPe | FRIO, NORMAL, QUENTE |
| OdorPe | SEM_ODOR, LEVE, FORTE |
| FormaPagamento | DINHEIRO, CARTAO_CREDITO, PIX |
