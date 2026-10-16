/* Mostra apenas o campo da forma de pagamento escolhida. */

function atualizarPagamento() {
	var forma = document.getElementById('forma').value;
	mostrar('campo-cartao', forma === 'CARTAO_CREDITO');
	mostrar('campo-pix', forma === 'PIX');
	mostrar('campo-dinheiro', forma === 'DINHEIRO');
}

function mostrar(id, visivel) {
	var campo = document.getElementById(id);
	if (campo) {
		campo.style.display = visivel ? 'flex' : 'none';
	}
}

window.addEventListener('DOMContentLoaded', atualizarPagamento);
