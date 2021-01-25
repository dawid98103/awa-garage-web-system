package pl.dkobylarz.garage_system_api.issue.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dkobylarz.garage_system_api.issue.dto.CreateIssueDto;
import pl.dkobylarz.garage_system_api.issue.dto.IssueDetailsDto;
import pl.dkobylarz.garage_system_api.issue.dto.IssueHistoryDto;
import pl.dkobylarz.garage_system_api.issue.dto.IssueWithCarDto;

import java.math.BigDecimal;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class IssueFacade {

    private final IssueService issueService;
    private final IssueHistoryService issueHistoryService;

    public Set<IssueWithCarDto> getAllActiveIssues() {
        return issueService.getActiveIssues();
    }

    public IssueDetailsDto getIssueByIssueNumber(String issueNumber) {
        return issueService.getIssueByNumber(issueNumber);
    }

    public Set<IssueWithCarDto> getActiveIssuesForUser(int userId) {
        return issueService.getActiveIssuesForUser(userId);
    }

    public IssueDetailsDto getIssueDetails(int issueId) {
        return issueService.getIssueDetailsById(issueId);
    }

    public Set<IssueHistoryDto> getIssuesHistory() {
        return issueHistoryService.getIssuesHistory();
    }

    public Set<IssueHistoryDto> getIssueHistoryForCar(int carId) {
        return issueHistoryService.getIssueHistoryFor(carId);
    }

    public void changeIssueStatus(int issueId, int statusId) {
        issueService.changeIssueStatus(issueId, statusId);
    }

    public void assignAmountToIssue(int issueId, BigDecimal amount) {
        issueService.assignAmountToIssue(issueId, amount);
    }

    public void createIssue(CreateIssueDto createIssueDto) {
        issueService.createIssue(createIssueDto);
    }

}
