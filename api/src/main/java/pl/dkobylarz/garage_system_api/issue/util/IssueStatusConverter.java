package pl.dkobylarz.garage_system_api.issue.util;

import pl.dkobylarz.garage_system_api.issue.constant.IssueStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class IssueStatusConverter implements AttributeConverter<IssueStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(IssueStatus issueStatus) {
        return issueStatus.getStatusId();
    }

    @Override
    public IssueStatus convertToEntityAttribute(Integer integer) {
        return IssueStatus.getStatusByStatusId(integer);
    }
}
