package bo.com.tigo.comodato.DTO.plex;

import bo.com.tigo.comodato.shared.util.constants.Constants;
import com.ibm.as400.data.PcmlException;
import com.ibm.as400.data.ProgramCallDocument;

public class PlexComodatoServiceResponse {
    private final String code;
    private final String message;



    public PlexComodatoServiceResponse(String code,String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }


    public static PlexComodatoServiceResponse fromPcmlCall(ProgramCallDocument pcml) throws PcmlException {

        return new PlexComodatoServiceResponse(
                pcml.getStringValue("generateInvoice.outputStruct1.output1"),
                pcml.getStringValue("generateInvoice.outputStruct2.output2")
        );
    }


    public boolean isSuccess(){
        return Constants.STATUS_CODE_SUCCESSFUL.equals(this.code);
    }

    @Override
    public String toString() {
        return "PlexGenerateInvoiceServiceResponse{" +
                ", code='" + code + '\'' +
                "message='" + message + '\'' +
                '}';
    }
}
