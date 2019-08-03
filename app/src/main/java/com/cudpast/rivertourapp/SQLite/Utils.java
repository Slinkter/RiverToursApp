package com.cudpast.rivertourapp.SQLite;

public class Utils {

    public static final int db_version = 1;

    //Constates campos  usuario
    public static final String TABLA_USUARIO = "usuario";

    public static final String CAMPO_ID_USUARIO = "idUsuario";
    public static final String CAMPO_NOMBRE_USUARIO = "nombreUsuario";
    public static final String CAMPO_APELLIDO_USUARIO = "apellidoUsuario";
    public static final String CAMPO_DNI_USUARIO = "dniUsuario";
    public static final String CAMPO_CORREO_USUARIO = "correoUsuario";
    public static final String CAMPO_TELEFONO_USUARIO = "telefonoUsuario";

    public static final String CREATE_TABLA_USUARIO =
            "CREATE TABLE " + TABLA_USUARIO + "(" +
                    CAMPO_ID_USUARIO + " " + "PRIMARY KEY AUTOINCREMENT," +
                    CAMPO_NOMBRE_USUARIO + " " + " TEXT," +
                    CAMPO_APELLIDO_USUARIO + " " + "TEXT," +
                    CAMPO_DNI_USUARIO + " " + "TEXT," +
                    CAMPO_CORREO_USUARIO + " " + "TEXT," +
                    CAMPO_NOMBRE_USUARIO + " " + "TEXT," +
                    CAMPO_TELEFONO_USUARIO + "TEXT)";





    //Constates campos vehiculos
    public static final String TABLA_VEHICULO = "vehiculo";

    public static final String CAMPO_ID_VEHICULO = "idVehiculo";
    public static final String CAMPO_NOMBRE_VEHICULO = "nombreVehiculo";
    public static final String CAMPO_MARCA_VEHICULO = "marcaVehiculo";
    public static final String CAMPO_MATRICULA_VEHICULO = "matriculaVehiculo";
    public static final String CAMPO_PLACA_VEHICULO = "placaVehiculo";

    public static final String CREATE_TABLA_VEHICULO =
            " CREATE TABLE " + TABLA_VEHICULO + "( " +
                    CAMPO_ID_VEHICULO + " " + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    CAMPO_NOMBRE_VEHICULO + " " + "TEXT," +
                    CAMPO_MARCA_VEHICULO + " " + "TEXT," +
                    CAMPO_MATRICULA_VEHICULO + " " + "TEXT," +
                    CAMPO_PLACA_VEHICULO + " " + "TEXT )";

    // Constates campos de Chofer
    public static final String TABLA_CHOFER = "chofer";

    public static final String CAMPO_ID_CHOFER = "idChofer";
    public static final String CAMPO_NOMBRE_CHOFER = "nombreChofer";
    public static final String CAMPO_APELLIDO_CHOFER = "apellidoChofer";
    public static final String CAMPO_DNI_CHOFER = "dniChofer";
    public static final String CAMPO_BREVETE_CHOFER = "breveteChofer";
    public static final String CAMPO_TELEFONO_CHOFER = "telefonoChofer";

    public static final String CREATE_TABLA_CHOFER =
            "CREATE TABLE " + TABLA_CHOFER + "(" +
                    CAMPO_ID_CHOFER + " " + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    CAMPO_NOMBRE_CHOFER + " " + "TEXT," +
                    CAMPO_APELLIDO_CHOFER + " " + "TEXT," +
                    CAMPO_DNI_CHOFER + " " + "TEXT," +
                    CAMPO_BREVETE_CHOFER + " " + "TEXT," +
                    CAMPO_TELEFONO_CHOFER + "TEXT)";




    // Constates campos de Pasajero
    public static final String TABLA_PASAJERO = "mascota";
    public static final String CAMPO_ID_MASCOTA = "id_mascota";
    public static final String CAMPO_NOMBRE_MASCOTA = "nombre_mascota";
    public static final String CAMPO_RAZA_MASCOTA = "raza_mascota";
    public static final String CAMPO_ID_DUENO = "id_dueno";

    public static final String CREATE_TABLA_MASCOTA =
            "CREATE TABLE " + TABLA_PASAJERO + "(" +
                    CAMPO_ID_MASCOTA + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                    CAMPO_NOMBRE_MASCOTA + "  TEXT," +
                    CAMPO_RAZA_MASCOTA + " TEXT ," +
                    CAMPO_ID_DUENO + " INTEGER )";
    //Constates campos  Manifiesto


}
