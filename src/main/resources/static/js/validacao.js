function validarTelefone(valor) {
	var digitos = valor.replace(/\D/g, '');
	return digitos.length >= 8 && digitos.length <= 15;
}

function validarFormulario(evento) {
	var telefone = document.querySelector('[name="telefone"]');
	if (telefone && !validarTelefone(telefone.value)) {
		evento.preventDefault();
		alert('Telefone inválido: ' + telefone.value + '. Use de 8 a 15 dígitos, como (84) 99999-0000.');
	}
}

window.addEventListener('DOMContentLoaded', function () {
	var formulario = document.querySelector('form.formulario');
	if (formulario) {
		formulario.addEventListener('submit', validarFormulario);
	}
});
