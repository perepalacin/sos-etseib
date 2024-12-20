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

    public void signInUser(UserDao user) throws SQLException, InvalidCredentialsException, UserNotActivatedException {
        UserDao foundUser = usersRepository.findUserByEmail(user.getEmail());

        if (BCrypt.verifyer().verify(user.getPassword().toCharArray(), foundUser.getPassword()).verified) {
            //TODO: Store session!
            if (!user.getIsActivated()) {
                throw new UserNotActivatedException("Encara no has verificat la teva adreça electrònica. T'hem enviat un nou correu perque la puguis validar.");
            }
            System.out.println("Hey!");
        } else {
            throw new InvalidCredentialsException();
        }
    }

    public void singUpUser(UserDao user) throws SQLException, InvalidEmailException, InvalidPasswordException, AddressException, MessagingException {
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
        String validationCode =  jwtService.generateToken(user.getId());
        emailService.sendVerificationEmail(user.getEmail(), user.getUsername(), "http://localhost:8000/validation/" + validationCode + "?email="+ user.getEmail());
    }

    public void validateRegisteredUser (String token, String userEmail) throws SQLException, InvalidValidationTokenException {
        UserDao user = usersRepository.findUserByEmail(userEmail);
        if (jwtService.validateToken(token, user.getId())) {
            usersRepository.activateUser(user.getId());
        } else {
            throw new InvalidValidationTokenException("Token is expired");
        }
    }
}
