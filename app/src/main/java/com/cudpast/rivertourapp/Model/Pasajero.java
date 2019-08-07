package com.cudpast.rivertourapp.Model;

public class Pasajero {
    private String id;
    private String nombre;
    private String apellido;
    private String edad;
    private String ocupacion;
    private String nacionalidad;
    private String numBoleta;
    private String dni;
    private String destino;

    public Pasajero() {

    }

    public Pasajero(String nombre, String edad, String ocupacion, String nacionalidad, String numBoleta, String dni, String destino) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.ocupacion = ocupacion;
        this.nacionalidad = nacionalidad;
        this.numBoleta = numBoleta;
        this.dni = dni;
        this.destino = destino;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getNumBoleta() {
        return numBoleta;
    }

    public void setNumBoleta(String numBoleta) {
        this.numBoleta = numBoleta;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }
}
