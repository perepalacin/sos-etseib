package services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.sun.net.httpserver.HttpExchange;
import exceptions.MissingFieldsException;
import org.junit.Test;
import entities.dto.UserDto;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class BodyParserTestsUser {

    @Test (expected = MissingFieldsException.class)
    public void testValidateRequiredEmailUserDto() {
        BodyParser.validateRequiredFields(new UserDto(null, "12345"), UserDto.class);
    }

    @Test (expected = MissingFieldsException.class)
    public void testValidateRequiredPasswordUserDto() {
        BodyParser.validateRequiredFields(new UserDto("test@email.com", null), UserDto.class);
    }

    @Test
    public void testValidateRequiredFieldsUserDto() {
        BodyParser.validateRequiredFields(new UserDto("test@email.com", "1234"), UserDto.class);
    }

    @Test
    public void testParseUrlEncoded_withEmailAndPassword() throws UnsupportedEncodingException {
        String body = "email=test%40example.com&password=secret";
        Map<String, String> params = BodyParser.parseUrlEncoded(body);
        assertEquals("test@example.com", params.get("email"));
        assertEquals("secret", params.get("password"));
    }

    @Test
    public void testValidParseRequestBodyUserDto() throws IOException {
        HttpExchange exchange = mock(HttpExchange.class);
        String jsonInput = "email=test%40example.com&password=secret";
        ByteArrayInputStream bis = new ByteArrayInputStream(jsonInput.getBytes());

        when(exchange.getRequestBody()).thenReturn(bis);

        try {
            UserDto user = BodyParser.parseRequestBody(exchange, UserDto.class);
            assert user.getEmail().equals("test@example.com");
            assert user.getPassword().equals("secret");
        } catch (IOException e){
            throw new IOException(e.getMessage());
        }
        verify(exchange).getRequestBody();
    }

    @Test (expected = MissingFieldsException.class)
    public void testInvalidParseRequestBodyUserDto() throws IOException {
        HttpExchange exchange = mock(HttpExchange.class);
        String jsonInput = "username=test%40example.com&password=secret";
        ByteArrayInputStream bis = new ByteArrayInputStream(jsonInput.getBytes());

        when(exchange.getRequestBody()).thenReturn(bis);

        try {
            UserDto user = BodyParser.parseRequestBody(exchange, UserDto.class);
            assert user.getEmail().equals("test@example.com");
            assert user.getPassword().equals("secret");
        } catch (IOException e){
            throw new IOException(e.getMessage());
        }
        verify(exchange).getRequestBody();
    }

}
