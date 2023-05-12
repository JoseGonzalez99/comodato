package bo.com.tigo.comodato.DTO.plex;

import bo.com.tigo.comodato.DTO.dto.WsComodatoValidRequest;
import bo.com.tigo.comodato.DTO.dto.commonClass.Characteristic;
import bo.com.tigo.comodato.shared.util.connections.CurrentAs400Connection;
import com.ibm.as400.data.PcmlException;
import com.ibm.as400.data.ProgramCallDocument;

import java.util.List;

public class PlexComodatoServiceRequest {


    private List<Characteristic> characteristic;

    private final String numeroDocumento;
    private final String tipoDocumento;
    private final String clienteId;
    private final String contrato;
    private final String phoneNumber;
    private final String usuario;
    private final String identificadorIndividual;
    private final String comprobanteCNV;
    private final String tipoComprobanteCNV;
    private final String serieComprobanteCNV;
    private final String nroComprobanteCNV;
    private final String[] components;

    public PlexComodatoServiceRequest(WsComodatoValidRequest request) {
        this.characteristic = request.getCharacteristic();
        this.numeroDocumento=getCharacteristicValue("numeroDocumento");
        this.tipoDocumento=getCharacteristicValue("tipoDocumento");
        this.clienteId=getCharacteristicValue("clienteId");
        this.contrato=getCharacteristicValue("contrato");
        this.phoneNumber=getCharacteristicValue("phoneNumber");
        this.usuario=getCharacteristicValue("usuario");
        this.identificadorIndividual=getCharacteristicValue("identificadorIndividual");
        this.comprobanteCNV=getCharacteristicValue("comprobanteCNV");
        //splitComprobanteCNV
        this.components=this.comprobanteCNV.split("-");
        this.tipoComprobanteCNV = this.components[0];
        this.serieComprobanteCNV=this.components[1];
        this.nroComprobanteCNV=this.components[2];
    }

    public static PlexComodatoServiceRequest from(WsComodatoValidRequest request){
        return new PlexComodatoServiceRequest(request);
    }

    //buscar valor por nombre por si la lista characteristic no este ordenada
    public String getCharacteristicValue(String name) {
        for (Characteristic characteristic : this.characteristic) {
            if (name.equals(characteristic.getName())) {
                return characteristic.getValue();
            }
        }
        return "notMach " + name;
    }

    public ProgramCallDocument buildPcml(CurrentAs400Connection connection, String pcmlOperation) throws PcmlException {

        ProgramCallDocument pcmlObject = new ProgramCallDocument(connection.as400(),pcmlOperation);
        pcmlObject.setStringValue("generateInvoice.entrada","");
        pcmlObject.setStringValue("generateInvoice.inputStruct1.input1",this.numeroDocumento);
        pcmlObject.setStringValue("generateInvoice.inputStruct2.input2",this.tipoDocumento);
        pcmlObject.setStringValue("generateInvoice.inputStruct3.input3",this.clienteId);
        pcmlObject.setStringValue("generateInvoice.inputStruct4.input4",this.contrato);
        pcmlObject.setStringValue("generateInvoice.inputStruct5.input5",this.phoneNumber);
        pcmlObject.setStringValue("generateInvoice.inputStruct6.input6",this.usuario);
        pcmlObject.setStringValue("generateInvoice.inputStruct7.input7",this.identificadorIndividual);
        pcmlObject.setStringValue("generateInvoice.inputStruct8.input8",this.tipoComprobanteCNV);
        pcmlObject.setStringValue("generateInvoice.inputStruct8.input9",this.serieComprobanteCNV);
        pcmlObject.setStringValue("generateInvoice.inputStruct8.input10",this.nroComprobanteCNV);
        return pcmlObject;

    }
}
