package bo.com.tigo.comodato.DTO.validations;


import bo.com.tigo.comodato.DTO.dto.WsComodatoValidRequest;

import java.util.function.Function;

public interface WsRequestValidator extends Function<WsComodatoValidRequest, WsRequestValidator.ValidatorResult> {


    /*
    * Validaciones para el campo MSISDN
    * */

    static WsRequestValidator isMsisdnNotNull() {
        return body -> body.getMandatory()== null || body.getRequestedStartDate().isEmpty()
                ? ValidatorResult.NULL_EMPTY_MSISDN
                : ValidatorResult.SUCCESS;
    }
/*
    static WsRequestValidator isValidMsisdn(){
        return body -> body.getMsisdn().length() > 10 ? ValidatorResult.NOT_VALID_MSISDN: ValidatorResult.SUCCESS;
    }


    static WsRequestValidator isMsisdnOnlyNumbers(){
        return body -> body.getMsisdn().matches("[0-9]+") ? ValidatorResult.SUCCESS
                : ValidatorResult.MSISDN_IS_NOT_NUMBER;
    }

*/
   /*
   * Validaciones para el campo de antiguo servicio
   * */
/*
    static WsRequestValidator isOldServiceNotNull() {
        return body -> body.getOldServiceCode()== null || body.getOldServiceCode().isEmpty()
                ? ValidatorResult.NULL_OLD_SERVICE_CODE
                : ValidatorResult.SUCCESS;
    }

    static WsRequestValidator isValidOldServiceCode(){
        return body -> body.getOldServiceCode().length() > 10 ? ValidatorResult.NOT_VALID_OLD_SERVICE_CODE: ValidatorResult.SUCCESS;
    }
*/
    /*
    static WsRequestValidator isOldServiceOnlyNumbers(){
        return body -> body.getOldServiceCode().matches("[0-9]+") ? ValidatorResult.SUCCESS
                : ValidatorResult.OLD_SERVICE_IS_NOT_NUMBER;
    }
*/

/*
* Validaciones para el campo nuevo servicio
* */
/*
    static WsRequestValidator isNewServiceNotNull() {
        return body -> body.getNewServiceCode()== null || body.getNewServiceCode().isEmpty()
                ? ValidatorResult.NULL_NEW_SERVICE_CODE
                : ValidatorResult.SUCCESS;
    }

   static WsRequestValidator isValidNewServiceCode(){
       return body -> body.getNewServiceCode().length() > 10 ? ValidatorResult.NOT_VALID_NEW_SERVICE_CODE: ValidatorResult.SUCCESS;
   }


    static WsRequestValidator isNewServiceOnlyNumbers(){
        return body -> body.getNewServiceCode().matches("[0-9]+") ? ValidatorResult.SUCCESS
                : ValidatorResult.NEW_SERVICE_IS_NOT_NUMBER;
    }

*/


    /*
   static WsRequestValidator isValidAction(){
       return body -> body.getAction().equals("1") || body.getAction().equals("0") ?  ValidatorResult.SUCCESS:ValidatorResult.NOT_VALID_ACTION;
   }

   static WsRequestValidator isActionNotNull() {
       return body -> body.getAction()== null || body.getAction().isEmpty()
               ? ValidatorResult.NULL_ACTION
               : ValidatorResult.SUCCESS;
   }

*/

    /*
    * Las validaciones se verifican primero
    * */
   /* static WsRequestValidator isValidRequest() {
        return isMsisdnNotNull().and(isOldServiceNotNull().and(isNewServiceNotNull()
                                        .and(isValidMsisdn().and(isValidOldServiceCode().and(isValidNewServiceCode()
                                                        .and(isMsisdnOnlyNumbers().and(isOldServiceOnlyNumbers().and(isNewServiceOnlyNumbers()

                                                        ))))))));
    }
*/
    default WsRequestValidator and(WsRequestValidator other) {
        return request -> {
            ValidatorResult result = this.apply(request);
            return result.equals(ValidatorResult.SUCCESS) ? other.apply(request) : result;
        };
    }
    enum ValidatorResult {
        SUCCESS("SUCCESS"),
        NOT_VALID_MSISDN("MSISDN supero el limite de 10 caracteres"),
        MSISDN_IS_NOT_NUMBER("No se permiten letras o caracteres especiales en la campo MSISDN"),
        NULL_EMPTY_MSISDN("MSISDN no puede ser nulo o vacio"),

        NOT_VALID_OLD_SERVICE_CODE("El campo del codigo de servicio actual supero el limite de 10 caracteres"),
        OLD_SERVICE_IS_NOT_NUMBER("No se permiten letras o caracteres especiales en la campo de servicio activo actual"),
        NULL_OLD_SERVICE_CODE("El campo codigo de servicio actual no puede nulo o vacio"),

        NOT_VALID_NEW_SERVICE_CODE("El campo del coodido de nuevo servicio supero el limite de 10 caracteres"),
        NEW_SERVICE_IS_NOT_NUMBER("No se permiten letras o caracteres especiales en la campo de nuevo servicio"),

        NULL_NEW_SERVICE_CODE("El campo codigo del nuevo servicio no puede nulo o vacio"),
        NOT_VALID_ACTION("El valor en el campo accion es invalido"),
        NULL_ACTION("El campo accion no puede ser nulo o vacio");

        private final String description;

        ValidatorResult(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
