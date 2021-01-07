package propets.lostfound.dto;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostSearchDto {
	
	String id;  
	boolean typePost; 
	String userLogin;  
	String userName;
	String avatar;
	
	LocalDateTime datePost;
	
	String type;
	String sex;
	String breed;
	
	String color;
	
	Set<String> tags;
	LocationDto location;
	
	String phone;
	String email;

}
