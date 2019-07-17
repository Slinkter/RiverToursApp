package com.cudpast.rivertourapp.Model;

public class User {

    private String uid;
    private String firstname;
    private String lastname;
    private String image;
    private String dni;
    private String correo;
    private String numphone;

    public User() {
    }

    public User(String uid, String firstname, String lastname, String image, String dni, String correo, String numphone) {
        this.uid = uid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.image = image;
        this.dni = dni;
        this.correo = correo;
        this.numphone = numphone;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
