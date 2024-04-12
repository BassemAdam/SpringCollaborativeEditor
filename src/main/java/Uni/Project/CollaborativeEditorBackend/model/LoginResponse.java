package Uni.Project.CollaborativeEditorBackend.model;

public class LoginResponse {
    private final User user;

    private final String token;

    public LoginResponse(User user, String token) {
        this.user = user;
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }
}
