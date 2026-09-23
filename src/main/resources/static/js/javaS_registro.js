document.addEventListener("DOMContentLoaded", function () {

    const formulario = document.getElementById("formularioRegistro");
    const btnMostrar = document.getElementById("btnMostrarContrasena");
    const contrasena = document.getElementById("contrasenaRegistro");
    const icono = document.getElementById("iconoContrasena");

    // MOSTRAR Y OCULTAR CONTRASEÑA
    if (btnMostrar && contrasena && icono) {
        btnMostrar.addEventListener("click", function () {
            const mostrar = contrasena.type === "password";
            contrasena.type = mostrar ? "text" : "password";
            icono.classList.toggle("bi-eye", !mostrar);
            icono.classList.toggle("bi-eye-slash", mostrar);
            btnMostrar.setAttribute("aria-label", mostrar ? "Ocultar contraseña" : "Mostrar contraseña");
        });
    }

    if (formulario) {
        formulario.addEventListener("submit", function (evento) {
            if (!formulario.checkValidity()) {
                evento.preventDefault();
                evento.stopPropagation();
            }
            formulario.classList.add("was-validated");
        });
    }
});
