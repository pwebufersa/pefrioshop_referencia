function atualizarPagamento() {
	var forma = document.getElementById('forma').value;
	alternar('campo-cartao', forma === 'CARTAO_CREDITO');
	alternar('campo-pix', forma === 'PIX');
	alternar('campo-dinheiro', forma === 'DINHEIRO');
}

function alternar(id, visivel) {
	var campo = document.getElementById(id);
	if (campo) {
		campo.style.display = visivel ? 'flex' : 'none';
	}
}

window.addEventListener('DOMContentLoaded', atualizarPagamento);
