package bo.com.tigo.comodato.shared.util;

import bo.com.tigo.comodato.shared.util.constants.ErrorMessage;
import com.ibm.as400.access.ConnectionPoolException;
import com.ibm.as400.data.PcmlException;
import org.apache.commons.dbcp.SQLNestedException;

import javax.ws.rs.core.Response.Status;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;


public enum SupportedException {

    /*
     * Excepciones disponibles con su StatusCode Correspondiente
     */
    ERRORCONNECTION(ConnectionPoolException.class,
                    ErrorMessage.ERRORCONNECTION.msg(),
                    Status.SERVICE_UNAVAILABLE.getStatusCode()),// Este es para cuando no se puede conectar a la Base de datos

    ERRORCONVERSION(ClassCastException.class, ErrorMessage.ERRORCONVERSION.msg(), Status.FORBIDDEN.getStatusCode()),
    TIMEOUT_EXCEPTION(TimeoutException.class, ErrorMessage.TIMEOUT.msg(),Status.REQUEST_TIMEOUT.getStatusCode()),

    ILLEGALARGUMENT(IllegalArgumentException.class,
                    ErrorMessage.ILLEGALARGUMENT.msg(),
                    Status.BAD_REQUEST.getStatusCode()),

    SQLEXCEPTION(SQLException.class, ErrorMessage.ERRORSQL.msg(), Status.SERVICE_UNAVAILABLE.getStatusCode()),
    BDEXCEPTION(SQLNestedException.class,ErrorMessage.DBCP_ERROR.msg(),Status.SERVICE_UNAVAILABLE.getStatusCode()),
    PCMLEXCEPTION(PcmlException.class, ErrorMessage.ERRORPCML.msg(), Status.SERVICE_UNAVAILABLE.getStatusCode()),//Este es cuando hay un error en el objeto pcml
    ERRORCALLAS400(null, ErrorMessage.ERRORCALLAS400.msg(), Status.SERVICE_UNAVAILABLE.getStatusCode()),//No se pudo conectar a la As44 (Actualmente no se usa)
    ERRPRCALLCOMMAND(null , ErrorMessage.ERRORCALLCOMMAND.msg(), Status.SERVICE_UNAVAILABLE.getStatusCode()),//No se pudo ejectuar el comando
    ERRORGENERAL(Exception.class, ErrorMessage.ERRORGENERAL.msg(), Status.INTERNAL_SERVER_ERROR.getStatusCode());
    
    private final Class<? extends Exception> exceptionClass;
    private final String message;
    private final int statusCode;

    SupportedException(Class<? extends Exception> exception, String message, int statusCode) {
        this.exceptionClass = exception;
        this.message = message;
        this.statusCode = statusCode;
    }

    public Class<? extends Exception> getExceptionClass() {
        return exceptionClass;
    }

    public String getException() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Busca el status correspondiente a una excepcion
     *
     * @param message el mensaje de una excepcion
     * @return el status code
     */
    public static Integer getStatusFromMessage(Object message) {
        if (message == null)
            return null;

        return Arrays.stream(values()).filter(value -> message.equals(value.message)).findFirst()
                .map(output -> output.statusCode)
                .orElse(Status.INTERNAL_SERVER_ERROR.getStatusCode());
    }

    /**
     * Busca el mensaje correspondiente a una excepcion
     *
     * @param throwable la excepcion
     * @return el mensaje
     */
    public static String getMessageFromException(Throwable throwable) {
        if (throwable == null)
            return null;

        return Arrays.stream(values()).filter(value -> hasSameClass(value, throwable)).findFirst()
                .map(output -> output.message)
                .orElse(ErrorMessage.ERRORGENERAL.msg());
    }

    /**
     * Evalua si {@link SupportedException} tiene la misma clase que
     * el Throwable
     *
     * @param exception
     * @param throwable
     * @return Boolean
     */
    private static Boolean hasSameClass(SupportedException exception, Throwable throwable) {
        return throwable.getClass().equals(exception.getExceptionClass());
    }


}
