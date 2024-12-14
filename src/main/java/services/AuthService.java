package services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import entities.UserDao;
import exceptions.InvalidCredentialsException;
import exceptions.InvalidEmailException;
import exceptions.InvalidPasswordException;
import exceptions.UserNotFoundException;
import org.postgresql.util.PSQLException;
import repositories.UsersRepository;

import java.sql.SQLException;
import java.util.UUID;

public class AuthService {

    private final JwtService jwtService = new JwtService();
    private final UsersRepository usersRepository = new UsersRepository();

    public String signInUser(UserDao user) throws SQLException, PSQLException, InvalidCredentialsException {
        UserDao foundUser = usersRepository.findUserByEmail(user.getEmail());
        if (BCrypt.verifyer().verify(user.getPassword().toCharArray(), foundUser.getPassword()).verified) {
            return jwtService.generateToken(foundUser.getId());
        } else {
            throw new InvalidCredentialsException();
        }
    }

    public void singUpUser(UserDao user) throws SQLException, PSQLException, InvalidEmailException, InvalidPasswordException {
        if (user.validateUserEmail()) {
            if (user.validatePassword()) {
                user.setPassword(BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray()));
                usersRepository.findUserByEmail(user.getEmail());
            } else {
                throw new InvalidPasswordException();
            }
        } else {
            throw new InvalidEmailException();
        }
    }
}
