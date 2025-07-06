package ec.edu.ups.modelo;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String usuario;
    private String contrasenia;
    private Rol rol;
    private String nombreCompleto;
    private String fechaNacimiento;
    private String celular;
    private String correo;
    private List<PreguntasRespuestas> preguntasRespuestas;


    public Usuario() {
    }

    public Usuario(String usuario, String contrasenia, Rol rol) {
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.rol = rol;
    }

    public Usuario(String usuario, String contrasenia, Rol rol, String nombreCompleto,
                   String fechaNacimiento, String celular, String correo) {
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.nombreCompleto = nombreCompleto;
        this.fechaNacimiento = fechaNacimiento;
        this.celular = celular;
        this.correo = correo;
        this.preguntasRespuestas = new ArrayList<>();
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public List<PreguntasRespuestas> getPreguntasRespuestas() {
        return preguntasRespuestas;
    }

    public void setPreguntasRespuestas(List<PreguntasRespuestas> preguntasRespuestas) {
        this.preguntasRespuestas = preguntasRespuestas;
    }
    public void agregarPreguntas(List<PreguntasRespuestas> preguntasRes){
        preguntasRespuestas.addAll(preguntasRes);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "usuario='" + usuario + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", rol=" + rol +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", celular='" + celular + '\'' +
                ", correo='" + correo + '\'' +
                ", preguntasRespuestas=" + preguntasRespuestas +
                '}';
    }
}
