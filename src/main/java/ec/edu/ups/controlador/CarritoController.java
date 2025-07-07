package ec.edu.ups.controlador;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.*;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.CarritoView.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class CarritoController {
    private final CarritoDAO carritoDAO;
    private final CarritoAnadirView carritoAnadirView;
    private final Carrito carritoActual;
    private final Usuario usuario;
    private final ProductoDAO productoDAO;
    private CarritoDetalleView carritoDetalleView;
    private final CarritoListarView carritoListarView;
    private final CarritoModificarView carritoModificarView;
    private final CarritoEliminarView carritoEliminarView;
    private final MensajeInternacionalizacionHandler idioma;

    public CarritoController(CarritoDAO carritoDAO, CarritoAnadirView carritoAnadirView,
                             ProductoDAO productoDAO, CarritoListarView carritoListarView,
                             Usuario usuario, CarritoModificarView carritoModificarView,
                             CarritoEliminarView carritoEliminarView,
                             MensajeInternacionalizacionHandler idioma) {
        this.carritoDAO = carritoDAO;
        this.carritoAnadirView = carritoAnadirView;
        this.carritoActual = new Carrito();
        this.productoDAO = productoDAO;
        this.usuario = usuario;
        this.carritoListarView = carritoListarView;
        this.carritoModificarView = carritoModificarView;
        this.carritoEliminarView = carritoEliminarView;
        this.idioma = idioma;
        configurarEventos();
    }
    // creamos el los eventos de los botones
    public void configurarEventos() {
        carritoAnadirView.getBtnAnadir().addActionListener(e -> agregarProductoAlCarrito());
        carritoAnadirView.getBtnGuardar().addActionListener(e -> guardarCarrito());
        carritoAnadirView.getBtnLimpiar().addActionListener(e -> vaciarCarrito());

        carritoListarView.getBtnMostrar().addActionListener(e -> buscarCarritos());
        carritoListarView.getBtnListar().addActionListener(e -> mostrarLosCarritos());
        carritoListarView.getBtnMostrarDetalle().addActionListener(e -> mostrarDetalle());

        carritoModificarView.getBtnBuscar().addActionListener(e -> buscarCarritoParaModificar());
        carritoModificarView.getBtnActualizar().addActionListener(e -> modificarCarrito());
        carritoModificarView.getBtnLimpiar().addActionListener(e -> carritoModificarView.limpiarCampos());

        carritoEliminarView.getBtnBuscar().addActionListener(e -> buscarCarritoParaEliminar());
        carritoEliminarView.getBtnEliminar().addActionListener(e -> eliminarCarrito());
    }
    // metodo para eliminar el carrito
    private void eliminarCarrito() {
        String textoCodigo = carritoEliminarView.getTxtCodigo().getText().trim();

        if (textoCodigo.isEmpty() || !textoCodigo.chars().allMatch(Character::isDigit)) {
            carritoEliminarView.mostrarMensaje(idioma.get("carrito.eliminar.error.numero"));
            return;
        }

        int codigo = Integer.parseInt(textoCodigo);
        carritoDAO.eliminar(codigo);
        carritoEliminarView.mostrarMensaje(idioma.get("carrito.eliminar.exito"));
        carritoEliminarView.limpiarCampos();
    }

    // metodo para buscar el carrito por codigo y poder eliminar
    private void buscarCarritoParaEliminar() {
        String codigo = carritoEliminarView.getTxtCodigo().getText();
        if (!codigo.isEmpty()) {
            int codigoCarrito = Integer.parseInt(codigo);
            Carrito carrito = carritoDAO.buscarPorCodigo(codigoCarrito);
            if (carrito != null) {
                carritoEliminarView.cargarDatos(carrito);
                carritoEliminarView.setVisible(true);
                carritoEliminarView.moveToFront();
                carritoEliminarView.requestFocusInWindow();
            } else {
                carritoEliminarView.mostrarMensaje(idioma.get("carrito.no.encontrado") + codigoCarrito);
                carritoEliminarView.limpiarCampos();
            }
        } else {
            carritoEliminarView.mostrarMensaje(idioma.get("mensaje.codigo.invalido"));
        }
    }
    // metodo para eliminar carrito
    private void modificarCarrito() {
        int filaSeleccionada = carritoModificarView.getTblProductos().getSelectedRow();
        if (filaSeleccionada == -1) {
            carritoModificarView.mostrarMensaje(idioma.get("mensaje.seleccionar.producto"));
            return;
        }

        String textoCantidad = carritoModificarView.getCbxCantidad().getSelectedItem().toString();
        if (!textoCantidad.matches("\\d+")) {
            carritoModificarView.mostrarMensaje(idioma.get("mensaje.numero.invalido"));
            return;
        }

        int cantidad = Integer.parseInt(textoCantidad);
        if (cantidad < 1 || cantidad > 20) {
            carritoModificarView.mostrarMensaje(idioma.get("mensaje.cantidad.rango"));
            return;
        }

        int codigoCarrito = Integer.parseInt(carritoModificarView.getTxtCarrito().getText());
        Carrito carrito = carritoDAO.buscarPorCodigo(codigoCarrito);

        if (carrito == null) {
            carritoModificarView.mostrarMensaje(idioma.get("carrito.no.encontrado"));
            return;
        }

        int codigoProducto = (int) carritoModificarView.getModelo().getValueAt(filaSeleccionada, 0);

        for (ItemCarrito item : carrito.obtenerItems()) {
            if (item.getProducto().getCodigo() == codigoProducto) {
                item.setCantidad(cantidad);
                break;
            }
        }

        carritoModificarView.cargarDatos(carrito);
        Locale locale = idioma.getLocale();
        carritoModificarView.getTxtSubtotal().setText(FormateadorUtils.formatearMoneda(carrito.calcularTotal(), locale));
        carritoModificarView.getTxtIVA().setText(FormateadorUtils.formatearMoneda(carrito.calcularIVA(), locale));
        carritoModificarView.getTxtTotal().setText(FormateadorUtils.formatearMoneda(carrito.calcularTotalConIVA(), locale));
        carritoModificarView.mostrarMensaje(idioma.get("mensaje.cantidad.actualizada"));
    }
    // metodo para buscar
    private void buscarCarritoParaModificar() {
        String codigo = carritoModificarView.getTxtCarrito().getText();
        if (!codigo.isEmpty()) {
            int codigoCarrito = Integer.parseInt(codigo);
            Carrito carrito = carritoDAO.buscarPorCodigo(codigoCarrito);
            if (carrito != null) {
                Locale locale = idioma.getLocale();
                carritoModificarView.getTxtSubtotal().setText(FormateadorUtils.formatearMoneda(carrito.calcularTotal(), locale));
                carritoModificarView.getTxtIVA().setText(FormateadorUtils.formatearMoneda(carrito.calcularIVA(), locale));
                carritoModificarView.getTxtTotal().setText(FormateadorUtils.formatearMoneda(carrito.calcularTotalConIVA(), locale));

                carritoModificarView.cargarDatos(carrito);
                carritoModificarView.setVisible(true);
                carritoModificarView.moveToFront();
                carritoModificarView.requestFocusInWindow();
            } else {
                carritoModificarView.mostrarMensaje(idioma.get("carrito.no.encontrado") + codigoCarrito);
                carritoModificarView.limpiarCampos();
            }
        } else {
            carritoModificarView.mostrarMensaje(idioma.get("mensaje.codigo.invalido"));
        }
    }
// metodo para mostrar detalles del carrito
    private void mostrarDetalle() {
        int filaSeleccionada = carritoListarView.getTblProductos().getSelectedRow();

        if (filaSeleccionada != -1) {
            int codigoCarrito = (int) carritoListarView.getModelo().getValueAt(filaSeleccionada, 0);
            Carrito carrito = carritoDAO.buscarPorCodigo(codigoCarrito);

            if (carrito != null) {
                if (carritoDetalleView == null || carritoDetalleView.isClosed()) {
                    carritoDetalleView = new CarritoDetalleView(idioma);
                    carritoListarView.getDesktopPane().add(carritoDetalleView);
                }

                carritoDetalleView.cargarDatos(carrito);
                Locale locale = idioma.getLocale();
                carritoDetalleView.getTxtSubtotal().setText(FormateadorUtils.formatearMoneda(carrito.calcularTotal(), locale));
                carritoDetalleView.getTxtIVA().setText(FormateadorUtils.formatearMoneda(carrito.calcularIVA(), locale));
                carritoDetalleView.getTxtTotal().setText(FormateadorUtils.formatearMoneda(carrito.calcularTotalConIVA(), locale));

                carritoDetalleView.setVisible(true);
                carritoDetalleView.moveToFront();
                carritoDetalleView.requestFocusInWindow();
            } else {
                JOptionPane.showMessageDialog(carritoListarView, idioma.get("carrito.no.encontrado"));
            }
        } else {
            JOptionPane.showMessageDialog(carritoListarView, idioma.get("mensaje.seleccionar.carrito"));
        }
    }


    private void guardarCarrito() {
        if (carritoActual.estaVacio()) {
            carritoAnadirView.mostrarMensaje(idioma.get("carrito.vacio"));
            return;
        }
        carritoActual.setUsuario(usuario);
        carritoActual.setFechaCreacion(new GregorianCalendar());
        carritoDAO.crear(carritoActual);
        carritoAnadirView.mostrarMensaje(idioma.get("carrito.guardado") + carritoActual.getCodigo());

        carritoActual.LimpiarCarrito();
        agregarProductoAlCarrito();
        cargarProductos();
        carritoAnadirView.limpiarCampos();
    }

    private void agregarProductoAlCarrito() {
        int codigoProducto = Integer.parseInt(carritoAnadirView.getTxtCodigo().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigoProducto);
        int cantidad = Integer.parseInt(carritoAnadirView.getCbxCantidad().getSelectedItem().toString());
        carritoActual.agregarProducto(producto, cantidad);

        cargarProductos();
        mostrarTotal();
    }

    private void cargarProductos() {
        List<ItemCarrito> items = carritoActual.obtenerItems();
        DefaultTableModel modelo = (DefaultTableModel) carritoAnadirView.getTblProductos().getModel();
        modelo.setNumRows(0);
        for (ItemCarrito item : items) {
            Locale locale = idioma.getLocale();
            modelo.addRow(new Object[]{item.getProducto().getCodigo(),
                    item.getProducto().getNombre(),
                    FormateadorUtils.formatearMoneda(item.getProducto().getPrecio(), locale),
                    item.getCantidad(),
                    item.getProducto().getPrecio() * item.getCantidad()});
        }
    }

    private void mostrarTotal() {
        Locale locale = idioma.getLocale();
        carritoAnadirView.getTxtSubtotal().setText(FormateadorUtils.formatearMoneda(carritoActual.calcularTotal(), locale));
        carritoAnadirView.getTxtIva().setText(FormateadorUtils.formatearMoneda(carritoActual.calcularIVA(), locale));
        carritoAnadirView.getTxtTotal().setText(FormateadorUtils.formatearMoneda(carritoActual.calcularTotalConIVA(), locale));
    }

    public void vaciarCarrito() {
        carritoActual.LimpiarCarrito();
        cargarProductos();
        mostrarTotal();
    }

    public void buscarCarritos() {
        String codigo = carritoListarView.getTxtCarrito().getText();
        if (!codigo.isEmpty()) {
            int codigoCarrito = Integer.parseInt(codigo);
            Carrito carrito = carritoDAO.buscarPorCodigo(codigoCarrito);
            if (carrito != null) {
                carritoListarView.cargarDatos(List.of(carrito));
                carritoListarView.setVisible(true);
                carritoListarView.moveToFront();
                carritoListarView.requestFocusInWindow();
            } else {
                carritoListarView.mostrarMensaje(idioma.get("carrito.no.encontrado") + codigoCarrito);
                carritoListarView.limpiarCampos();
            }
        } else {
            carritoListarView.mostrarMensaje(idioma.get("mensaje.codigo.invalido"));
        }
    }

    public void mostrarLosCarritos() {
        List<Carrito> carritos = carritoDAO.listarTodos();
        if (carritos.isEmpty()) {
            carritoListarView.mostrarMensaje(idioma.get("carrito.lista.vacia"));
            carritoListarView.limpiarCampos();
        } else {
            carritoListarView.cargarDatos(carritos);
        }
    }
}
