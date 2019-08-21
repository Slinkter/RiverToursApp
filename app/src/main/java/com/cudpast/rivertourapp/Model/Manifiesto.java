package com.cudpast.rivertourapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Manifiesto {

    @Expose
    @SerializedName("idGuiaMani")
    private String idGuiaMani;
    @Expose
    @SerializedName("fechaMani")
    private String fechaMani;
    @Expose
    @SerializedName("destinoMani")
    private String destinoMani;
    @Expose
    @SerializedName("vehiculoMani")
    private String vehiculoMani;
    @Expose
    @SerializedName("choferMani")
    private String choferMani;

    private int sync_status;

    //Response from Server : fail Retrofit
    @Expose
    @SerializedName("success")
    private Boolean success;
    @Expose
    @SerializedName("message")
    private String message;

    public Manifiesto() {

    }

    public String getIdGuiaMani() {
        return idGuiaMani;
    }

    public void setIdGuiaMani(String idGuiaMani) {
        this.idGuiaMani = idGuiaMani;
    }

    public String getFechaMani() {
        return fechaMani;
    }

    public void setFechaMani(String fechaMani) {
        this.fechaMani = fechaMani;
    }

    public String getDestinoMani() {
        return destinoMani;
    }

    public void setDestinoMani(String destinoMani) {
        this.destinoMani = destinoMani;
    }

    public String getVehiculoMani() {
        return vehiculoMani;
    }

    public void setVehiculoMani(String vehiculoMani) {
        this.vehiculoMani = vehiculoMani;
    }

    public String getChoferMani() {
        return choferMani;
    }

    public void setChoferMani(String choferMani) {
        this.choferMani = choferMani;
    }

    //

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


    public int getSync_status() {
        return sync_status;
    }

    public void setSync_status(int sync_status) {
        this.sync_status = sync_status;
    }
}
