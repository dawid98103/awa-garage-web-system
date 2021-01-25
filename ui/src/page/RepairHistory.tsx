import React, { useEffect, useState } from 'react';
import DataTable, { IDataTableStyles } from 'react-data-table-component';
import { Segment, Loader } from 'semantic-ui-react'
import { IIssueHistoryDto } from '../api/types'
import { fetchIssuesHistory } from '../api/GarageApi'
import { AxiosResponse } from 'axios';
import SemanticDatePicker from 'react-semantic-ui-datepickers';
import { SemanticDatepickerProps } from 'react-semantic-ui-datepickers/dist/types';
import NoDataFoundComponent from '../component/NoDataFoundComponent';
import 'react-semantic-ui-datepickers/dist/react-semantic-ui-datepickers.css';

const customStyles: IDataTableStyles = {
    headCells: {
        style: {
            textAlign: "center",
            fontFamily: "Montserrat",
            fontSize: 14,
            fontWeight: "bold"
        }
    }
}

const columns = [
    {
        name: 'Id zgłoszenia',
        selector: 'issueId',
        sortable: true
    },
    {
        name: 'Klient',
        selector: 'username',
        sortable: true
    },
    {
        name: 'Marka',
        selector: 'carBrandName',
        sortable: true
    },
    {
        name: 'Model',
        selector: 'carModelName',
        sortable: true
    },
    {
        name: 'Kwota',
        selector: 'amount',
        sortable: true,
        cell: (row: any) => `${row.amount} zł`
    },
    {
        name: 'Data utworzenia',
        selector: 'createdDate',
        sortable: true
    },
    {
        name: 'Data zakończenia',
        selector: 'lastUpdated',
        sortable: true
    }
]

let initialData: IIssueHistoryDto[] = [];

const RepairHistory: React.FC = () => {
    const [issues, setIssues] = useState<IIssueHistoryDto[]>([]);
    const [datePickerValue, setDatePickerValue] = useState<Date[]>([new Date(), new Date()]);

    useEffect(() => {
        let fetchIssues = async () => {
            let issuesHistory: AxiosResponse<IIssueHistoryDto[]> = await fetchIssuesHistory();
            if (issuesHistory.status === 200) {
                initialData = issuesHistory.data;
                console.log(issuesHistory.data);
                setIssues(issuesHistory.data);
            } else {
                setIssues([]);
            }
        }

        fetchIssues();
    }, [])

    const filterIssuesByDate = (startDate: Date, endDate: Date): IIssueHistoryDto[] => {
        return initialData.filter(issue => new Date(issue.lastUpdated) >= startDate && new Date(issue.lastUpdated) <= endDate);
    }

    const handleChangeDatepicker = (event: React.SyntheticEvent<Element, Event> | undefined, data: SemanticDatepickerProps) => {
        if (Array.isArray(data.value) && data.value.length === 2) {
            let startDate: Date = new Date(data.value[0]);
            let endDate: Date = new Date(data.value[1]);

            setIssues(filterIssuesByDate(startDate, endDate));
            setDatePickerValue(Array.of(startDate, endDate));
        } else if (data.value === null) {
            setIssues(initialData);
        }
    }

    const ExpanderRow = (row: any) => {
        return (
            <Segment style={{ marginBottom: 10, marginTop: 10 }}>
                {
                    row.data.description
                }
            </Segment>
        )
    }

    const SubHeaderComponent = () => {
        return (
            <Segment style={{ width: "100%" }} className="subheader">
                <SemanticDatePicker
                    locale="pl-PL"
                    type="range"
                    value={datePickerValue}
                    onChange={(event, data: SemanticDatepickerProps) => handleChangeDatepicker(event, data)}
                />
            </Segment>
        )
    }

    return (
        <Segment>
            <DataTable
                noHeader
                columns={columns}
                data={issues}
                customStyles={customStyles}
                pagination
                defaultSortField="lastUpdated"
                defaultSortAsc={false}
                expandableRows
                expandableRowsComponent={<ExpanderRow />}
                subHeader
                subHeaderComponent={<SubHeaderComponent />}
                paginationComponentOptions={{ rowsPerPageText: "Wierszy na stronę", rangeSeparatorText: "z" }}
                noDataComponent={<NoDataFoundComponent />}
                progressComponent={<Loader />}
            />
        </Segment>
    )
}

export default RepairHistory;