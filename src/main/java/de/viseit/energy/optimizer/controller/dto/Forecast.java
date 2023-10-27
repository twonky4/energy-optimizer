package de.viseit.energy.optimizer.controller.dto;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Forecast {
	private Result result;
	private Message message;

	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Result {
		private Map<String, BigDecimal> watts;
		@JsonProperty("watt_hours_period")
		private Map<String, BigDecimal> wattHoursPeriod;
		@JsonProperty("watt_hours")
		private Map<String, BigDecimal> wattHours;
		@JsonProperty("watt_hours_day")
		private Map<String, BigDecimal> wattHoursDay;
	}

	@Data
	public static class Message {
		private int code;
		private String type;
		private String text;
		private String pid;
		private Info info;
		@JsonProperty("ratelimit")
		private RateLimit rateLimit;

		@Data
		public static class Info {
			private BigDecimal latitude;
			private BigDecimal longitude;
			private BigDecimal distance;
			private String place;
			private String timezone;
			private String time;
			@JsonProperty("time_utc")
			private String timeUtc;
		}

		@Data
		public static class RateLimit {
			private BigDecimal period;
			private BigDecimal longitude;
			private BigDecimal remaining;
		}
	}
}
