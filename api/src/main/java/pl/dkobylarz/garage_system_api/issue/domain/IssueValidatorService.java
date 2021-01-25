package pl.dkobylarz.garage_system_api.issue.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dkobylarz.garage_system_api.issue.constant.IssueStatus;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
class IssueValidatorService {

    private final IssueRepository issueRepository;

    public boolean activeIssueForUserIdExists(int userId) {
        return issueRepository.existsByUserIdAndStatusIn(userId, IssueStatus.getActiveIssueStatus());
    }

    public boolean amountIsNotProper(BigDecimal amount) {
        return !(amount.compareTo(BigDecimal.ZERO) >= 0);
    }

    public boolean activeIssueForCarIdExists(int carId) {
        return issueRepository.existsByCarIdAndStatusIn(carId, IssueStatus.getActiveIssueStatus());
    }
}
