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
    @SerializedName("breveteChofer")
    private String breveteChofer;
    @Expose
    @SerializedName("numphoneChofer")
    private String numphoneChofer;
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

    public String getNameChofer() {
        return nameChofer;
    }

    public String getLastChofer() {
        return lastChofer;
    }

    public String getDniChofer() {
        return dniChofer;
    }

    public String getBreveteChofer() {
        return breveteChofer;
    }

    public String getNumphoneChofer() {
        return numphoneChofer;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setNameChofer(String nameChofer) {
        this.nameChofer = nameChofer;
    }

    public void setLastChofer(String lastChofer) {
        this.lastChofer = lastChofer;
    }

    public void setDniChofer(String dniChofer) {
        this.dniChofer = dniChofer;
    }

    public void setBreveteChofer(String breveteChofer) {
        this.breveteChofer = breveteChofer;
    }

    public void setNumphoneChofer(String numphoneChofer) {
        this.numphoneChofer = numphoneChofer;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
