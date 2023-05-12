package bo.com.tigo.comodato.DTO.dto;

import bo.com.tigo.comodato.DTO.dto.commonClass.Channel;
import bo.com.tigo.comodato.DTO.dto.commonClass.Characteristic;
import bo.com.tigo.comodato.DTO.dto.commonClass.RelatedEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


public class WsComodatoValidRequest {
    @NotNull
    @JsonProperty("requestedStartDate")
    private String requestedStartDate;

    @NotNull
    @JsonProperty("isMandatory")
    private Boolean isMandatory;

    @Valid
    @NotNull
    @Size(min = 1, max = 1)
    @JsonProperty("channel")
    private List<Channel> channel;

    @Valid
    @NotNull
    @Size(min = 8, max = 8)
    @JsonProperty("characteristic")
    private List<Characteristic> characteristic;

    @Valid
    @JsonProperty("relatedEntity")
    private List<RelatedEntity> relatedEntity;

    @NotNull
    @JsonProperty("@type")
    private String type;

    @NotNull
    @JsonProperty("@baseType")
    private String baseType;
    // Constructor, getters, and setters omitted for brevity

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
