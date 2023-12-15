package gossipVille.services;

import gossipVille.data.models.Entry;
import gossipVille.data.repositories.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class EntryServiceImpl implements  EntryService {
    @Autowired
    private EntryRepository entryRepository;

    @Override
    public void create(String title, String body, String diaryId) {
        Entry entry = new Entry();
        entry.setBody(body);
        entry.setTitle(title);
        entry.setDiaryId(diaryId);
        if (findByTitle(title) != null) update(entry);
        entryRepository.save(entry);
    }

    private void update(Entry newEntry) {
        Entry entry = findByTitle(newEntry.getTitle());
        String oldBody = entry.getBody();
        entryRepository.deleteById(entry.getId());
        newEntry.setBody(oldBody + "/n" + newEntry.getBody());
        entryRepository.save(newEntry);
    }

    private Entry findByTitle(String title) {
        for (Entry entry: entryRepository.findAll()){
            if (entry.getTitle().equals(title)) return entry;
        }
        return null;
    }

    @Override
    public List<Entry> findByDiaryId(String diaryId) {
        List<Entry> entries = new ArrayList<>();
        for (Entry entry : entryRepository.findAll()) {
            if (entry.getDiaryId().equals(diaryId)) entries.add(entry);
        }
        return entries;
    }
    @Override
    public Entry findEntryByDiaryIdAndTitle(String id, String title) {
        List<Entry> entries = findByDiaryId(id);
        for (Entry entry : entries){
            if(entry.getTitle().equals(title)) return entry;
        }
        return null;
    }
}

