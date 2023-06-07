package bo.com.tigo.comodato.shared.util.connections;

import bo.com.tigo.comodato.shared.util.AppConfiguration;
import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400ConnectionPool;
import com.ibm.as400.access.ConnectionPoolException;
import com.ibm.as400.access.SocketProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeoutException;

/*
* Esta clase sirve para crear la conexion, mas no es para usarlo directamente.
* */
@Service
public class As400ConnectionService {
    private static final Logger log = LogManager.getLogger(As400ConnectionService.class);

    private final AS400ConnectionPool pool;

    private final AppConfiguration conf;
    @Autowired
    public As400ConnectionService(AppConfiguration conf) {
        this.conf = conf;
        this.pool = createConnectionPool();
    }

    private AS400ConnectionPool createConnectionPool() {
        AS400ConnectionPool pool = new AS400ConnectionPool();
        SocketProperties socketProperties = new SocketProperties();
        socketProperties.setLoginTimeout(conf.getAs400ConnectionTimeout() * 1000);
        socketProperties.setSoTimeout(conf.getAs400SoTimeout() * 1000);
        pool.setMaxConnections(conf.getAs400MaxConnections());
        pool.setMaxInactivity(conf.getAs400MaxInactivity() * 1000);
        pool.setPretestConnections(conf.getAs400PretestConnection());
        pool.setSocketProperties(socketProperties);
        return pool;
    }


    private void validateAs400Configuration() {
        if (conf.getAs400Ip() == null) {
            log.error("Fallo al intentar conexion con la AS400. Valor nulo del atributo -bccs.ip-");
            throw new IllegalArgumentException("Dirección IP de AS400 no configurada");
        }
        if (conf.getAs400User() == null) {
            log.error("Fallo al intentar conexion con la AS400. Valor nulo del atributo -bccs.user-");
            throw new IllegalArgumentException("Usuario de AS400 no configurado");
        }
        if (conf.getAs400Password() == null) {
            log.error("Fallo al intentar conexion con la AS400. Valor nulo o invalido del atributo -bccs.password-");
            throw new IllegalArgumentException("Contraseña de AS400 no configurada o inválida");
        }
    }

    public AS400 getConnection(String uuid) throws ConnectionPoolException, TimeoutException {
        validateAs400Configuration();
        AS400 conn = getConnectionWithRetry(uuid);
        log.debug(poolInfo());
        log.debug(socketInfo(conn.getSocketProperties()));
        return conn;
    }



    private AS400 getConnectionWithRetry(String uuid) throws ConnectionPoolException, TimeoutException {
        long retryInterval = ThreadLocalRandom.current().nextInt(0, 2001); // Intervalo de espera entre reintentos en milisegundos 0 a 2 segundos
        log.debug("Hilo: " + uuid + " , retry interval"+retryInterval);
        long startTime = System.currentTimeMillis();
        long maxTime = startTime + Integer.parseInt(conf.getRetryInterval()); // Tiempo máximo permitido en milisegundos (en este caso, 5 segundos)
        boolean connected = false;
        AS400 conn = null;

        while (!connected && System.currentTimeMillis() < maxTime) {
            try {
                synchronized (pool) {
                    conn = pool.getConnection(conf.getAs400Ip(), conf.getAs400User(), conf.getAs400Password());
                    connected = true; // La conexión se estableció correctamente
                    log.debug("Hilo: " + uuid + " ,obtuvo conexión");
                    printConnectionsStatus();
                }
            } catch (ConnectionPoolException e) {
                if (e.getReturnCode() == ConnectionPoolException.MAX_CONNECTIONS_REACHED) {
                    //log.warn(e.getMessage());
                    // Código de excepción específico (3 en este caso)
                    try {
                        Thread.sleep(retryInterval); // Espera el intervalo de tiempo antes de reintentar
                    } catch (InterruptedException ex) {
                        log.debug("El hilo " + uuid + " se interrumpió");
                        ex.printStackTrace();
                    }
                } else {
                    throw e;
                }
            }
        }

        if (!connected) {
            // Si no se pudo establecer la conexión después del tiempo máximo
            throw new TimeoutException("TimeoutEx");
        }

        return conn;
    }


    public void returnConnection(AS400 conn) {
        if (conn != null) {
            log.debug("Retornando conexion al pool --> " + conn.getSystemName());
            synchronized (pool) {
                log.debug("Retornando conexion --> isConnected=" + conn.isConnected() + ", isConnectionAlive="
                        + conn.isConnectionAlive());
                if (conn.isConnected() && conn.isConnectionAlive())
                    pool.returnConnectionToPool(conn);
            }
        }
    }

    public void printConnectionsStatus() {
        synchronized (pool) {
            log.debug("Número de conexiones activas  --> "
                    + pool.getActiveConnectionCount(conf.getAs400Ip(), conf.getAs400User())
                    + ", Número de conexiones disponibles para reutilizar --> "
                    + pool.getAvailableConnectionCount(conf.getAs400Ip(), conf.getAs400User()));
        }
    }

    public String socketInfo(SocketProperties socketProperties) {
        return "SocketProperties {" + "loginTimeoutInMillis='" + socketProperties.getLoginTimeout() + ',' +
                "soTimeoutInMillis='" + socketProperties.getSoTimeout() + ',' +
                "receiveBufferSize='" + socketProperties.getReceiveBufferSize() + ',' +
                "sendBufferSize='" + socketProperties.getSendBufferSize() + ',' +
                "soLinger='" + socketProperties.getSoLinger() + ',' +
                '}';
    }

    public String poolInfo() {
        synchronized (pool) {
            return "PoolInfo {" + "maxConnections='" + pool.getMaxConnections() + ',' +
                    "maxInactivityInMillis='" + pool.getMaxInactivity() + ',' +
                    '}';
        }
    }
}
