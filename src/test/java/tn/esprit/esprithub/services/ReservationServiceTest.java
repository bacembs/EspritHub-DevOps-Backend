package tn.esprit.esprithub.services;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.esprithub.entities.Reservation;
import tn.esprit.esprithub.entities.SportTeam;
import tn.esprit.esprithub.entities.User;
import tn.esprit.esprithub.repository.IReservationRepository;
import tn.esprit.esprithub.repository.ISportTeamRepository;
import tn.esprit.esprithub.repository.IUserRepositoryy;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private IReservationRepository reservationRepository;

    @Mock
    private IUserRepositoryy userRepository;

    @Mock
    private ISportTeamRepository sportTeamRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test 1: Successful reservation cancellation by the captain
    @Test
    public void testCancelReservationForSportTeam_Success() {
        // Arrange
        Long reservationId = 1L;
        Long captainId = 1L;

        // Mock the captain user
        User captain = new User();
        captain.setUserId(captainId);

        // Mock the sport team with captain and team members
        SportTeam sportTeam = new SportTeam();
        sportTeam.setCaptain(captain);
        Set<User> teamMembers = new HashSet<>();
        User teamMember = new User();
        teamMember.setUserId(2L);
        teamMembers.add(teamMember);
        sportTeam.setUsers(teamMembers);

        captain.setSportTeams(sportTeam);

        // Mock the reservation
        Reservation reservation = new Reservation();
        reservation.setReservationId(reservationId);

        // Mock repository behavior
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(userRepository.findById(captainId)).thenReturn(Optional.of(captain));

        // Act
        reservationService.cancelReservationForSportTeam(reservationId, captainId);

        // Assert
        verify(reservationRepository, times(1)).delete(reservation); // Verify deletion of reservation
        verify(userRepository, times(1)).save(any(User.class)); // Verify that user save was called
    }


    // Test 2: Reservation not found
    @Test
    public void testCancelReservationForSportTeam_ReservationNotFound() {
        Long reservationId = 1L;
        Long captainId = 1L;

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            reservationService.cancelReservationForSportTeam(reservationId, captainId);
        });
    }

    // Test 3: Captain not found
    @Test
    public void testCancelReservationForSportTeam_CaptainNotFound() {
        Long reservationId = 1L;
        Long captainId = 1L;

        Reservation reservation = new Reservation();
        reservation.setReservationId(reservationId);

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(userRepository.findById(captainId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            reservationService.cancelReservationForSportTeam(reservationId, captainId);
        });
    }

    // Test 4: Captain not associated with any sport team
    @Test
    public void testCancelReservationForSportTeam_CaptainNotAssociatedWithTeam() {
        Long reservationId = 1L;
        Long captainId = 1L;

        User captain = new User();
        captain.setUserId(captainId);

        Reservation reservation = new Reservation();
        reservation.setReservationId(reservationId);

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(userRepository.findById(captainId)).thenReturn(Optional.of(captain));

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> {
            reservationService.cancelReservationForSportTeam(reservationId, captainId);
        });
    }

    // Test 5: User is not the captain of the team
    @Test
    public void testCancelReservationForSportTeam_UserNotCaptain() {
        Long reservationId = 1L;
        Long captainId = 1L;

        User captain = new User();
        captain.setUserId(captainId);
        SportTeam sportTeam = new SportTeam();
        sportTeam.setCaptain(new User()); // Different captain

        Reservation reservation = new Reservation();
        reservation.setReservationId(reservationId);

        captain.setSportTeams(sportTeam);

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(userRepository.findById(captainId)).thenReturn(Optional.of(captain));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            reservationService.cancelReservationForSportTeam(reservationId, captainId);
        });
    }
}
