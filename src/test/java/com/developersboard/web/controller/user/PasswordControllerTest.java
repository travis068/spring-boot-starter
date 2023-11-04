package com.developersboard.web.controller.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.developersboard.backend.service.user.UserService;
import com.developersboard.constant.user.PasswordConstants;
import com.developersboard.shared.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ExtendWith(MockitoExtension.class)
class PasswordControllerTest {

  private transient MockMvc mockMvc;

  @InjectMocks private transient PasswordController passwordController;

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(passwordController).build();
  }

  @Test
  void testResetStartPathAndViewName() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get(PasswordConstants.PASSWORD_RESET_ROOT_MAPPING))
        .andExpect(
            MockMvcResultMatchers.view().name(PasswordConstants.PASSWORD_RESET_START_VIEW_NAME))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  void testStartPasswordResetWithoutEmailReturns400() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.post(PasswordConstants.PASSWORD_RESET_ROOT_MAPPING))
        .andExpect(MockMvcResultMatchers.status().is4xxClientError());
  }

  @Mock UserService userService;
  @Mock PasswordEncoder passwordEncoder;

  @Test
  void changePasswordCorrectCredentials() {
    Model model = mock(Model.class);
    RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
    UserDto userDto = new UserDto();
    userDto.setUsername("Raven");
    userDto.setPassword("sammy48384fireBond");

    when(userService.findByUsername("Raven")).thenReturn(userDto);
    when(passwordEncoder.matches("sammy48384fireBond", userDto.getPassword())).thenReturn(true);

    String result = passwordController.changePassword(userDto, model, redirectAttributes);

    assertEquals(PasswordConstants.PASSWORD_RESET_COMPLETE_VIEW_NAME, result);
  }
}
