package br.edu.ufersa.pefrioshop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class FichaClinicaDoPe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(optional = false)
	@JoinColumn(name = "cliente_id", nullable = false, unique = true)
	private Cliente cliente;

	@NotNull(message = "O comprimento do pé é obrigatório.")
	@Positive(message = "O comprimento do pé deve ser maior que zero.")
	private BigDecimal comprimentoCm;

	@NotNull(message = "A largura é obrigatória.")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 12)
	private LarguraPe largura;

	@NotNull(message = "O tipo de pisada é obrigatório.")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 12)
	private TipoPisada pisada;

	@NotNull(message = "O número preferido é obrigatório.")
	@Positive(message = "O número preferido deve ser maior que zero.")
	private Integer numeroPreferidoBr;

	@Column(length = 80)
	private String marcaPreferida;

	@NotNull(message = "A temperatura habitual é obrigatória.")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 12)
	private TemperaturaPe temperatura;

	@NotNull(message = "O odor é obrigatório.")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 12)
	private OdorPe odor;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate dataUltimaVisitaPodologo;

	@Column(nullable = false)
	private boolean temJoanete;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public BigDecimal getComprimentoCm() {
		return comprimentoCm;
	}

	public void setComprimentoCm(BigDecimal comprimentoCm) {
		this.comprimentoCm = comprimentoCm;
	}

	public LarguraPe getLargura() {
		return largura;
	}

	public void setLargura(LarguraPe largura) {
		this.largura = largura;
	}

	public TipoPisada getPisada() {
		return pisada;
	}

	public void setPisada(TipoPisada pisada) {
		this.pisada = pisada;
	}

	public Integer getNumeroPreferidoBr() {
		return numeroPreferidoBr;
	}

	public void setNumeroPreferidoBr(Integer numeroPreferidoBr) {
		this.numeroPreferidoBr = numeroPreferidoBr;
	}

	public String getMarcaPreferida() {
		return marcaPreferida;
	}

	public void setMarcaPreferida(String marcaPreferida) {
		this.marcaPreferida = marcaPreferida;
	}

	public TemperaturaPe getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(TemperaturaPe temperatura) {
		this.temperatura = temperatura;
	}

	public OdorPe getOdor() {
		return odor;
	}

	public void setOdor(OdorPe odor) {
		this.odor = odor;
	}

	public LocalDate getDataUltimaVisitaPodologo() {
		return dataUltimaVisitaPodologo;
	}

	public void setDataUltimaVisitaPodologo(LocalDate dataUltimaVisitaPodologo) {
		this.dataUltimaVisitaPodologo = dataUltimaVisitaPodologo;
	}

	public boolean isTemJoanete() {
		return temJoanete;
	}

	public void setTemJoanete(boolean temJoanete) {
		this.temJoanete = temJoanete;
	}

}
