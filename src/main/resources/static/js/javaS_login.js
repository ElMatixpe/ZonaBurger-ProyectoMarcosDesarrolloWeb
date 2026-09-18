/* MOSTRAR Y OCULTAR CONTRASEÑA */

const btnMostrarContrasena =
    document.getElementById("btnMostrarContrasena");

const contrasena =
    document.getElementById("contrasena");

const iconoContrasena =
    document.getElementById("iconoContrasena");


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