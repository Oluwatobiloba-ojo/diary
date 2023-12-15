package gossipVille.data.repositories;

import gossipVille.data.models.Diary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
class DiaryRepositoryImplementationTest {
    @Autowired
    private DiaryRepository diaryRepository;
    @BeforeEach
    public void doThisAfterAllTest(){
        diaryRepository.deleteAll();
    }
    @Test
    public void saveOneDiary_countIsOne() {
        Diary diary = new Diary();
        diaryRepository.save(diary);
        assertEquals(1, diaryRepository.count());
    }

    @Test
    public void saveTwoDiary_countIsTwo() {
        Diary diary = new Diary();
        Diary secondDiary = new Diary();
        diaryRepository.save(diary);
        diaryRepository.save(secondDiary);
        assertEquals(2, diaryRepository.count());
    }

    @Test
    public void saveTwoDiary_findAllCountReturnsTwoTest() {
        Diary diary = new Diary();
        Diary secondDiary = new Diary();
        diaryRepository.save(diary);
        diaryRepository.save(secondDiary);
        assertEquals(2, diaryRepository.findAll().size());
    }

    @Test
    public void saveThreeDiary_deleteByDiary(){
        Diary diary = new Diary();
        Diary secondDiary = new Diary();
        Diary thirdDiary = new Diary();
        diaryRepository.save(diary);
        diaryRepository.save(secondDiary);
        diaryRepository.save(thirdDiary);
        assertEquals(3, diaryRepository.count());
        diaryRepository.delete(diary);
        assertEquals(2, diaryRepository.count());
    }
}