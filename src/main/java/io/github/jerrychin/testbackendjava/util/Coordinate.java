package io.github.jerrychin.testbackendjava.util;

import java.math.BigDecimal;

public class Coordinate {
	private static final double EARTH_RADIUS_IN_KM = 6371;

	public final BigDecimal longitude;
	public final BigDecimal latitude;

	public Coordinate(final BigDecimal longitude, final BigDecimal latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}

	/**
	 * Calculate the distance between two coordinates on the Earth.
	 *
	 * See <a href="https://stackoverflow.com/questions/365826/calculate-distance-between-2-gps-coordinates">calculate-distance-between-2-gps-coordinates</a>
	 * <br />
	 *
	 * <a href="http://www.gpsspg.com/maps.htm">GPSspg</a>
	 *
	 * @param a1 coordinates to be measured.
	 * @param a2 coordinates to be measured.
	 * @return a1 and a2 distance in meter.
	 */
	public static long dBetween(Coordinate a1,  Coordinate a2 ) {
		double dLat = Math.toRadians(a1.latitude.subtract(a2.latitude).doubleValue());
		double dLon = Math.toRadians(a1.longitude.subtract(a2.longitude).doubleValue());

		double lat1 = Math.toRadians(a1.latitude.doubleValue());
		double lat2 = Math.toRadians(a2.latitude.doubleValue());

		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
				Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return Math.round(EARTH_RADIUS_IN_KM * c * 1000);
	}
}
