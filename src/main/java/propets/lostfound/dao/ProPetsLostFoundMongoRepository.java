package propets.lostfound.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import propets.lostfound.model.LostFoundPetPost;

public interface ProPetsLostFoundMongoRepository extends MongoRepository<LostFoundPetPost, String> {
	
	List<LostFoundPetPost> findByTypePostOrderByDatePostDesc(boolean type);
	
	List<LostFoundPetPost> findByIdIn(List<String> ids);
	
}
