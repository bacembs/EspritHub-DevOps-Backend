package tn.esprit.esprithub.services;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.esprithub.entities.SportTeam;
import tn.esprit.esprithub.entities.User;
import tn.esprit.esprithub.repository.IUserRepositoryy;
import tn.esprit.esprithub.repository.ISportTeamRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SportTeamServiceTest {

    @InjectMocks
    private SportTeamService sportTeamService; // Service to be tested

    @Mock
    private ISportTeamRepository sportTeamRepository; // Mock the repository

    @Mock
    private IUserRepositoryy userRepository; // Mock the user repository

    @Mock
    private MultipartFile photoFile; // Mock the MultipartFile for file upload

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    public void testAddSportTeamCap3_Success() throws IOException {
        // Arrange
        Long captainId = 1L;
        String teamName = "Team A";
        byte[] photoBytes = "mock photo content".getBytes();
        String fileName = "logo.png";

        // Mock the user
        User captainUser = new User();
        captainUser.setUserId(captainId);

        // Mock repository behavior
        when(userRepository.findById(captainId)).thenReturn(Optional.of(captainUser)); // Mock user retrieval
        when(photoFile.getBytes()).thenReturn(photoBytes); // Mock file bytes
        when(photoFile.getOriginalFilename()).thenReturn(fileName); // Mock original filename
        when(sportTeamRepository.save(any(SportTeam.class))).thenAnswer(invocation -> invocation.getArgument(0)); // Mock save behavior

        // Act
        SportTeam result = sportTeamService.addSportTeamCap3(teamName, captainId, photoFile);

        // Assert
        assertNotNull(result); // Ensure that the result is not null
        assertEquals(teamName, result.getNameTeam()); // Check that the team name is set correctly
        assertEquals(fileName, result.getLogoTeam()); // Check that the logo team is set correctly
        assertEquals(captainUser, result.getCaptain()); // Check that the captain is set correctly
        verify(sportTeamRepository, times(1)).save(result); // Verify that save was called once
        verify(userRepository, times(1)).save(captainUser); // Verify that the user save was called once
    }

    @Test
    public void testAddSportTeamCap3_UserNotFound() throws IOException {
        // Arrange
        Long captainId = 1L;
        String teamName = "Team A";

        when(userRepository.findById(captainId)).thenReturn(Optional.empty()); // Mock user retrieval (not found)

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            sportTeamService.addSportTeamCap3(teamName, captainId, photoFile);
        });
    }
}
