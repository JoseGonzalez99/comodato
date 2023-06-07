package bo.com.tigo.comodato.service;

import bo.com.tigo.comodato.AppConfig;
import bo.com.tigo.comodato.DTO.dto.WsComodatoResponse;
import bo.com.tigo.comodato.DTO.dto.WsComodatoResponseErr;
import bo.com.tigo.comodato.DTO.dto.WsComodatoValidRequest;
import bo.com.tigo.comodato.DTO.dto.commonClass.Characteristic;
import bo.com.tigo.comodato.DTO.dto.commonClass.RelatedEntity;
import bo.com.tigo.comodato.DTO.plex.PlexComodatoServiceRequest;
import bo.com.tigo.comodato.DTO.plex.PlexComodatoServiceResponse;
import bo.com.tigo.comodato.DTO.validations.WsRequestValidator;
import bo.com.tigo.comodato.shared.util.CharacteristicFeatureException;
import bo.com.tigo.comodato.shared.util.LogService;
import bo.com.tigo.comodato.shared.util.SupportedException;
import bo.com.tigo.comodato.shared.util.connections.As400ConnectionService;
import bo.com.tigo.comodato.shared.util.connections.CurrentAs400Connection;
import bo.com.tigo.comodato.shared.util.constants.Constants;
import bo.com.tigo.comodato.shared.util.constants.ErrorMessage;
import bo.com.tigo.comodato.shared.util.constants.LogConstants;
import bo.com.tigo.comodato.shared.util.constants.SuccessMessage;
import com.ibm.as400.data.ProgramCallDocument;
import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class ComodatoService {

    private final AppConfig appConfiguration;
    private final DataSource dataSource;
    private final As400ConnectionService as400ConnectionService;
    private final LogService logService;
    private int statusCode;
    private final Logger log = LoggerFactory.getLogger(ComodatoService.class);
    @Autowired
    public ComodatoService(AppConfig appConfiguration, BasicDataSource dataSource,As400ConnectionService as400connection) {
        this.appConfiguration =appConfiguration;
        this.logService = new LogService();
        this.dataSource = dataSource;
        this.as400ConnectionService=as400connection;
    }

    public ResponseEntity<?> associateComodato(Object request, HttpServletRequest requesthead) throws Exception {
        Map<String, String> headers=requesthead.getTrailerFields();
        WsComodatoValidRequest requestDTO = (WsComodatoValidRequest) request;
        WsComodatoResponse responseDTO = null;
        WsComodatoResponseErr responseDTOErr = null;
        int sequence=1;
        String uuid = String.format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16));
        /*
         * Log del Request
         * */
        log.info(logService.getLogRequest(uuid,sequence,
                headers.get(Constants.AUTHORIZATION),
                request,
                headers));
        sequence++;
        //CurrentAs400Connection connection=null;

        try {
            //Validar todos los campos necesarios para la llamada PCML
            checkCharacteristicList(requestDTO);
            // Construccion de la clase PlexComodatoServiceRequest
            PlexComodatoServiceRequest plexRequest = PlexComodatoServiceRequest.from(requestDTO);
            /* * *
             * Se adquiere conexion con la As400
             *  * */

            //Log para informar conexion al AS400
            log.info(logService.getLogStep(uuid,sequence,"Conectando al AS400",
                    appConfiguration.getAppConfiguration().getAs400User(),
                    appConfiguration.getAppConfiguration().getAs400Ip(),System.currentTimeMillis()));
            sequence++;//3


            try(CurrentAs400Connection connection = new CurrentAs400Connection(
                    as400ConnectionService.getConnection(uuid),
                    null,
                    as400ConnectionService)){
                /*
                 * Preparar y Ejecutar el objeto PCML
                 * */
                String pcmlOperation = Constants.ENABLE_SERVICE_OPERATION;
                final ProgramCallDocument pcml = plexRequest.buildPcml(connection, pcmlOperation);
                /*
                 * Log del pcmlRequest
                 * */
                //Log para informar conexion al AS400
                log.info(logService.getLogPcmlRequest(plexRequest,
                        LogConstants.INVOICESERVICE_PLEX,
                        appConfiguration.getAppConfiguration().getAs400User(), uuid,sequence));
                sequence++;//4

                if (pcml.callProgram(pcmlOperation)) {
                    PlexComodatoServiceResponse plexResponse = PlexComodatoServiceResponse.fromPcmlCall(pcml);
                    /*
                     * Log del pcmlResponse
                     * */
                    log.info(logService.getLogPcmlResponse(sequence,
                            plexResponse,
                            LogConstants.INVOICESERVICE_PLEX,
                            appConfiguration.getAppConfiguration().getAs400User(),
                            uuid,
                            System.currentTimeMillis()));
                    sequence++;//5
                    statusCode=200;
                    if(plexResponse.getCode().equals("0")) {
                        log.info(logService.getLogStep(uuid,sequence,
                                "Llamada al programa pcml satisfactorio",
                                appConfiguration.getAppConfiguration().getAs400User(),
                                appConfiguration.getAppConfiguration().getAs400Ip(),
                                System.currentTimeMillis()));
                        sequence++;//6
                        responseDTO =buildGoodResponse(requestDTO);
                    }else{
                        log.info(logService.getLogStep(uuid,sequence,
                                "El proceso no paso una validacion interna del objeto",
                                appConfiguration.getAppConfiguration().getAs400User(),
                                appConfiguration.getAppConfiguration().getAs400Ip(),
                                System.currentTimeMillis()));
                        sequence++;
                        responseDTOErr = buildErrorResponse(plexResponse);
                    }
                } else {
                    statusCode=500;
                    log.info(logService.getLogStep(uuid,sequence,"Error al ejecutar el objeto RPG",
                            appConfiguration.getAppConfiguration().getAs400User(),
                            appConfiguration.getAppConfiguration().getAs400Ip(),System.currentTimeMillis()));
                    sequence++;

                    responseDTO = new WsComodatoResponse();
                    responseDTO.setCodigo(Constants.ERROR);
                    responseDTO.setMensaje("No se pudo ejecutar el objeto RPG");
                }
                connection.close();
            }
        } catch (Exception | CharacteristicFeatureException e ) {
            log.error(logService.getLogException(uuid,sequence,e.getClass().getName(),e.getMessage(),
                    appConfiguration.getAppConfiguration().getAs400User(),
                    appConfiguration.getAppConfiguration().getAs400Ip(),System.currentTimeMillis()));
            responseDTOErr = new WsComodatoResponseErr();
            if (e instanceof CharacteristicFeatureException) {
                // manejar la excepción CharacteristicsException
                statusCode=400;
                responseDTOErr.setErrorCode("400");
                responseDTOErr.setErrorType("BadRequest");
                responseDTOErr.setErrorDescription(e.getMessage());
                responseDTOErr.setStatus("400");
                responseDTOErr.setReferenceError("");
                responseDTOErr.setSchemaLocation("");
                responseDTOErr.setType("");
                responseDTOErr.setBaseType("");
            } else {
                responseDTOErr.setErrorDescription(SupportedException.getMessageFromException(e));
                statusCode = SupportedException.getStatusFromMessage(responseDTOErr.getErrorDescription());
                responseDTOErr.setErrorCode(Integer.toString(statusCode));
                HttpStatus httpstatus= HttpStatus.valueOf(statusCode);
                responseDTOErr.setErrorType(httpstatus.getReasonPhrase());
                responseDTOErr.setStatus(Integer.toString(statusCode));
                responseDTOErr.setReferenceError("");
                responseDTOErr.setSchemaLocation("");
                responseDTOErr.setType("");
                responseDTOErr.setBaseType("");
            }
        }
        if (responseDTOErr!= null){
            log.info(logService.getLogResponse(uuid,sequence,
                    appConfiguration.getAppConfiguration().getApiUrl(),
                    "ERROR",
                    responseDTOErr.getErrorCode(),
                    responseDTOErr.getErrorDescription(),
                    responseDTOErr, System.currentTimeMillis()));
            return ResponseEntity.status(statusCode).body(responseDTOErr);
        }
        log.info(logService.getLogResponse(uuid,sequence,
                appConfiguration.getAppConfiguration().getApiUrl(),
                "OK",
                responseDTO.getCodigo(),
                responseDTO.getMensaje(),
                responseDTO, System.currentTimeMillis()));
        return ResponseEntity.ok().body(responseDTO);
    }

    private void checkCharacteristicList(WsComodatoValidRequest requestDTO) throws Exception, CharacteristicFeatureException {

        requestDTO.setCharacteristic(WsRequestValidator.validateAndSortCharacteristicList(requestDTO.getCharacteristic()));

    }

    private WsComodatoResponse buildGoodResponse(WsComodatoValidRequest requestDTO) {
        WsComodatoResponse responseDTO = new WsComodatoResponse();
        responseDTO.setCodigo(Constants.OK);
        responseDTO.setMensaje(SuccessMessage.SUCCESS_PROCESS.msg());
        responseDTO.setRequestedStartDate(requestDTO.getRequestedStartDate());
        responseDTO.setMandatory(requestDTO.getMandatory());
        responseDTO.setChannel(requestDTO.getChannel());
        responseDTO.setCharacteristic(requestDTO.getCharacteristic());
        responseDTO.setRelatedEntity(requestDTO.getRelatedEntity());
        responseDTO.setType("TaskFlow");
        responseDTO.setBaseType("TaskFlow");

        return responseDTO;
    }
    private WsComodatoResponseErr buildErrorResponse(PlexComodatoServiceResponse plexResponse) {
        WsComodatoResponseErr responseDTOErr = new WsComodatoResponseErr();
        responseDTOErr.setErrorCode(plexResponse.getCode());
        responseDTOErr.setErrorType(ErrorMessage.NOT_PROCESSED.msg());
        responseDTOErr.setErrorDescription(plexResponse.getMessage());
        responseDTOErr.setStatus("204");
        responseDTOErr.setReferenceError("");
        responseDTOErr.setSchemaLocation("");
        responseDTOErr.setType("");
        responseDTOErr.setBaseType("");
        return responseDTOErr;
    }
}
