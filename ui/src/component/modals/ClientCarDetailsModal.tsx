import React, { useState } from 'react';
import { Button, Card, Divider, Header, List, Modal, ModalActions, Segment, Image, Table, Icon } from 'semantic-ui-react';
import { ICarDto, IIssueHistoryDto } from '../../api/types';
import { fetchIssuesHistoryForCar, deactivateCarForUser } from '../../api/GarageApi';
import { AxiosResponse } from 'axios';

import _ from 'lodash';
import CarCard from '../../component/CarCard';
import DeleteButton from '../DeleteButton';
import PrimaryButton from '../PrimaryButton';
import { store } from 'react-notifications-component';

interface IClientCarDetailsModalProps {
    car: ICarDto,
    refreshList: () => void
}

const ClientCarDetailsModal: React.FC<IClientCarDetailsModalProps> = ({ car, refreshList }) => {
    const [open, setOpen] = useState<boolean>(false);
    const [issuesHistory, setIssuesHistory] = useState<IIssueHistoryDto[]>([]);

    const handleOnModalOpen = async () => {
        let carIssuesHistory: AxiosResponse<IIssueHistoryDto[]> = await fetchIssuesHistoryForCar(car.carId);
        if (carIssuesHistory.status == 200) {
            setIssuesHistory(carIssuesHistory.data);
        }

        setOpen(true);
    }

    const handeDeleteCar = async (carId: number) => {
        await deactivateCarForUser(carId).then(response => {
            store.addNotification({
                title: 'Pojazdy',
                type: 'success',
                message: response.data.message,
                insert: "bottom",
                container: "bottom-left",
                dismiss: {
                    duration: 4000
                }
            })
            refreshList();
            setOpen(false)
        }).catch(error => {
            store.addNotification({
                title: 'Pojazdy',
                type: 'warning',
                message: error.response.data.message,
                insert: "bottom",
                container: "bottom-left",
                dismiss: {
                    duration: 4000
                }
            })
        })
    }

    function calculateExpenses(): number {
        return _.reduce(issuesHistory, (sum, n) => { return sum + n.amount }, 0)
    }

    return (
        <Modal
            open={open}
            onClose={() => setOpen(false)}
            onOpen={() => handleOnModalOpen()}
            trigger={
                <span className={"cursor-pointer"}>
                    <CarCard car={car} />
                </span>
            }
        >
            <Modal.Header>Szczegóły pojazdu: {car.carBrandName} {car.carModelName}</Modal.Header>
            <Modal.Content image>
                <Card className="car-img-card">
                    <Image size="medium" src={car.carPhotoUrl} wrapped />
                </Card>
                <div className="issue-details">
                    <Modal.Description>
                        <Header>Szczegóły: {car.carBrandName} {car.carModelName}</Header>
                        <List divided relaxed>
                            <List.Item>
                                <List.Content className="issue-details-content">
                                    {`Marka: ${car.carBrandName}`}
                                </List.Content>
                            </List.Item>
                            <List.Item>
                                <List.Content className="issue-details-content">
                                    {`Model: ${car.carModelName}`}
                                </List.Content>
                            </List.Item>
                            <List.Item>
                                <List.Content className="issue-details-content">
                                    {`Przebieg: ${car.carMileage} km`}
                                </List.Content>
                            </List.Item>
                            <List.Item>
                                <List.Content className="issue-details-content">
                                    {`Rok produkcji: ${car.yearOfProduction}`}
                                </List.Content>
                            </List.Item>
                            <List.Item>
                                <List.Content className="issue-details-content">
                                    {`Data utworzenia: ${car.createdDate}`}
                                </List.Content>
                            </List.Item>
                            <List.Item>
                                <List.Content className="issue-details-content">
                                    {`Data ostatniej aktualizacji: ${car.lastUpdated}`}
                                </List.Content>
                            </List.Item>
                        </List>
                    </Modal.Description>
                </div>
            </Modal.Content>
            <div className="issue-details-decription-container">
                <h3>Historia napraw:</h3>
                <Divider />
                <Segment className="repair-history-segment">
                    <Table celled textAlign={"center"}>
                        <Table.Header>
                            <Table.Row>
                                <Table.HeaderCell>Id naprawy</Table.HeaderCell>
                                <Table.HeaderCell>Marka</Table.HeaderCell>
                                <Table.HeaderCell>Model</Table.HeaderCell>
                                <Table.HeaderCell>Cena</Table.HeaderCell>
                                <Table.HeaderCell>Data naprawy</Table.HeaderCell>
                            </Table.Row>
                        </Table.Header>
                        <Table.Body className={"scrollable-table"}>
                            {
                                issuesHistory.map(issuesHistory => {
                                    return (
                                        <Table.Row>
                                            <Table.Cell>{issuesHistory.issueId}</Table.Cell>
                                            <Table.Cell>{issuesHistory.carBrandName}</Table.Cell>
                                            <Table.Cell>{issuesHistory.carModelName}</Table.Cell>
                                            <Table.Cell>{issuesHistory.amount} zł</Table.Cell>
                                            <Table.Cell>{issuesHistory.lastUpdated}</Table.Cell>
                                        </Table.Row>
                                    )
                                })
                            }
                        </Table.Body>
                        <Table.Footer>
                            <Table.Row>
                                <Table.Cell>Suma wydatków: </Table.Cell>
                                <Table.Cell />
                                <Table.Cell />
                                <Table.Cell>{calculateExpenses()} zł</Table.Cell>
                            </Table.Row>
                        </Table.Footer>
                    </Table>
                </Segment>
            </div>
            <ModalActions>
                <DeleteButton text="Usuń pojazd" onClick={() => handeDeleteCar(car.carId)} />
                <PrimaryButton text="Edytuj pojazd" onClick={() => console.log("edycja")} iconName={"edit"} />
                <Button onClick={() => setOpen(false)}>
                    Zamknij
                </Button>
            </ModalActions>
        </Modal>
    );
}

export default ClientCarDetailsModal;