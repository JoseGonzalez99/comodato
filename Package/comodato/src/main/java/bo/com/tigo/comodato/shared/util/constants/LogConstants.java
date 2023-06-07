package bo.com.tigo.comodato.shared.util.constants;

public class LogConstants {

    //LOG
    public static final String WS_NAME = "AsociarComodato";
    public static final String INVOICESERVICE_PLEX = "CGabF.PGM";


    public static final String LOG_WS_NAME = "${WS_NAME}";
    public static final String LOG_OBJECT_NAME = "${OBJECT_NAME}";
    public static final String LOG_UUID = "${UUID}";
    public static final String LOG_DATETIME = "${DATETIME}";
    public static final String LOG_USER = "${USER}";
    public static final String LOG_CHANNEL = "${CHANNEL}";
    public static final String LOG_RESULT = "${RESULT}";
    public static final String LOG_REQUEST = "${REQUEST}";
    public static final String LOG_RESPONSE = "${RESPONSE}";
    public static final String LOG_TIME = "${TIME}";
    public static final String LOG_URL = "${URL}";
    public static final String LOG_CODE = "${CODE}";
    public static final String LOG_IP = "${IP}";
    public static final String LOG_DESCRIPTION = "${DESCRIPTION}";
    public static final String LOG_BODY = "${BODY}";

    public static final String LOG_EXNAME = "${EXNAME}";
    public static final String LOG_EXMESSAGE = "${MESSAGE}";
    public static final String LOG_CAUSE = "${CAUSE}";



    public static final String LOG_SEC = "${SEQUENCE}";

    public static final String LOG_HEADERS = "${HEADERS}";
    public static final String LOG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String LOG_WS_REQUEST = "LOG_WS: [TRANSACTION_ID:${UUID}, REQUEST [${WS_NAME}], SECUENCIA:${SEQUENCE}, DATETIME:${DATETIME}, USER:${USER}, REQUEST:${REQUEST}]";
    public static final String LOG_WS_OBJECT_REQUEST = "LOG_PLEX: [TRANSACTION_ID:${UUID}, REQUEST TO [${OBJECT_NAME}], SECUENCIA:${SEQUENCE}, DATETIME:${DATETIME}, USER:${USER}, VALUES :${REQUEST}]";
    public static final String LOG_WS_OBJECT_RESPONSE = "LOG_PLEX: [TRANSACTION_ID:${UUID}, RESPONSE OF [${OBJECT_NAME}], SECUENCIA:${SEQUENCE}, DATETIME:${DATETIME}, RESPONSE:${RESPONSE}, TIME:${TIME} ms.]";
    public static final String LOG_WS_RESPONSE = "LOG_WS: [TRANSACTION_ID:${UUID}, RESPONSE [${WS_NAME}], SECUENCIA:${SEQUENCE}, DATETIME:${DATETIME}, URL:${URL}, RESULT:${RESULT}, RESPONSE:${RESPONSE}, TIME:${TIME} ms.]";

    public static final String LOG_WS_STEP = "LOG_WS: [TRANSACTION_ID:${UUID}, MESSAGE:${DESCRIPTION}, SECUENCIA:${SEQUENCE}, AS400_IP:${IP}, USER:${USER}, DATETIME:${DATETIME}]";


    public static final String LOG_WS_EXCEPTION = "LOG_WS_ERROR: [TRANSACTION_ID:${UUID},SECUENCIA:${SEQUENCE}, TYPE:${EXNAME}, MESSAGE:${MESSAGE}, AS400_IP:${IP}, USER:${USER}, DATETIME:${DATETIME}]";

    public static final String LOG_REQUEST_POST = "HTTP-METHOD:POST, HEADERS => ${HEADERS}, PAREMETERS => {}, BODY => ${BODY}";
}
