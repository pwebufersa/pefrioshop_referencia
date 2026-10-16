package br.edu.ufersa.pefrioshop.ferramenta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

/** Conferência da minificação do CSS e do JavaScript (publicação). */
class MinificadorTest {

	private static final Path FOLHA = Path.of("src/main/resources/static/css/style.css");

	@Test
	void cssCompactoMantemTodasAsDeclaracoes() throws IOException {
		String fonte = Files.readString(FOLHA, StandardCharsets.UTF_8);
		String semComentarios = fonte.replaceAll("(?s)/\\*.*?\\*/", "");
		String compacto = Minificador.minificarCss(fonte);

		assertEquals(declaracoes(semComentarios), declaracoes(compacto));
		assertTrue(compacto.length() < fonte.length());
	}

	@Test
	void cssCompactoPreservaEnderecoETextoDeRotulo() {
		String fonte = """
				@import url("https://fonts.example/?family=Archivo:wght@500;600");
				.rotulo::before { content: attr(data-rotulo) ": "; }
				""";

		String compacto = Minificador.minificarCss(fonte);

		assertTrue(compacto.contains("https://fonts.example/?family=Archivo:wght@500;600"));
		assertTrue(compacto.contains("\": \""));
	}

	@Test
	void cssCompactoRemoveComentarioEEspaco() {
		String compacto = Minificador.minificarCss("/* nota */\n.menu {\n\tcolor: #fff;\n}\n");

		assertEquals(".menu{color:#fff}", compacto);
	}

	@Test
	void jsCompactoRemoveComentarioEMantemAExpressaoRegular() {
		String fonte = """
				/* limpa o telefone */
				function limpar(valor) {
					// só dígitos
					return valor.replace(/\\D/g, '');
				}
				""";

		String compacto = Minificador.minificarJs(fonte);

		assertFalse(compacto.contains("limpa o telefone"));
		assertFalse(compacto.contains("só dígitos"));
		assertTrue(compacto.contains("/\\D/g"));
		assertTrue(compacto.contains("function limpar(valor)"));
		assertEquals(3, compacto.lines().count());
	}

	@Test
	void jsCompactoMantemTextoEntreAspasComBarra() {
		String compacto = Minificador.minificarJs("var caminho = '/produtos/busca'; // fim\n");

		assertEquals("var caminho = '/produtos/busca';", compacto);
	}

	private List<String> declaracoes(String texto) {
		Matcher achado = Pattern.compile("([a-zA-Z-]+)\\s*:\\s*([^;{}]+)").matcher(texto);
		List<String> declaracoes = new java.util.ArrayList<>();
		while (achado.find()) {
			String valor = achado.group(2).replaceAll("\\s*,\\s*", ",").replaceAll("\\s+", " ").strip();
			declaracoes.add(achado.group(1) + ":" + valor);
		}
		return declaracoes.stream().sorted().toList();
	}

}
