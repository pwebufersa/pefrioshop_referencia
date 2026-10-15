function buscarCep() {
	var campoCep = document.getElementById('cep');
	var aviso = document.getElementById('aviso-cep');
	var cep = campoCep.value.replace(/\D/g, '');

	if (cep.length !== 8) {
		aviso.textContent = 'Informe um CEP com 8 dígitos.';
		return;
	}

	aviso.textContent = 'Consultando o CEP...';

	fetch('/api/cep/' + cep)
		.then(function (resposta) {
			if (!resposta.ok) {
				throw new Error('CEP não encontrado.');
			}
			return resposta.json();
		})
		.then(function (endereco) {
			preencherEndereco(endereco);
			aviso.textContent = '';
		})
		.catch(function (erro) {
			aviso.textContent = erro.message;
		});
}

function preencherEndereco(endereco) {
	definirValor('endereco.rua', endereco.rua);
	definirValor('endereco.bairro', endereco.bairro);
	definirValor('endereco.cidade', endereco.cidade);
}

function definirValor(nome, valor) {
	var campo = document.querySelector('[name="' + nome + '"]');
	if (campo && valor) {
		campo.value = valor;
	}
}
