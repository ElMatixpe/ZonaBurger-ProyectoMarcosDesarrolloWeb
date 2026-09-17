/* ADMINISTRACIÓN SIMULADA - ZONA BURGER */

/* MOSTRAR INFORMACIÓN DE PEDIDO */

const botonesVerPedido =
    document.querySelectorAll(".btn-outline-dark");

botonesVerPedido.forEach(function (boton) {

    boton.addEventListener("click", function () {

        const fila = boton.closest("tr");

        const numeroPedido =
            fila.cells[0].textContent.trim();

        const cliente =
            fila.cells[1].textContent.trim();

        const total =
            fila.cells[2].textContent.trim();

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

/* AGREGAR PRODUCTO */

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


        const categoria =
            prompt("Ingrese la categoría del producto:");

        if (categoria === null) {
            return;
        }

        if (categoria.trim() === "") {

            alert("Debe ingresar la categoría.");

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
            "Producto: " + nombreProducto + "\n" +
            "Categoría: " + categoria + "\n" +
            "Precio: S/ " + precio
        );

    });

}

/* EDITAR PRODUCTO */

const botonesEditar =
    document.querySelectorAll(".btn-outline-primary");

botonesEditar.forEach(function (boton) {

    boton.addEventListener("click", function () {

        const fila = boton.closest("tr");

        const productoActual =
            fila.cells[0].textContent.trim();

        const precioActual =
            fila.cells[2].textContent.trim();


        const nuevoNombre =
            prompt(
                "Editar nombre del producto:",
                productoActual
            );


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


        fila.cells[0].textContent =
            nuevoNombre;

        fila.cells[2].textContent =
            "S/ " + nuevoPrecio;


        alert(
            "Producto actualizado correctamente."
        );

    });

});

/* ELIMINAR PRODUCTO */

const botonesEliminar =
    document.querySelectorAll(".btn-outline-danger");

botonesEliminar.forEach(function (boton) {

    boton.addEventListener("click", function () {

        const fila = boton.closest("tr");

        const nombreProducto =
            fila.cells[0].textContent.trim();


        const confirmar =
            confirm(
                "¿Está seguro de eliminar el producto:\n\n" +
                nombreProducto + "?"
            );


        if (confirmar) {

            fila.remove();

            alert(
                "Producto eliminado correctamente."
            );

        }

    });

});

/* ACCESOS RÁPIDOS */

const botonesAcceso =
    document.querySelectorAll(".btn-acceso");


botonesAcceso.forEach(function (boton) {

    boton.addEventListener("click", function () {

        const texto =
            boton.querySelector("span").textContent.trim();


        if (texto === "Gestionar pedidos") {

            alert(
                "Módulo de pedidos seleccionado.\n\n" +
                "Aquí se podrán consultar y gestionar " +
                "los pedidos realizados."
            );

        }


        else if (texto === "Gestionar productos") {

            alert(
                "Módulo de productos seleccionado.\n\n" +
                "Aquí se podrán agregar, editar y eliminar " +
                "productos."
            );

        }


        else if (texto === "Ver clientes") {

            alert(
                "Módulo de clientes seleccionado.\n\n" +
                "Aquí se podrá consultar la información " +
                "de los clientes registrados."
            );

        }

    });

});

/* MENSAJE DE BIENVENIDA */

window.addEventListener("load", function () {

    console.log(
        "Panel de Administración de Zona Burger cargado correctamente."
    );

});