package gossipVille.util;

import gossipVille.data.models.Diary;
import gossipVille.dtos.request.RegisterRequest;

public class Map {

    public static Diary mapRegisterToDiary(RegisterRequest registerRequest) {
        Diary newDiary = new Diary();
        newDiary.setUsername(registerRequest.getUsername());
        newDiary.setPassword(registerRequest.getPassword());
        return newDiary;
    }
}
