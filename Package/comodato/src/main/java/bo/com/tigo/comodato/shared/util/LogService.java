package bo.com.tigo.comodato.shared.util;


import bo.com.tigo.comodato.shared.util.constants.Constants;
import bo.com.tigo.comodato.shared.util.constants.LogConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

public class LogService {
    private static final Logger logger = LoggerFactory.getLogger(LogService.class);

    public String getLogRequest(String uuid,int sequence, Object authorization, Object body, Map<String, String> headers) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String seq=String.valueOf(sequence);
        SimpleDateFormat dateTime = new SimpleDateFormat(LogConstants.LOG_DATE_FORMAT);
        String jsonBody;
        try {
            jsonBody = mapper.writeValueAsString(body);
        } catch (Exception e) {
            jsonBody = body.toString();
        }
        String request = LogConstants.LOG_REQUEST_POST.replace(LogConstants.LOG_BODY, jsonBody);
        request = request.replace(LogConstants.LOG_HEADERS, getHeaders(headers));

        String logRequest = LogConstants.LOG_WS_REQUEST.replace(LogConstants.LOG_WS_NAME, LogConstants.WS_NAME);
        logRequest = logRequest.replace(LogConstants.LOG_UUID, uuid != null ? uuid : "");
        logRequest = logRequest.replace(LogConstants.LOG_DATETIME, dateTime.format(new Date()));
        String user = getUser(authorization);
        logRequest = logRequest.replace(LogConstants.LOG_SEC, seq);
        logRequest = logRequest.replace(LogConstants.LOG_USER, user != null ? user : "");
        logRequest = logRequest.replace(LogConstants.LOG_CHANNEL, LogConstants.WS_NAME);
        logRequest = logRequest.replace(LogConstants.LOG_REQUEST, request);

        return logRequest;
    }

    public String getLogResponse(String uuid, int sequence ,String url, String result, String code, String description,
                                 Object response, long requestTime) {

        String seq=String.valueOf(sequence);
        ObjectMapper mapper = new ObjectMapper();
        SimpleDateFormat dateTime = new SimpleDateFormat(LogConstants.LOG_DATE_FORMAT);
        String stringResponse;
        try {
            stringResponse = mapper.writeValueAsString(response);
        } catch (Exception e) {
            stringResponse = response.toString();
        }

        String logResponse = LogConstants.LOG_WS_RESPONSE.replace(LogConstants.LOG_WS_NAME, LogConstants.WS_NAME);
        logResponse = logResponse.replace(LogConstants.LOG_UUID, uuid != null ? uuid : "");
        logResponse = logResponse.replace(LogConstants.LOG_DATETIME, dateTime.format(new Date()));
        logResponse = logResponse.replace(LogConstants.LOG_URL, url != null ? url : "");
        logResponse = logResponse.replace(LogConstants.LOG_RESULT, result != null ? result : "");
        logResponse = logResponse.replace(LogConstants.LOG_SEC, seq);
        logResponse = logResponse.replace(LogConstants.LOG_CODE, code != null ? code : "");
        logResponse = logResponse.replace(LogConstants.LOG_DESCRIPTION, description != null ? description : "");
        logResponse = logResponse.replace(LogConstants.LOG_RESPONSE, stringResponse != null ? stringResponse : "");
        logResponse = logResponse.replace(LogConstants.LOG_TIME, String.valueOf(System.currentTimeMillis() - requestTime));
        return logResponse;
    }

    public String getLogStep(String uuid,int sequence,String message,String user,String ip,
                             long requestTime) {

        String seq=String.valueOf(sequence);

        SimpleDateFormat dateTime = new SimpleDateFormat(LogConstants.LOG_DATE_FORMAT);
        String logResponse = LogConstants.LOG_WS_STEP.replace(LogConstants.LOG_WS_NAME, LogConstants.WS_NAME);
        logResponse = logResponse.replace(LogConstants.LOG_UUID, uuid != null ? uuid : "");
        logResponse = logResponse.replace(LogConstants.LOG_DATETIME, dateTime.format(new Date()));
        logResponse = logResponse.replace(LogConstants.LOG_DESCRIPTION, message != null ? message : "");
        logResponse = logResponse.replace(LogConstants.LOG_IP, ip != null ? ip : "");
        logResponse = logResponse.replace(LogConstants.LOG_USER, user != null ? user : "");
        logResponse = logResponse.replace(LogConstants.LOG_SEC, seq);

        logResponse = logResponse.replace(LogConstants.LOG_TIME, String.valueOf(System.currentTimeMillis() - requestTime));
        return logResponse;
    }

    public String getLogException(String uuid,int sequence,String exClass,String exMessage,String user,String ip,
                                  long requestTime) {

        String seq=String.valueOf(sequence);

        SimpleDateFormat dateTime = new SimpleDateFormat(LogConstants.LOG_DATE_FORMAT);
        String logResponse = LogConstants.LOG_WS_EXCEPTION.replace(LogConstants.LOG_WS_NAME, LogConstants.WS_NAME);
        logResponse = logResponse.replace(LogConstants.LOG_UUID, uuid != null ? uuid : "");
        logResponse = logResponse.replace(LogConstants.LOG_DATETIME, dateTime.format(new Date()));
        logResponse = logResponse.replace(LogConstants.LOG_EXNAME, exClass);
        logResponse = logResponse.replace(LogConstants.LOG_EXMESSAGE, exMessage);
        logResponse = logResponse.replace(LogConstants.LOG_IP, ip != null ? ip : "");
        logResponse = logResponse.replace(LogConstants.LOG_USER, user != null ? user : "");
        logResponse = logResponse.replace(LogConstants.LOG_SEC, seq);
        logResponse = logResponse.replace(LogConstants.LOG_TIME, String.valueOf(System.currentTimeMillis() - requestTime));
        return logResponse;
    }

    public String getLogPcmlRequest(Object pcml, String objectName, String user, String uuid,int sequence) {
        SimpleDateFormat dateTime = new SimpleDateFormat(LogConstants.LOG_DATE_FORMAT);
        ObjectMapper mapper = new ObjectMapper();
        String stringRequest;
        String seq=String.valueOf(sequence);
        try {
            stringRequest = mapper.writeValueAsString(pcml);
        } catch (Exception e) {
            stringRequest = pcml.toString();
        }
        String logRequest = LogConstants.LOG_WS_OBJECT_REQUEST.replace(LogConstants.LOG_OBJECT_NAME, objectName);
        logRequest = logRequest.replace(LogConstants.LOG_UUID, uuid != null ? uuid : "");
        logRequest = logRequest.replace(LogConstants.LOG_DATETIME, dateTime.format(new Date()));
        logRequest = logRequest.replace(LogConstants.LOG_USER, user);
        logRequest = logRequest.replace(LogConstants.LOG_REQUEST, stringRequest);
        logRequest = logRequest.replace(LogConstants.LOG_SEC, seq);

        return logRequest;
    }

    public String getLogPcmlResponse(int sequence,Object pcmlResponse, String objectName, String url, String uuid, long requestTime) {
        SimpleDateFormat dateTime = new SimpleDateFormat(LogConstants.LOG_DATE_FORMAT);

        ObjectMapper mapper = new ObjectMapper();
        String seq=String.valueOf(sequence);

        String stringResponse;
        try {
            stringResponse = mapper.writeValueAsString(pcmlResponse);
        } catch (Exception e) {
            stringResponse = pcmlResponse.toString();
        }
        String logResponse = LogConstants.LOG_WS_OBJECT_RESPONSE.replace(LogConstants.LOG_OBJECT_NAME, objectName);
        logResponse = logResponse.replace(LogConstants.LOG_UUID, uuid != null ? uuid : "");
        logResponse = logResponse.replace(LogConstants.LOG_DATETIME, dateTime.format(new Date()));
        logResponse = logResponse.replace(LogConstants.LOG_URL, url);
        logResponse = logResponse.replace(LogConstants.LOG_RESPONSE, stringResponse);
        logResponse = logResponse.replace(LogConstants.LOG_TIME, String.valueOf(System.currentTimeMillis() - requestTime));
        logResponse = logResponse.replace(LogConstants.LOG_SEC, seq);

        return logResponse;
    }


    private String getUser(Object authorization) {
        String response = "";
        try {
            if (authorization != null) {
                byte[] userBytes = Base64.decodeBase64(authorization.toString().substring(6));
                response = new String(userBytes, StandardCharsets.UTF_8).split(":")[0];
            }
        } catch (Exception e) {
            logger.warn("Error al obtener el usuario de la sesion: " + e.getMessage());
        }

        return response;
    }

    private String getHeaders(Map<String, String> headers) {
        return headers.entrySet()
                .stream()
                .filter((value) -> !value.getKey().equals(Constants.AUTHORIZATION)
                        && !value.getKey().equals(Constants.CAMEL_AUTHENTICATION)
                        && !value.getKey().equals(Constants.CAMEL_CXF_MESSAGE)
                        && !value.getKey().equals(Constants.CAMEL_CXF_RS_OP)
                        && !value.getKey().equals(Constants.CAMEL_CXF_RS_RE_CLASS)
                        && !value.getKey().equals(Constants.CAMEL_CXF_RS_RE_GENERIC))
                .map((entry) -> ("> " + entry.getKey() + ": " + entry.getValue()))
                .collect(Collectors.joining("  "));
    }
}
