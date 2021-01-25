import React, { useState, useEffect } from 'react';
import { Segment } from 'semantic-ui-react';
import { fetchUsersByRole } from '../api/GarageApi';
import { IClientDto } from '../api/types';
import DataTable, { IDataTableStyles } from 'react-data-table-component';
import { AxiosResponse } from 'axios';
import ClientsCarModal from '../component/modals/ClientsCarModal';
import DeleteUserModal from '../component/modals/DeleteUserModal';

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

const ClientsPage: React.FC = () => {

    const [clients, setClients] = useState<IClientDto[]>([]);
    const [refresh, setRefresh] = useState<boolean>(false);

    const columns = [
        {
            name: 'Id klienta',
            selector: 'userId',
            sortable: true
        },
        {
            name: 'Nazwa użytkownika',
            selector: 'username',
            sortable: true
        },
        {
            name: 'Data utworzenia',
            selector: 'createdDate',
            sortable: true
        },
        {
            name: 'Data aktualizacji',
            selector: 'lastUpdated',
            sortable: true
        },
        {
            name: 'Akcje',
            button: true,
            cell: (row: IClientDto) => {
                return (
                    <div className="clients-action-cell">
                        <ClientsCarModal userCars={row.carDtos} username={row.username} />
                        <DeleteUserModal userId={row.userId} username={row.username} refreshList={() => setRefresh(!refresh)} />
                    </div>)
            }
        }
    ]


    useEffect(() => {
        let fetchClients = async () => {
            let fetchedUsers: AxiosResponse<IClientDto[]> = await fetchUsersByRole(1);
            if (fetchedUsers.status === 200) {
                setClients(fetchedUsers.data);
            } else {
                setClients([]);
                console.log(fetchedUsers.statusText);
            }
        }

        fetchClients();
    }, [refresh])

    return (
        <Segment>
            <DataTable
                noHeader
                columns={columns}
                data={clients}
                customStyles={customStyles}
                pagination
                defaultSortField="lastUpdated"
                defaultSortAsc={false}
                paginationComponentOptions={{ rowsPerPageText: "Wierszy na stronę", rangeSeparatorText: "z" }}
            />
        </Segment>
    );
}

export default ClientsPage;

