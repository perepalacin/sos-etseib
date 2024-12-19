package services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import entities.UserDao;
import exceptions.*;
import org.postgresql.util.PSQLException;
import repositories.UsersRepository;

import java.sql.SQLException;
import java.util.Random;
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
        if (!user.validateUserEmail()) {
            throw new InvalidEmailException();
        }
        if (!user.validateUserEmail()) {
            throw new InvalidPasswordException();
        }
        UserDao foundUser = usersRepository.findUserByEmail(user.getEmail());
        if (foundUser != null) {
            throw new UserAlreadyRegisteredException();
        }
        user.setPassword(BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray()));
        user = usersRepository.createUser(user.getEmail(), user.getPassword());
        String validationCode = generateValidationCode();
        usersRepository.registerValidationCode(validationCode, user.getId());
    }

    public String generateValidationCode () {
        String code = "";
        for (int i = 0; i < 6; i++) {
            code = String.valueOf(new Random().nextInt(10));
        }
        return code;
    }
}
