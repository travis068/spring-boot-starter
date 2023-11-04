package com.developersboard.web.controller.user;

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

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class UserProfileController {

  @Autowired
  private transient MockMvc mockMvc;

  @InjectMocks
  UserProfileController userProfileController;

  @Mock private UserService userService;

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(userProfileController).build();
  }

  @Test
  void correctProfileUpdate() {
    UserDto user = new UserDto();
    user.setUsername("Raven");
    user.setEmail("patronasCharm@acdu.ca");
  }

}
