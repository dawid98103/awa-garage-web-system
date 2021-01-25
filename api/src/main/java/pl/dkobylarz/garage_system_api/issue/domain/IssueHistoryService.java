package pl.dkobylarz.garage_system_api.issue.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dkobylarz.garage_system_api.car.domain.CarFacade;
import pl.dkobylarz.garage_system_api.issue.constant.IssueStatus;
import pl.dkobylarz.garage_system_api.issue.dto.IssueHistoryDto;
import pl.dkobylarz.garage_system_api.user.domain.UserFacade;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class IssueHistoryService {

    private final IssueRepository issueRepository;
    private final UserFacade userFacade;
    private final CarFacade carFacade;

    public Set<IssueHistoryDto> getIssuesHistory() {
        Set<Issue> issues = issueRepository.findAllEntitiesByStatusIn(Set.of(IssueStatus.FINISHED));
        return createIssueHistoryDtoSetFor(issues);
    }

    public Set<IssueHistoryDto> getIssueHistoryFor(int carId) {
        Set<Issue> issues = issueRepository.findAllEntitiesByCarIdAndStatusIn(carId, Set.of(IssueStatus.FINISHED));
        return createIssueHistoryDtoSetFor(issues);
    }

    private Set<IssueHistoryDto> createIssueHistoryDtoSetFor(Set<Issue> issues) {
        return issues.stream()
                .map(issue -> IssueHistoryDto.builder()
                        .issueId(issue.getIssueId())
                        .carBrandName(getBrandNameFor(issue.getCarId()))
                        .carModelName(getModelNameFor(issue.getCarId()))
                        .username(getUsernameFor(issue.getUserId()))
                        .description(issue.getDescription())
                        .amount(issue.getAmount())
                        .createdDate(issue.getCreatedDate())
                        .lastUpdated(issue.getLastUpdated())
                        .build())
                .collect(Collectors.toSet());
    }

    private String getUsernameFor(int userId) {
        return userFacade.getUsernameByUserId(userId);
    }

    private String getBrandNameFor(int carId) {
        return carFacade.getCarDtoById(carId).getCarBrandName();
    }

    private String getModelNameFor(int carId) {
        return carFacade.getCarDtoById(carId).getCarModelName();
    }
}
