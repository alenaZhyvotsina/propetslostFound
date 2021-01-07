package propets.lostfound.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LocationDto {
	
	String country;
	String city;
	String street;
	Integer building;
	Double longitude;
	Double latitude;

}
