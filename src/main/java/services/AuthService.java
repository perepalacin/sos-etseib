package services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import entities.UserDao;
import exceptions.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import org.postgresql.util.PSQLException;
import repositories.UsersRepository;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

public class AuthService {

    private final JwtService jwtService = new JwtService();
    private final UsersRepository usersRepository = new UsersRepository();
    private final EmailService emailService = new EmailService();
    private final String testEmail = "perepalacin@gmail.com";

    public UUID signInUser(UserDao user) throws SQLException, InvalidCredentialsException, UserNotActivatedException {
        UserDao foundUser = usersRepository.findUserByEmail(user.getEmail());

        if (foundUser != null && BCrypt.verifyer().verify(user.getPassword().toCharArray(), foundUser.getPassword()).verified) {
            if (!foundUser.getIsActivated()) {
                throw new UserNotActivatedException("Encara no has verificat la teva adreça electrònica. T'hem enviat un nou correu perquè la puguis validar.");
            }
            UUID sessionId = usersRepository.createSession(foundUser.getId());
            return sessionId;
        } else {
            throw new InvalidCredentialsException();
        }
    }

    public void singUpUser(UserDao user) throws SQLException, InvalidEmailException, InvalidPasswordException, MessagingException, AddressException {
        if (!user.validateUserEmail() && !testEmail.equals(user.getEmail())) {
            throw new InvalidEmailException();
        }

        if (!user.validatePassword()) {
            throw new InvalidPasswordException();
        }

        UserDao foundUser = usersRepository.findUserByEmail(user.getEmail());
        if (foundUser != null) {
            throw new UserAlreadyRegisteredException();
        }

        user.setPassword(BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray()));
        user = usersRepository.createUser(user.getEmail(), user.getPassword());
        this.generateUserValidationEmail(user.getId(), user.getEmail(), user.getUsername());
    }

    public void resendValidationEmail(String userEmail) throws SQLException, MessagingException {
        UserDao foundUser = usersRepository.findUserByEmail(userEmail);
        if (foundUser == null) {
            throw new UserNotFoundException();
        }
        this.generateUserValidationEmail(foundUser.getId(), foundUser.getEmail(), foundUser.getUsername());
    }


    public void validateRegisteredUser (String token, String userEmail) throws SQLException, InvalidValidationTokenException {
        UserDao user = usersRepository.findUserByEmail(userEmail);
        if (jwtService.validateToken(token, user.getId())) {
            usersRepository.activateUser(user.getId());
        } else {
            //The expired jwt get's thrown automatically;
            throw new InvalidValidationTokenException("Hi ha hagut un error durant el proces de registre, si us plau torna a registrar-te.");
        }
    }

    public void generateUserValidationEmail(UUID userId, String userEmail, String username) throws AddressException, MessagingException {
        String validationCode =  jwtService.generateToken(userId);
        emailService.sendVerificationEmail(userEmail, username, "http://localhost:8000/validation/" + validationCode + "?email="+ userEmail);
    }

    public UUID isUserLoggedIn(UUID sessionId) throws SQLException {
        return usersRepository.isSessionValid(sessionId);
    }

    public boolean logOutUser(UUID sessionId) throws SQLException {
        return usersRepository.deleteSession(sessionId);
    }
}
