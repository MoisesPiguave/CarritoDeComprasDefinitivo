package ec.edu.ups.vista.CarritoView;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.AdministradorView.LoginView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class CarritoListarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTable tblProductos;
    private JTextField txtCarrito;
    private JButton btnMostrarDetalle;
    private JButton btnMostrar;
    private JButton btnListar;
    private JLabel lblCodigo;
    private JLabel lblListar;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler idioma;

    public CarritoListarView(MensajeInternacionalizacionHandler idioma) {
        super(idioma.get("carrito.listar.titulo"), true, true, false, true);
        this.idioma = idioma;
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);

        modelo = new DefaultTableModel(new Object[]{
                idioma.get("carrito.listar.columna.codigo"),
                idioma.get("carrito.listar.columna.fecha"),
                idioma.get("carrito.listar.columna.subtotal"),
                idioma.get("carrito.listar.columna.iva"),
                idioma.get("carrito.listar.columna.total")
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblProductos.setModel(modelo);

        cambiarIdioma();
        iconos();
    }

    public void cambiarIdioma() {
        setTitle(idioma.get("carrito.listar.titulo"));
        lblListar.setText(idioma.get("carrito.listar.etiqueta"));
        lblCodigo.setText(idioma.get("carrito.listar.codigo"));
        btnMostrar.setText(idioma.get("carrito.listar.boton.mostrar"));
        btnMostrarDetalle.setText(idioma.get("carrito.listar.boton.detalle"));
        btnListar.setText(idioma.get("carrito.listar.boton.listar"));

        modelo.setColumnIdentifiers(new Object[]{
                idioma.get("carrito.listar.columna.codigo"),
                idioma.get("carrito.listar.columna.fecha"),
                idioma.get("carrito.listar.columna.subtotal"),
                idioma.get("carrito.listar.columna.iva"),
                idioma.get("carrito.listar.columna.total")
        });
    }

    public void cargarDatos(List<Carrito> carritos) {
        modelo.setRowCount(0);
        Locale locale = idioma.getLocale();
        for (Carrito carrito : carritos) {
            String fecha = FormateadorUtils.formatearFecha(carrito.getFechaCreacion().getTime(), locale);
            modelo.addRow(new Object[]{
                    carrito.getCodigo(),
                    fecha,
                    FormateadorUtils.formatearMoneda(carrito.calcularTotal(), locale),
                    FormateadorUtils.formatearMoneda(carrito.calcularIVA(), locale),
                    FormateadorUtils.formatearMoneda(carrito.calcularTotalConIVA(), locale)
            });
        }
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void limpiarCampos() {
        txtCarrito.setText("");
        modelo.setNumRows(0);
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JTable getTblProductos() {
        return tblProductos;
    }

    public void setTblProductos(JTable tblProductos) {
        this.tblProductos = tblProductos;
    }

    public JTextField getTxtCarrito() {
        return txtCarrito;
    }

    public void setTxtCarrito(JTextField txtCarrito) {
        this.txtCarrito = txtCarrito;
    }

    public JButton getBtnMostrarDetalle() {
        return btnMostrarDetalle;
    }

    public void setBtnMostrarDetalle(JButton btnMostrarDetalle) {
        this.btnMostrarDetalle = btnMostrarDetalle;
    }

    public JButton getBtnMostrar() {
        return btnMostrar;
    }

    public void setBtnMostrar(JButton btnMostrar) {
        this.btnMostrar = btnMostrar;
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public void setBtnListar(JButton btnListar) {
        this.btnListar = btnListar;
    }

    public JLabel getLblCodigo() {
        return lblCodigo;
    }

    public void setLblCodigo(JLabel lblCodigo) {
        this.lblCodigo = lblCodigo;
    }

    public JLabel getLblListar() {
        return lblListar;
    }

    public void setLblListar(JLabel lblListar) {
        this.lblListar = lblListar;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    public MensajeInternacionalizacionHandler getMi() {
        return idioma;
    }

    public void setMi(MensajeInternacionalizacionHandler idioma) {
        this.idioma = idioma;
    }

    public void iconos() {
        URL botonListar = LoginView.class.getClassLoader().getResource("imagenes/ListarTodo.svg.png");
        if (botonListar != null) {
            ImageIcon icono = new ImageIcon(botonListar);
            btnListar.setIcon(icono);
        } else {
            System.err.println("Icono no encontrado");
        }
        URL botonMostrar = LoginView.class.getClassLoader().getResource("imagenes/BuscarTodo.svg.png");
        if (botonMostrar != null) {
            ImageIcon icono = new ImageIcon(botonMostrar);
            btnMostrar.setIcon(icono);
        } else {
            System.err.println("Icono no encontrado");
        }
        URL botonMostrarDetalle = LoginView.class.getClassLoader().getResource("imagenes/Terminar.svg.png");
        if (botonMostrarDetalle != null) {
            ImageIcon icono = new ImageIcon(botonMostrarDetalle);
            btnMostrarDetalle.setIcon(icono);
        } else {
            System.err.println("Icono no encontrado");
        }
    }
}
