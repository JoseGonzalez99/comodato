package bo.com.tigo.comodato.shared.util.constants;

public class LogConstants {

    //LOG
    public static final String WS_NAME = "ENABLE_SERVICE_BCCS";
    public static final String INVOICESERVICE_PLEX = "CGaaF.PGM";


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
    public static final String LOG_DESCRIPTION = "${DESCRIPTION}";
    public static final String LOG_BODY = "${BODY}";
    public static final String LOG_HEADERS = "${HEADERS}";
    public static final String LOG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String LOG_WS_REQUEST = "LOG_WS: [TRANSACTION_ID:${UUID}, REQUEST [${WS_NAME}], SECUENCIA:1, DATETIME:${DATETIME}, USER:${USER}, CHANNEL:${CHANNEL}, REQUEST:${REQUEST}]";
    public static final String LOG_WS_OBJECT_REQUEST = "LOG_PLEX: [TRANSACTION_ID:${UUID}, REQUEST [${OBJECT_NAME}], SECUENCIA:1, DATETIME:${DATETIME}, USER:${USER}, REQUEST:${REQUEST}]";
    public static final String LOG_REQUEST_POST = "HTTP-METHOD:POST, HEADERS => ${HEADERS}, PAREMETERS => {}, BODY => ${BODY}";
    public static final String LOG_WS_RESPONSE = "LOG_WS: [TRANSACTION_ID:${UUID}, RESPONSE [${WS_NAME}], SECUENCIA:2, DATETIME:${DATETIME}, URL:${URL}, RESULT:${RESULT}, CODE:${CODE}, DESCRIPTION:${DESCRIPTION}, RESPONSE:${RESPONSE}, TIME:${TIME} ms.]";
    public static final String LOG_WS_OBJECT_RESPONSE = "LOG_PLEX: [TRANSACTION_ID:${UUID}, RESPONSE [${OBJECT_NAME}], SECUENCIA:2, DATETIME:${DATETIME}, URL:${URL}, RESPONSE:${RESPONSE}, TIME:${TIME} ms.]";
}
