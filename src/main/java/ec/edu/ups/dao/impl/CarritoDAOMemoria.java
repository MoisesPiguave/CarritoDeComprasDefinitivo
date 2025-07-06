package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CarritoDAOMemoria implements CarritoDAO {

    private List<Carrito> carritos;

    public CarritoDAOMemoria() {
        this.carritos = new ArrayList<Carrito>();
    }

    @Override
    public void crear(Carrito carrito) {
        carrito.setCodigo(carritos.size() + 1);
        Carrito copia = carrito.copiar();
        carritos.add(copia);
        System.out.println("Carrito creado, codigo del carrito : : " + copia.getCodigo());
    }

    @Override
    public Carrito buscarPorCodigo(int codigo) {
        for (Carrito carrito : carritos) {
            if (carrito.getCodigo() == codigo) {
                return carrito;
            }
        }
        return null;
    }

    @Override
    public void actualizar(Carrito carrito) {
        for (int i = 0; i < carritos.size(); i++) {
            if (carritos.get(i).getCodigo() == carrito.getCodigo()) {
                carritos.set(i, carrito);
                break;
            }
        }
    }

    @Override
    public void eliminar(int codigo) {
        Iterator<Carrito> iterator = carritos.iterator();
        while (iterator.hasNext()) {
            Carrito carrito = iterator.next();
            if (carrito.getCodigo() == codigo) {
                iterator.remove();
            }
        }
    }

    @Override
    public List<Carrito> listarTodos() {
        return carritos;
    }

    @Override
    public List<Carrito> buscarPorUsuario(Usuario usuario) {
        List<Carrito> carritosUsuario = new ArrayList<>();
        for (Carrito carrito : carritos) {
            if (carrito.getUsuario().getUsuario().equals(usuario.getUsuario())) {
                carritosUsuario.add(carrito);
            }
        }
        return carritosUsuario;
    }
}