// Script Contacto
const formulario = document.getElementById("formularioContacto");

formulario.addEventListener("submit", function (event) {

    event.preventDefault();

    const nombre = document.getElementById("nombre").value;
    const apellido = document.getElementById("apellido").value;
    const correo = document.getElementById("correo").value;
    const telefono = document.getElementById("telefono").value;
    const motivo = document.getElementById("motivo").value;
    const mensaje = document.getElementById("mensaje").value;

    document.getElementById("resumenContacto").innerHTML =
        "<strong>Cliente:</strong> "
        + nombre + " " + apellido
        + "<br>"
        + "<strong>Correo:</strong> "
        + correo
        + "<br>"
        + "<strong>Teléfono:</strong> "
        + telefono
        + "<br>"
        + "<strong>Motivo:</strong> "
        + motivo
        + "<br>"
        + "<strong>Mensaje:</strong> "
        + mensaje;

    const modal = new bootstrap.Modal(
        document.getElementById("modalContacto")
    );

    modal.show();

    formulario.reset();
});