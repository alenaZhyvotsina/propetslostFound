package propets.lostfound.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LostFoundFilterDto {
	
	String type;
	String sex;
	String breed;
	String additionalFeatures;
	Set<String> tags = new HashSet<>();
	LocationDto location = new LocationDto();

}
