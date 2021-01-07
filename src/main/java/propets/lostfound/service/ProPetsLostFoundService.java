package propets.lostfound.service;

import java.util.List;

import propets.lostfound.dto.LostFoundPetNewDto;
import propets.lostfound.dto.LostFoundPetPostDto;
import propets.lostfound.dto.LostFoundUpdateDto;
import propets.lostfound.dto.PostsIdDto;
import propets.lostfound.dto.ImageUrlRequestDto;
import propets.lostfound.dto.LostFoundFilterDto;
import propets.lostfound.dto.TagsAndColorsOfPictureDto;

public interface ProPetsLostFoundService {
	
	LostFoundPetPostDto addLostFoundPetPost(boolean flagFound, String userLogin, LostFoundPetNewDto lostFoundPetNewDto);
	
	LostFoundPetPostDto findLostFoundPetPostById(String id);
	
	LostFoundPetPostDto editLostFoundPetPost(String id, LostFoundUpdateDto lostFoundUpdateDto);
	
	LostFoundPetPostDto deleteLostFoundPetPost(String id);
	
	List<LostFoundPetPostDto> getLostFoundPosts(boolean flagFound);
	
	List<LostFoundPetPostDto> findLostFoundPostsByInformation(boolean flagFound, LostFoundFilterDto lostFoundFilterDto);
	
	TagsAndColorsOfPictureDto getTagsAndColorsOfPicture(ImageUrlRequestDto imageUrlRequestDto);
	
	List<LostFoundPetPostDto> getPostsOfUser(PostsIdDto postsIdDto);
	

}
