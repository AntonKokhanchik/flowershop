package flowershop.backend.services;

import org.dozer.CustomConverter;
import org.dozer.MappingException;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DateCustomConverter implements CustomConverter {
    @Override
    public Object convert(Object dest, Object source, Class<?> destClass, Class<?> sourceClass) {
        if (source == null) {
            return null;
        }

        if (source instanceof Timestamp)
            return ((Timestamp) source).toLocalDateTime();
        else if (source instanceof LocalDateTime)
            return Timestamp.valueOf((LocalDateTime) source);
        else
            throw new MappingException("Converter TestCustomConverter "
                    + "used incorrectly. Arguments passed in were:"
                    + dest + " and " + source);

    }
}
