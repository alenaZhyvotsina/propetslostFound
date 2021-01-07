package propets.lostfound.dto;

import java.util.HashSet;
import java.util.Set;

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
public class LostFoundPetNewDto {
	
	String userName;
	String avatar;
	
	String type;
	String sex;
	String breed;
	
	String color;
	Integer minHeight;
	Integer maxHeight;
	String distFeatures;
	String description;
		
	Set<String> tags = new HashSet<>();
	Set<String> photos = new HashSet<>();
	LocationDto location = new LocationDto();
	
	String phone;
	String email;

}
