package depchemobile.com.bod.checkdeposit.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Rony Díaz
 */
public class Menu implements Serializable {
    private String codigo;
    private String nombre;
    private boolean disponible;
    private ArrayList<Menu> opciones;

    public Menu() {
        setCodigo("");
        setNombre("");
        setOpciones(new ArrayList());
        disponible = true;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public ArrayList<Menu> getOpciones() {
        return opciones;
    }

    public void setOpciones(ArrayList<Menu> opciones) {
        this.opciones = opciones;
    }

    public boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}
