package v2.sideproject.store.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.LocalDateTime;

@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, String> {

    @Override
    public String convertToDatabaseColumn(LocalDateTime attribute) {
        return DateFormatter.format(attribute);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(String dbData) {
        return DateFormatter.parse(dbData);
    }
}