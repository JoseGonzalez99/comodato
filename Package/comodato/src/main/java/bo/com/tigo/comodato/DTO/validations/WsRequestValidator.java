package bo.com.tigo.comodato.DTO.validations;

import bo.com.tigo.comodato.DTO.dto.WsComodatoValidRequest;
import bo.com.tigo.comodato.DTO.dto.commonClass.Characteristic;
import bo.com.tigo.comodato.shared.util.CharacteristicFeatureException;

import java.util.*;

public class WsRequestValidator {


    public static List<Characteristic> validateAndSortCharacteristicList(List<Characteristic> characteristics) throws CharacteristicFeatureException {

        //Map de los campos de caracteristicas mandatoria
        Map<String, Integer> fieldNamesMap = new LinkedHashMap<>();
        fieldNamesMap.put("numeroDocumento", 20);
        fieldNamesMap.put("tipoDocumento", 5);
        fieldNamesMap.put("clienteId", 9);
        fieldNamesMap.put("contrato", 9);
        fieldNamesMap.put("phoneNumber", 28);
        fieldNamesMap.put("usuario", 10);
        fieldNamesMap.put("identificadorIndividual", 19);
        fieldNamesMap.put("comprobanteCNV", 15);

        List<Characteristic> sortedCharacteristics = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : fieldNamesMap.entrySet()) {
            String fieldName = entry.getKey();
            int fieldCapacity = entry.getValue();
            Characteristic matchingCharacteristic = findCharacteristic(characteristics, fieldName);
            validateCharacteristic(fieldName, matchingCharacteristic, fieldCapacity);
            sortedCharacteristics.add(matchingCharacteristic);
        }
        return sortedCharacteristics;
    }

    private static Characteristic findCharacteristic(List<Characteristic> characteristics, String fieldName) throws CharacteristicFeatureException {
        for (Characteristic characteristic : characteristics) {
            if (fieldName.equals(characteristic.getName())) {
                return characteristic;
            }
        }
        throw new CharacteristicFeatureException("El campo " + fieldName + " no está presente en la lista de características");
    }

    private static void validateCharacteristic(String fieldName, Characteristic characteristic, int fieldCapacity) throws CharacteristicFeatureException {
        if (characteristic == null) {
            throw new CharacteristicFeatureException("El campo " + fieldName + " no está presente en la lista de características");
        }
        if (characteristic.getValue().length() > fieldCapacity) {
            throw new CharacteristicFeatureException("El campo " + fieldName + " supera el límite de caracteres admitidos");
        }

        if((characteristic.getName().equals("comprobanteCNV"))){
                if ( !(characteristic.getValue() .matches("[A-Z]{3}-[A-Z]{2}-[0-9]+"))) {
                    throw new CharacteristicFeatureException("El campo comprobanteCNV no tiene cumple el formato requerido");
                }
            }
        }
    }
