/* ADMINISTRACION ZONA BURGER */
/* ACCESOS RAPIDOS */
const botonesAcceso = document.querySelectorAll(".btn-acceso");

botonesAcceso.forEach(function (boton) {

    boton.addEventListener("click", function () {

        const destino =
            document.getElementById(boton.dataset.destino);

        if (destino) {
            destino.scrollIntoView({ behavior: "smooth" });
        }
    });
});

const baseAdmin = document.querySelector("main.contenedor-administracion").dataset.base || "/vista-admin";

function abrirModal(idModal) {

    bootstrap.Modal.getOrCreateInstance(document.getElementById(idModal)).show();

}

/* MOSTRAR O OCULTAR CONTRASEÑA */
document.querySelectorAll(".btn-ojo-contrasena").forEach(function (boton) {

    boton.addEventListener("click", function () {

        const campo = document.getElementById(boton.dataset.objetivo);
        const icono = boton.querySelector("i");
        const mostrar = campo.type === "password";

        campo.type = mostrar ? "text" : "password";
        icono.classList.toggle("bi-eye", !mostrar);
        icono.classList.toggle("bi-eye-slash", mostrar);
    });
});

// Al cerrar un modal con contraseña, vuelve a quedar oculta
document.querySelectorAll(".modal").forEach(function (modal) {

    modal.addEventListener("hidden.bs.modal", function () {

        modal.querySelectorAll(".btn-ojo-contrasena").forEach(function (boton) {

            const campo = document.getElementById(boton.dataset.objetivo);
            const icono = boton.querySelector("i");

            campo.type = "password";
            icono.classList.add("bi-eye");
            icono.classList.remove("bi-eye-slash");
        });
    });
});

/* EN PEDIDOS PARA VER DETALLE */
document.querySelectorAll(".btn-ver-pedido").forEach(function (boton) {

    boton.addEventListener("click", function () {

        document.getElementById("detalleNumeroPedido").textContent = "PE" + boton.dataset.id.padStart(3, "0");
        document.getElementById("detalleClientePedido").textContent = boton.dataset.cliente;
        document.getElementById("detalleEstadoPedido").textContent = boton.dataset.estado;
        document.getElementById("detalleTotalPedido").textContent = boton.dataset.total;

        const cuerpo = document.getElementById("detalleItemsPedido");
        cuerpo.innerHTML = "";

        boton.closest("td").querySelectorAll(".item-detalle").forEach(function (item) {

            const fila = document.createElement("tr");

            const celdaNombre = document.createElement("td");
            celdaNombre.textContent = item.dataset.nombre;

            const celdaCantidad = document.createElement("td");
            celdaCantidad.className = "text-center";
            celdaCantidad.textContent = item.dataset.cantidad;

            const celdaSubtotal = document.createElement("td");
            celdaSubtotal.className = "text-end";
            celdaSubtotal.textContent = item.dataset.subtotal;

            fila.append(celdaNombre, celdaCantidad, celdaSubtotal);
            cuerpo.appendChild(fila);
        });
        abrirModal("modalPedidoDetalle");
    });
});

/* EN PEDIDOS PARA CAMBIAR ESTADO */
document.querySelectorAll(".btn-editar-pedido").forEach(function (boton) {

    boton.addEventListener("click", function () {

        document.getElementById("estadoNumeroPedido").textContent = "PE" + boton.dataset.id.padStart(3, "0");
        document.getElementById("estadoClientePedido").value = boton.dataset.cliente;
        document.getElementById("estadoPedido").value = boton.dataset.estado;

        document.getElementById("formularioPedidoEstado").action =
            baseAdmin + "/pedidos/" + boton.dataset.id + "/estado";

        abrirModal("modalPedidoEstado");
    });
});

/* EN PRODUCTOS AGREGAR Y EDITAR */
const formularioProducto = document.getElementById("formularioProducto");

document.getElementById("btnAgregarProducto").addEventListener("click", function () {

    formularioProducto.reset();
    formularioProducto.action = baseAdmin + "/productos/agregar";
    document.getElementById("tituloModalProducto").textContent = "Agregar producto";

    abrirModal("modalProducto");

});

document.querySelectorAll(".btn-editar-producto").forEach(function (boton) {

    boton.addEventListener("click", function () {

        document.getElementById("productoCategoria").value = boton.dataset.categoria;
        document.getElementById("productoNombre").value = boton.dataset.nombre;
        document.getElementById("productoDescripcion").value = boton.dataset.descripcion;
        document.getElementById("productoPrecio").value = boton.dataset.precio;
        document.getElementById("productoImagen").value = boton.dataset.imagen;

        formularioProducto.action = baseAdmin + "/productos/" + boton.dataset.id + "/editar";
        document.getElementById("tituloModalProducto").textContent = "Editar producto";

        abrirModal("modalProducto");

    });

});

/* CLIENTES Y ADMINISTRADORES: EDITAR */
document.querySelectorAll(".btn-editar-usuario").forEach(function (boton) {

    boton.addEventListener("click", function () {

        document.getElementById("usuarioNombre").value = boton.dataset.nombre;
        document.getElementById("usuarioCorreo").value = boton.dataset.correo;
        document.getElementById("usuarioUsuario").value = boton.dataset.usuario;
        document.getElementById("usuarioContrasena").value = "";
        document.getElementById("usuarioRol").value = boton.dataset.rol;

        const esAdmin = boton.dataset.rol === "ADMINISTRADOR";
        document.getElementById("tituloModalUsuario").textContent =
            esAdmin ? "Editar administrador" : "Editar cliente";

        document.getElementById("formularioUsuario").action =
            baseAdmin + "/usuarios/" + boton.dataset.id + "/editar";

        abrirModal("modalUsuario");
    });
});

/* ELIMINAR (productos, clientes y administradores) */
document.querySelectorAll(".btn-eliminar").forEach(function (boton) {

    boton.addEventListener("click", function () {

        document.getElementById("textoEliminar").textContent = boton.dataset.texto;
        document.getElementById("formularioEliminar").action = boton.dataset.url;
        abrirModal("modalEliminar");
    });
});

/* BUSCADORES POR ID*/
function habilitarBusqueda(idInput, idTabla, indiceColumna) {
    const input = document.getElementById(idInput);
    const tabla = document.getElementById(idTabla);

    if (!input || !tabla) {
        return;
    }

    const mensajeSinResultados =
        document.querySelector('.mensaje-sin-resultados[data-tabla="' + idTabla + '"]');

    input.addEventListener("input", function () {

        const termino = input.value.trim().toLowerCase();

        const filas = tabla.querySelectorAll("tbody tr:not(.fila-vacia)");

        let coincidencias = 0;

        filas.forEach(function (fila) {

            const celda = fila.cells[indiceColumna];

            if (!celda) {
                return;
            }

            const texto = celda.textContent.trim().toLowerCase();
            const coincide = texto.includes(termino);

            fila.style.display = coincide ? "" : "none";

            if (coincide) {
                coincidencias++;
            }

        });

        if (mensajeSinResultados) {

            const sinResultados =
                termino !== "" && coincidencias === 0 && filas.length > 0;

            mensajeSinResultados.classList.toggle("d-none", !sinResultados);

        }
    });
}

habilitarBusqueda("buscarPedidos", "tablaPedidos", 0);
habilitarBusqueda("buscarProductos", "tablaProductos", 0);
habilitarBusqueda("buscarProductos", "tablaProductosInactivos", 0);
habilitarBusqueda("buscarClientes", "tablaClientes", 0);
habilitarBusqueda("buscarAdministradores", "tablaAdministradores", 0);

window.addEventListener("load", function () {
    console.log(
        "Panel de Administración de Zona Burger cargado con datos reales de los servicios."
    );
});