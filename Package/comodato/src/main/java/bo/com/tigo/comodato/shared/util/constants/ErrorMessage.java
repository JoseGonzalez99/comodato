package bo.com.tigo.comodato.shared.util.constants;

public enum ErrorMessage {


    ERRORCONNECTION("Error en la conexion"),
    ERRORCONVERSION("Error en la conversion del dato"),
    ERRORGENERAL("Error General"),
    ILLEGALARGUMENT("Error en los argumentos ingresados"),
    ERRORSQL("Error en query sql"),
    ERRORCALLAS400("Error en la llamada al objeto del AS400"),
    ERRORCALLCOMMAND("Error en la ejecucion del comando AS400"),
    ERRORPCML("Error al utilizar el objeto AS400"),
    DBCP_ERROR("No se pudo establecer conexion a la base de datos"),
    NOT_PROCESSED("Error al procesar el pedido --> ErrorCode: %s"),;


    private final String msg;

    ErrorMessage(String msg) {
        this.msg = msg;
    }

    public String msg() {
        return msg;
    }
}
