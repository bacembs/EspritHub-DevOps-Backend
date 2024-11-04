package tn.esprit.esprithub.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.esprithub.entities.Reservation;
import tn.esprit.esprithub.entities.SportTeam;
import tn.esprit.esprithub.entities.User;
import tn.esprit.esprithub.repository.IReservationRepository;
import tn.esprit.esprithub.repository.ISportTeamRepository;
import tn.esprit.esprithub.repository.IUserRepositoryy;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ReservationServiceJUnitTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private IReservationRepository reservationRepository;

    @Autowired
    private IUserRepositoryy userRepository;

    @Autowired
    private ISportTeamRepository sportTeamRepository;

    private User captain;
    private SportTeam sportTeam;
    private Reservation reservation;
    private User teamMember;

    @BeforeEach
    public void setUp() {
        // Create and save a captain
        captain = new User();
        captain.setLastName("Captain");
        captain.setFirstName("Test");
        captain.setEmail("captain@test.com");
        userRepository.save(captain);

        // Create and save a team member
        teamMember = new User();
        teamMember.setLastName("Member");
        teamMember.setFirstName("Test");
        teamMember.setEmail("member@test.com");
        userRepository.save(teamMember);

        // Create and save a sport team
        sportTeam = new SportTeam();
        sportTeam.setCaptain(captain);
        Set<User> teamMembers = new HashSet<>();
        teamMembers.add(teamMember);
        sportTeam.setUsers(teamMembers);
        sportTeamRepository.save(sportTeam);

        // Update captain with sport team
        captain.setSportTeams(sportTeam);
        userRepository.save(captain);

        // Create and save a reservation
        reservation = new Reservation();
        Set<User> reservationUsers = new HashSet<>();
        reservationUsers.add(captain);
        reservationUsers.add(teamMember);
        reservation.setUsers(reservationUsers);
        reservationRepository.save(reservation);
    }

    @Test
    public void testCancelReservationForSportTeam_Success() {
        // Arrange
        Long reservationId = reservation.getReservationId();
        Long captainId = captain.getUserId();

        // Act
        reservationService.cancelReservationForSportTeam(reservationId, captainId);

        // Assert
        assertFalse(reservationRepository.existsById(reservationId));
        assertFalse(captain.getReservations().contains(reservation));
        assertFalse(teamMember.getReservations().contains(reservation));
    }

    @Test
    public void testCancelReservationForSportTeam_ReservationNotFound() {
        // Arrange
        Long nonExistentReservationId = 99999L;
        Long captainId = captain.getUserId();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            reservationService.cancelReservationForSportTeam(nonExistentReservationId, captainId);
        });
        assertEquals("Reservation not found", exception.getMessage());
    }

    @Test
    public void testCancelReservationForSportTeam_CaptainNotFound() {
        // Arrange
        Long reservationId = reservation.getReservationId();
        Long nonExistentCaptainId = 99999L;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            reservationService.cancelReservationForSportTeam(reservationId, nonExistentCaptainId);
        });
        assertEquals("Captain not found", exception.getMessage());
    }

    @Test
    public void testCancelReservationForSportTeam_CaptainNotAssociatedWithTeam() {
        // Arrange
        User captainWithoutTeam = new User();
        captainWithoutTeam.setLastName("CaptainNoTeam");
        captainWithoutTeam.setFirstName("Test");
        captainWithoutTeam.setEmail("captainwithoutteam@test.com");
        userRepository.save(captainWithoutTeam);

        Long reservationId = reservation.getReservationId();
        Long captainWithoutTeamId = captainWithoutTeam.getUserId();

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            reservationService.cancelReservationForSportTeam(reservationId, captainWithoutTeamId);
        });
        assertEquals("Captain is not associated with any sport team", exception.getMessage());
    }
}