//package pl.dkobylarz.garage_system_api.factory;
//
//import pl.dkobylarz.garage_system_api.issue.constant.IssueStatus;
//import pl.dkobylarz.garage_system_api.issue.domain.Issue;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//public class IssueFactory {
//
//    public static Issue createRandomIssueWith(int issueId, int carId, int userId, IssueStatus issueStatus, BigDecimal amount) {
//        return Issue.builder()
//                .issueId(issueId)
//                .description("desc")
//                .status(issueStatus)
//                .carId(carId)
//                .userId(userId)
//                .amount(amount)
//                .createdDate(LocalDateTime.now())
//                .lastUpdated(LocalDateTime.now())
//                .build();
//    }
//}
