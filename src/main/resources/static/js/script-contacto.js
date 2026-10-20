// Formulario de contacto
document.addEventListener("DOMContentLoaded", function () {
    // Validacion basica en el navegador 
    const formulario = document.getElementById("formularioContacto");

    if (formulario) {
        formulario.addEventListener("submit", function (evento) {
            if (!formulario.checkValidity()) {
                evento.preventDefault();
                evento.stopPropagation();
            }
            formulario.classList.add("was-validated");
        });
    }

    // Si el mensaje se guardo, se muestra la ventana de confirmacion
    const modal = document.getElementById("modalContacto");

    if (modal && modal.dataset.abrir === "true") {
        bootstrap.Modal.getOrCreateInstance(modal).show();
    }
});