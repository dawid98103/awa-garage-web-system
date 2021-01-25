import React, { useState, useEffect } from 'react';
import { Table, Button, Icon, Segment } from 'semantic-ui-react';

import { IIssueWithCarDto } from '../api/types';
import { fetchActiveIssuesForUser } from '../api/GarageApi';
import { fetchAllActiveIssues } from '../api/GarageApi';
import IssueDetailsModal from '../component/modals/IssueDetailsModal';
import IssueCreateModal from '../component/modals/IssueCreateModal';

interface CurrentUserIdProps {
    userId: number,
    isMechanic: boolean
}

const IssueList: React.FC<CurrentUserIdProps> = ({ userId, isMechanic }) => {

    const [issues, setIssues] = useState<IIssueWithCarDto[]>([]);
    const [refresh, setRefresh] = useState<boolean>(true);

    useEffect(() => {

        let fetchFunction: any = null;

        if (isMechanic) {
            fetchFunction = async () => {
                const { data } = await fetchAllActiveIssues();
                setIssues(data);
            }
        } else {
            fetchFunction = async () => {
                const { data } = await fetchActiveIssuesForUser(userId);
                setIssues(data);
            }
        }

        fetchFunction();
    }, [refresh])

    const handleRefresh = () => {
        setRefresh(!refresh);
    }

    const briefDescription = (description: string): string => {
        return description.slice(0, 30).concat("...");
    }

    return (
        <div>
            <Segment style={{ marginTop: "10px" }}>
                {issues.length === 0 ?
                    <div style={{ textAlign: "center" }}>
                        <h3>Nie znaleziono aktywnych spraw</h3>
                    </div>
                    :
                    <Table basic='very' style={{ marginTop: 30 }}>
                        <Table.Header>
                            <Table.Row textAlign="center">
                                <Table.HeaderCell>ID Zgłoszenia</Table.HeaderCell>
                                <Table.HeaderCell>Marka</Table.HeaderCell>
                                <Table.HeaderCell>Model</Table.HeaderCell>
                                <Table.HeaderCell>Opis</Table.HeaderCell>
                                <Table.HeaderCell>Status</Table.HeaderCell>
                                <Table.HeaderCell>Ostatnia aktualizacja</Table.HeaderCell>
                                <Table.HeaderCell>Akcja</Table.HeaderCell>
                            </Table.Row>
                        </Table.Header>
                        <Table.Body>
                            {
                                issues.map(issue => {
                                    return (
                                        <Table.Row textAlign="center" key={issue.issueId}>
                                            <Table.Cell>{issue.issueId}</Table.Cell>
                                            <Table.Cell>{issue.carBrandName}</Table.Cell>
                                            <Table.Cell>{issue.carModelName}</Table.Cell>
                                            <Table.Cell>{briefDescription(issue.description)}</Table.Cell>
                                            <Table.Cell>{issue.status}</Table.Cell>
                                            <Table.Cell>{issue.lastUpdated}</Table.Cell>
                                            <Table.Cell>
                                                <IssueDetailsModal issueId={issue.issueId} issueNumber={null} trigger={<Button icon><Icon name="search plus" /></Button>} clearInput={null} refreshIssueList={handleRefresh} />
                                            </Table.Cell>
                                        </Table.Row>
                                    )
                                })
                            }
                        </Table.Body>
                    </Table>
                }
            </Segment>
            <div style={{ display: "flex", padding: 10, justifyContent: "flex-end", alignItems: "flex-end" }}>
                <IssueCreateModal refreshIssueList={handleRefresh} />
            </div>
        </div>
    )
}

export default IssueList;