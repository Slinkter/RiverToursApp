package com.cudpast.rivertourapp.Model;

public class Guia {

    private String id;
    private String idVehiculo;
    private String guia;
    private String fecha;
    private String idChofer1;
    private String idChofer2;
    private String idDestino;
    private String idListaPasajero;

    public Guia() {
    }

    public Guia(String id, String idVehiculo, String guia, String fecha, String idChofer1, String idChofer2, String idDestino, String idListaPasajero) {
        this.id = id;
        this.idVehiculo = idVehiculo;
        this.guia = guia;
        this.fecha = fecha;
        this.idChofer1 = idChofer1;
        this.idChofer2 = idChofer2;
        this.idDestino = idDestino;
        this.idListaPasajero = idListaPasajero;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(String idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getGuia() {
        return guia;
    }

    public void setGuia(String guia) {
        this.guia = guia;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getIdChofer1() {
        return idChofer1;
    }

    public void setIdChofer1(String idChofer1) {
        this.idChofer1 = idChofer1;
    }

    public String getIdChofer2() {
        return idChofer2;
    }

    public void setIdChofer2(String idChofer2) {
        this.idChofer2 = idChofer2;
    }

    public String getIdDestino() {
        return idDestino;
    }

    public void setIdDestino(String idDestino) {
        this.idDestino = idDestino;
    }

    public String getIdListaPasajero() {
        return idListaPasajero;
    }

    public void setIdListaPasajero(String idListaPasajero) {
        this.idListaPasajero = idListaPasajero;
    }
}
