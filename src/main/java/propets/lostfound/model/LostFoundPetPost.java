package propets.lostfound.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = { "id" })
@Document(collection = "lostfound_pets")
public class LostFoundPetPost implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1780427338140821951L;
	
	@Id
	String id;  // +
	boolean typePost; //  lost - false;  found - true;
	String userLogin;  
	String userName;
	String avatar;
	LocalDateTime datePost;  // +
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
	Location location;	
	
	String phone;
	String email;
		
		
	public LostFoundPetPost(boolean typePost, String userLogin, String userName, String avatar,
			String type, String sex, String breed, String color, Integer minHeight,
			Integer maxHeight, String distFeatures, String description, Set<String> tags, Set<String> photos,
			Location location, String phone, String email) {
		
		this.typePost = typePost;
		this.userLogin = userLogin;
		this.userName = userName;
		this.avatar = avatar;
		datePost = LocalDateTime.now();
		this.type = type;
		this.sex = sex;
		this.breed = breed;
		this.color = color;
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
		this.distFeatures = distFeatures;
		this.description = description;

		if(tags != null) {
			this.tags = tags;
		} else {
			this.tags = new HashSet<>();
		}
		
		if(photos != null) {
			this.photos = photos;
		} else {
			this.photos = new HashSet<>();
		}
		
		if(location != null) {
			this.location = location;
		} else {
			this.location = new Location();
		}
				
		this.phone = phone;
		this.email = email;
	}
	
	
	public boolean addTag(String tag) {
		return tags.add(tag);
	}
	
	public boolean removeTag(String tag) {
		return tags.remove(tag);
	}
			
	public boolean addPhoto(String photo) {
		return photos.add(photo);
	}
	
	public boolean removePhoto(String photo) {
		return photos.remove(photo);
	}

	
}
