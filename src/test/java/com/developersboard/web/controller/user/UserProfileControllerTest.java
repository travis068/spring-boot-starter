package com.developersboard.web.controller.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.developersboard.constant.ErrorConstants;
import com.developersboard.constant.user.ProfileConstants;
import com.developersboard.shared.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ExtendWith(MockitoExtension.class)
public class UserProfileControllerTest {

  @Autowired private transient MockMvc mockMvc;

  @InjectMocks UserProfileController userProfileController;

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(userProfileController).build();
  }

  @Test
  void incorrectProfileUpdate() {
    // Setting up the parameter values
    UserDto user = new UserDto();
    user.setUsername("Raven");
    user.setEmail("patronasCharm@acdu.ca");

    // Mocking the redirectAttributes and bindingResult variables
    RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
    BindingResult bindingResult = mock(BindingResult.class);

    // Mocking bindingResult to have errors, in order to enter if-statement criteria and increase
    // branch coverage
    when(bindingResult.hasErrors()).thenReturn(true);

    // Get results based on the mock configurations
    String result = userProfileController.updateProfile(user, bindingResult, redirectAttributes);

    // Assert Statement according to the results => test fail because incorrect profile credentials
    // confirmed through mock binding results
    assertNotEquals(ProfileConstants.REDIRECT_TO_PROFILE, result);
    verify(redirectAttributes, times(0)).addFlashAttribute(ErrorConstants.ERROR, false);
  }
}
