"""Minifica o CSS e o JavaScript para a publicação, sem tocar nos originais.

Os originais legíveis ficam em ``src/main/resources/static``. O resultado é
gravado em ``target/classes/static``, que é o diretório levado para dentro do
jar. Assim a leitura e a manutenção acontecem no arquivo legível, e apenas a
hospedagem recebe o arquivo compacto.

Sequência de publicação, em que o original é preservado até o empacotamento:

    ./mvnw -DskipTests package
    python scripts/minificar.py
    ./mvnw jar:jar

A minificação de CSS remove comentários e o espaço entre as regras. A de
JavaScript remove comentários e indentação, sem juntar linhas, para não alterar
nenhuma inserção automática de ponto e vírgula.
"""

import re
import sys
from pathlib import Path

RAIZ = Path(__file__).resolve().parent.parent
ORIGEM = RAIZ / "src" / "main" / "resources" / "static"
DESTINO = RAIZ / "target" / "classes" / "static"

TEXTO = re.compile(r'"(?:[^"\\]|\\.)*"|\'(?:[^\'\\]|\\.)*\'')
COMENTARIO_BLOCO = re.compile(r"/\*.*?\*/", re.S)
ESPACO = re.compile(r"\s+")
PONTUACAO = re.compile(r"\s*([{};:,>])\s*")
PONTO_FINAL = re.compile(r";}")

ABRE_EXPRESSAO = set("(,=:[!&|?{};+-*%<>~^")
PALAVRAS_DE_EXPRESSAO = ("return", "typeof", "case", "in", "of", "do", "else")


def guardar_textos(codigo):
    """Substitui cada literal entre aspas por um marcador, para não ser tocado."""
    guardados = []

    def guardar(achado):
        guardados.append(achado.group(0))
        return "\x00%d\x00" % (len(guardados) - 1)

    return TEXTO.sub(guardar, codigo), guardados


def devolver_textos(codigo, guardados):
    for indice, literal in enumerate(guardados):
        codigo = codigo.replace("\x00%d\x00" % indice, literal)
    return codigo


def minificar_css(texto):
    protegido, guardados = guardar_textos(texto)
    protegido = COMENTARIO_BLOCO.sub("", protegido)
    protegido = ESPACO.sub(" ", protegido)
    protegido = PONTUACAO.sub(r"\1", protegido)
    protegido = PONTO_FINAL.sub("}", protegido)
    return devolver_textos(protegido.strip(), guardados)


def abre_expressao(anterior, palavra):
    """Diz se uma barra inicia expressão regular, e não divisão."""
    if anterior and anterior in ABRE_EXPRESSAO:
        return True
    return palavra in PALAVRAS_DE_EXPRESSAO


def remover_comentarios(codigo):
    saida = []
    palavra = ""
    anterior = ""
    i = 0
    tamanho = len(codigo)

    while i < tamanho:
        caractere = codigo[i]
        if caractere in "\"'`":
            fim = i + 1
            while fim < tamanho:
                if codigo[fim] == "\\":
                    fim += 2
                    continue
                if codigo[fim] == caractere:
                    break
                fim += 1
            saida.append(codigo[i:fim + 1])
            i = fim + 1
            anterior, palavra = caractere, ""
            continue
        if caractere == "/" and i + 1 < tamanho and codigo[i + 1] == "/":
            fim = codigo.find("\n", i)
            i = tamanho if fim == -1 else fim
            continue
        if caractere == "/" and i + 1 < tamanho and codigo[i + 1] == "*":
            fim = codigo.find("*/", i + 2)
            i = tamanho if fim == -1 else fim + 2
            continue
        if caractere == "/" and abre_expressao(anterior, palavra):
            fim = i + 1
            classe = False
            while fim < tamanho:
                if codigo[fim] == "\\":
                    fim += 2
                    continue
                if codigo[fim] == "[":
                    classe = True
                elif codigo[fim] == "]":
                    classe = False
                elif codigo[fim] == "/" and not classe:
                    break
                fim += 1
            saida.append(codigo[i:fim + 1])
            i = fim + 1
            anterior, palavra = "/", ""
            continue
        saida.append(caractere)
        if caractere.isalnum() or caractere in "_$":
            palavra += caractere
        elif not caractere.isspace():
            palavra = ""
        if not caractere.isspace():
            anterior = caractere
        i += 1

    return "".join(saida)


def minificar_js(codigo):
    linhas = [linha.strip() for linha in remover_comentarios(codigo).splitlines()]
    return "\n".join(linha for linha in linhas if linha)


def minificar(origem):
    if origem.suffix == ".css":
        return minificar_css(origem.read_text(encoding="utf-8"))
    return minificar_js(origem.read_text(encoding="utf-8"))


def arquivos():
    for pasta in ("css", "js"):
        yield from sorted((ORIGEM / pasta).glob("*.*"))


def principal():
    if not (RAIZ / "target" / "classes").is_dir():
        print("Execute ./mvnw -DskipTests package antes da minificação.", file=sys.stderr)
        return 1

    antes = depois = 0
    for origem in arquivos():
        compacto = minificar(origem)
        destino = DESTINO / origem.relative_to(ORIGEM)
        destino.parent.mkdir(parents=True, exist_ok=True)
        destino.write_text(compacto, encoding="utf-8")
        antes += origem.stat().st_size
        depois += len(compacto.encode("utf-8"))
        print("%-14s %6d -> %6d bytes" % (origem.name, origem.stat().st_size, len(compacto.encode("utf-8"))))

    print("total          %6d -> %6d bytes (%d%%)" % (antes, depois, 100 - round(depois * 100 / antes)))
    return 0


if __name__ == "__main__":
    raise SystemExit(principal())
