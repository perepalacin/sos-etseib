package entities.dto;

import annotations.Required;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private int id;
    private String username;
    @Required
    private String email;
    @Required
    private String password;

    public UserDto(String email, String password) {
        this.email = email;
        this.password = password;
        this.username = this.email;
    }

}