package br.edu.ufersa.pefrioshop.ferramenta;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Minifica o CSS e o JavaScript no empacotamento, sem tocar nos originais.
 *
 * <p>Os originais legíveis ficam em {@code src/main/resources/static}; o
 * resultado é gravado em {@code target/classes/static}, que é o diretório
 * levado para dentro do jar. A execução é feita pelo {@code exec-maven-plugin}
 * na fase {@code prepare-package}, antes da montagem do jar e antes do
 * empacotamento do Spring Boot. Assim, a minificação fica fora do
 * desenvolvimento e acontece em qualquer construção de publicação, como a do
 * Railway, sem depender de outra linguagem instalada além do próprio Java.
 *
 * <p>Uso: {@code java -cp target/classes
 * br.edu.ufersa.pefrioshop.ferramenta.Minificador <origem> <destino>}.
 */
public final class Minificador {

	private static final Pattern TEXTO = Pattern.compile("\"(?:[^\"\\\\]|\\\\.)*\"|'(?:[^'\\\\]|\\\\.)*'");
	private static final Pattern COMENTARIO_DE_BLOCO = Pattern.compile("/\\*.*?\\*/", Pattern.DOTALL);
	private static final Pattern ESPACO = Pattern.compile("\\s+");
	private static final Pattern PONTUACAO = Pattern.compile("\\s*([{};:,>])\\s*");
	private static final Pattern PONTO_FINAL = Pattern.compile(";}");
	private static final String ABRE_EXPRESSAO = "(,=:[!&|?{};+-*%<>~^";
	private static final List<String> PALAVRAS_DE_EXPRESSAO =
			List.of("return", "typeof", "case", "in", "of", "do", "else");
	private static final List<String> PASTAS = List.of("css", "js");

	private Minificador() {
	}

	/**
	 * Minifica os arquivos de {@code origem} e grava o resultado em {@code destino},
	 * informando o tamanho de cada arquivo antes e depois.
	 */
	public static void main(String[] args) throws IOException {
		Path origem = Path.of(args.length > 0 ? args[0] : "src/main/resources/static");
		Path destino = Path.of(args.length > 1 ? args[1] : "target/classes/static");

		if (!Files.isDirectory(origem)) {
			System.err.println("Diretório de origem não encontrado: " + origem);
			System.exit(1);
		}

		long antes = 0;
		long depois = 0;
		for (String pasta : PASTAS) {
			Path pastaOrigem = origem.resolve(pasta);
			if (!Files.isDirectory(pastaOrigem)) {
				continue;
			}
			for (Path arquivo : listar(pastaOrigem)) {
				String fonte = Files.readString(arquivo, StandardCharsets.UTF_8);
				String compacto = arquivo.getFileName().toString().endsWith(".css")
						? minificarCss(fonte)
						: minificarJs(fonte);
				Path alvo = destino.resolve(pasta).resolve(arquivo.getFileName());
				Files.createDirectories(alvo.getParent());
				Files.writeString(alvo, compacto, StandardCharsets.UTF_8);

				long tamanhoAntes = bytes(fonte);
				long tamanhoDepois = bytes(compacto);
				antes += tamanhoAntes;
				depois += tamanhoDepois;
				System.out.printf("%-16s %6d -> %6d bytes%n", arquivo.getFileName(), tamanhoAntes, tamanhoDepois);
			}
		}
		System.out.printf("%-16s %6d -> %6d bytes (%d%% menor)%n", "total", antes, depois,
				100 - Math.round(depois * 100f / antes));
	}

	/**
	 * Tira os comentários e o espaço entre as regras, preservando o conteúdo entre
	 * aspas, que carrega endereços e textos de rótulo.
	 */
	static String minificarCss(String texto) {
		List<String> guardados = new ArrayList<>();
		String protegido = guardarTextos(texto, guardados);
		protegido = COMENTARIO_DE_BLOCO.matcher(protegido).replaceAll("");
		protegido = ESPACO.matcher(protegido).replaceAll(" ");
		protegido = PONTUACAO.matcher(protegido).replaceAll("$1");
		protegido = PONTO_FINAL.matcher(protegido).replaceAll("}");
		return devolverTextos(protegido.strip(), guardados);
	}

	/**
	 * Tira os comentários e a indentação, sem juntar linhas, para não alterar
	 * nenhuma inserção automática de ponto e vírgula.
	 */
	static String minificarJs(String codigo) {
		return removerComentarios(codigo).lines()
				.map(String::strip)
				.filter(linha -> !linha.isEmpty())
				.collect(Collectors.joining("\n"));
	}

	private static List<Path> listar(Path pasta) throws IOException {
		try (Stream<Path> arquivos = Files.list(pasta)) {
			return arquivos.filter(Files::isRegularFile).sorted().toList();
		}
	}

	private static long bytes(String texto) {
		return texto.getBytes(StandardCharsets.UTF_8).length;
	}

	private static String guardarTextos(String texto, List<String> guardados) {
		Matcher achado = TEXTO.matcher(texto);
		StringBuilder saida = new StringBuilder();
		while (achado.find()) {
			guardados.add(achado.group());
			achado.appendReplacement(saida, "\u0000" + (guardados.size() - 1) + "\u0000");
		}
		achado.appendTail(saida);
		return saida.toString();
	}

	private static String devolverTextos(String texto, List<String> guardados) {
		String devolvido = texto;
		for (int indice = 0; indice < guardados.size(); indice++) {
			devolvido = devolvido.replace("\u0000" + indice + "\u0000", guardados.get(indice));
		}
		return devolvido;
	}

	private static String removerComentarios(String codigo) {
		StringBuilder saida = new StringBuilder();
		String palavra = "";
		char anterior = '\0';
		int tamanho = codigo.length();
		int i = 0;

		while (i < tamanho) {
			char caractere = codigo.charAt(i);

			if (caractere == '"' || caractere == '\'' || caractere == '`') {
				int fim = fimDoTexto(codigo, i, caractere);
				saida.append(codigo, i, Math.min(fim + 1, tamanho));
				i = fim + 1;
				anterior = caractere;
				palavra = "";
				continue;
			}
			if (caractere == '/' && i + 1 < tamanho && codigo.charAt(i + 1) == '/') {
				int fim = codigo.indexOf('\n', i);
				i = fim == -1 ? tamanho : fim;
				continue;
			}
			if (caractere == '/' && i + 1 < tamanho && codigo.charAt(i + 1) == '*') {
				int fim = codigo.indexOf("*/", i + 2);
				i = fim == -1 ? tamanho : fim + 2;
				continue;
			}
			if (caractere == '/' && abreExpressao(anterior, palavra)) {
				int fim = fimDaExpressao(codigo, i);
				saida.append(codigo, i, Math.min(fim + 1, tamanho));
				i = fim + 1;
				anterior = '/';
				palavra = "";
				continue;
			}

			saida.append(caractere);
			if (Character.isLetterOrDigit(caractere) || caractere == '_' || caractere == '$') {
				palavra = palavra + caractere;
			} else if (!Character.isWhitespace(caractere)) {
				palavra = "";
			}
			if (!Character.isWhitespace(caractere)) {
				anterior = caractere;
			}
			i++;
		}
		return saida.toString();
	}

	/** Devolve a posição do fechamento do texto entre aspas ou acentos graves. */
	private static int fimDoTexto(String codigo, int inicio, char delimitador) {
		int fim = inicio + 1;
		while (fim < codigo.length()) {
			if (codigo.charAt(fim) == '\\') {
				fim += 2;
				continue;
			}
			if (codigo.charAt(fim) == delimitador) {
				return fim;
			}
			fim++;
		}
		return codigo.length() - 1;
	}

	/** Devolve a posição do fechamento da expressão regular iniciada na barra. */
	private static int fimDaExpressao(String codigo, int inicio) {
		int fim = inicio + 1;
		boolean dentroDeClasse = false;
		while (fim < codigo.length()) {
			char caractere = codigo.charAt(fim);
			if (caractere == '\\') {
				fim += 2;
				continue;
			}
			if (caractere == '[') {
				dentroDeClasse = true;
			} else if (caractere == ']') {
				dentroDeClasse = false;
			} else if (caractere == '/' && !dentroDeClasse) {
				return fim;
			}
			fim++;
		}
		return codigo.length() - 1;
	}

	/** Diz se a barra inicia expressão regular, e não divisão. */
	private static boolean abreExpressao(char anterior, String palavra) {
		return ABRE_EXPRESSAO.indexOf(anterior) >= 0 || PALAVRAS_DE_EXPRESSAO.contains(palavra);
	}

}
