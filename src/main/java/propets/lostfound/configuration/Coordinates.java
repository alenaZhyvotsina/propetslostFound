package propets.lostfound.configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Coordinates {
	
	Double latitude;  // in degrees
	Double longitude; // in degrees
	
	
	
	public Coordinates(Double latitude, Double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	/**
	 * 
	 * @param distance - distance in meters, 1500 by default
	 *  
	 * @return double[distanceLatitudeDeg, distanceLongitudeDeg]
	 */
	public static double[] distancesNear(Double latitude, Double longitude, int distance) {
		/*
			l - length in meters
			m = (360 * sin(α) * l)/40000000   
			p = (360 * cos(α) * l)/(40075696 * cos(φ))
		*/	
		
		if(latitude == null || longitude == null) {
			return null;
		}
		
		if(distance<=0) distance = 1500;
				
		 
		double distLongitude = (360 * Math.sin(Math.toRadians(longitude)) * distance) / 40_000_000;
		//FIXME
		double distLatitude = (360 * Math.cos(Math.toRadians(longitude)) * distance) 
									/ (40_075_696 * Math.cos(Math.toRadians(latitude)));
		
		System.out.println("long - " + distLongitude);
		System.out.println("lat - " + distLatitude);
				
		return new double[]{distLatitude, distLongitude};
		
	}
	
}
