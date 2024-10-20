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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ReservationServiceMockitoTest {

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

    @Test
    public void testCancelReservationForSportTeam_Success() {
        // Arrange
        Long reservationId = 1L;
        Long captainId = 1L;

        User captain = User.builder()
                .userId(captainId)
                .reservations(new HashSet<>())
                .build();

        SportTeam sportTeam = SportTeam.builder()
                .captain(captain)
                .build();

        Set<User> teamMembers = new HashSet<>();
        teamMembers.add(User.builder()
                .userId(2L)
                .reservations(new HashSet<>())
                .build());
        sportTeam.setUsers(teamMembers);
        captain.setSportTeams(sportTeam);

        Reservation reservation = Reservation.builder()
                .reservationId(reservationId)
                .users(new HashSet<>())
                .build();

        captain.getReservations().add(reservation);
        reservation.getUsers().add(captain);

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(userRepository.findById(captainId)).thenReturn(Optional.of(captain));

        // Act
        reservationService.cancelReservationForSportTeam(reservationId, captainId);

        // Assert
        verify(reservationRepository, times(1)).delete(reservation);
    }

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

    @Test
    public void testCancelReservationForSportTeam_CaptainNotFound() {
        Long reservationId = 1L;
        Long captainId = 1L;

        Reservation reservation = Reservation.builder()
                .reservationId(reservationId)
                .build();

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(userRepository.findById(captainId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            reservationService.cancelReservationForSportTeam(reservationId, captainId);
        });
    }

    @Test
    public void testCancelReservationForSportTeam_CaptainNotAssociatedWithTeam() {
        Long reservationId = 1L;
        Long captainId = 1L;

        User captain = User.builder()
                .userId(captainId)
                .build();

        Reservation reservation = Reservation.builder()
                .reservationId(reservationId)
                .build();

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(userRepository.findById(captainId)).thenReturn(Optional.of(captain));

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> {
            reservationService.cancelReservationForSportTeam(reservationId, captainId);
        });
    }

    @Test
    public void testCancelReservationForSportTeam_UserNotCaptain() {
        Long reservationId = 1L;
        Long captainId = 1L;

        User captain = User.builder()
                .userId(captainId)
                .build();

        SportTeam sportTeam = SportTeam.builder()
                .captain(new User()) // Different captain
                .build();

        Reservation reservation = Reservation.builder()
                .reservationId(reservationId)
                .build();

        captain.setSportTeams(sportTeam);

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(userRepository.findById(captainId)).thenReturn(Optional.of(captain));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            reservationService.cancelReservationForSportTeam(reservationId, captainId);
        });
    }
}