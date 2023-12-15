package gossipVille.services;

import gossipVille.data.models.Diary;
import gossipVille.data.models.Entry;
import gossipVille.dtos.request.LoginRequest;
import gossipVille.dtos.request.RegisterRequest;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface DiaryService {
    void register(RegisterRequest registerRequest);
    void login(LoginRequest loginRequest);
    Diary findDiaryBelongingTo(String username);
    void writeOn(String username, String title, String body);
    List<Entry> findEntriesBelongingTo(String username);
    Entry readEntriesBelongingTo(String username, String title);
}
