package bo.com.tigo.comodato.shared.util.constants;

public class Constants {

    public static final String BO_ZONEID = "America/La_Paz";

    //STATUS_CODE ERROR
    public static final String STATUS_CODE_ERROR = "1";

    public static  final String STATUS_CODE_ERROR2= "900";//ESTO ESTA SUJETO A CAMBIOS AL CODIGO CORRECTO.
    public static final String STATUS_CODE_SUCCESSFUL = "0";
    public static final String ErrorEnBD = "801";
    public static final String ErrorGeneral = "601";

    public static final String INFO = "Info";
    public static final String ERROR = "Error";
    public static final String COMPLETED = "Success";

    public static final String ENABLE_SERVICE_OPERATION = "generateInvoice";
    public static final String MESSAGE = "MENSAJE";

    public static final String SERVICE_NOT_FOUND="No se encontro ningun servicio para dar de baja";

    public static final String SERVICE_CANT_UPGRADE="No se encontro ningun servicio para realizar el upgrade/downgrade";

    public static final String SERVICE_ALREADY_EXIST="No se puede dar de Alta, el servicio ya existe";


    //Headers
    public static final String AUTHORIZATION = "Authorization";
    public static final String CAMEL_AUTHENTICATION = "CamelAuthentication";
    public static final String CAMEL_CXF_MESSAGE = "CamelCxfMessage";
    public static final String CAMEL_CXF_RS_OP = "CamelCxfRsOperationResourceInfoStack";
    public static final String CAMEL_CXF_RS_RE_CLASS = "CamelCxfRsResponseClass";
    public static final String CAMEL_CXF_RS_RE_GENERIC = "CamelCxfRsResponseGenericType";
}
