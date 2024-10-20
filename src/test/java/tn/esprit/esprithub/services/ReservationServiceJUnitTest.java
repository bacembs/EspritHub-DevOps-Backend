//package tn.esprit.esprithub.services;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import tn.esprit.esprithub.entities.Reservation;
//import tn.esprit.esprithub.entities.SportTeam;
//import tn.esprit.esprithub.entities.User;
//import tn.esprit.esprithub.repository.IReservationRepository;
//import tn.esprit.esprithub.repository.UserRepository;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.util.*;
//
//public class ReservationServiceImplTest {
//    private ReservationService reservationService;
//    private TestReservationRepository reservationRepository;
//    private TestUserRepository userRepository;
//
//    @BeforeEach
//    void setUp() {
//        reservationRepository = new TestReservationRepository();
//        userRepository = new TestUserRepository();
//        reservationService = new ReservationService(reservationRepository, userRepository);
//    }
//
//    @Test
//    void testCancelReservationForSportTeam_Success() {
//        // Setup test data
//        User captain = new User();
//        captain.setUserId(1L);
//
//        SportTeam team = new SportTeam();
//        team.setTeamId(1L);
//        team.setCaptain(captain);
//
//        Set<User> teamMembers = new HashSet<>();
//        User member1 = new User();
//        member1.setUserId(2L);
//        User member2 = new User();
//        member2.setUserId(3L);
//        teamMembers.add(member1);
//        teamMembers.add(member2);
//        team.setUsers(teamMembers);
//
//        captain.setSportTeams(team);
//
//        Reservation reservation = new Reservation();
//        reservation.setReservationId(1L);
//        Set<Reservation> reservations = new HashSet<>();
//        reservations.add(reservation);
//        member1.setReservations(reservations);
//        member2.setReservations(reservations);
//
//        // Add to repositories
//        reservationRepository.addReservation(reservation);
//        userRepository.addUser(captain);
//        userRepository.addUser(member1);
//        userRepository.addUser(member2);
//
//        // Test the method
//        reservationService.cancelReservationForSportTeam(1L, 1L);
//
//        // Assertions
//        assertNull(reservationRepository.findById(1L).orElse(null),
//                "Reservation should be deleted");
//        assertTrue(member1.getReservations().isEmpty(),
//                "Member1's reservations should be empty");
//        assertTrue(member2.getReservations().isEmpty(),
//                "Member2's reservations should be empty");
//    }
//
//    @Test
//    void testCancelReservationForSportTeam_ReservationNotFound() {
//        assertThrows(IllegalArgumentException.class, () -> {
//            reservationService.cancelReservationForSportTeam(999L, 1L);
//        }, "Should throw IllegalArgumentException when reservation not found");
//    }
//
//    @Test
//    void testCancelReservationForSportTeam_CaptainNotFound() {
//        // Setup
//        Reservation reservation = new Reservation();
//        reservation.setReservationId(1L);
//        reservationRepository.addReservation(reservation);
//
//        assertThrows(IllegalArgumentException.class, () -> {
//            reservationService.cancelReservationForSportTeam(1L, 999L);
//        }, "Should throw IllegalArgumentException when captain not found");
//    }
//
//    @Test
//    void testCancelReservationForSportTeam_NotTeamCaptain() {
//        // Setup
//        User notCaptain = new User();
//        notCaptain.setUserId(1L);
//
//        SportTeam team = new SportTeam();
//        team.setTeamId(1L);
//        team.setCaptain(new User()); // Different captain
//
//        notCaptain.setSportTeams(team);
//
//        Reservation reservation = new Reservation();
//        reservation.setReservationId(1L);
//
//        reservationRepository.addReservation(reservation);
//        userRepository.addUser(notCaptain);
//
//        assertThrows(IllegalArgumentException.class, () -> {
//            reservationService.cancelReservationForSportTeam(1L, 1L);
//        }, "Should throw IllegalArgumentException when user is not team captain");
//    }
//
//    @Test
//    void testCancelReservationForSportTeam_CaptainNotAssociatedWithTeam() {
//        // Setup
//        User captain = new User();
//        captain.setUserId(1L);
//        captain.setSportTeams(null);
//
//        Reservation reservation = new Reservation();
//        reservation.setReservationId(1L);
//
//        reservationRepository.addReservation(reservation);
//        userRepository.addUser(captain);
//
//        assertThrows(IllegalStateException.class, () -> {
//            reservationService.cancelReservationForSportTeam(1L, 1L);
//        }, "Should throw IllegalStateException when captain is not associated with any team");
//    }
//
//    // Test Repository implementations
//    private static abstract class TestReservationRepository implements IReservationRepository {
//        private final Map<Long, Reservation> reservations = new HashMap<>();
//
//        public void addReservation(Reservation reservation) {
//            reservations.put(reservation.getReservationId(), reservation);
//        }
//
//        @Override
//        public Optional<Reservation> findById(Long id) {
//            return Optional.ofNullable(reservations.get(id));
//        }
//
//        @Override
//        public void delete(Reservation reservation) {
//            reservations.remove(reservation.getReservationId());
//        }
//    }
//
//    private static abstract class TestUserRepository implements UserRepository {
//        private final Map<Long, User> users = new HashMap<>();
//
//        public void addUser(User user) {
//            users.put(user.getUserId(), user);
//        }
//
//        @Override
//        public Optional<User> findById(Long id) {
//            return Optional.ofNullable(users.get(id));
//        }
//
//        @Override
//        public User save(User user) {
//            users.put(user.getUserId(), user);
//            return user;
//        }
//
//        // For all other methods in UserRepository interface, we can add empty implementations
//        // that throw UnsupportedOperationException since they won't be used in our tests
//
//        @Override
//        public List<User> findAll() {
//            throw new UnsupportedOperationException("Method not implemented for testing");
//        }
//
//        @Override
//        public void delete(User user) {
//            throw new UnsupportedOperationException("Method not implemented for testing");
//        }
//
//        @Override
//        public void deleteById(Long id) {
//            throw new UnsupportedOperationException("Method not implemented for testing");
//        }
//
//        @Override
//        public boolean existsById(Long id) {
//            throw new UnsupportedOperationException("Method not implemented for testing");
//        }
//
//        // Add any other methods that are in your UserRepository interface
//        // with the same UnsupportedOperationException implementation
//    }
//}