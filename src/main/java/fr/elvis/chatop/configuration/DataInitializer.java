package fr.elvis.chatop.configuration;

import fr.elvis.chatop.controller.LoginController;
import fr.elvis.chatop.entities.*;
import fr.elvis.chatop.repository.MessagesRepository;
import fr.elvis.chatop.repository.RentalRepository;
import fr.elvis.chatop.repository.RoleRepository;
import fr.elvis.chatop.repository.UserRepository;
import fr.elvis.chatop.servicies.DataService;
import fr.elvis.chatop.servicies.RoleService;
import fr.elvis.chatop.servicies.UserService;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class DataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);


    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbusername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;
    private final MessagesRepository messageRepository;
    private final RoleRepository roleRepository;
    private final DataService dataService;

    public DataInitializer(UserRepository userRepository, RentalRepository rentalRepository, MessagesRepository messageRepository, RoleRepository roleRepository, DataService dataService) {
        this.userRepository = userRepository;
        this.rentalRepository = rentalRepository;
        this.messageRepository = messageRepository;
        this.roleRepository = roleRepository;
        this.dataService = dataService;
    }

    @Bean
    @Transactional
    CommandLineRunner initDatabase() {
        return args -> {
            initSequences();
            initRoles();

            dataService.sauvegarderUtilisateurs(initUsers()).thenRun(() -> {
                dataService.obtenirUtilisateurs().thenAccept(persistedUsers -> {
                    assignRolesToUsers(persistedUsers);

                    List<Rental> rentals = initRentals(persistedUsers);
                    dataService.sauvegarderLocations(rentals).thenRun(() -> {
                        dataService.obtenirLocations().thenAccept(persistedRentals -> {
                            List<MessagesEntity> messages = initMessages(persistedUsers, persistedRentals);
                            dataService.sauvegarderMessages(messages).thenRun(() -> {
                            });
                        });
                    });
                });
            });
        };
    }

    private void initSequences() {
        try (Connection conn = DriverManager.getConnection(dbUrl, dbusername, dbPassword);
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS messages_seq (next_val BIGINT)");
            stmt.execute("INSERT INTO messages_seq (next_val) VALUES (1) ON DUPLICATE KEY UPDATE next_val = next_val");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initRoles() {
        List<Role> roles = Arrays.asList(new Role("USER"), new Role("ADMIN"));
        roles.forEach(role -> {
            if (!roleRepository.existsByName(role.getName())) {
                logger.info("Saving role: " + role.getName());
                roleRepository.save(role);
            } else {
                logger.info("Role already exists: " + role.getName());
            }
        });
    }


    @Transactional
    public void assignRolesToUsers(List<UserEntity> persistedUsers) {
        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseThrow(() -> new RuntimeException("Role not found: ADMIN"));
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role not found: USER"));

        for (UserEntity user : persistedUsers) {
            Hibernate.initialize(user.getRole());
            Set<Role> roles = new HashSet<>(user.getRole());

            boolean hasUserRole = userService.hasRole(user.getUsername(), "USER");
            if (!hasUserRole) {
                roles.add(userRole);
            }

            boolean hasAdminRole = userService.hasRole(user.getUsername(), "ADMIN");
            if (user.getUsername().toLowerCase().contains("admin") && !hasAdminRole) {
                roles.add(adminRole);
            }

            if (!roles.equals(user.getRole())) {
                user.setRole(roles);
                userRepository.save(user);
                logger.info("Assigned roles to user: " + user.getUsername() + " - " + roles);
            }
        }
    }


    private List<UserEntity> initUsers() {
        List<UserEntity> users = Arrays.asList(
                new UserEntity("admin1", "admin1@example.com", "$2a$10$YPV6Wn8tI/j0t83cXtd6h.HV1uWkJWOPFEfHGWfCLc1wuOg8kOC/W", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())), // password: admin
                new UserEntity("admin2", "admin2@example.com", "$2a$10$YPV6Wn8tI/j0t83cXtd6h.HV1uWkJWOPFEfHGWfCLc1wuOg8kOC/W", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())), // password: admin
                new UserEntity("admin3", "admin3@example.com", "$2a$10$YPV6Wn8tI/j0t83cXtd6h.HV1uWkJWOPFEfHGWfCLc1wuOg8kOC/W", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())), // password: admin
                new UserEntity("admin4", "admin4@example.com", "$2a$10$U7dfl0h.Qj1/Ai/bcH9lo.uxUuMDj5jANX8NrRD7Hl2eJxD1NHmPO", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())), // password: admin
                new UserEntity("user1", "user1@example.com", "$2a$10$OlwUpvd42IwtBkJ0E5jNbO2Kgj3AOu4lf5vApbeiTCFb3PLsxmvra", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())), // password: user
                new UserEntity("user2", "user2@example.com", "$2a$10$OlwUpvd42IwtBkJ0E5jNbO2Kgj3AOu4lf5vApbeiTCFb3PLsxmvra", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())), // password: user
                new UserEntity("user3", "user3@example.com", "$2a$10$OlwUpvd42IwtBkJ0E5jNbO2Kgj3AOu4lf5vApbeiTCFb3PLsxmvra", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())), // password: user
                new UserEntity("user4", "user4@example.com", "$2a$10$OlwUpvd42IwtBkJ0E5jNbO2Kgj3AOu4lf5vApbeiTCFb3PLsxmvra", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())), // password: user
                new UserEntity("user5", "user5@example.com", "$2a$10$OlwUpvd42IwtBkJ0E5jNbO2Kgj3AOu4lf5vApbeiTCFb3PLsxmvra", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())), // password: user
                new UserEntity("user6", "user6@example.com", "$2a$10$OlwUpvd42IwtBkJ0E5jNbO2Kgj3AOu4lf5vApbeiTCFb3PLsxmvra", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())), // password: user
                new UserEntity("user7", "user7@example.com", "$2a$10$OlwUpvd42IwtBkJ0E5jNbO2Kgj3AOu4lf5vApbeiTCFb3PLsxmvra", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())), // password: user
                new UserEntity("user8", "user8@example.com", "$2a$10$OlwUpvd42IwtBkJ0E5jNbO2Kgj3AOu4lf5vApbeiTCFb3PLsxmvra", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())), // password: user
                new UserEntity("user9", "user9@example.com", "$2a$10$OlwUpvd42IwtBkJ0E5jNbO2Kgj3AOu4lf5vApbeiTCFb3PLsxmvra", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())), // password: user
                new UserEntity("user10", "user10@example.com", "$2a$10$OlwUpvd42IwtBkJ0E5jNbO2Kgj3AOu4lf5vApbeiTCFb3PLsxmvra", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())), // password: user
                new UserEntity("user11", "user11@example.com", "$2a$10$OlwUpvd42IwtBkJ0E5jNbO2Kgj3AOu4lf5vApbeiTCFb3PLsxmvra", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())), // password: user
                new UserEntity("user12", "user12@example.com", "$2a$10$OlwUpvd42IwtBkJ0E5jNbO2Kgj3AOu4lf5vApbeiTCFb3PLsxmvra", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())), // password: user
                new UserEntity("user13", "user13@example.com", "$2a$10$OlwUpvd42IwtBkJ0E5jNbO2Kgj3AOu4lf5vApbeiTCFb3PLsxmvra", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())), // password: user
                new UserEntity("user14", "user14@example.com", "$2a$10$OlwUpvd42IwtBkJ0E5jNbO2Kgj3AOu4lf5vApbeiTCFb3PLsxmvra", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())), // password: user
                new UserEntity("user15", "user15@example.com", "$2a$10$OlwUpvd42IwtBkJ0E5jNbO2Kgj3AOu4lf5vApbeiTCFb3PLsxmvra", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())), // password: user
                new UserEntity("user16", "user16@example.com", "$2a$10$OlwUpvd42IwtBkJ0E5jNbO2Kgj3AOu4lf5vApbeiTCFb3PLsxmvra", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())), // password: user
                new UserEntity("user17", "user17@example.com", "$2a$10$OlwUpvd42IwtBkJ0E5jNbO2Kgj3AOu4lf5vApbeiTCFb3PLsxmvra", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())), // password: user
                new UserEntity("user18", "user18@example.com", "$2a$10$OlwUpvd42IwtBkJ0E5jNbO2Kgj3AOu4lf5vApbeiTCFb3PLsxmvra", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())), // password: user
                new UserEntity("user19", "user19@example.com", "$2a$10$OlwUpvd42IwtBkJ0E5jNbO2Kgj3AOu4lf5vApbeiTCFb3PLsxmvra", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())), // password: user
                new UserEntity("user20", "user20@example.com", "$2a$10$OlwUpvd42IwtBkJ0E5jNbO2Kgj3AOu4lf5vApbeiTCFb3PLsxmvra", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()))
                );
        return users;
    }

    private List<Rental> initRentals(List<UserEntity> persistedUsers) {
        List<Rental> rentals = Arrays.asList(
                new Rental(new Date(), "Bel appartement de 2 chambres.", 1, "Location 1", "https://example.com/pictures/rental1.jpg", 750, persistedUsers.get(0), 55.5, new Date()),
                new Rental(new Date(), "Appartement moderne avec terrasse.", 2, "Location 2", "https://example.com/pictures/rental2.jpg", 800, persistedUsers.get(1), 65.0, new Date()),
                new Rental(new Date(), "Maison de campagne avec jardin.", 3, "Location 3", "https://example.com/pictures/rental3.jpg", 950, persistedUsers.get(2), 72.0, new Date()),
                new Rental(new Date(), "Appartement lumineux à proximité du centre.", 4, "Location 4", "https://example.com/pictures/rental4.jpg", 700, persistedUsers.get(3), 58.5, new Date()),
                new Rental(new Date(), "Grande maison avec piscine.", 5, "Location 5", "https://example.com/pictures/rental5.jpg", 1100, persistedUsers.get(4), 85.0, new Date()),
                new Rental(new Date(), "Villa avec vue sur la mer.", 6, "Location 6", "https://example.com/pictures/rental6.jpg", 1200, persistedUsers.get(5), 95.0, new Date()),
                new Rental(new Date(), "Studio cosy en centre-ville.", 7, "Location 7", "https://example.com/pictures/rental7.jpg", 650, persistedUsers.get(6), 48.0, new Date()),
                new Rental(new Date(), "Grande maison de 4 chambres avec grand jardin.", 8, "Location 8", "https://example.com/pictures/rental8.jpg", 1000, persistedUsers.get(7), 80.0, new Date()),
                new Rental(new Date(), "Appartement avec balcon et garage.", 9, "Location 9", "https://example.com/pictures/rental9.jpg", 850, persistedUsers.get(8), 68.5, new Date()),
                new Rental(new Date(), "Charmante maison de ville.", 10, "Location 10", "https://example.com/pictures/rental10.jpg", 900, persistedUsers.get(9), 76.0, new Date()),
                new Rental(new Date(), "Appartement spacieux à proximité des transports.", 11, "Location 11", "https://example.com/pictures/rental11.jpg", 750, persistedUsers.get(10), 65.0, new Date()),
                new Rental(new Date(), "Maison de luxe avec jardin et jacuzzi.", 12, "Location 12", "https://example.com/pictures/rental12.jpg", 1050, persistedUsers.get(11), 85.0, new Date()),
                new Rental(new Date(), "Studio cosy et bien situé.", 13, "Location 13", "https://example.com/pictures/rental13.jpg", 680, persistedUsers.get(12), 58.5, new Date()),
                new Rental(new Date(), "Maison avec piscine et jardin tropical.", 14, "Location 14", "https://example.com/pictures/rental14.jpg", 1150, persistedUsers.get(13), 92.0, new Date()),
                new Rental(new Date(), "Appartement contemporain avec vue.", 15, "Location 15", "https://example.com/pictures/rental15.jpg", 950, persistedUsers.get(14), 70.0, new Date()),
                new Rental(new Date(), "Maison de vacances en bord de mer.", 16, "Location 16", "https://example.com/pictures/rental16.jpg", 1050, persistedUsers.get(15), 80.0, new Date()),
                new Rental(new Date(), "Penthouse avec grande terrasse.", 17, "Location 17", "https://example.com/pictures/rental17.jpg", 900, persistedUsers.get(16), 75.0, new Date()),
                new Rental(new Date(), "Appartement avec cuisine équipée.", 18, "Location 18", "https://example.com/pictures/rental18.jpg", 800, persistedUsers.get(17), 60.0, new Date()),
                new Rental(new Date(), "Grande villa avec terrasse.", 19, "Location 19", "https://example.com/pictures/rental19.jpg", 1100, persistedUsers.get(18), 88.0, new Date()),
                new Rental(new Date(), "Maison familiale avec jardin et garage.", 20, "Location 20", "https://example.com/pictures/rental20.jpg", 1200, persistedUsers.get(19), 92.5, new Date())
        );
        return rentals;
    }


    private List<MessagesEntity> initMessages(List<UserEntity> persistedUsers, List<Rental> rentals) {
        Map<String, UserEntity> userMap = persistedUsers.stream()
                .collect(Collectors.toMap(UserEntity::getUsername, Function.identity()));

        Map<Integer, Rental> rentalMap = rentals.stream().filter(rental -> rental.getId() != 0)
                .collect(Collectors.toMap(Rental::getId, Function.identity()));

        List<MessagesEntity> messages = Arrays.asList(
                new MessagesEntity(rentalMap.get(1), userMap.get("admin1"), "Je suis intéressé par la location.", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(2), userMap.get("admin2"), "La maison a l'air magnifique, est-elle encore disponible ?", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(1), userMap.get("admin1"), "Je suis intéressé par la location.", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(2), userMap.get("admin2"), "La maison a l'air magnifique, est-elle encore disponible ?", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(3), userMap.get("admin3"), "J'aimerais avoir plus d'informations sur cet appartement.", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(4), userMap.get("admin4"), "Est-ce que les animaux sont acceptés dans cette location ?", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(5), userMap.get("admin5"), "Quel est le loyer mensuel de cette maison ?", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(6), userMap.get("admin6"), "Je souhaite visiter cet appartement, pouvez-vous me donner une date ?", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(7), userMap.get("admin7"), "Y a-t-il un parking disponible ?", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(8), userMap.get("admin8"), "La cuisine est-elle entièrement équipée ?", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(9), userMap.get("admin9"), "Est-ce que l'appartement est meublé ?", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(10), userMap.get("admin10"), "Je suis intéressé par la location à long terme. Est-ce possible ?", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(11), userMap.get("admin11"), "Pouvez-vous me donner plus de détails sur le quartier ?", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(12), userMap.get("admin12"), "La piscine est-elle incluse dans la location ?", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(13), userMap.get("admin13"), "Ce logement est-il disponible immédiatement ?", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(14), userMap.get("admin14"), "Le loyer est-il négociable ?", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(15), userMap.get("admin15"), "Est-ce un logement sécurisé ?", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(16), userMap.get("admin16"), "Y a-t-il des transports publics à proximité ?", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(17), userMap.get("admin17"), "Est-ce que cet appartement dispose d'une terrasse ?", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(18), userMap.get("admin18"), "J'aimerais savoir si le loyer inclut les charges.", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(19), userMap.get("admin19"), "Ce logement accepte-t-il les colocataires ?", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(20), userMap.get("admin20"), "Avez-vous des offres similaires à cette location ?", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(1), userMap.get("user1"), "Je suis intéressé par la location.", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(2), userMap.get("user2"), "La maison a l'air magnifique, est-elle encore disponible ?", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(3), userMap.get("user3"), "J'aimerais avoir plus d'informations sur cet appartement.", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(4), userMap.get("user4"), "Est-ce que les animaux sont acceptés dans cette location ?", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(5), userMap.get("user5"), "Quel est le loyer mensuel de cette maison ?", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(6), userMap.get("user6"), "Je souhaite visiter cet appartement, pouvez-vous me donner une date ?", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(7), userMap.get("user7"), "Y a-t-il un parking disponible ?", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(8), userMap.get("user8"), "La cuisine est-elle entièrement équipée ?", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(9), userMap.get("user9"), "Est-ce que l'appartement est meublé ?", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(10), userMap.get("user10"), "Je suis intéressé par la location à long terme. Est-ce possible ?", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(11), userMap.get("user11"), "P ouvez-vous me donner plus de détails sur le quartier ?", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(12), userMap.get("user12"), " La piscine est-elle incluse dans la location ?", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(13), userMap.get("user13"), "  Ce logement est-il disponible immédiatement ?", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(14), userMap.get("user14"), "  Le loyer est-il négociable ?", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(15), userMap.get("user15"), "  Est-ce un logement sécurisé ?", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(16), userMap.get("user16"), "  Y a-t-il des transports publics à proximité ?", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(17), userMap.get("user17"), "  Est-ce que cet appartement dispose d'une terrasse ?", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(18), userMap.get("user18"), "  J'aimerais savoir si le loyer inclut les charges.", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(19), userMap.get("user19"), "  Ce logement accepte-t-il les colocataires ?", new Timestamp(System.currentTimeMillis())),
                new MessagesEntity(rentalMap.get(20), userMap.get("user20"), "  Avez-vous des offres similaires à cette location ?", new Timestamp(System.currentTimeMillis()))
        );

        return messages;
    }
}

