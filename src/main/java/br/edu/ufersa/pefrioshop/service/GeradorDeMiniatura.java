package br.edu.ufersa.pefrioshop.service;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Gera a miniatura da foto do produto.
 *
 * <p>A redução usa apenas o JDK, por {@link ImageIO} e {@link Graphics2D}. Nao
 * ha dependencia de ImageMagick nem de biblioteca externa, o que mantem a
 * imagem do contêiner enxuta. O modo sem interface grafica e assumido, e o
 * Spring Boot o habilita por padrao.
 *
 * <p>Uso: {@code byte[] miniatura = gerador.gerar(conteudoOriginal);}. Quando o
 * arquivo nao for uma imagem legivel, devolve {@code null}, e a listagem usa o
 * marcador de produto sem foto.
 */
@Component
public class GeradorDeMiniatura {

	private static final Logger LOG = LoggerFactory.getLogger(GeradorDeMiniatura.class);
	private static final int LARGURA_MAXIMA = 160;

	public byte[] gerar(byte[] original) {
		if (original == null || original.length == 0) {
			return null;
		}
		try {
			BufferedImage imagem = ImageIO.read(new ByteArrayInputStream(original));
			if (imagem == null) {
				LOG.warn("Arquivo enviado nao foi lido como imagem; a miniatura nao sera gerada.");
				return null;
			}
			BufferedImage reduzida = reduzir(imagem);
			ByteArrayOutputStream saida = new ByteArrayOutputStream();
			ImageIO.write(reduzida, "png", saida);
			return saida.toByteArray();
		} catch (IOException excecao) {
			LOG.warn("Falha ao gerar a miniatura: {}", excecao.getMessage());
			return null;
		}
	}

	private BufferedImage reduzir(BufferedImage original) {
		int largura = Math.min(LARGURA_MAXIMA, original.getWidth());
		int altura = Math.max(1, original.getHeight() * largura / original.getWidth());
		BufferedImage reduzida = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);
		Graphics2D pincel = reduzida.createGraphics();
		pincel.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		pincel.drawImage(original, 0, 0, largura, altura, null);
		pincel.dispose();
		return reduzida;
	}

}
