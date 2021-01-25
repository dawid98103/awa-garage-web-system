package pl.dkobylarz.garage_system_api.util;

import pl.dkobylarz.garage_system_api.car.constant.EngineType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class EngineTypeConverter implements AttributeConverter<EngineType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EngineType engineType) {
        return engineType.getTypeId();
    }

    @Override
    public EngineType convertToEntityAttribute(Integer integer) {
        return EngineType.getEngineTypeByTypeId(integer);
    }
}
