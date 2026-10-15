function validarTelefone(valor) {
	var digitos = valor.replace(/\D/g, '');
	return digitos.length === 10 || digitos.length === 11;
}

function validarFormulario(evento) {
	var telefone = document.querySelector('[name="telefone"]');
	if (telefone && !validarTelefone(telefone.value)) {
		evento.preventDefault();
		alert('Telefone inválido: ' + telefone.value + '. Informe DDD e número (10 ou 11 dígitos).');
	}
}

window.addEventListener('DOMContentLoaded', function () {
	var formulario = document.querySelector('form.formulario');
	if (formulario) {
		formulario.addEventListener('submit', validarFormulario);
	}
});
