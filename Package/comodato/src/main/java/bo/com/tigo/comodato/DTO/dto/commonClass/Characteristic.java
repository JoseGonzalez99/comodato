package bo.com.tigo.comodato.DTO.dto.commonClass;

import bo.com.tigo.comodato.shared.util.CharacteristicFeatureException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Characteristic {
    @NotNull
    @NotBlank
    @NotEmpty
    @JsonProperty("name")
    private String name;
    @NotNull
    @JsonProperty("valueType")
    private String valueType;
    @NotNull
    @NotEmpty
    @NotBlank
    @JsonProperty("value")
    private String value;

    public Characteristic() {
    }

    public Characteristic(String name, String valueType, String value) {
        this.name = name;
        this.valueType = valueType;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static void validate(List<Characteristic> characteristics, List<String> fieldNames) throws CharacteristicFeatureException  {
        for (String fieldName : fieldNames) {
            boolean found = false;
            for (Characteristic characteristic : characteristics) {
                if (fieldName.equals(characteristic.getName())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new CharacteristicFeatureException("El campo " + fieldName + " no está presente en la lista de características");
            }
        }
    }



}
