//package pl.dkobylarz.garage_system_api.service.integration;
//
//import org.apache.commons.lang3.RandomStringUtils;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//import pl.dkobylarz.garage_system_api.issue.constant.IssueStatus;
//import pl.dkobylarz.garage_system_api.user.constant.UserRoles;
//import pl.dkobylarz.garage_system_api.user.dto.ClientDto;
//import pl.dkobylarz.garage_system_api.car.domain.Car;
//import pl.dkobylarz.garage_system_api.issue.domain.Issue;
//import pl.dkobylarz.garage_system_api.user.domain.User;
//import pl.dkobylarz.garage_system_api.user.exception.UserHasActiveIssueException;
//import pl.dkobylarz.garage_system_api.car.domain.CarRepository;
//import pl.dkobylarz.garage_system_api.issue.domain.IssueRepository;
//import pl.dkobylarz.garage_system_api.user.domain.UserRepository;
//import pl.dkobylarz.garage_system_api.user.domain.UserService;
//import pl.dkobylarz.garage_system_api.factory.CarFactory;
//import pl.dkobylarz.garage_system_api.factory.IssueFactory;
//import pl.dkobylarz.garage_system_api.factory.UserFactory;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Set;
//
//import static org.assertj.core.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//@AutoConfigureMockMvc
//class UserServiceITest {
//
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private CarRepository carRepository;
//    @Autowired
//    private IssueRepository issueRepository;
//
//    @Test
//    void getUsersByRoleShouldReturnClientsWhereRoleIdEqualsOne() {
//        //given
//        Car car = CarFactory.createCarWith("Audi A3");
//        Car car1 = CarFactory.createCarWith("Passat B5");
//        Car car2 = CarFactory.createCarWith("Golf 4");
//
//        Set<Car> cars = Set.of(car);
//        Set<Car> cars1 = Set.of(car1);
//        Set<Car> cars2 = Set.of(car2);
//
//        User user = UserFactory.createUserWithCarsAndRole(0, "testowy", UserRoles.CLIENT, cars);
//        User user1 = UserFactory.createUserWithCarsAndRole(0, "testowy", UserRoles.CAR_MECHANIC, cars1);
//        User user2 = UserFactory.createUserWithCarsAndRole(0, "testowy", UserRoles.CLIENT, cars2);
//
//        User savedUser = userRepository.save(user);
//        User savedUser1 = userRepository.save(user1);
//        User savedUser2 = userRepository.save(user2);
//
//        //when
//        Set<ClientDto> retrievedUsers = userService.getUsersByRoleId(1);
//
//        //then
//        assertThat(retrievedUsers).as("Retrieved set cannot be null").isNotNull();
//        assertThat(retrievedUsers.size()).as("Retrieved set should contain single element").isEqualTo(2);
//        assertThat(retrievedUsers.stream().anyMatch(clientDto -> clientDto.getUserId() == savedUser.getUserId())).isEqualTo(true);
//        assertThat(retrievedUsers.stream().anyMatch(clientDto -> clientDto.getUserId() == savedUser2.getUserId())).isEqualTo(true);
//        assertThat(retrievedUsers.stream().anyMatch(clientDto -> clientDto.getUserId() == savedUser1.getUserId())).isEqualTo(false);
//    }
//
//    @Test
//    void getUserAvatarShouldReturnUrlToAvatar() {
//        //given
//        String avatarUrl = RandomStringUtils.randomAlphabetic(10);
//        User newUser = User.builder()
//                .password("passwd")
//                .username("testowy")
//                .userAvatar(avatarUrl)
//                .roleId(0)
//                .cars(null)
//                .createdDate(LocalDateTime.now())
//                .lastUpdated(LocalDateTime.now())
//                .build();
//
//        int userId = userRepository.save(newUser).getUserId();
//
//        //when
//        String retrievedUrl = userService.getUserAvatarUrl(userId);
//
//        //then
//        assertThat(retrievedUrl).isNotNull();
//        assertThat(retrievedUrl).isEqualTo(avatarUrl);
//    }
//
//    @Test
//    void deleteUserByIdShouldDeleteUserWithAssignedCars(){
//        //given
//        Set<Car> carsForUser = CarFactory.createRandomCars();
//        Set<Car> carsForUser1 = CarFactory.createRandomCars();
//        Set<Car> carsForUser2 = CarFactory.createRandomCars();
//
//        User user = UserFactory.createUserWithCars(0, "user", carsForUser);
//        User user1 = UserFactory.createUserWithCars(0, "user1", carsForUser1);
//        User user2 = UserFactory.createUserWithCars(0, "user2", carsForUser2);
//
//        User savedUser = userRepository.save(user);
//        User savedUser1 = userRepository.save(user1);
//        User savedUser2 = userRepository.save(user2);
//
//        //when
//        userService.deleteUserById(savedUser.getUserId());
//        List<User> users = userRepository.findAll();
//        List<Car> cars = carRepository.findAll();
//
//        //then
//        assertThat(users).isNotNull();
//        assertThat(users.size()).isEqualTo(2);
//        assertThat(users).contains(savedUser1);
//        assertThat(users).contains(savedUser2);
//        assertThat(users).doesNotContain(savedUser);
//
//        assertThat(cars).isNotNull();
//        assertThat(cars.size()).isEqualTo(6);
//        assertThat(cars).containsAll(carsForUser1);
//        assertThat(cars).containsAll(carsForUser2);
//        assertThat(cars).doesNotContainAnyElementsOf(carsForUser);
//    }
//
//    @Test
//    void deleteUserByIdWhichHasActiveIssueShouldThrowException(){
//        //given
//        User user = UserFactory.createUserWith(0, "user");
//        User savedUser = userRepository.save(user);
//
//        Issue issue = IssueFactory.createRandomIssueWith(0, 2, savedUser.getUserId(), IssueStatus.ACCEPTED, BigDecimal.valueOf(5));
//        issueRepository.save(issue);
//
//        //when
//        Throwable thrown = catchThrowable(() -> userService.deleteUserById(savedUser.getUserId()));
//
//        //then
//        assertThat(thrown).as("Should throw exception").isInstanceOf(UserHasActiveIssueException.class);
//    }
//
//    @Test
//    void getCarsForUserShouldThrowExceptionWhenUserNotExists() {
//
//    }
//}