package com.cudpast.rivertourapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pasajero {

    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("nombrePasajero")
    private String nombrePasajero;
    @Expose
    @SerializedName("apellidoPasajero")
    private String apellidoPasajero;
    @Expose
    @SerializedName("edadPasajero")
    private String edadPasajero;
    @Expose
    @SerializedName("ocupacionPasajero")
    private String ocupacionPasajero;
    @Expose
    @SerializedName("nacionalidadPasajero")
    private String nacionalidadPasajero;
    @Expose
    @SerializedName("numBoleta")
    private String numBoleta;
    @Expose
    @SerializedName("dniPasajero")
    private String dniPasajero;
    @Expose
    @SerializedName("destinoPasajero")
    private String destinoPasajero;
    @Expose
    @SerializedName("idGuia")
    private String idGuia;

    //Response from Server : fail Retrofit
    @Expose
    @SerializedName("success")
    private Boolean success;
    @Expose
    @SerializedName("message")
    private String message;


    private int img_remove;

    public Pasajero() {

    }

    public Pasajero(String nombrePasajero, String edadPasajero, String ocupacionPasajero, String nacionalidadPasajero, String numBoleta, String dniPasajero, String destinoPasajero) {
        this.nombrePasajero = nombrePasajero;
        this.apellidoPasajero = apellidoPasajero;
        this.edadPasajero = edadPasajero;
        this.ocupacionPasajero = ocupacionPasajero;
        this.nacionalidadPasajero = nacionalidadPasajero;
        this.numBoleta = numBoleta;
        this.dniPasajero = dniPasajero;
        this.destinoPasajero = destinoPasajero;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombrePasajero() {
        return nombrePasajero;
    }

    public void setNombrePasajero(String nombrePasajero) {
        this.nombrePasajero = nombrePasajero;
    }

    public String getApellidoPasajero() {
        return apellidoPasajero;
    }

    public void setApellidoPasajero(String apellidoPasajero) {
        this.apellidoPasajero = apellidoPasajero;
    }

    public String getEdadPasajero() {
        return edadPasajero;
    }

    public void setEdadPasajero(String edadPasajero) {
        this.edadPasajero = edadPasajero;
    }

    public String getOcupacionPasajero() {
        return ocupacionPasajero;
    }

    public void setOcupacionPasajero(String ocupacionPasajero) {
        this.ocupacionPasajero = ocupacionPasajero;
    }

    public String getNacionalidadPasajero() {
        return nacionalidadPasajero;
    }

    public void setNacionalidadPasajero(String nacionalidadPasajero) {
        this.nacionalidadPasajero = nacionalidadPasajero;
    }

    public String getNumBoleta() {
        return numBoleta;
    }

    public void setNumBoleta(String numBoleta) {
        this.numBoleta = numBoleta;
    }

    public String getDniPasajero() {
        return dniPasajero;
    }

    public void setDniPasajero(String dniPasajero) {
        this.dniPasajero = dniPasajero;
    }

    public String getDestinoPasajero() {
        return destinoPasajero;
    }

    public void setDestinoPasajero(String destinoPasajero) {
        this.destinoPasajero = destinoPasajero;
    }

    public String getIdGuia() {
        return idGuia;
    }

    public void setIdGuia(String idGuia) {
        this.idGuia = idGuia;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getImg_remove() {
        return img_remove;
    }

    public void setImg_remove(int img_remove) {
        this.img_remove = img_remove;
    }
}
