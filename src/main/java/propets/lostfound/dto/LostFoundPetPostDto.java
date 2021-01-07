package propets.lostfound.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LostFoundPetPostDto {

	String id;  
	boolean typePost; 
	String userLogin;  
	String userName;
	String avatar;
	
	@JsonFormat(pattern = "dd.MM.yyyy HH:mm")
	LocalDateTime datePost;  
	
	String type;
	String sex;
	String breed;
	
	String color;
	Integer minHeight;
	Integer maxHeight;
	String distFeatures;
	String description;
	
	Set<String> tags;
	Set<String> photos;
	LocationDto location;
	
	String phone;
	String email;
}
