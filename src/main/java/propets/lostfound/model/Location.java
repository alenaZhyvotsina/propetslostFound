package propets.lostfound.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Location {
	
	String country;
	String city;
	String street;
	Integer building;
	Double longitude;
	Double latitude;
}
