package com.example.zonaburger.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "estado", nullable = false)
    private String estado = "Pendiente";

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> items = new ArrayList<>();

    // Requerido por JPA
    protected Pedido() {
    }

    public Pedido(Usuario usuario) {
        this.usuario = usuario;
    }

    public void agregarItem(Producto producto, int cantidad, double precioUnitario) {
        items.add(new DetallePedido(this, producto, cantidad, precioUnitario));
    }

    public int getId() {
        return id == null ? 0 : id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    /** Nombre del cliente tal como se muestra en el panel de administración. */
    public String getCliente() {
        String nombre = usuario.getNombreCompleto();
        return (nombre == null || nombre.isBlank()) ? usuario.getUsuario() : nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public List<DetallePedido> getItems() {
        return items;
    }

    public double getTotal() {
        double total = 0;
        for (DetallePedido item : items) {
            total += item.getSubtotal();
        }
        return total;
    }
}