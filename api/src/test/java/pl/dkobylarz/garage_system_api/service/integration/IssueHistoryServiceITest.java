//package pl.dkobylarz.garage_system_api.service.integration;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//import pl.dkobylarz.garage_system_api.issue.constant.IssueStatus;
//import pl.dkobylarz.garage_system_api.issue.dto.IssueHistoryDto;
//import pl.dkobylarz.garage_system_api.car.domain.Car;
//import pl.dkobylarz.garage_system_api.issue.domain.Issue;
//import pl.dkobylarz.garage_system_api.user.domain.User;
//import pl.dkobylarz.garage_system_api.car.domain.CarRepository;
//import pl.dkobylarz.garage_system_api.issue.domain.IssueRepository;
//import pl.dkobylarz.garage_system_api.user.domain.UserRepository;
//import pl.dkobylarz.garage_system_api.issue.domain.IssueHistoryService;
//import pl.dkobylarz.garage_system_api.factory.CarFactory;
//import pl.dkobylarz.garage_system_api.factory.IssueFactory;
//import pl.dkobylarz.garage_system_api.factory.UserFactory;
//
//import java.math.BigDecimal;
//import java.util.Set;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@Transactional
//@AutoConfigureMockMvc
//class IssueHistoryServiceITest {
//
//    @Autowired
//    private IssueHistoryService issueHistoryService;
//    @Autowired
//    private CarRepository carRepository;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private IssueRepository issueRepository;
//
//    @Test
//    void getIssueHistoryShouldReturnProperSet() {
//        //given
//        Car car1 = CarFactory.createCarWith("Audi A3");
//        Car car2 = CarFactory.createCarWith("VW Passat");
//        Car car3 = CarFactory.createCarWith("Toyota Rav 4");
//        Car car4 = CarFactory.createCarWith("Polonez Caro");
//
//        Car retrievedCar1 = carRepository.save(car1);
//        Car retrievedCar2 = carRepository.save(car2);
//        Car retrievedCar3 = carRepository.save(car3);
//        Car retrievedCar4 = carRepository.save(car4);
//
//        User user1 = UserFactory.createUserWith(0, "Grzegorz");
//        User user2 = UserFactory.createUserWith(0, "Dawid");
//        User user3 = UserFactory.createUserWith(0, "Marta");
//        User user4 = UserFactory.createUserWith(0, "Marcin");
//
//        User retrievedUser1 = userRepository.save(user1);
//        User retrievedUser2 = userRepository.save(user2);
//        User retrievedUser3 = userRepository.save(user3);
//        User retrievedUser4 = userRepository.save(user4);
//
//        Issue issue1 = IssueFactory.createRandomIssueWith(0, retrievedCar1.getCarId(), retrievedUser1.getUserId(), IssueStatus.FINISHED, BigDecimal.valueOf(2137));
//        Issue issue2 = IssueFactory.createRandomIssueWith(0, retrievedCar2.getCarId(), retrievedUser2.getUserId(), IssueStatus.IN_PROGRESS, BigDecimal.valueOf(2138));
//        Issue issue3 = IssueFactory.createRandomIssueWith(0, retrievedCar3.getCarId(), retrievedUser3.getUserId(), IssueStatus.WAITING, BigDecimal.valueOf(2139));
//        Issue issue4 = IssueFactory.createRandomIssueWith(0, retrievedCar4.getCarId(), retrievedUser4.getUserId(), IssueStatus.FINISHED, BigDecimal.valueOf(2140));
//
//        int issue1Id = issueRepository.save(issue1).getIssueId();
//        int issue2Id = issueRepository.save(issue2).getIssueId();
//        int issue3Id = issueRepository.save(issue3).getIssueId();
//        int issue4Id = issueRepository.save(issue4).getIssueId();
//
//        //when
//        Set<IssueHistoryDto> issueHistoryDtos = issueHistoryService.getIssuesHistory();
//
//        //then
//        assertThat(issueHistoryDtos).isNotNull();
//        assertThat(issueHistoryDtos).hasSize(2);
//
//        assertThat(issueHistoryDtos).filteredOn("issueId", issue1Id).hasSize(1);
//        assertThat(issueHistoryDtos).filteredOn("issueId", issue2Id).hasSize(0);
//        assertThat(issueHistoryDtos).filteredOn("issueId", issue3Id).hasSize(0);
//        assertThat(issueHistoryDtos).filteredOn("issueId", issue4Id).hasSize(1);
//
//        assertThat(issueHistoryDtos.stream().filter(x -> x.getIssueId() == issue1Id).findFirst().get().getDescription()).isEqualTo(issue1.getDescription());
//        assertThat(issueHistoryDtos.stream().filter(x -> x.getIssueId() == issue1Id).findFirst().get().getAmount()).isEqualTo(issue1.getAmount());
//        assertThat(issueHistoryDtos.stream().filter(x -> x.getIssueId() == issue1Id).findFirst().get().getAmount()).isEqualTo(issue1.getAmount());
//        assertThat(issueHistoryDtos.stream().filter(x -> x.getIssueId() == issue4Id).findFirst().get().getDescription()).isEqualTo(issue4.getDescription());
//        assertThat(issueHistoryDtos.stream().filter(x -> x.getIssueId() == issue4Id).findFirst().get().getAmount()).isEqualTo(issue4.getAmount());
//    }
//
//    @Test
//    void getIssueHistoryForSpecificCar(){
//        //given
//        Car car1 = CarFactory.createCarWith("Audi A3");
//        Car retrievedCar1 = carRepository.save(car1);
//
//        User user1 = UserFactory.createUserWith(0, "Grzegorz");
//        User retrievedUser1 = userRepository.save(user1);
//
//        Issue issue1 = IssueFactory.createRandomIssueWith(0, retrievedCar1.getCarId(), retrievedUser1.getUserId(), IssueStatus.FINISHED, BigDecimal.valueOf(2137));
//        Issue issue2 = IssueFactory.createRandomIssueWith(0, retrievedCar1.getCarId(), retrievedUser1.getUserId(), IssueStatus.FINISHED, BigDecimal.valueOf(2140));
//        Issue issue3 = IssueFactory.createRandomIssueWith(0, retrievedCar1.getCarId(), retrievedUser1.getUserId(), IssueStatus.ACCEPTED, BigDecimal.valueOf(2140));
//
//        int issue1Id = issueRepository.save(issue1).getIssueId();
//        int issue2Id = issueRepository.save(issue2).getIssueId();
//        int issue3Id = issueRepository.save(issue3).getIssueId();
//
//        //when
//        Set<IssueHistoryDto> issueHistoryDtos = issueHistoryService.getIssueHistoryFor(retrievedCar1.getCarId());
//
//        //then
//        assertThat(issueHistoryDtos).isNotNull();
//        assertThat(issueHistoryDtos).hasSize(2);
//        assertThat(issueHistoryDtos.stream().anyMatch(x -> x.getIssueId() == issue1Id));
//        assertThat(issueHistoryDtos.stream().anyMatch(x -> x.getIssueId() == issue2Id));
//        assertThat(issueHistoryDtos.stream().noneMatch(x -> x.getIssueId() == issue3Id));
//    }
//}
