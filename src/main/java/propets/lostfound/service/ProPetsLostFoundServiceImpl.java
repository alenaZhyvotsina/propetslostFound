package propets.lostfound.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import propets.lostfound.configuration.Coordinates;
import propets.lostfound.dao.ProPetsLostFoundMongoRepository;
import propets.lostfound.dto.ImageUrlRequestDto;
import propets.lostfound.dto.LostFoundFilterDto;
import propets.lostfound.dto.LostFoundPetNewDto;
import propets.lostfound.dto.LostFoundPetPostDto;
import propets.lostfound.dto.LostFoundUpdateDto;
import propets.lostfound.dto.PostSearchDto;
import propets.lostfound.dto.PostsIdDto;
import propets.lostfound.dto.TagsAndColorsOfPictureDto;
import propets.lostfound.exceptions.BadDataException;
import propets.lostfound.exceptions.PostNotFoundException;
import propets.lostfound.exceptions.TaggingServiceException;
import propets.lostfound.model.Location;
import propets.lostfound.model.LostFoundPetPost;

@Service
public class ProPetsLostFoundServiceImpl implements ProPetsLostFoundService, ProPetsLostFoundAddService {

	@Autowired
	ProPetsLostFoundMongoRepository lostFoundRepository;

	@Autowired
	ModelMapper mapper;

	@Autowired
	MongoTemplate mongoTemplate;

	@Value("${postsearcher.value}")
	String postSearcerServiceUrl;

	@Autowired
	RestTemplate restTemplate;

	@Override
	@Transactional
	public LostFoundPetPostDto addLostFoundPetPost(boolean flagFound, String userLogin,
			LostFoundPetNewDto lostFoundPetNewDto) {

		if (lostFoundPetNewDto == null) {
			throw new BadDataException();
		}

		LostFoundPetPost lostFoundPetPost = new LostFoundPetPost(flagFound, userLogin, lostFoundPetNewDto.getUserName(),
				lostFoundPetNewDto.getAvatar(), lostFoundPetNewDto.getType(), lostFoundPetNewDto.getSex(),
				lostFoundPetNewDto.getBreed(), lostFoundPetNewDto.getColor(), lostFoundPetNewDto.getMinHeight(),
				lostFoundPetNewDto.getMaxHeight(), lostFoundPetNewDto.getDistFeatures(),
				lostFoundPetNewDto.getDescription(), lostFoundPetNewDto.getTags(), lostFoundPetNewDto.getPhotos(),
				mapper.map(lostFoundPetNewDto.getLocation(), Location.class), lostFoundPetNewDto.getPhone(),
				lostFoundPetNewDto.getEmail());

		LostFoundPetPost lostFoundPetPostSaved = lostFoundRepository.save(lostFoundPetPost);

		LostFoundPetPostDto lostFoundPetPostDtoSaved = mapper.map(lostFoundPetPostSaved, LostFoundPetPostDto.class);
		
		PostSearchDto postSearchDto = 
				new PostSearchDto(lostFoundPetPostSaved.getId(), 
								  flagFound, 
								  lostFoundPetPostSaved.getUserLogin(), 
								  lostFoundPetPostSaved.getUserName(), 
								  lostFoundPetPostSaved.getAvatar(), 
								  lostFoundPetPostSaved.getDatePost(), 
								  lostFoundPetPostSaved.getType(), 
								  lostFoundPetPostSaved.getSex(),
								  lostFoundPetPostSaved.getBreed(),
								  lostFoundPetPostSaved.getColor(),
								  lostFoundPetPostSaved.getTags(),
								  lostFoundPetPostDtoSaved.getLocation(),
								  lostFoundPetPostSaved.getPhone(),
								  lostFoundPetPostSaved.getEmail());
						
		sendToAsyncSearch(postSearchDto);
		
		// TODO
		// addPostToMapActivities(lostFoundPetPostDtoSaved);

		return lostFoundPetPostDtoSaved;
	}
		

	@Override
	public LostFoundPetPostDto findLostFoundPetPostById(String id) {
		LostFoundPetPost lostFoundPetPost = lostFoundRepository.findById(id)
				.orElseThrow(() -> new PostNotFoundException(id));

		return mapper.map(lostFoundPetPost, LostFoundPetPostDto.class);
	}

	@Override
	@Transactional
	public LostFoundPetPostDto editLostFoundPetPost(String id, LostFoundUpdateDto lostFoundUpdateDto) {
		LostFoundPetPost lostFoundPetPost = lostFoundRepository.findById(id)
				.orElseThrow(() -> new PostNotFoundException(id));

		String type = lostFoundUpdateDto.getType();
		if (type != null && !"".equals(type) && !lostFoundPetPost.getType().equalsIgnoreCase(type)) {
			lostFoundPetPost.setType(type);
		}

		String sex = lostFoundUpdateDto.getSex();
		if (sex != null && !"".equals(sex) && !lostFoundPetPost.getSex().equalsIgnoreCase(sex)) {
			lostFoundPetPost.setSex(sex);
		}

		String breed = lostFoundUpdateDto.getBreed();
		if (breed != null && !"".equals(breed) && !lostFoundPetPost.getBreed().equalsIgnoreCase(breed)) {
			lostFoundPetPost.setBreed(breed);
		}

		Set<String> tags = lostFoundUpdateDto.getTags();
		if (tags != null && !tags.isEmpty()) {
			lostFoundPetPost.getTags().clear();
			lostFoundPetPost.setTags(tags);
		}

		Set<String> photos = lostFoundUpdateDto.getPhotos();
		if (photos != null && !photos.isEmpty()) {
			lostFoundPetPost.getPhotos().clear();
			lostFoundPetPost.setPhotos(photos);
		}

		Location locationNew = mapper.map(lostFoundUpdateDto.getLocation(), Location.class);
		Location location = lostFoundPetPost.getLocation();
		if (location != null) {
			String country = locationNew.getCountry();
			if (country != null && !"".equals(country) && !country.equalsIgnoreCase(location.getCountry())) {
				location.setCountry(country);
			}
			String city = locationNew.getCity();
			if (city != null && !"".equals(city) && !city.equalsIgnoreCase(location.getCity())) {
				location.setCity(city);
			}
			String street = locationNew.getStreet();
			if (street != null && !"".equals(street) && !street.equalsIgnoreCase(location.getStreet())) {
				location.setStreet(street);
			}
			Integer building = locationNew.getBuilding();
			if (building != null && building > 0 && !building.equals(location.getBuilding())) {
				location.setBuilding(building);
			}
			Double latitude = locationNew.getLatitude();
			if (latitude != null && latitude > 0 && !latitude.equals(location.getLatitude())) {
				location.setLatitude(latitude);
			}
			Double longitude = locationNew.getLongitude();
			if (longitude != null && longitude > 0 && !longitude.equals(location.getLongitude())) {
				location.setLongitude(longitude);
			}
		}

		lostFoundRepository.save(lostFoundPetPost);

		return mapper.map(lostFoundPetPost, LostFoundPetPostDto.class);
	}

	@Override
	@Transactional
	public LostFoundPetPostDto deleteLostFoundPetPost(String id) {
		LostFoundPetPost lostFoundPetPost = lostFoundRepository.findById(id)
				.orElseThrow(() -> new PostNotFoundException(id));

		lostFoundRepository.delete(lostFoundPetPost);

		return mapper.map(lostFoundPetPost, LostFoundPetPostDto.class);
	}

	@Override
	public List<LostFoundPetPostDto> getLostFoundPosts(boolean flagFound) {
		return lostFoundRepository.findByTypePostOrderByDatePostDesc(flagFound).stream()
				.map((p -> mapper.map(p, LostFoundPetPostDto.class))).collect(Collectors.toList());
	}

	@Override
	public List<LostFoundPetPostDto> findLostFoundPostsByInformation(boolean flagFound,
			LostFoundFilterDto lostFoundFilterDto) {
		Query query = new Query();
		query.addCriteria(Criteria.where("typePost").is(flagFound));

		String type = lostFoundFilterDto.getType();
		if (type != null && !"".equals(type)) {
			query.addCriteria(Criteria.where("type").is(type));
		}

		String sex = lostFoundFilterDto.getSex();
		if (sex != null && !"".equals(sex) && !"unknown".equals(sex)) {
			query.addCriteria(Criteria.where("sex").in(sex, "unknown"));
		}

		String breed = lostFoundFilterDto.getBreed();
		if (breed != null && !"".equals(breed)) {
			query.addCriteria(Criteria.where("breed").regex(".*" + breed + ".*", "i"));
		}

		Set<String> tags = lostFoundFilterDto.getTags();
		if (tags != null && !tags.isEmpty()) {
			query.addCriteria(Criteria.where("tags").in(tags));
		}

		if (lostFoundFilterDto.getLocation() != null) {
			Location location = mapper.map(lostFoundFilterDto.getLocation(), Location.class);
			if (location != null) {
				if (location.getCountry() != null) {
					query.addCriteria(Criteria.where("location.country").is(location.getCountry()));
				}
				if (location.getCity() != null) {
					query.addCriteria(Criteria.where("location.city").is(location.getCity()));
				}
				if (location.getBuilding() != null) {
					query.addCriteria(Criteria.where("location.building").is(location.getBuilding()));
				}
				if (location.getLatitude() != null && location.getLongitude() != null) {
					/*
					 * NearQuery nearQuery = NearQuery.near(new Point(location.getLongitude(),
					 * location.getLatitude())) .maxDistance(1, Metrics.KILOMETERS);
					 */
					double[] dist = Coordinates.distancesNear(location.getLatitude(), location.getLongitude(), 1500);

					query.addCriteria(Criteria.where("location.latitude").gte(location.getLatitude() - dist[0])
							.andOperator(Criteria.where("location.latitude").lte(location.getLatitude() + dist[0])));

					query.addCriteria(Criteria.where("location.longitude").gte(location.getLongitude() - dist[1])
							.andOperator(Criteria.where("location.longitude").lte(location.getLongitude() + dist[1])));

				}
			}
		}

		query.with(Sort.by(Sort.Direction.DESC, "datePost"));

		System.out.println(query.toString());

		List<LostFoundPetPost> posts = mongoTemplate.find(query, LostFoundPetPost.class);

		return posts.stream().map(p -> mapper.map(p, LostFoundPetPostDto.class)).collect(Collectors.toList());
	}

	@Override
	public TagsAndColorsOfPictureDto getTagsAndColorsOfPicture(ImageUrlRequestDto imageUrlRequestDto) {

		RestTemplate restTemplate = new RestTemplate();

		String url = "http://localhost:9090/tagging/tags";

		URL urlUrl;
		try {
			urlUrl = new URL(url);
			try {
				HttpURLConnection con = (HttpURLConnection) urlUrl.openConnection();
				System.out.println(con.getResponseCode());
				// urlUrl.openConnection();
			} catch (IOException e) {
				throw new TaggingServiceException();
			}
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}

		HttpEntity<ImageUrlRequestDto> requestEntity = new HttpEntity<>(imageUrlRequestDto);

		ResponseEntity<TagsAndColorsOfPictureDto> responseEntity = restTemplate.exchange(URI.create(url),
				HttpMethod.POST, requestEntity, TagsAndColorsOfPictureDto.class);

		if (responseEntity.getStatusCode().isError()) {
			throw new TaggingServiceException();
		}
		return responseEntity.getBody();
	}

	@Override
	public List<LostFoundPetPostDto> getPostsOfUser(PostsIdDto postsIdDto) {
		return lostFoundRepository.findByIdIn(postsIdDto.getIds()).stream()
				.map(p -> mapper.map(p, LostFoundPetPostDto.class)).collect(Collectors.toList());
	}

	@Override
	public boolean addPostToMapActivities(LostFoundPetPostDto lostFoundPetPostDto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sendToAsyncSearch(PostSearchDto postSearchDto) {
		String url = postSearcerServiceUrl + "/searcher";

		URI uri;
		try {
			uri = new URI(url);

			HttpEntity<PostSearchDto> requestEntity = new HttpEntity<>(postSearchDto);

			ResponseEntity<List<String>> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, requestEntity,
					new ParameterizedTypeReference<List<String>>() {
					});
			
			responseEntity.getBody().forEach(e -> System.out.println("email - " + e));

			return true;

		} catch (URISyntaxException e) {
			e.printStackTrace();
			return false;
		}
	}

}
