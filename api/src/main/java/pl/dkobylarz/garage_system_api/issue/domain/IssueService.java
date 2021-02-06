package pl.dkobylarz.garage_system_api.issue.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import pl.dkobylarz.garage_system_api.car.domain.CarFacade;
import pl.dkobylarz.garage_system_api.infrastructure.logs.Logger;
import pl.dkobylarz.garage_system_api.issue.constant.IssueStatus;
import pl.dkobylarz.garage_system_api.issue.dto.CreateIssueDto;
import pl.dkobylarz.garage_system_api.issue.dto.IssueDetailsDto;
import pl.dkobylarz.garage_system_api.issue.dto.IssueDto;
import pl.dkobylarz.garage_system_api.issue.dto.IssueWithCarDto;
import pl.dkobylarz.garage_system_api.issue.exception.ActiveIssueForCarIdAlreadyExistsException;
import pl.dkobylarz.garage_system_api.issue.exception.ActiveIssueNotFoundException;
import pl.dkobylarz.garage_system_api.issue.exception.BadAmountException;
import pl.dkobylarz.garage_system_api.issue.util.IssueLogPrinter;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
class IssueService {

    private final IssueValidatorService validatorService;
    private final IssueRepository issueRepository;
    private final CarFacade carFacade;
    private final Logger issueLogger = new IssueLogPrinter();

    public IssueDetailsDto getIssueByNumber(String issueNumber) {
        Issue issue = issueRepository.findByIssueNumberAndStatusIn(issueNumber, IssueStatus.getActiveIssueStatus())
                .orElseThrow(ActiveIssueNotFoundException::new);

        return IssueDetailsDto.builder()
                .issueId(issue.getIssueId())
                .status(issue.getStatus())
                .issueNumber(issueNumber)
                .carDto(carFacade.getCarDtoById(issue.getCarId()))
                .description(issue.getDescription())
                .lastUpdated(issue.getLastUpdated())
                .build();
    }

    public IssueDetailsDto getIssueDetailsById(int issueId) {
        IssueDto issueDto = issueRepository.findByIssueId(issueId);

        return IssueDetailsDto.builder()
                .issueId(issueId)
                .amount(issueDto.getAmount())
                .status(issueDto.getStatus())
                .issueNumber(issueDto.getIssueNumber())
                .carDto(carFacade.getCarDtoById(issueDto.getCarId()))
                .description(issueDto.getDescription())
                .lastUpdated(issueDto.getLastUpdated())
                .build();
    }

    public Set<IssueWithCarDto> getActiveIssues() {
        Set<IssueDto> issuesDto = issueRepository.findAllByStatusIn(IssueStatus.getActiveIssueStatus());
        return getIssueWithCarDtoSetFor(issuesDto);
    }

    public Set<IssueWithCarDto> getActiveIssuesForUser(int userId) {
        Set<IssueDto> issuesDto = issueRepository.findByUserIdAndStatusIn(userId, IssueStatus.getActiveIssueStatus());
        return getIssueWithCarDtoSetFor(issuesDto);
    }

    public void createIssue(CreateIssueDto createIssueDto) {
        if (validatorService.activeIssueForCarIdExists(createIssueDto.getCarId()))
            throw new ActiveIssueForCarIdAlreadyExistsException();

        Issue issueToPersist = Issue.builder()
                .issueId(0)
                .userId(createIssueDto.getUserId())
                .carId(createIssueDto.getCarId())
                .description(createIssueDto.getDescription())
                .amount(BigDecimal.ZERO)
                .issueNumber(RandomStringUtils.randomNumeric(10))
                .status(IssueStatus.WAITING)
                .build();

        issueLogger.printInfo(String.format("Utworzono sprawę dla pojazdu o id: %d dla użytkownika o id: %d z opisem: %s",
                createIssueDto.getCarId(),
                createIssueDto.getUserId(),
                createIssueDto.getDescription()));

        issueRepository.save(issueToPersist);
    }

    public void assignAmountToIssue(int issueId, BigDecimal amount) {
        if (validatorService.amountIsNotProper(amount)) {
            throw new BadAmountException();
        }

        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(ActiveIssueNotFoundException::new);

        issueLogger.printInfo(String.format("Przypisano kwotę: %s zł do sprawy o id: %d",
                amount.toString(),
                issueId));

        issueRepository.save(issue.toBuilder()
                .amount(amount)
                .build());
    }

    public void changeIssueStatus(int issueId, int issueStatusId) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(ActiveIssueNotFoundException::new);

        issueLogger.printInfo(String.format("Zmieniono status sprawy %s na %s dla użytkownika o id: %d",
                issue.getIssueNumber(),
                IssueStatus.getStatusByStatusId(issueStatusId),
                issue.getUserId()));

        issueRepository.save(
                issue.toBuilder()
                        .status(IssueStatus.getStatusByStatusId(issueStatusId))
                        .build()
        );
    }

    private Set<IssueWithCarDto> getIssueWithCarDtoSetFor(Set<IssueDto> issuesDto) {
        return issuesDto.stream()
                .map(issueDto -> IssueWithCarDto.builder()
                        .issueId(issueDto.getIssueId())
                        .carBrandName(carFacade.getCarDtoById(issueDto.getCarId()).getCarBrandName())
                        .carModelName(carFacade.getCarDtoById(issueDto.getCarId()).getCarModelName())
                        .amount(issueDto.getAmount())
                        .description(issueDto.getDescription())
                        .status(issueDto.getStatus())
                        .lastUpdated(issueDto.getLastUpdated())
                        .build())
                .collect(Collectors.toSet());
    }
}
