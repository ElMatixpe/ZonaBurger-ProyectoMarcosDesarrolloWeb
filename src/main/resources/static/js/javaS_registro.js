// Validación básica del formulario de registro
document.addEventListener("DOMContentLoaded", function () {
    const formulario = document.querySelector("form");
    if (!formulario) return;

    formulario.addEventListener("submit", function (evento) {
        if (!formulario.checkValidity()) {
            evento.preventDefault();
            evento.stopPropagation();
        }
        formulario.classList.add("was-validated");
    });
});
