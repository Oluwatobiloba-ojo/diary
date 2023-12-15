package gossipVille.controller;

import com.fasterxml.jackson.annotation.JacksonInject;
import gossipVille.dtos.request.LoginRequest;
import gossipVille.dtos.request.RegisterRequest;
import gossipVille.dtos.response.ApiResponse;
import gossipVille.dtos.response.LoginResponse;
import gossipVille.dtos.response.RegisterResponse;
import gossipVille.exceptions.DiaryAppException;
import gossipVille.services.DiaryService;
import gossipVille.services.DiaryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
public class DiaryController {
    private DiaryService diaryService = new DiaryServiceImpl();
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest){
        RegisterResponse registerResponse = new RegisterResponse();
        try{
            diaryService.register(registerRequest);
            registerResponse.setMessage("Account created");
            return new ResponseEntity<>(new ApiResponse(true, registerResponse), HttpStatus.CREATED);
        }catch(DiaryAppException exception){
            registerResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, registerResponse), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(LoginRequest loginRequest){
        LoginResponse loginResponse = new LoginResponse();
        try{
            diaryService.login(loginRequest);
            loginResponse.setMessage("You don login !!!!!!!!!!!!!");
            return new ResponseEntity<>(new ApiResponse(true, loginResponse), HttpStatus.ACCEPTED);
        }catch (DiaryAppException exception){
            loginResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, loginResponse), HttpStatus.NOT_FOUND);
        }
    }
   @PostMapping("/entry")
    public String writeOn(@RequestParam String username, @RequestParam String title, @PathVariable String body){
        try{
            diaryService.writeOn(username, title, body);
            return "We don write am ooooo";
        }catch(DiaryAppException exception){
            return exception.getMessage();
        }
    }
    @GetMapping("/findEntry/{username}")
    public Object findEntry(@PathVariable String username){
        try{
           return diaryService.findEntriesBelongingTo(username);
        }catch(DiaryAppException exception){
            return exception.getMessage();
        }
    }
}
