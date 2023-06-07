package bo.com.tigo.comodato.DTO.dto;

import bo.com.tigo.comodato.DTO.dto.commonClass.Channel;
import bo.com.tigo.comodato.DTO.dto.commonClass.Characteristic;
import bo.com.tigo.comodato.DTO.dto.commonClass.RelatedEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class WsComodatoResponse {
    @JsonIgnore
    private String codigo;
    @JsonIgnore
    private String mensaje;

    private String requestedStartDate;

    private Boolean isMandatory;

    private List<Channel> channel;

    private List<Characteristic> characteristic;

    private List<RelatedEntity> relatedEntity;
    @JsonProperty("@type")
    private String type;
    @JsonProperty("@baseType")
    private String baseType;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBaseType() {
        return baseType;
    }

    public void setBaseType(String baseType) {
        this.baseType = baseType;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }


    public String getRequestedStartDate() {
        return requestedStartDate;
    }

    public void setRequestedStartDate(String requestedStartDate) {
        this.requestedStartDate = requestedStartDate;
    }

    public Boolean getMandatory() {
        return isMandatory;
    }

    public void setMandatory(Boolean mandatory) {
        isMandatory = mandatory;
    }

    public List<Channel> getChannel() {
        return channel;
    }

    public void setChannel(List<Channel> channel) {
        this.channel = channel;
    }

    public List<Characteristic> getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(List<Characteristic> characteristic) {
        this.characteristic = characteristic;
    }

    public List<RelatedEntity> getRelatedEntity() {
        return relatedEntity;
    }

    public void setRelatedEntity(List<RelatedEntity> relatedEntity) {
        this.relatedEntity = relatedEntity;
    }



}
