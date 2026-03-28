package Modelo;

public class Usuario {

    private String nombre;
    private String cedula;

    public Usuario(String nombre, String cedula) {
        this.nombre = nombre;
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCedula() {
        return cedula;
    }

    @Override
    public String toString() {
        return "Cedula N: " + cedula + " | Usuario: " + nombre;
    }
}
