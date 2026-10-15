/* Marca a página atual no menu e controla o submenu de relatórios. */

function marcarPaginaAtual() {
	var caminho = window.location.pathname;
	var links = document.querySelectorAll(".menu a.menu-item");

	links.forEach(function (link) {
		var destino = new URL(link.href, window.location.origin).pathname;
		if (destino === caminho) {
			link.setAttribute("aria-current", "page");
		}
	});

	var submenu = document.querySelector(".submenu");
	if (submenu && submenu.querySelector('a[href="' + caminho + '"]')) {
		submenu.setAttribute("open", "open");
	}
}

document.addEventListener("click", function (evento) {
	var submenu = document.querySelector(".submenu");
	if (submenu && submenu.hasAttribute("open") && !submenu.contains(evento.target)) {
		submenu.removeAttribute("open");
	}
});

document.addEventListener("keydown", function (evento) {
	if (evento.key !== "Escape") {
		return;
	}
	var submenu = document.querySelector(".submenu");
	if (submenu) {
		submenu.removeAttribute("open");
	}
});

window.addEventListener("DOMContentLoaded", marcarPaginaAtual);
