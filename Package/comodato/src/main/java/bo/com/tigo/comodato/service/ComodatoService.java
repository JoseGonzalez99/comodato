package bo.com.tigo.comodato.service;

import bo.com.tigo.comodato.AppConfig;
import bo.com.tigo.comodato.DTO.dto.WsComodatoResponse;
import bo.com.tigo.comodato.DTO.dto.WsComodatoValidRequest;
import bo.com.tigo.comodato.DTO.dto.commonClass.Characteristic;
import bo.com.tigo.comodato.DTO.dto.commonClass.RelatedEntity;
import bo.com.tigo.comodato.DTO.plex.PlexComodatoServiceRequest;
import bo.com.tigo.comodato.DTO.plex.PlexComodatoServiceResponse;
import bo.com.tigo.comodato.shared.util.CharacteristicFeatureException;
import bo.com.tigo.comodato.shared.util.LogService;
import bo.com.tigo.comodato.shared.util.SupportedException;
import bo.com.tigo.comodato.shared.util.connections.As400ConnectionService;
import bo.com.tigo.comodato.shared.util.connections.CurrentAs400Connection;
import bo.com.tigo.comodato.shared.util.constants.Constants;
import bo.com.tigo.comodato.shared.util.constants.LogConstants;
import com.ibm.as400.data.ProgramCallDocument;
import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final String uuid = String.format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16));
    @Autowired
    public ComodatoService(AppConfig appConfiguration, BasicDataSource dataSource,As400ConnectionService as400connection) {
        this.appConfiguration =appConfiguration;
        this.logService = new LogService();
        this.dataSource = dataSource;
        //this.as400ConnectionService = new As400ConnectionService(appConfiguration.getAppConfiguration());
        this.as400ConnectionService=as400connection;
    }

    public ResponseEntity<?> associateComodato(Object request, HttpServletRequest requesthead) throws Exception {
        Map<String, String> headers=requesthead.getTrailerFields();
        WsComodatoValidRequest requestDTO = (WsComodatoValidRequest) request;
        WsComodatoResponse responseDTO = null;

        /*
         * Log del Request
         * */
        log.info(logService.getLogRequest(uuid,
                headers.get(Constants.AUTHORIZATION),
                request,
                headers));
        try {
            /* *
             * Validar todos los campos necesarios para la llamada PCML
             * lanza una excepcion El campo " + fieldName + " no está presente en la lista de características
             * */
            validateDataCharacterictic(requestDTO);
            /* *
             * Validar el formato del campo comprobanteCNV (tipoComprobante-serieComprobante-nroComprobante)
            * */
            validateComprobanteCNV(requestDTO);
            /* * *
             * Construccion de la clase PlexComodatoServiceRequest
             *  * */
            PlexComodatoServiceRequest plexRequest = PlexComodatoServiceRequest.from(requestDTO);
            /* * *
             * Se adquiere conexion con la As400
             *  * */

            log.info("Conectando al AS400.");
            CurrentAs400Connection connection = new CurrentAs400Connection(
                    as400ConnectionService.getConnection(),
                    appConfiguration.dataSource().getConnection(),
                    as400ConnectionService);

            /*
             * Preparar y Ejecutar el objeto PCML
             * */
            String pcmlOperation = Constants.ENABLE_SERVICE_OPERATION;
            final ProgramCallDocument pcml = plexRequest.buildPcml(connection, pcmlOperation);
            /*
             * Log del pcmlRequest
             * */
            log.info(logService.getLogPcmlRequest(plexRequest,
                    LogConstants.INVOICESERVICE_PLEX,
                    appConfiguration.getAppConfiguration().getAs400User(), uuid));


            if (pcml.callProgram(pcmlOperation)) {
                PlexComodatoServiceResponse plexResponse = PlexComodatoServiceResponse.fromPcmlCall(pcml);
                /*
                 * Log del pcmlResponse
                 * */
                log.info(logService.getLogPcmlResponse(
                        plexResponse,
                        LogConstants.INVOICESERVICE_PLEX,
                        appConfiguration.getAppConfiguration().getAs400User(),
                        uuid,
                        System.currentTimeMillis()));

                if(plexResponse.getCode().equals("0")) {
                    log.info("Llamada al programa pcml satisfactorio!!");
                    responseDTO=buildResponse(plexResponse);
                }else{
                    /*Error Type*/
                    log.info("Llamada al programa pcml No satisfactorio!!");
                    responseDTO = new WsComodatoResponse();
                    responseDTO.setCodigo(plexResponse.getCode());
                    //responseDTO.setErrorType(plexResponse.getMessage());
                    responseDTO.setMensaje("Error recibido en la llamada del programa pcml");
                }
            } else {
                log.error("Error al ejecutar el objeto!!");
                responseDTO = new WsComodatoResponse();
                responseDTO.setCodigo(Constants.STATUS_CODE_ERROR);
                //responseDTO.setErrorType("Internal Server Error");
                responseDTO.setMensaje("Llamada al programa pcml No satisfactorio!!");
            }

            /*
             * Se cierra la conexion a la AS400 y BD
             * */
            connection.close();
            System.out.println("Se finalizo la conexion correctamente");
        } catch (Exception | CharacteristicFeatureException e ) {
            log.info("A ocurrido un error de ejecucion!!");
            log.info("Exception:" + e);
            log.error("TID = {} - Error al realizar la operacion: {}", uuid, e.getMessage());
            responseDTO = new WsComodatoResponse();
            if (e instanceof CharacteristicFeatureException) {
                // manejar la excepción CharacteristicsException
                responseDTO.setCodigo("400");
                //responseDTO.setErrorType("BadRequest");
                responseDTO.setMensaje(((CharacteristicFeatureException) e).getMessage());
            } else {
                //responseDTO.setErrorType(Constants.ERROR);
                responseDTO.setMensaje(SupportedException.getMessageFromException(e));
                statusCode = SupportedException.getStatusFromMessage(responseDTO.getMensaje());
                responseDTO.setCodigo(Integer.toString(statusCode));
            }
        }

        if (!responseDTO.getCodigo().equals("OK"))
            return ResponseEntity.badRequest().body(responseDTO);

        log.info(logService.getLogResponse(uuid,
                appConfiguration.getAppConfiguration().getApiUrl(),
                null,
                responseDTO.getCodigo(),
                responseDTO.getMensaje(),
                responseDTO, System.currentTimeMillis()));
        return ResponseEntity.ok().body(responseDTO);

    }

    private void validateDataCharacterictic(WsComodatoValidRequest requestDTO) throws Exception, CharacteristicFeatureException {

        List<String> fieldNames = Arrays.asList("numeroDocumento", "tipoDocumento",
                                                "clienteId","contrato","phoneNumber","usuario",
                                                "identificadorIndividual","comprobanteCNV");
        Characteristic.validate(requestDTO.getCharacteristic(), fieldNames); // Validas los campos
    }
    private void validateComprobanteCNV(WsComodatoValidRequest requestDTO) throws CharacteristicFeatureException {
        String value = getCharacteristicValue("comprobanteCNV", requestDTO.getCharacteristic());
        if (value.matches("[A-Z]{3}-[A-Z]{2}-[0-9]+")) {

        }else {
            throw new CharacteristicFeatureException("El campo comprobanteCNV no tiene el formato esperado");
        }
    }

    private WsComodatoResponse buildResponse(PlexComodatoServiceResponse plexResponse) {
        WsComodatoResponse responseDTO = new WsComodatoResponse();
        responseDTO.setCodigo("OK");
        responseDTO.setMensaje(plexResponse.getMessage());
        return responseDTO;
    }
    public String getCharacteristicValue(String name, List<Characteristic> characteristicList) {
        for (Characteristic characteristic : characteristicList) {
            if (name.equals(characteristic.getName())) {
                return characteristic.getValue();
            }
        }
        return "notMach " + name;
    }

}
