package bo.com.tigo.comodato.shared.util.connections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConnectionUtils {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionUtils.class);

    /*
    * Este sirve para cerrar statements
    * */
    public static void close(PreparedStatement statement) {
        try {
            if (statement != null && !statement.isClosed())
                statement.close();
        } catch (Exception e) {
            logger.error("Fallo al cerrar recurso --> " + e.getMessage());
        }
    }

    /*
    * Este sirve para cerrar resultsets
    * */
    public static void close(ResultSet resultset) {
        try {
            if (resultset != null && !resultset.isClosed())
                resultset.close();
        } catch (Exception e) {
            logger.error("Fallo al cerrar recurso --> " + e.getMessage());
        }
    }

    /*
    * Este sirve para cerrar una conexion a base de datos
    * */
    public static void close(Connection as400conn) {
        try {
            if (as400conn != null && !as400conn.isClosed())
                as400conn.close();
        } catch (Exception e) {
            logger.error("Fallo al cerrar recurso --> " + e.getMessage());
        }
    }
}
