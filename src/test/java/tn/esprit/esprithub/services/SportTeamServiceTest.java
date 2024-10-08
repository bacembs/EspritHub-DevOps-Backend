package tn.esprit.esprithub.services;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.esprithub.entities.Field;
import tn.esprit.esprithub.entities.Reservation;
import tn.esprit.esprithub.entities.SportTeam;
import tn.esprit.esprithub.entities.User;
import tn.esprit.esprithub.repository.IFieldRepository;
import tn.esprit.esprithub.repository.IReservationRepository;
import tn.esprit.esprithub.repository.IUserRepositoryy;
import tn.esprit.esprithub.repository.ISportTeamRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SportTeamServiceTest {

    @InjectMocks
    private SportTeamService sportTeamService;



    @Mock
    private ISportTeamRepository sportTeamRepository;

    @Mock
    private IUserRepositoryy userRepository;

    @Mock
    private IFieldRepository fieldRepository;

    @Mock
    private IReservationRepository reservationRepository;

    @Mock
    private IReservationService reservationService;

    @Mock
    private MultipartFile photoFile;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
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
        when(userRepository.findById(captainId)).thenReturn(Optional.of(captainUser));
        when(photoFile.getBytes()).thenReturn(photoBytes);
        when(photoFile.getOriginalFilename()).thenReturn(fileName);
        when(sportTeamRepository.save(any(SportTeam.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        SportTeam result = sportTeamService.addSportTeamCap3(teamName, captainId, photoFile);

        // Assert
        assertNotNull(result);
        assertEquals(teamName, result.getNameTeam());
        assertEquals(fileName, result.getLogoTeam());
        assertEquals(captainUser, result.getCaptain());
        verify(sportTeamRepository, times(1)).save(result); // Verify that save was called once
        verify(userRepository, times(1)).save(captainUser); // Verify that the user save was called once
    }

    @Test
    public void testAddSportTeamCap3_UserNotFound() throws IOException {
        // Arrange
        Long captainId = 1L;
        String teamName = "Team A";

        when(userRepository.findById(captainId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            sportTeamService.addSportTeamCap3(teamName, captainId, photoFile);
        });
    }

    @Test
    public void testAddSportTeamCap3_MultipleCaptains() throws IOException {
        Long captainId1 = 1L;
        Long captainId2 = 2L;
        String teamName1 = "Team A";
        String teamName2 = "Team B";
        byte[] photoBytes = "mock photo content".getBytes();
        String fileName = "logo.png";

        User captainUser1 = new User();
        captainUser1.setUserId(captainId1);

        User captainUser2 = new User();
        captainUser2.setUserId(captainId2);

        when(userRepository.findById(captainId1)).thenReturn(Optional.of(captainUser1));
        when(userRepository.findById(captainId2)).thenReturn(Optional.of(captainUser2));
        when(photoFile.getBytes()).thenReturn(photoBytes);
        when(photoFile.getOriginalFilename()).thenReturn(fileName);

        // Act
        SportTeam result1 = sportTeamService.addSportTeamCap3(teamName1, captainId1, photoFile);
        SportTeam result2 = sportTeamService.addSportTeamCap3(teamName2, captainId2, photoFile);

        // Assert
        assertNotNull(result1);
        assertEquals(teamName1, result1.getNameTeam());
        assertEquals(captainUser1, result1.getCaptain());

        assertNotNull(result2);
        assertEquals(teamName2, result2.getNameTeam());
        assertEquals(captainUser2, result2.getCaptain());
    }

//    @Test
//    public void testMakeTeamReservation_Success() {
//        // Arrange
//        Long sportTeamId = 1L;
//        Long captainId = 1L;
//        Long fieldId = 1L;
//        LocalDateTime startDate = LocalDateTime.now().plus(1, ChronoUnit.HOURS); // 1 hour from now
//        LocalDateTime endDate = startDate.plus(2, ChronoUnit.HOURS); // 2 hours long reservation
//
//        Reservation reservation = new Reservation();
//        reservation.setStartDate(startDate);
//        reservation.setEndDate(endDate);
//
//        SportTeam sportTeam = new SportTeam();
//        User captain = new User();
//        captain.setUserId(captainId);
//        sportTeam.setCaptain(captain);
//        sportTeam.setUsers(Set.of(new User())); // Add team members as needed
//
//        Field field = new Field(); // Initialize with appropriate values
//        field.setCapacityField(5); // Set the field capacity
//
//        when(sportTeamRepository.findById(sportTeamId)).thenReturn(Optional.of(sportTeam));
//        when(fieldRepository.findById(fieldId)).thenReturn(Optional.of(field));
//        when(reservationRepository.save(any(Reservation.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        // Act
//        sportTeamService.makeTeamReservation(sportTeamId, captainId, fieldId, reservation);
//
//        // Assert
//        verify(reservationRepository, times(1)).save(reservation);
//    }
//
//    @Test
//    public void testMakeTeamReservation_FieldNotAvailable() {
//        // Arrange
//        Long sportTeamId = 1L;
//        Long captainId = 1L;
//        Long fieldId = 1L;
//        LocalDateTime startDate = LocalDateTime.now().plus(1, ChronoUnit.HOURS); // 1 hour from now
//        LocalDateTime endDate = startDate.plus(2, ChronoUnit.HOURS); // 2 hours long reservation
//
//        Reservation reservation = new Reservation();
//        reservation.setStartDate(startDate);
//        reservation.setEndDate(endDate);
//
//        SportTeam sportTeam = new SportTeam();
//        User captain = new User();
//        captain.setUserId(captainId);
//        sportTeam.setCaptain(captain);
//        sportTeam.setUsers(Set.of(new User())); // Add team members as needed
//
//        Field field = new Field();
//        field.setCapacityField(5);
//
//        when(sportTeamRepository.findById(sportTeamId)).thenReturn(Optional.of(sportTeam));
//        when(fieldRepository.findById(fieldId)).thenReturn(Optional.of(field));
//        when(reservationService.isFieldAvailableForReservation(fieldId, reservation.getStartDate(), reservation.getEndDate())).thenReturn(false);
//
//        // Act & Assert
//        assertThrows(IllegalArgumentException.class, () -> {
//            sportTeamService.makeTeamReservation(sportTeamId, captainId, fieldId, reservation);
//        });
//    }
//
//    @Test
//    public void testMakeTeamReservation_ExceedsCapacity() {
//        // Arrange
//        Long sportTeamId = 1L;
//        Long captainId = 1L;
//        Long fieldId = 1L;
//        LocalDateTime startDate = LocalDateTime.now().plus(1, ChronoUnit.HOURS); // 1 hour from now
//        LocalDateTime endDate = startDate.plus(2, ChronoUnit.HOURS); // 2 hours long reservation
//
//        Reservation reservation = new Reservation();
//        reservation.setStartDate(startDate);
//        reservation.setEndDate(endDate);
//
//        SportTeam sportTeam = new SportTeam();
//        User captain = new User();
//        captain.setUserId(captainId);
//        sportTeam.setCaptain(captain);
//        // Add enough users to exceed field capacity
//        sportTeam.setUsers(Set.of(new User(), new User(), new User(), new User(), new User(), new User())); // 6 users
//
//        Field field = new Field();
//        field.setCapacityField(5);
//
//        when(sportTeamRepository.findById(sportTeamId)).thenReturn(Optional.of(sportTeam));
//        when(fieldRepository.findById(fieldId)).thenReturn(Optional.of(field));
//
//        // Act & Assert
//        assertThrows(IllegalArgumentException.class, () -> {
//            sportTeamService.makeTeamReservation(sportTeamId, captainId, fieldId, reservation);
//        });
//    }
//
//    @Test
//    public void testMakeTeamReservation_UserHasReservation() {
//        // Arrange
//        Long sportTeamId = 1L;
//        Long captainId = 1L;
//        Long fieldId = 1L;
//        LocalDateTime startDate = LocalDateTime.now().plus(1, ChronoUnit.HOURS); // 1 hour from now
//        LocalDateTime endDate = startDate.plus(2, ChronoUnit.HOURS); // 2 hours long reservation
//
//        Reservation reservation = new Reservation();
//        reservation.setStartDate(startDate);
//        reservation.setEndDate(endDate);
//
//        User member = new User();
//        member.setReservations(Set.of(reservation)); // Member already has a reservation
//
//        SportTeam sportTeam = new SportTeam();
//        User captain = new User();
//        captain.setUserId(captainId);
//        sportTeam.setCaptain(captain);
//        sportTeam.setUsers(Set.of(member));
//
//        Field field = new Field();
//        field.setCapacityField(5);
//
//        when(sportTeamRepository.findById(sportTeamId)).thenReturn(Optional.of(sportTeam));
//        when(fieldRepository.findById(fieldId)).thenReturn(Optional.of(field));
//
//        // Act & Assert
//        assertThrows(IllegalArgumentException.class, () -> {
//            sportTeamService.makeTeamReservation(sportTeamId, captainId, fieldId, reservation);
//        });
//    }
}
