package bo.com.tigo.comodato.shared.util.connections;

import bo.com.tigo.comodato.shared.util.AppConfiguration;
import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400ConnectionPool;
import com.ibm.as400.access.ConnectionPoolException;
import com.ibm.as400.access.SocketProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/*
* Esta clase sirve para crear la conexion, mas no es para usarlo directamente.
* */
@Service
public class As400ConnectionService {

    private static final AS400ConnectionPool pool = new AS400ConnectionPool();

    private final AppConfiguration conf;
    @Autowired
    public As400ConnectionService(AppConfiguration conf) {
        this.conf = conf;
    }

    /**
     * Asignar una conexion a traves del pool de conexiones de la AS400
     *
     * @return conexion a la AS400
     * @throws ConnectionPoolException excepcion propagada por la libreria
     */

    public AS400 getConnection() throws ConnectionPoolException {
        AS400 conn = null;
        // Verificar direccion IP
        if (conf.getAs400Ip() == null) {
            //log.error("Fallo al intentar conexion con la AS400. Valor nulo del atributo -bccs.ip-");
        } else // Verificar Usuario
        if (conf.getAs400User() == null) {
            //log.error("Fallo al intentar conexion con la AS400. Valor nulo del atributo -bccs.user-");
        } else // Verificar Password
        if (conf.getAs400Password() == null) {
            //log.error("Fallo al intentar conexion con la AS400. Valor nulo o invalido del atributo -bccs.password-");
        } else {
           // log.debug("Obteniendo conexion a la AS400");
            synchronized (pool) {
                SocketProperties socketProperties = new SocketProperties();
                socketProperties.setLoginTimeout(conf.getAs400ConnectionTimeout() * 1000);
                socketProperties.setSoTimeout(conf.getAs400SoTimeout() * 1000);
                pool.setMaxConnections(conf.getAs400MaxConnections());
                pool.setMaxInactivity(conf.getAs400MaxInactivity() * 1000);
                pool.setPretestConnections(conf.getAs400PretestConnection());
                pool.setSocketProperties(socketProperties);
                conn = pool.getConnection(conf.getAs400Ip(), conf.getAs400User(), conf.getAs400Password());
                //log.debug(poolInfo());
                //log.debug(socketInfo(conn.getSocketProperties()));
            }
        }

        if (conn == null)
            throw new IllegalArgumentException("Parametro/s de conexion a la AS400 invalido/s");
        else
            return conn;
    }

    /**
     * Retorna la conexion al pool de conexiones
     *
     * @param conn la instancia
     */
    public void returnConnection(AS400 conn) {
        if (conn != null) {
            //log.debug("Retornando conexion al pool --> " + conn.getSystemName());
            synchronized (pool) {
                //log.debug("Retornando conexion --> isConnected=" + conn.isConnected() + ", isConnectionAlive="
                //  + conn.isConnectionAlive());
                if (conn.isConnected() && conn.isConnectionAlive())
                    pool.returnConnectionToPool(conn);
            }
        }
    }

    /**
     * Imprime informacion referente a las conexiones del pool y del socket
     *
     */
    public void printConnectionsStatus() {
        synchronized (pool) {
            /*
            log.debug("Número de conexiones activas  --> "
                    + pool.getActiveConnectionCount(conf.getAs400Ip(), conf.getAs400User())
                    + ", Número de conexiones disponibles para reutilizar --> "
                    + pool.getAvailableConnectionCount(conf.getAs400Ip(), conf.getAs400User()));*/
        }
    }

    public String socketInfo(SocketProperties socketProperties) {
        final StringBuilder sb = new StringBuilder("SocketProperties {");
        sb.append("loginTimeoutInMillis='").append(socketProperties.getLoginTimeout()).append(',');
        sb.append("soTimeoutInMillis='").append(socketProperties.getSoTimeout()).append(',');
        sb.append("receiveBufferSize='").append(socketProperties.getReceiveBufferSize()).append(',');
        sb.append("sendBufferSize='").append(socketProperties.getSendBufferSize()).append(',');
        sb.append("soLinger='").append(socketProperties.getSoLinger()).append(',');
        sb.append('}');
        return sb.toString();
    }

    public String poolInfo() {
        synchronized (pool) {
            final StringBuilder sb = new StringBuilder("PoolInfo {");
            sb.append("maxConnections='").append(pool.getMaxConnections()).append(',');
            sb.append("maxInactivityInMillis='").append(pool.getMaxInactivity()).append(',');
            sb.append('}');
            return sb.toString();
        }
    }


    public synchronized void clearPoolConnections() {
        pool.removeFromPool(conf.getAs400Ip(), conf.getAs400User());

    }


    public synchronized void closeConnection() {
        pool.close();
    }
}
