package com.cudpast.rivertourapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chofer {

    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("nameChofer")
    private String nameChofer;
    @Expose
    @SerializedName("lastChofer")
    private String lastChofer;
    @Expose
    @SerializedName("dniChofer")
    private String dniChofer;
    @Expose
    @SerializedName("brevete")
    private String brevete;
    @Expose
    @SerializedName("numphone")
    private String numphone;

    @Expose
    @SerializedName("success")
    private Boolean success;
    @Expose
    @SerializedName("message")
    private String message;

    public Chofer() {

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameChofer() {
        return nameChofer;
    }

    public void setNameChofer(String nameChofer) {
        this.nameChofer = nameChofer;
    }

    public String getLastChofer() {
        return lastChofer;
    }

    public void setLastChofer(String lastChofer) {
        this.lastChofer = lastChofer;
    }

    public String getDniChofer() {
        return dniChofer;
    }

    public void setDniChofer(String dniChofer) {
        this.dniChofer = dniChofer;
    }

    public String getBrevete() {
        return brevete;
    }

    public void setBrevete(String brevete) {
        this.brevete = brevete;
    }

    public String getNumphone() {
        return numphone;
    }

    public void setNumphone(String numphone) {
        this.numphone = numphone;
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
