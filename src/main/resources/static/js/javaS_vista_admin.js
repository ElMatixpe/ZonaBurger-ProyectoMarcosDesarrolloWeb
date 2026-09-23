/* ADMINISTRACIÓN - ZONA BURGER */
/* Las tablas de Pedidos, Productos, Clientes y Administradores se llenan
   con datos reales que vienen del controlador (ProductoService,
   UsuarioService y PedidoService). Este script agrega interacciones sobre
   esos datos (ver pedido, agregar/editar/eliminar producto, cliente o
   administrador, buscador y accesos rápidos). Las acciones de
   editar/eliminar/agregar todavía son simulaciones en pantalla: el
   backend no tiene endpoints para persistir esos cambios.        */

/* ============================================================
   ACCESOS RÁPIDOS: llevan directo a cada sección de la página
   ============================================================ */

const botonesAcceso =
    document.querySelectorAll(".btn-acceso");

botonesAcceso.forEach(function (boton) {

    boton.addEventListener("click", function () {

        const destino =
            document.getElementById(boton.dataset.destino);

        if (destino) {
            destino.scrollIntoView({ behavior: "smooth" });
        }

    });

});

/* ============================================================
   PEDIDOS: ver detalle
   ============================================================ */

const botonesVerPedido =
    document.querySelectorAll("#tablaPedidos .btn-outline-dark");

botonesVerPedido.forEach(function (boton) {

    boton.addEventListener("click", function () {

        const fila = boton.closest("tr");

        const numeroPedido =
            fila.cells[4].textContent.trim();

        const cliente =
            fila.cells[1].textContent.trim();

        const total =
            fila.cells[4].textContent.trim();

        const estado =
            fila.cells[3].textContent.trim();


        alert(
            "DETALLE DEL PEDIDO\n\n" +
            "Pedido: " + numeroPedido + "\n" +
            "Cliente: " + cliente + "\n" +
            "Total: " + total + "\n" +
            "Estado: " + estado
        );

    });

});

/* ============================================================
   PRODUCTOS
   ============================================================ */

/* AGREGAR PRODUCTO (simulado en pantalla) */

const botonAgregarProducto =
    document.querySelector(".btn-inicio-sesion");


if (botonAgregarProducto) {

    botonAgregarProducto.addEventListener("click", function () {

        const nombreProducto =
            prompt("Ingrese el nombre del producto:");

        if (nombreProducto === null) {
            return;
        }

        if (nombreProducto.trim() === "") {

            alert("Debe ingresar el nombre del producto.");

            return;
        }


        const descripcion =
            prompt("Ingrese la descripción del producto:");

        if (descripcion === null) {
            return;
        }

        if (descripcion.trim() === "") {

            alert("Debe ingresar la descripción.");

            return;
        }


        const precio =
            prompt("Ingrese el precio del producto:");

        if (precio === null) {
            return;
        }

        if (precio.trim() === "") {

            alert("Debe ingresar el precio.");

            return;
        }


        alert(
            "Producto agregado correctamente.\n\n" +
            "Nota: este panel aún no guarda el producto en el servidor, " +
            "solo lo simula en pantalla.\n\n" +
            "Producto: " + nombreProducto + "\n" +
            "Descripción: " + descripcion + "\n" +
            "Precio: S/ " + precio
        );

    });

}

/* EDITAR / ELIMINAR PRODUCTO (simulado en pantalla) */
/* Columnas: 0 = Producto, 1 = Precio, 2 = Descripción, 3 = Acción */

document.querySelectorAll("#tablaProductos .btn-outline-primary").forEach(function (boton) {

    boton.addEventListener("click", function () {

        const fila = boton.closest("tr");

        const productoActual =
            fila.cells[4].textContent.trim();

        const precioActual =
            fila.cells[1].textContent.trim();


        const nuevoNombre =
            prompt("Editar nombre del producto:", productoActual);

        if (nuevoNombre === null) {
            return;
        }

        if (nuevoNombre.trim() === "") {
            alert("El nombre no puede estar vacío.");
            return;
        }


        const nuevoPrecio =
            prompt(
                "Editar precio del producto:",
                precioActual.replace("S/", "").trim()
            );

        if (nuevoPrecio === null) {
            return;
        }

        if (nuevoPrecio.trim() === "") {
            alert("El precio no puede estar vacío.");
            return;
        }


        fila.cells[4].textContent = nuevoNombre;
        fila.cells[1].textContent = "S/ " + nuevoPrecio;

        alert(
            "Producto actualizado en pantalla.\n\n" +
            "Nota: el cambio todavía no se guarda en el servidor."
        );

    });

});

document.querySelectorAll("#tablaProductos .btn-outline-danger").forEach(function (boton) {

    boton.addEventListener("click", function () {

        const fila = boton.closest("tr");

        const nombreProducto =
            fila.cells[4].textContent.trim();

        const confirmar =
            confirm("¿Está seguro de eliminar el producto:\n\n" + nombreProducto + "?");

        if (confirmar) {

            fila.remove();

            alert(
                "Producto eliminado de la vista.\n\n" +
                "Nota: el cambio todavía no se guarda en el servidor."
            );

        }

    });

});

/* ============================================================
   CLIENTES Y ADMINISTRADORES
   Ambas tablas comparten la misma estructura de columnas:
   0 = Nombre, 1 = Correo, 2 = Usuario, 3 = Rol, 4 = Acción
   ============================================================ */

function habilitarEdicionUsuarios(idTabla, etiqueta) {

    document.querySelectorAll("#" + idTabla + " .btn-outline-primary").forEach(function (boton) {

        boton.addEventListener("click", function () {

            const fila = boton.closest("tr");

            const usuarioActual =
                fila.cells[2].textContent.trim();

            const nuevoUsuario =
                prompt("Editar nombre de usuario de " + etiqueta + ":", usuarioActual);

            if (nuevoUsuario === null) {
                return;
            }

            if (nuevoUsuario.trim() === "") {
                alert("El nombre de usuario no puede estar vacío.");
                return;
            }

            fila.cells[2].textContent = nuevoUsuario;

            alert(
                etiqueta + " actualizado en pantalla.\n\n" +
                "Nota: el cambio todavía no se guarda en el servidor."
            );

        });

    });

    document.querySelectorAll("#" + idTabla + " .btn-outline-danger").forEach(function (boton) {

        boton.addEventListener("click", function () {

            const fila = boton.closest("tr");

            const usuarioActual =
                fila.cells[2].textContent.trim();

            const confirmar =
                confirm("¿Está seguro de eliminar a " + etiqueta.toLowerCase() + ":\n\n" + usuarioActual + "?");

            if (confirmar) {

                fila.remove();

                alert(
                    etiqueta + " eliminado de la vista.\n\n" +
                    "Nota: el cambio todavía no se guarda en el servidor."
                );

            }

        });

    });

}

habilitarEdicionUsuarios("tablaClientes", "Cliente");
habilitarEdicionUsuarios("tablaAdministradores", "Administrador");

/* ============================================================
   BUSCADORES POR NOMBRE (uno por cada tabla)
   ============================================================ */

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

habilitarBusqueda("buscarPedidos", "tablaPedidos", 1);
habilitarBusqueda("buscarProductos", "tablaProductos", 0);
habilitarBusqueda("buscarClientes", "tablaClientes", 2);
habilitarBusqueda("buscarAdministradores", "tablaAdministradores", 2);

/* MENSAJE DE BIENVENIDA */

window.addEventListener("load", function () {

    console.log(
        "Panel de Administración de Zona Burger cargado con datos reales de los servicios."
    );

});
