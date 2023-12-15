package gossipVille.data.repositories;

import gossipVille.data.models.Entry;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface EntryRepository extends MongoRepository<Entry, String> {
}
