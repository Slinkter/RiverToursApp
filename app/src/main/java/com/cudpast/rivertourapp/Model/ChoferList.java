package com.cudpast.rivertourapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class ChoferList {

    @SerializedName("ListaChofer")
    @Expose
    private ArrayList<Chofer> listaChofer = null;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;


    public ChoferList() {
    }

    public ArrayList<Chofer> getListaChofer() {
        return listaChofer;
    }

    public void setListaChofer(ArrayList<Chofer> listaChofer) {
        this.listaChofer = listaChofer;
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
