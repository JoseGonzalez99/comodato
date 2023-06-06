package bo.com.tigo.comodato.shared.util.connections;

import com.ibm.as400.access.AS400;

import java.sql.Connection;
import java.sql.SQLException;

public class CurrentAs400Connection implements AutoCloseable {
    private final AS400 as400;
    private final As400ConnectionService as400ConnectionService;
    private final Connection as400DBConnection;

    /**
     * @param as400 Current as400 connection
     * @param as400DBConnection Current as400 database connection
     * @param as400ConnectionService the operations required to return the current Connection
     */
    public CurrentAs400Connection(AS400 as400, Connection as400DBConnection, As400ConnectionService as400ConnectionService) {
        this.as400 = as400;
        this.as400ConnectionService = as400ConnectionService;
        this.as400DBConnection = as400DBConnection;
    }

    public AS400 as400() {
        return this.as400;
    }

    public Connection as400DBConnection() {
        return this.as400DBConnection;
    }

    @Override
    public void close() throws SQLException {
        if (as400 != null)
            this.as400ConnectionService.returnConnection(as400);
        if (as400DBConnection != null)
            this.as400DBConnection.close();
    }
}
