package br.senai.lab365.LABMedical.controllers;

import br.senai.lab365.LABMedical.dtos.DashboardResponse;
import br.senai.lab365.LABMedical.services.DashboardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class DashboardControllerTest {

    @InjectMocks
    private DashboardController dashboardController;

    @Mock
    private DashboardService dashboardService;

    @Mock
    private Authentication authentication;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    public void testGetDashboard() {
        // Arrange
        Long field1 = 1L;
        Long field2 = 2L;
        Long field3 = 3L;
        Long field4 = 4L;

        DashboardResponse dashboardResponse = new DashboardResponse(field1, field2, field3, field4);
        when(authentication.isAuthenticated()).thenReturn(true);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> "ROLE_USER");


        when(dashboardService.getDashboard("ROLE_USER")).thenReturn(dashboardResponse);
        // Act
        DashboardResponse result = dashboardController.getDashboard();

        // Assert
        assertEquals(dashboardResponse, result);
        verify(dashboardService, times(1)).getDashboard("ROLE_USER");
    }

    @Test
    public void testGetDashboardUnauthorized() {
        // Arrange
        when(authentication.isAuthenticated()).thenReturn(false);

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> dashboardController.getDashboard());
    }
}