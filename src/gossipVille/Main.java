package gossipVille;

import gossipVille.controller.DiaryController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    static DiaryController diaryController = new DiaryController();
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}