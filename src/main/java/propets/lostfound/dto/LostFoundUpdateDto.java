package propets.lostfound.dto;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LostFoundUpdateDto {
	
	String type;
	String sex;
	String breed;
	
	String color;
	Integer minHeight;
	Integer maxHeight;
	String distFeatures;
	String desccription;
	
	Set<String> tags;
	Set<String> photos;
	LocationDto location;
	
	String phone;
	String email;

}
