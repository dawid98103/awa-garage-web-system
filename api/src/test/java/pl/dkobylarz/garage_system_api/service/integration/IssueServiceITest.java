//package pl.dkobylarz.garage_system_api.service.integration;
//
//import org.apache.commons.lang3.RandomStringUtils;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//import pl.dkobylarz.garage_system_api.issue.constant.IssueStatus;
//import pl.dkobylarz.garage_system_api.issue.dto.CreateIssueDto;
//import pl.dkobylarz.garage_system_api.issue.domain.Issue;
//import pl.dkobylarz.garage_system_api.issue.exception.ActiveIssueForCarIdAlreadyExistsException;
//import pl.dkobylarz.garage_system_api.issue.exception.BadAmountException;
//import pl.dkobylarz.garage_system_api.car.domain.CarRepository;
//import pl.dkobylarz.garage_system_api.issue.domain.IssueRepository;
//import pl.dkobylarz.garage_system_api.user.domain.UserRepository;
//import pl.dkobylarz.garage_system_api.issue.domain.IssueService;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.catchThrowable;
//
//@SpringBootTest
//@Transactional
//@AutoConfigureMockMvc
//class IssueServiceITest {
//
//    @Autowired
//    private IssueRepository issueRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private CarRepository carRepository;
//
//    @Autowired
//    private IssueService issueService;
//
//    @Test
//    void createIssueShouldCreateNewIssue() {
//        //given
//        CreateIssueDto createIssueDto = CreateIssueDto.builder()
//                .carId(1)
//                .userId(2)
//                .description("desc")
//                .build();
//
//        CreateIssueDto createIssueDto1 = CreateIssueDto.builder()
//                .carId(2)
//                .userId(2)
//                .description("desc1")
//                .build();
//        //when
//        Issue issue = issueService.createIssue(createIssueDto);
//        Issue issue1 = issueService.createIssue(createIssueDto1);
//
//        List<Issue> issues = issueRepository.findAll();
//
//        //then
//        assertThat(issues).isNotNull();
//        assertThat(issues).hasSize(2);
//        assertThat(issues).contains(issue, issue1);
//        assertThat(issue.getAmount()).isEqualTo(BigDecimal.ZERO);
//        assertThat(issue.getStatus()).isEqualTo(IssueStatus.WAITING);
//        assertThat(issue.getIssueNumber().length()).isEqualTo(10);
//        assertThat(issue1.getAmount()).isEqualTo(BigDecimal.ZERO);
//        assertThat(issue1.getStatus()).isEqualTo(IssueStatus.WAITING);
//        assertThat(issue1.getIssueNumber().length()).isEqualTo(10);
//    }
//
//    @Test
//    void createIssueWhenIssueForUserAndCarExistsShouldThrowException() {
//        //given
//        CreateIssueDto createIssueDto = CreateIssueDto.builder()
//                .carId(1)
//                .userId(2)
//                .description("desc")
//                .build();
//
//        CreateIssueDto createIssueDto1 = CreateIssueDto.builder()
//                .carId(1)
//                .userId(3)
//                .description("desc1")
//                .build();
//        //when
//        Issue issue = issueService.createIssue(createIssueDto);
//        Throwable thrown = catchThrowable(() -> issueService.createIssue(createIssueDto1));
//        //then
//        assertThat(thrown).isInstanceOf(ActiveIssueForCarIdAlreadyExistsException.class);
//    }
//
//    @Test
//    void changeIssueStatusShouldChangeStatusForIssue() {
//        //given
//        Issue issue = Issue.builder()
//                .issueId(0)
//                .userId(1)
//                .carId(2)
//                .issueNumber(RandomStringUtils.randomAlphabetic(5))
//                .status(IssueStatus.WAITING)
//                .amount(BigDecimal.valueOf(255))
//                .description("desc")
//                .lastUpdated(LocalDateTime.now())
//                .createdDate(LocalDateTime.now())
//                .build();
//
//        Issue issue1 = Issue.builder()
//                .issueId(0)
//                .userId(1)
//                .carId(3)
//                .issueNumber(RandomStringUtils.randomAlphabetic(5))
//                .status(IssueStatus.IN_PROGRESS)
//                .amount(BigDecimal.valueOf(355))
//                .description("desc")
//                .lastUpdated(LocalDateTime.now())
//                .createdDate(LocalDateTime.now())
//                .build();
//
//        Issue issue2 = Issue.builder()
//                .issueId(0)
//                .userId(1)
//                .carId(3)
//                .issueNumber(RandomStringUtils.randomAlphabetic(5))
//                .status(IssueStatus.FINISHED)
//                .amount(BigDecimal.valueOf(355))
//                .description("desc")
//                .lastUpdated(LocalDateTime.now())
//                .createdDate(LocalDateTime.now())
//                .build();
//
//        Issue issue3 = Issue.builder()
//                .issueId(0)
//                .userId(1)
//                .carId(3)
//                .issueNumber(RandomStringUtils.randomAlphabetic(5))
//                .status(IssueStatus.ACCEPTED)
//                .amount(BigDecimal.valueOf(355))
//                .description("desc")
//                .lastUpdated(LocalDateTime.now())
//                .createdDate(LocalDateTime.now())
//                .build();
//
//        //when
//        Issue retrievedIssue = issueRepository.save(issue);
//        Issue retrievedIssue1 = issueRepository.save(issue1);
//        Issue retrievedIssue2 = issueRepository.save(issue2);
//        Issue retrievedIssue3 = issueRepository.save(issue3);
//
//        issueService.changeIssueStatus(retrievedIssue.getIssueId(), 2);
//        issueService.changeIssueStatus(retrievedIssue1.getIssueId(), 1);
//        issueService.changeIssueStatus(retrievedIssue2.getIssueId(), 4);
//        issueService.changeIssueStatus(retrievedIssue3.getIssueId(), 2);
//
//        Issue retrievedIssueAfterChange = issueRepository.getOne(retrievedIssue.getIssueId());
//        Issue retrievedIssue1AfterChange = issueRepository.getOne(retrievedIssue1.getIssueId());
//        Issue retrievedIssue2AfterChange = issueRepository.getOne(retrievedIssue2.getIssueId());
//        Issue retrievedIssue3AfterChange = issueRepository.getOne(retrievedIssue3.getIssueId());
//
//        //then
//        assertThat(retrievedIssueAfterChange).isNotNull();
//        assertThat(retrievedIssue1AfterChange).isNotNull();
//        assertThat(retrievedIssue2AfterChange).isNotNull();
//        assertThat(retrievedIssueAfterChange).usingRecursiveComparison().ignoringFields("status").isEqualTo(retrievedIssue);
//        assertThat(retrievedIssueAfterChange.getStatus()).isEqualTo(IssueStatus.ACCEPTED);
//        assertThat(retrievedIssue1AfterChange).usingRecursiveComparison().ignoringFields("status").isEqualTo(retrievedIssue1);
//        assertThat(retrievedIssue1AfterChange.getStatus()).isEqualTo(IssueStatus.WAITING);
//        assertThat(retrievedIssue2AfterChange).usingRecursiveComparison().ignoringFields("status").isEqualTo(retrievedIssue2);
//        assertThat(retrievedIssue2AfterChange.getStatus()).isEqualTo(IssueStatus.TO_RECEIVED);
//        assertThat(retrievedIssue3AfterChange).usingRecursiveComparison().ignoringFields("status").isEqualTo(retrievedIssue3);
//        assertThat(retrievedIssue3AfterChange.getStatus()).isEqualTo(IssueStatus.ACCEPTED);
//    }
//
//    @Test
//    void assignAmountToIssueShouldSetProperAmount() {
//        //given
//        Issue issue = Issue.builder()
//                .issueId(0)
//                .description("desc")
//                .status(IssueStatus.TO_RECEIVED)
//                .issueNumber(RandomStringUtils.randomAlphabetic(10))
//                .carId(5)
//                .userId(5)
//                .amount(BigDecimal.ZERO)
//                .createdDate(LocalDateTime.now())
//                .lastUpdated(LocalDateTime.now().minusHours(1))
//                .build();
//
//        Issue issue1 = Issue.builder()
//                .issueId(0)
//                .description("desc")
//                .status(IssueStatus.WAITING)
//                .issueNumber(RandomStringUtils.randomAlphabetic(10))
//                .carId(5)
//                .userId(6)
//                .amount(BigDecimal.ZERO)
//                .createdDate(LocalDateTime.now())
//                .lastUpdated(LocalDateTime.now().minusHours(1))
//                .build();
//
//        Issue savedIssue = issueRepository.save(issue);
//        Issue savedIssue1 = issueRepository.save(issue1);
//
//        BigDecimal amount = BigDecimal.valueOf(2137);
//        BigDecimal amount1 = BigDecimal.valueOf(-222);
//
//        //when
//        issueService.assignAmountToIssue(savedIssue.getIssueId(), amount);
//        Throwable thrown = catchThrowable(() -> issueService.assignAmountToIssue(savedIssue1.getIssueId(), amount1));
//
//        Issue retrievedIssue = issueRepository.getOne(issue.getIssueId());
//        Issue retrievedIssue1 = issueRepository.getOne(issue1.getIssueId());
//
//        //then
//        assertThat(retrievedIssue).as("Should not be null").isNotNull();
//        assertThat(retrievedIssue.getAmount()).as("Should be equal %s", amount.toString()).isEqualTo(amount);
//        assertThat(retrievedIssue.getLastUpdated()).isEqualToIgnoringNanos(LocalDateTime.now());
//        assertThat(retrievedIssue).usingRecursiveComparison().ignoringFields("lastUpdated", "amount").isEqualTo(issue);
//
//        assertThat(retrievedIssue1).as("Should not be null").isNotNull();
//        assertThat(retrievedIssue1.getAmount()).as("Should be equal %s", BigDecimal.ZERO.toString()).isEqualTo(BigDecimal.ZERO);
//        assertThat(issue1.getLastUpdated()).isEqualToIgnoringNanos(retrievedIssue.getLastUpdated());
//        assertThat(retrievedIssue1).usingRecursiveComparison().isEqualTo(issue1);
//
//        assertThat(thrown).as("Should be proper instance of exception").isInstanceOf(BadAmountException.class).hasMessage("Podano nieprawidłową kwotę");
//    }
//
//    @Test
//    void getIssueByNumber() {
//    }
//
//    @Test
//    void getIssueDetailsById() {
//    }
//
//    @Test
//    void getActiveIssues() {
//    }
//
//    @Test
//    void getActiveIssuesForUser() {
//    }
//
//    @Test
//    void checkIfActiveIssueForCarIdExists() {
//    }
//}