package gossipVille.exceptions;

public class InvalidDetailsException extends DiaryAppException{
    public InvalidDetailsException() {
        super("Login credentials is invalid");
    }
}
