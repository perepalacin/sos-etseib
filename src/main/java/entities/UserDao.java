package entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class UserDao {
    private UUID id;
    private String username;
    private String email;
    private String password;

    public UserDao(UUID userId, String email, String password) {
        this.id = userId;
        this.email = email;
        this.password = password;
        this.username = generateUsernameFromEmail();
    }

    public UserDao(String email, String password) {
        this.email = email;
        this.password = password;
        this.username = generateUsernameFromEmail();
    }

    public String generateUsernameFromEmail () {
        String[] parts = this.email.split("\\.");
        if (parts.length > 1) {
            return parts[0].substring(0, 1).toUpperCase() + ". " + parts[1].substring(0, 1).toUpperCase() + parts[1].substring(1);
        }
        return "Anonymous";
    }

    public boolean validateUserEmail () {
        String[] parts = this.email.split("@");
        if (parts.length > 1) {
            return Objects.equals(parts[1], "estudiantat.upc.edu");
        }
        return false;
    }

    public boolean validatePassword() {
        return this.password.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}");
    }
}
