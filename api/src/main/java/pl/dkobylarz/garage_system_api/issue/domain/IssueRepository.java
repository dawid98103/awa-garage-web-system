package pl.dkobylarz.garage_system_api.issue.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.dkobylarz.garage_system_api.issue.constant.IssueStatus;
import pl.dkobylarz.garage_system_api.issue.dto.IssueDto;

import java.util.Optional;
import java.util.Set;

@Repository
interface IssueRepository extends JpaRepository<Issue, Integer> {

    void deleteAllByUserId(int userId);

    IssueDto findByIssueId(int issueId);

    Optional<Issue> findByIssueNumberAndStatusIn(String issueNumber, Set<IssueStatus> activeStatuses);

    @Query("SELECT i FROM Issue i where i.status in :issueStatuses")
    Set<Issue> findAllEntitiesByStatusIn(Set<IssueStatus> issueStatuses);

    @Query("SELECT i FROM Issue i where i.carId = :carId and i.status in :issueStatuses")
    Set<Issue> findAllEntitiesByCarIdAndStatusIn(int carId, Set<IssueStatus> issueStatuses);

    Set<IssueDto> findAllByStatusIn(Set<IssueStatus> activeStatuses);

    Set<IssueDto> findByUserIdAndStatusIn(int userId, Set<IssueStatus> activeIssues);

    boolean existsByUserIdAndStatusIn(int userId, Set<IssueStatus> issueStatuses);

    boolean existsByCarIdAndStatusIn(int carId, Set<IssueStatus> issueStatuses);
}
