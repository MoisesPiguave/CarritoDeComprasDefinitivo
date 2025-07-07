package ec.edu.ups.vista.UsuarioView;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.AdministradorView.LoginView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;

public class UsuarioListarView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JTextField TxtUsuario;
    private JButton BtnBuscar;
    private JButton BtnListar;
    private JTable tblUsuario;
    private JLabel lblListar;
    private JLabel lblUser;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler idioma;

    public UsuarioListarView( MensajeInternacionalizacionHandler idioma) {
        this.idioma = idioma;
        setContentPane(panelPrincipal);
        setTitle("Listar Usuarios");
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        modelo = new DefaultTableModel(new Object[]{"Nombre", "Usuario", "Contraseña", "Correo", "Celular", "Fcha de Nacimiento", "Rol"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblUsuario.setModel(modelo);
        cambiarIdioma();
        iconos();
    }
    public void cambiarIdioma() {
        setTitle(idioma.get("usuario.listar.titulo"));
        lblListar.setText(idioma.get("usuario.listar.tituloTabla"));
        lblUser.setText(idioma.get("usuario.listar.usuario"));
        BtnBuscar.setText(idioma.get("usuario.listar.boton.buscar"));
        BtnListar.setText(idioma.get("usuario.listar.boton.listar"));

        modelo.setColumnIdentifiers(new Object[]{
                idioma.get("usuario.listar.columna.nombre"),
                idioma.get("usuario.listar.columna.usuario"),
                idioma.get("usuario.listar.columna.contrasena"),
                idioma.get("usuario.listar.columna.correo"),
                idioma.get("usuario.listar.columna.celular"),
                idioma.get("usuario.listar.columna.fechaNacimiento"),
                idioma.get("usuario.listar.columna.rol")
        });
    }


    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JTextField getTxtUsuario() {
        return TxtUsuario;
    }

    public void setTxtUsuario(JTextField txtUsuario) {
        TxtUsuario = txtUsuario;
    }

    public JButton getBtnBuscar() {
        return BtnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        BtnBuscar = btnBuscar;
    }

    public JButton getBtnListar() {
        return BtnListar;
    }

    public void setBtnListar(JButton btnListar) {
        BtnListar = btnListar;
    }

    public JTable getTblUsuario() {
        return tblUsuario;
    }

    public void setTblUsuario(JTable tblUsuario) {
        this.tblUsuario = tblUsuario;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    public JLabel getLblListar() {
        return lblListar;
    }

    public void setLblListar(JLabel lblListar) {
        this.lblListar = lblListar;
    }

    public JLabel getLblUser() {
        return lblUser;
    }

    public void setLblUser(JLabel lblUser) {
        this.lblUser = lblUser;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void iconos(){
        URL botonBuscar = LoginView.class.getClassLoader().getResource("imagenes/BuscarTodo.svg.png");
        if (botonBuscar != null) {
            ImageIcon icono = new ImageIcon(botonBuscar);
            BtnBuscar.setIcon(icono);
        } else {
            System.err.println("Icono no encontrado");
        }
        URL botonListar = LoginView.class.getClassLoader().getResource("imagenes/ListarTodo.svg.png");
        if (botonListar != null) {
            ImageIcon icono = new ImageIcon(botonListar);
            BtnListar.setIcon(icono);
        } else {
            System.err.println("Icono no encontrado");
        }
    }
}
