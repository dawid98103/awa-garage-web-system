//package pl.dkobylarz.garage_system_api.service.unit;
//
//import lombok.NoArgsConstructor;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import pl.dkobylarz.garage_system_api.issue.dto.CreateIssueDto;
//import pl.dkobylarz.garage_system_api.issue.exception.ActiveIssueForCarIdAlreadyExistsException;
//import pl.dkobylarz.garage_system_api.issue.domain.IssueService;
//import pl.dkobylarz.garage_system_api.issue.domain.IssueValidatorService;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.catchThrowable;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//@NoArgsConstructor
//public class IssueServiceTest {
//
//    @Mock
//    public IssueValidatorService issueValidatorService;
//    @InjectMocks
//    public IssueService issueService;
//
//    @Test
//    public void createIssueWhenIssueForCarAlreadyExistsShouldThrowException() {
//        //given
//        CreateIssueDto createIssueDto = CreateIssueDto.builder()
//                .userId(1)
//                .carId(2)
//                .description("desc")
//                .build();
//
//        when(issueValidatorService.activeIssueForCarIdExists(anyInt())).thenReturn(true);
//
//        //when
//        Throwable thrown = catchThrowable(() -> issueService.createIssue(createIssueDto));
//
//        //then
//        assertThat(thrown).isInstanceOf(ActiveIssueForCarIdAlreadyExistsException.class).hasMessage("Istnieje aktywne zgłoszenie dla podanego pojazdu");
//    }
//}
