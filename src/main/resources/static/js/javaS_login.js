/* MOSTRAR Y OCULTAR CONTRASEÑA */

const btnMostrarContrasena = document.getElementById("btnMostrarContrasena");
const contrasena = document.getElementById("contrasena");
const iconoContrasena = document.getElementById("iconoContrasena");

btnMostrarContrasena.addEventListener("click", function () {

    if (contrasena.type === "password") {

        contrasena.type = "text";

        iconoContrasena.classList.remove("bi-eye");
        iconoContrasena.classList.add("bi-eye-slash");

    } else {

        contrasena.type = "password";

        iconoContrasena.classList.remove("bi-eye-slash");
        iconoContrasena.classList.add("bi-eye");

    }

});


/* VALIDACIÓN DEL INICIO DE SESIÓN */

const formularioInicioSesion =
    document.getElementById("formularioInicioSesion");

formularioInicioSesion.addEventListener("submit", function (evento) {

    evento.preventDefault();

    const usuario =
        document.getElementById("usuario").value.trim();

    const valorContrasena =
        document.getElementById("contrasena").value.trim();

    const errorUsuario =
        document.getElementById("errorUsuario");

    const errorContrasena =
        document.getElementById("errorContrasena");


    // Limpiar mensajes anteriores

    errorUsuario.textContent = "";
    errorContrasena.textContent = "";

    let formularioValido = true;


    /* VALIDAR USUARIO */

    if (usuario === "") {

        errorUsuario.textContent =
            "Ingrese su usuario.";

        formularioValido = false;

    } else if (usuario.length < 4) {

        errorUsuario.textContent =
            "El usuario debe tener al menos 4 caracteres.";

        formularioValido = false;

    }


    /* VALIDAR CONTRASEÑA */

    if (valorContrasena === "") {

        errorContrasena.textContent =
            "Ingrese su contraseña.";

        formularioValido = false;

    } else if (valorContrasena.length < 6) {

        errorContrasena.textContent =
            "La contraseña debe tener al menos 6 caracteres.";

        formularioValido = false;

    }


    /* REDIRIGIR A LA PÁGINA PRINCIPAL */

    if (formularioValido) {

        window.location.href = "/";

    }

});


/* RECUPERAR CONTRASEÑA */

const btnRecuperarContrasena =
    document.getElementById("btnRecuperarContrasena");

btnRecuperarContrasena.addEventListener("click", function () {

    const correo =
        document.getElementById("correoRecuperacion").value.trim();


    if (correo === "") {

        alert("Ingrese su correo electrónico.");

        return;

    }


    if (!correo.includes("@")) {

        alert("Ingrese un correo electrónico válido.");

        return;

    }


    alert(
        "Se han enviado las instrucciones de recuperación a su correo."
    );

});