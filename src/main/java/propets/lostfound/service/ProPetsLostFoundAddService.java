package propets.lostfound.service;

import propets.lostfound.dto.LostFoundPetPostDto;
import propets.lostfound.dto.PostSearchDto;

public interface ProPetsLostFoundAddService {
	
	boolean addPostToMapActivities(LostFoundPetPostDto lostFoundPetPostDto);
	
	boolean sendToAsyncSearch(PostSearchDto postSearchDto);	

}
