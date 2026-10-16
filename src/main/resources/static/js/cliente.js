/* Formulário do cliente: busca do endereço pelo CEP e conferência do telefone. */

async function buscarCep() {
	var aviso = document.getElementById('aviso-cep');
	var cep = document.getElementById('cep').value.replace(/\D/g, '');

	if (cep.length !== 8) {
		aviso.textContent = 'Informe um CEP com 8 dígitos.';
		return;
	}

	aviso.textContent = 'Consultando o CEP...';

	try {
		var resposta = await fetch('/api/cep/' + cep);
		if (!resposta.ok) {
			throw new Error('CEP não encontrado.');
		}
		preencherEndereco(await resposta.json());
		aviso.textContent = '';
	} catch (erro) {
		aviso.textContent = erro.message;
	}
}

function preencherEndereco(endereco) {
	['rua', 'bairro', 'cidade'].forEach(function (campo) {
		var entrada = document.querySelector('[name="endereco.' + campo + '"]');
		if (entrada && endereco[campo]) {
			entrada.value = endereco[campo];
		}
	});
}

function validarTelefone(evento) {
	var telefone = document.querySelector('[name="telefone"]');
	if (!telefone) {
		return;
	}

	var digitos = telefone.value.replace(/\D/g, '');
	if (digitos.length >= 8 && digitos.length <= 15) {
		return;
	}

	evento.preventDefault();
	alert('Telefone inválido: ' + telefone.value + '. Use de 8 a 15 dígitos, como (84) 99999-0000.');
}

window.addEventListener('DOMContentLoaded', function () {
	var formulario = document.querySelector('form.formulario');
	if (formulario) {
		formulario.addEventListener('submit', validarTelefone);
	}
});
