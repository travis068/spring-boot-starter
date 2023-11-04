/*
 * Reference: https://howtodoinjava.com/spring-boot2/testing/rest-controller-unit-test-example/
 * */

package com.developersboard.web.controller.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.developersboard.backend.service.mail.EmailService;
import com.developersboard.backend.service.security.EncryptionService;
import com.developersboard.backend.service.security.JwtService;
import com.developersboard.backend.service.user.UserService;
import com.developersboard.shared.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ExtendWith(MockitoExtension.class)
public class SignUpControllerTest {
  @Autowired private transient MockMvc mockMvc;

  @InjectMocks SignUpController signUpController;

  @Mock private JwtService jwtService;

  @Mock private UserService userService;

  // Could not implement as service as the methods related were void
  @Mock private EmailService emailService;

  @Mock private EncryptionService encryptionService;

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(signUpController).build();
  }

  @Test
  void correctCompleteSignUpTest() {
    // Setting up the parameter values
    String token = "79C07821B0D9A8A6EBC515ADFE480D60";
    RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

    // Mocking the redirectAttributes according to their constant key as well as the
    // verificationToken
    when(redirectAttributes.containsAttribute("signUpSuccess")).thenReturn(true);
    when(redirectAttributes.containsAttribute("newProfileCreated")).thenReturn(true);
    when(encryptionService.decode(token)).thenReturn("sammy48384fireBond");
    when(encryptionService.decrypt("sammy48384fireBond"))
        .thenReturn("FA23BBF2FE30F425AE1583E1358CCF40");

    // Creating user for initiating sign up
    UserDto userDto = new UserDto();
    userDto.setUsername("Raven");
    userDto.setVerificationToken("FA23BBF2FE30F425AE1583E1358CCF40");

    // Trigger the jwtAuthentication and set the mock values in place
    when(jwtService.isValidJwtToken("FA23BBF2FE30F425AE1583E1358CCF40")).thenReturn(true);
    when(jwtService.getUsernameFromToken("FA23BBF2FE30F425AE1583E1358CCF40")).thenReturn("Rabia");
    when(userService.findByUsername("Rabia")).thenReturn(userDto);

    // Get results based on the mock configurations
    String result = signUpController.completeSignUp(token, redirectAttributes);

    // Assert Statements according to the results => the SIGN_UP_VIEW_NAME constant as well to new
    // attributes (mocked previously)
    assertEquals("user/sign-up", result);
    assertTrue(redirectAttributes.containsAttribute("signUpSuccess"));
    assertTrue(redirectAttributes.containsAttribute("newProfileCreated"));
  }
}
