package br.edu.ufersa.pefrioshop.service;

import br.edu.ufersa.pefrioshop.domain.Cliente;
import br.edu.ufersa.pefrioshop.domain.FichaClinicaDoPe;
import br.edu.ufersa.pefrioshop.repository.ClienteRepository;
import br.edu.ufersa.pefrioshop.repository.FichaClinicaDoPeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FichaClinicaService {

	private final ClienteRepository clientes;
	private final FichaClinicaDoPeRepository fichas;

	public FichaClinicaService(ClienteRepository clientes, FichaClinicaDoPeRepository fichas) {
		this.clientes = clientes;
		this.fichas = fichas;
	}

	/** Ficha em branco ligada ao cliente, pronta para o formulario. */
	public FichaClinicaDoPe novaPara(Long clienteId) {
		Cliente cliente = buscarCliente(clienteId);
		if (cliente.getFichaClinica() != null) {
			throw new IllegalArgumentException("O cliente " + cliente.getNomeCompleto()
					+ " (id=" + clienteId + ") já possui ficha clínica. Edite a ficha existente.");
		}
		FichaClinicaDoPe ficha = new FichaClinicaDoPe();
		ficha.setCliente(cliente);
		return ficha;
	}

	public FichaClinicaDoPe buscarDoCliente(Long clienteId) {
		FichaClinicaDoPe ficha = buscarCliente(clienteId).getFichaClinica();
		if (ficha == null) {
			throw new IllegalArgumentException("O cliente id=" + clienteId
					+ " ainda não possui ficha clínica. Cadastre uma ficha primeiro.");
		}
		return ficha;
	}

	@Transactional
	public FichaClinicaDoPe salvarPara(Long clienteId, FichaClinicaDoPe ficha) {
		Cliente cliente = buscarCliente(clienteId);
		ficha.setCliente(cliente);
		FichaClinicaDoPe salva = fichas.save(ficha);
		cliente.setFichaClinica(salva);
		clientes.save(cliente);
		return salva;
	}

	@Transactional
	public void excluir(Long clienteId) {
		Cliente cliente = buscarCliente(clienteId);
		cliente.setFichaClinica(null);
		clientes.save(cliente);
	}

	private Cliente buscarCliente(Long clienteId) {
		return clientes.findById(clienteId).orElseThrow(() -> new IllegalArgumentException(
				"Cliente não encontrado: id=" + clienteId + ". Verifique a lista de clientes."));
	}

}
