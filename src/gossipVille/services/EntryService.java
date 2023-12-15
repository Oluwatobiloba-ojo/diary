package gossipVille.services;

import gossipVille.data.models.Entry;
import org.springframework.stereotype.Service;

import java.util.List;
public interface EntryService {
    void create(String title, String body, String DiaryId);
    List<Entry> findByDiaryId(String diaryId);
    Entry findEntryByDiaryIdAndTitle(String id, String title);
}
