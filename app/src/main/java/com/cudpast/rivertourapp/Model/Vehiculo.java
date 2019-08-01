package com.cudpast.rivertourapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Vehiculo {

    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("nombrevehiculo")
    private String nombrevehiculo;
    @Expose
    @SerializedName("marcaVehiculo")
    private String marcaVehiculo;
    @Expose
    @SerializedName("matriculoVehiculo")
    private String matriculoVehiculo;
    @Expose
    @SerializedName("placaVehiculo")
    private String placaVehiculo;

    @Expose
    @SerializedName("success")
    private Boolean success;
    @Expose
    @SerializedName("message")
    private String message;

    public Vehiculo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombrevehiculo() {
        return nombrevehiculo;
    }

    public void setNombrevehiculo(String nombrevehiculo) {
        this.nombrevehiculo = nombrevehiculo;
    }

    public String getMarcaVehiculo() {
        return marcaVehiculo;
    }

    public void setMarcaVehiculo(String marcaVehiculo) {
        this.marcaVehiculo = marcaVehiculo;
    }

    public String getMatriculoVehiculo() {
        return matriculoVehiculo;
    }

    public void setMatriculoVehiculo(String matriculoVehiculo) {
        this.matriculoVehiculo = matriculoVehiculo;
    }

    public String getPlacaVehiculo() {
        return placaVehiculo;
    }

    public void setPlacaVehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
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
}
