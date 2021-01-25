package pl.dkobylarz.garage_system_api.issue;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.dkobylarz.garage_system_api.user.dto.ResponseMessageDto;
import pl.dkobylarz.garage_system_api.issue.domain.IssueFacade;
import pl.dkobylarz.garage_system_api.issue.dto.CreateIssueDto;

import java.math.BigDecimal;

@RestController
@RequestMapping("/issues")
@RequiredArgsConstructor
class IssueController {

    private final IssueFacade issueFacade;

    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_MECHANIK')")
    public ResponseEntity<?> getAllActiveIssues() {
        return ResponseEntity.ok(issueFacade.getAllActiveIssues());
    }

    @GetMapping("/byNumber/{issueNumber}")
    public ResponseEntity<?> getIssueByNumber(@PathVariable String issueNumber) {
        return ResponseEntity.ok(issueFacade.getIssueByIssueNumber(issueNumber));
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_KLIENT, ROLE_MECHANIK')")
    public ResponseEntity<?> getActiveIssuesForUser(@PathVariable int userId) {
        return ResponseEntity.ok(issueFacade.getActiveIssuesForUser(userId));
    }

    @GetMapping("/{issueId}/details")
    @PreAuthorize("hasAnyRole('ROLE_KLIENT, ROLE_MECHANIK')")
    public ResponseEntity<?> getIssueDetails(@PathVariable int issueId) {
        return ResponseEntity.ok(issueFacade.getIssueDetails(issueId));
    }

    @GetMapping("/history")
    @PreAuthorize("hasRole('ROLE_MECHANIK')")
    public ResponseEntity<?> getIssuesHistory() {
        return ResponseEntity.ok(issueFacade.getIssuesHistory());
    }

    @GetMapping("/history/{carId}")
    @PreAuthorize("hasAnyRole('ROLE_KLIENT, ROLE_MECHANIK')")
    public ResponseEntity<?> getIssueHistoryForCar(@PathVariable int carId) {
        return ResponseEntity.ok(issueFacade.getIssueHistoryForCar(carId));
    }

    @PatchMapping("/{issueId}/status/{issueStatusId}")
    @PreAuthorize("hasRole('ROLE_MECHANIK')")
    public ResponseEntity<?> changeStatus(@PathVariable int issueId, @PathVariable int issueStatusId) {
        issueFacade.changeIssueStatus(issueId, issueStatusId);
        return ResponseEntity.ok(new ResponseMessageDto("Pomyślnie zmieniono status zgłoszenia"));
    }

    @PatchMapping("/{issueId}/amount/{amount:.+}")
    @PreAuthorize("hasRole('ROLE_MECHANIK')")
    public ResponseEntity<?> assignAmountToIssue(@PathVariable int issueId, @PathVariable BigDecimal amount) {
        issueFacade.assignAmountToIssue(issueId, amount);
        return ResponseEntity.ok(new ResponseMessageDto("Pomyślnie przypisano kwotę do sprawy"));
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_KLIENT')")
    public ResponseEntity<?> createIssue(@RequestBody CreateIssueDto createIssueDto) {
        issueFacade.createIssue(createIssueDto);
        return ResponseEntity.ok(new ResponseMessageDto("Pomyślnie utworzono zgłoszenie"));
    }
}
