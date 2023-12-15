package gossipVille.services;

import gossipVille.data.models.Entry;
import gossipVille.data.repositories.DiaryRepository;
import gossipVille.data.repositories.EntryRepository;
import gossipVille.dtos.request.LoginRequest;
import gossipVille.dtos.request.RegisterRequest;
import gossipVille.exceptions.InvalidDetailsException;
import gossipVille.exceptions.UserExistException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DiaryServiceImplTest {
    @Autowired
    private DiaryService diaryService;
    @Autowired
    private DiaryRepository diaryRepository;
    @Autowired
    private EntryRepository entryRepository;
    @AfterEach
    public void startAllTestWithThis(){
        diaryRepository.deleteAll();
        entryRepository.deleteAll();
    }
    @Test
    public void registerPhilip_registerPhilipAgain_throwsExceptionTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("philip");
        registerRequest.setPassword("password");
        diaryService.register(registerRequest);
        assertThrows(UserExistException.class, ()-> diaryService.register(registerRequest));
    }
    @Test
    public void registerUser_loginWithWrongPassword_throwsExceptionTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        LoginRequest loginRequest = new LoginRequest();
        registerRequest.setUsername("philip");
        registerRequest.setPassword("password");
        diaryService.register(registerRequest);
        loginRequest.setPassword("wrongPassword");
        loginRequest.setUsername("philip");
        assertThrows(InvalidDetailsException.class,
                ()->diaryService.login(loginRequest));
    }
    @Test
    public void registerUser_loginWithWrongUsername_throwsExceptionTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("philip");
        registerRequest.setPassword("password");
        diaryService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("wrongUsername");
        loginRequest.setPassword("password");
        assertThrows(InvalidDetailsException.class,
                ()->diaryService.login(loginRequest));
    }
    @Test
    public void registerUser_loginWithRightDetails_foundDiaryIsUnlockedTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("philip");
        registerRequest.setPassword("password");
        diaryService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("philip");
        loginRequest.setPassword("password");
        boolean isLocked = diaryService.findDiaryBelongingTo("philip").isLocked();
        assertTrue(isLocked);
        diaryService.login(loginRequest);
        isLocked = diaryService.findDiaryBelongingTo("philip").isLocked();
        assertFalse(isLocked);
    }
    @Test
    public void registerUser_loginWithRightDetails_createEntry_viewAllEntrySizeIsOneTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("philip");
        registerRequest.setPassword("password");
        diaryService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("philip");
        loginRequest.setPassword("password");
        diaryService.login(loginRequest);
        diaryService.writeOn("philip", "title", "body");
        assertEquals(1, diaryService.findEntriesBelongingTo("philip").size());
    }
    @Test
    public void registerUser_loginWithRightDetails_create_two_entry_viewAllEntrySizeIsTwoTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("philip");
        registerRequest.setPassword("password");
        diaryService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("philip");
        loginRequest.setPassword("password");
        diaryService.login(loginRequest);
        diaryService.writeOn("philip", "title", "body");
        diaryService.writeOn("philip", "titles", "body");
        assertEquals(2, diaryService.findEntriesBelongingTo("philip").size());
    }
    @Test
    public void testThatWhenUser_LoginWithCorrectDetails_andWantToWriteToUnknownUser_Throws_Exception(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("philip");
        registerRequest.setPassword("password");
        diaryService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("philip");
        loginRequest.setPassword("password");
        diaryService.login(loginRequest);
        assertThrows(InvalidDetailsException.class, () -> diaryService.writeOn("philips", "title", "body"));
    }
    @Test
    public void testThatWhenUser_LoginWithCorrectDetail_WriteAndWriteOnTheSameTopicWillUpdateAndNotCreateAnotherEntry(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("philip");
        registerRequest.setPassword("password");
        diaryService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("philip");
        loginRequest.setPassword("password");
        diaryService.login(loginRequest);
        diaryService.writeOn("philip", "title", "body");
        diaryService.writeOn("philip", "title", "body");
        assertEquals(1, diaryService.findEntriesBelongingTo("philip").size());
    }
    @Test
    public void testThatWhenUser_LoginWithCorrectDetails_WriteOn_AndWantToRead(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("philip");
        registerRequest.setPassword("password");
        diaryService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("philip");
        loginRequest.setPassword("password");
        diaryService.login(loginRequest);
        diaryService.writeOn("philip", "title", "body");
        Entry entry = diaryService.readEntriesBelongingTo("philip", "title");
        assertEquals("title", entry.getTitle());
        assertEquals("body", entry.getBody());
    }
    @Test
    public void testThatWeCanReadThroughADiaryWhenWeWriteOnTwoEntries(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("philip");
        registerRequest.setPassword("password");
        diaryService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("philip");
        loginRequest.setPassword("password");
        diaryService.login(loginRequest);
        diaryService.writeOn("philip", "title", "body");
        diaryService.writeOn("philip", "personal_title", "ope is a boy");
        Entry entry = diaryService.readEntriesBelongingTo("philip", "personal_title");
        assertEquals("personal_title", entry.getTitle());
        assertEquals("ope is a boy", entry.getBody());
    }

}