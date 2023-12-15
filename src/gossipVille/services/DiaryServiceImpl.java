package gossipVille.services;

import gossipVille.data.models.Diary;
import gossipVille.data.models.Entry;
import gossipVille.data.repositories.DiaryRepository;
import gossipVille.dtos.request.LoginRequest;
import gossipVille.dtos.request.RegisterRequest;
import gossipVille.exceptions.InvalidDetailsException;
import gossipVille.exceptions.UserExistException;
import gossipVille.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class DiaryServiceImpl  implements  DiaryService{
    @Autowired
    private DiaryRepository repository;
    @Autowired
    private EntryService entryService;
    @Override
    public void register(RegisterRequest registerRequest) {
        if(userExist(registerRequest.getUsername())) throw new UserExistException(registerRequest.getUsername() + " already exist");
        Diary newDiary = Map.mapRegisterToDiary(registerRequest);
        repository.save(newDiary);
    }
    private boolean userExist(String username){
        Diary foundDiary = repository.findByUsername(username);
        return foundDiary != null;
    }
    @Override
    public void login(LoginRequest loginRequest) {
      Diary foundDiary = repository.findByUsername(loginRequest.getUsername());
      if(!userExist(loginRequest.getUsername())) throw new InvalidDetailsException();
      if(!foundDiary.getPassword().equals(loginRequest.getPassword())) throw new InvalidDetailsException();
      foundDiary.setLocked(false);
      repository.save(foundDiary);
    }

    @Override
    public Diary findDiaryBelongingTo(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public void writeOn(String username, String title, String body) {
        Diary findDiary = repository.findByUsername(username);
        if (!userExist(username)) throw new InvalidDetailsException();
        entryService.create(title, body, findDiary.getId());
    }

    @Override
    public List<Entry> findEntriesBelongingTo(String username) {
        Diary foundDiary = repository.findByUsername(username);
        return entryService.findByDiaryId(foundDiary.getId());
    }

    @Override
    public Entry readEntriesBelongingTo(String username, String title) {
        Diary foundDiary = repository.findByUsername(username);
        return entryService.findEntryByDiaryIdAndTitle(foundDiary.getId(), title);
    }
}
