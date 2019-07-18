package com.cudpast.rivertourapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @Expose
    @SerializedName("firstname")
    private String firstname;
    @Expose
    @SerializedName("lastname")
    private String lastname;
    @Expose
    @SerializedName("dni")
    private String dni;
    @Expose
    @SerializedName("correo")
    private String correo;
    @Expose
    @SerializedName("numphone")
    private String numphone;
    @Expose
    @SerializedName("uid")
    private String uid;

    @Expose
    @SerializedName("success")
    private Boolean success;
    @Expose
    @SerializedName("message")
    private String message;

    public User() {

    }

    public User(String uid, String firstname, String lastname, String dni, String correo, String numphone) {
        this.uid = uid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dni = dni;
        this.correo = correo;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNumphone() {
        return numphone;
    }

    public void setNumphone(String numphone) {
        this.numphone = numphone;
    }
}
