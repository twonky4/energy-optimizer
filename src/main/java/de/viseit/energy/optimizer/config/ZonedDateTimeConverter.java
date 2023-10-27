package de.viseit.energy.optimizer.config;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ZonedDateTimeConverter implements AttributeConverter<ZonedDateTime, Timestamp> {
	public static final ZoneId ZONE_EUROPE_BERLIN = ZoneId.of("Europe/Berlin");

	@Override
	public Timestamp convertToDatabaseColumn(ZonedDateTime zonedDateTime) {
		if (zonedDateTime == null) {
			return null;
		}
		return Timestamp.valueOf(zonedDateTime.toLocalDateTime());
	}

	@Override
	public ZonedDateTime convertToEntityAttribute(Timestamp timestamp) {
		if (timestamp == null) {
			return null;
		}
		return timestamp.toLocalDateTime().atZone(ZONE_EUROPE_BERLIN);
	}
}
