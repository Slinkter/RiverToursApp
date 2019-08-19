package com.cudpast.rivertourapp.SQLite;

public class Utils {

    public static final int db_version = 1;
    //SYNC 0 y 1
    public static final int SYNC_STATUS_OK_MANIFIESTO = 0;
    public static final int SYNC_STATUS_FAILIDE_MANIFIESTO = 1;

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
                    CAMPO_TELEFONO_CHOFER + " " + "TEXT)";


    //Constates campos  Manifiesto
    public static final String TABLA_MANIFIESTO = "manifiesto";

    public static final String CAMPO_ID_MANIFIESTO = "idManfiesto";
    public static final String CAMPO_ID_GUIA = "idGuiaMani";
    public static final String CAMPO_FECHA_MANIFIESTO = "fechaMani";
    public static final String CAMPO_DESTINO_MANIFIESTO = "destinoMani";
    public static final String CAMPO_VEHICULO_MANIFIESTO = "vehiculoMani";
    public static final String CAMPO_CHOFER_MANIFIESTO = "choferMani";
    public static final String CAMPO_SYNC_STATUS_MANIFIESTO = "syncstatus";

    public static final String CREATE_TABLA_MANIFIESTO =
            "CREATE TABLE " + TABLA_MANIFIESTO + "(" +
                    CAMPO_ID_MANIFIESTO + " " + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    CAMPO_ID_GUIA + " " + " TEXT," +
                    CAMPO_FECHA_MANIFIESTO + " " + "TEXT," +
                    CAMPO_DESTINO_MANIFIESTO + " " + "TEXT," +
                    CAMPO_VEHICULO_MANIFIESTO + " " + "TEXT," +
                    CAMPO_CHOFER_MANIFIESTO + " " + "TEXT," +
                    CAMPO_SYNC_STATUS_MANIFIESTO + " " + "INTEGER);";


    // Constates campos de Pasajero

    public static final String TABLA_PASAJERO = "pasajero";

    public static final String CAMPO_ID_PASAJERO = "idPasajero";
    public static final String CAMPO_NOMBRE_PASAJERO = "nombrePasajero";
    public static final String CAMPO_EDAD_PASAJERO = "edadPasajero";
    public static final String CAMPO_OCUPACION_PASAJERO = "ocupacionPasajero";
    public static final String CAMPO_NACIONALIDAD_PASAJERO = "nacionalidadPasajero";
    public static final String CAMPO_NUMBOLETA_PASAJERO = "numBoletaPasajero";
    public static final String CAMPO_DNI_PASAJERO = "dniPasajero";
    public static final String CAMPO_DESTINO_PASAJERO = "destinoPasajero";

    public static final String CREATE_TABLA_PASAJERO =
            "CREATE TABLE " + TABLA_PASAJERO + "(" +
                    CAMPO_ID_PASAJERO + " " + "INTEGER PRIMARY KEY AUTOINCREMENT ," +
                    CAMPO_NOMBRE_PASAJERO + " " + "TEXT ," +
                    CAMPO_EDAD_PASAJERO + " " + " TEXT ," +
                    CAMPO_OCUPACION_PASAJERO + " " + " TEXT ," +
                    CAMPO_NACIONALIDAD_PASAJERO + " " + "TEXT ," +
                    CAMPO_NUMBOLETA_PASAJERO + " " + "TEXT ," +
                    CAMPO_DNI_PASAJERO + " " + "TEXT ," +
                    CAMPO_DESTINO_PASAJERO + " " + "TEXT)";


    // todo : 1 . se debe crear la tabla manifiesto ,  se debe crear el insert pasajero con la varaible sync_status_ok  y sync_status_faile ç
    // todo : 2 . se debe poner el icono de sync (color verde) y se debe sincroniza automaticamente (seguir el ejemplo)


}
