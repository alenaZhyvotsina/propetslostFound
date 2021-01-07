package propets.lostfound.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import propets.lostfound.dto.LostFoundPetNewDto;
import propets.lostfound.dto.LostFoundPetPostDto;
import propets.lostfound.dto.LostFoundUpdateDto;
import propets.lostfound.dto.PostsIdDto;
import propets.lostfound.dto.TagsAndColorsOfPictureDto;
import propets.lostfound.dto.ImageUrlRequestDto;
import propets.lostfound.dto.LostFoundFilterDto;
import propets.lostfound.service.ProPetsLostFoundService;

@RestController
@RequestMapping("/lostfound/en/v1")
public class ProPetsLostFoundController {
	
	@Autowired
	ProPetsLostFoundService lostFoundService;
	
	@PostMapping("/lost/{userLogin}")
	public LostFoundPetPostDto addNewLostPost(@PathVariable String userLogin, 
			@RequestBody LostFoundPetNewDto lostFoundPetNewDto) {
				
		return lostFoundService.addLostFoundPetPost(false, userLogin, lostFoundPetNewDto);
	}
	
	@PostMapping("/found/{userLogin}")
	public LostFoundPetPostDto addNewFoundPost(@PathVariable String userLogin, 
			@RequestBody LostFoundPetNewDto lostFoundPetNewDto) {
		
		return lostFoundService.addLostFoundPetPost(true, userLogin, lostFoundPetNewDto);
	}
	
	@GetMapping("/{id}")
	public LostFoundPetPostDto findPostbyId(@PathVariable String id) {
		return lostFoundService.findLostFoundPetPostById(id);
	}
	
	@PutMapping("/{id}")
	public LostFoundPetPostDto editPostbyId(@PathVariable String id, @RequestBody LostFoundUpdateDto lostFoundUpdateDto) {
		return lostFoundService.editLostFoundPetPost(id, lostFoundUpdateDto);
	}
	
	@DeleteMapping("/{id}")
	public LostFoundPetPostDto deletePostbyId(@PathVariable String id) {
		return lostFoundService.deleteLostFoundPetPost(id);
	}
	
	@GetMapping("/losts")
	public List<LostFoundPetPostDto> findLosts() {
		return lostFoundService.getLostFoundPosts(false);
	}
	
	@GetMapping("/founds")
	public List<LostFoundPetPostDto> findFounds() {
		return lostFoundService.getLostFoundPosts(true);
	}
	
	@PostMapping("/losts/filter")
	public List<LostFoundPetPostDto> findLostsByInformation(@RequestBody LostFoundFilterDto lostFoundFilterDto) {
		return lostFoundService.findLostFoundPostsByInformation(false, lostFoundFilterDto);
	}
	
	@PostMapping("/founds/filter")
	public List<LostFoundPetPostDto> findFoundsByInformation(@RequestBody LostFoundFilterDto lostFoundFilterDto) {
		return lostFoundService.findLostFoundPostsByInformation(true, lostFoundFilterDto);
	}
	
	@PostMapping("/tags")
	public TagsAndColorsOfPictureDto getPictureTags(@RequestBody ImageUrlRequestDto imageUrlRequestDto) {
		return lostFoundService.getTagsAndColorsOfPicture(imageUrlRequestDto);
	}
	
	@PostMapping("/userdata")
	public List<LostFoundPetPostDto> getLostFoundPostsOfUser(@RequestBody PostsIdDto postsIdDto) {
		return lostFoundService.getPostsOfUser(postsIdDto);
	}

}
