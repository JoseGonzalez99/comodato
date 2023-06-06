package bo.com.tigo.comodato.DTO.plex;

import bo.com.tigo.comodato.DTO.dto.WsComodatoValidRequest;
import bo.com.tigo.comodato.DTO.dto.commonClass.Characteristic;
import bo.com.tigo.comodato.shared.util.connections.CurrentAs400Connection;
import com.ibm.as400.data.PcmlException;
import com.ibm.as400.data.ProgramCallDocument;

import java.util.List;

public class PlexComodatoServiceRequest {


    private final List<Characteristic> characteristic;

    private final String numeroDocumento;
    private final String tipoDocumento;
    private final String clienteId;
    private final String contrato;
    private final String phoneNumber;
    private final String usuario;
    private final String identificadorIndividual;
    private final String tipoComprobanteCNV;
    private final String serieComprobanteCNV;
    private final String nroComprobanteCNV;

    public PlexComodatoServiceRequest(WsComodatoValidRequest request) {
        this.characteristic = request.getCharacteristic();
        this.numeroDocumento=request.getCharacteristic().get(0).getValue();
        this.tipoDocumento=request.getCharacteristic().get(1).getValue();
        this.clienteId=request.getCharacteristic().get(2).getValue();
        this.contrato=request.getCharacteristic().get(3).getValue();
        this.phoneNumber=request.getCharacteristic().get(4).getValue();
        this.usuario=request.getCharacteristic().get(5).getValue();
        this.identificadorIndividual=request.getCharacteristic().get(6).getValue();
        String comprobanteCNV = request.getCharacteristic().get(7).getValue();
        //splitComprobanteCNV
        String[] components = comprobanteCNV.split("-");
        this.tipoComprobanteCNV = components[0];
        this.serieComprobanteCNV= components[1];
        this.nroComprobanteCNV= components[2];
    }

    public static PlexComodatoServiceRequest from(WsComodatoValidRequest request){
        return new PlexComodatoServiceRequest(request);
    }

    public ProgramCallDocument buildPcml(CurrentAs400Connection connection, String pcmlOperation) throws PcmlException {

        ProgramCallDocument pcmlObject = new ProgramCallDocument(connection.as400(),pcmlOperation);
        pcmlObject.setStringValue("comodato.entrada","");
        pcmlObject.setStringValue("comodato.inputStruct1.input1",this.numeroDocumento);
        pcmlObject.setStringValue("comodato.inputStruct2.input2",this.tipoDocumento);
        pcmlObject.setStringValue("comodato.inputStruct3.input3",this.clienteId);
        pcmlObject.setStringValue("comodato.inputStruct4.input4",this.contrato);
        pcmlObject.setStringValue("comodato.inputStruct5.input5",this.phoneNumber);
        pcmlObject.setStringValue("comodato.inputStruct6.input6",this.usuario);
        pcmlObject.setStringValue("comodato.inputStruct7.input7",this.identificadorIndividual);
        pcmlObject.setStringValue("comodato.inputStruct8.input8",this.tipoComprobanteCNV);
        pcmlObject.setStringValue("comodato.inputStruct9.input9",this.serieComprobanteCNV);
        pcmlObject.setStringValue("comodato.inputStruct10.input10",this.nroComprobanteCNV);
        return pcmlObject;

    }
}
