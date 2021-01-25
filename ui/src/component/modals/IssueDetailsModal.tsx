import React, { useState } from 'react';
import { AxiosResponse } from 'axios';
import { Button, Modal, Image, Header, List, ModalActions, Card, Dropdown, DropdownProps, Input, Label, InputOnChangeData, Segment, Divider } from 'semantic-ui-react'
import { store } from 'react-notifications-component';

import { IIssueDetailsDto } from '../../api/types';
import { assignAmountToIssue, fetchIssueDetails, fetchIssueByNumber, changeIssueStatus } from '../../api/GarageApi';
import PrimaryButton from '../PrimaryButton';

const issueOptions = [
    { key: '1', value: '1', text: 'Oczekujące' },
    { key: '2', value: '2', text: 'Przyjęto' },
    { key: '3', value: '3', text: 'W trakcie realizacji...' },
    { key: '4', value: '4', text: 'Do odbioru' },
    { key: '5', value: '5', text: 'Ukończono' }
]

interface ComponentProps {
    issueId: number | null,
    issueNumber: string | null,
    trigger: any,
    refreshIssueList: any,
    clearInput: any | null
}

const IssueDetailsModal: React.FC<ComponentProps> = ({ issueId, issueNumber, clearInput, trigger, refreshIssueList }) => {

    const user = JSON.parse(localStorage.getItem('user') || 'null');

    const [open, setOpen] = useState<boolean>(false);
    const [issueDetails, setIssueDetails] = useState<IIssueDetailsDto>();
    const [editMode, setEditMode] = useState<boolean>(false);
    const [selectedStatus, setSelectedStatus] = useState<any>("");
    const [amount, setAmount] = useState<number>(0);

    const handleModalOpen = async () => {
        if (issueNumber !== null && issueNumber !== "") {
            try {
                await fetchIssueByNumber(issueNumber).then(response => {
                    setIssueDetails(response.data)
                    setOpen(true);
                }).catch(error => {
                    store.addNotification({
                        title: 'Szczegóły sprawy',
                        type: 'warning',
                        message: error.response.data.message,
                        insert: "bottom",
                        container: "bottom-left",
                        dismiss: {
                            duration: 4000
                        }
                    })
                })
            } catch (error) {
                clearInput();
            }
        } else {
            if (issueId !== null) {
                const response: AxiosResponse<IIssueDetailsDto> = await fetchIssueDetails(issueId);
                setIssueDetails(response.data);
            }
            setOpen(true);
        }
    }

    const determineInitialValue = (): any => {
        return issueOptions.find((option) => option.text === issueDetails?.status)?.value;
    }

    const handleModalClose = () => {
        setEditMode(false);
        setOpen(false);
    }

    const handleEditMode = () => {
        setEditMode(true);
    }

    const handleSelectChange = (event: React.SyntheticEvent<HTMLElement, Event>, { value }: DropdownProps) => {
        setSelectedStatus(value);
    }

    const handleAmountChange = (event: React.SyntheticEvent<HTMLElement, Event>, { value }: InputOnChangeData) => {
        setAmount(Number.parseInt(value));
    }

    const handleSaveStatus = async () => {
        if (issueId !== null) {
            await changeIssueStatus(issueId, selectedStatus).then(response => {
                if (amount !== 0) {
                    assignAmountToIssue(issueId, amount);
                }

                store.addNotification({
                    title: 'Szczegóły sprawy',
                    type: 'success',
                    message: response.data.message,
                    insert: "bottom",
                    container: "bottom-left",
                    dismiss: {
                        duration: 4000
                    }
                })

                setEditMode(false);
                refreshIssueList();
                setOpen(false);
            }).catch(error => {
                store.addNotification({
                    title: 'Szczegóły sprawy',
                    type: 'warning',
                    message: error.response.data.message,
                    insert: "bottom",
                    container: "bottom-left",
                    dismiss: {
                        duration: 4000
                    }
                })
            });

        }
    }

    return (
        <Modal
            onClose={() => handleModalClose()}
            onOpen={() => handleModalOpen()}
            open={open}
            trigger={trigger}
        >
            <Modal.Header>{`Szczegóły sprawy numer: ${issueDetails?.issueNumber}`}</Modal.Header>
            <Modal.Content image>
                <Card className="car-img-card">
                    <Image size="medium" src={issueDetails?.carDto.carPhotoUrl} wrapped />
                </Card>
                <div className="issue-details">
                    <Modal.Description>
                        <Header>Szczegóły:</Header>
                        <List divided relaxed>
                            <List.Item>
                                <List.Content className="issue-details-content">
                                    {`Data ostatniej aktualizacji: ${issueDetails?.lastUpdated}`}
                                </List.Content>
                            </List.Item>
                            <List.Item>
                                <List.Content className="issue-details-content">
                                    {(editMode) ?
                                        <span>
                                            <span>
                                                Obecny status: &nbsp;
                                            </span>
                                            <Dropdown inline options={issueOptions} defaultValue={determineInitialValue()} onChange={(event, value) => handleSelectChange(event, value)} />
                                        </span>
                                        :
                                        `Obecny status: ${issueDetails?.status} `
                                    }
                                </List.Content>
                            </List.Item>
                            <List.Item>
                                <List.Content className="issue-details-content">
                                    {`Nazwa pojazdu: ${issueDetails?.carDto.carBrandName} ${issueDetails?.carDto.carModelName}`}
                                </List.Content>
                            </List.Item>
                            <List.Item>
                                <List.Content className="issue-details-content">
                                    {`Rok produkcji: ${issueDetails?.carDto.yearOfProduction}`}
                                </List.Content>
                            </List.Item>
                            {
                                (issueDetails?.amount !== 0 && issueDetails?.amount != null) ?
                                    <List.Item>
                                        <List.Content className="issue-details-content">
                                            {`Do zapłaty: ${issueDetails?.amount} zł`}
                                        </List.Content>
                                    </List.Item>
                                    :
                                    null
                            }
                            <List.Item>
                                <List.Content className="issue-details-content">
                                    {
                                        (selectedStatus === '4' && editMode === true) ?
                                            <Input labelPosition="right" type="number" placeholder="kwota" onChange={(event, value) => handleAmountChange(event, value)}>
                                                <Label basic>Do zapłaty</Label>
                                                <input />
                                                <Label>zł</Label>
                                            </Input>
                                            :
                                            null
                                    }
                                </List.Content>
                            </List.Item>
                        </List>
                    </Modal.Description>
                </div>
            </Modal.Content>
            <div className="issue-details-decription-container">
                <h3>Opis usterki:</h3>
                <Divider />
                <Segment className="issue-details-description-segment">
                    {issueDetails?.description}
                </Segment>
            </div>
            <ModalActions>
                {
                    (user?.roles[0] === 'ROLE_MECHANIK') ?
                        (editMode) ?
                            <Button positive onClick={() => handleSaveStatus()}>Zapisz</Button>
                            :
                            <PrimaryButton text="Edytuj" onClick={() => handleEditMode()} iconName={"edit"} />
                        :
                        null
                }
                <Button onClick={() => handleModalClose()}>
                    Zamknij
                </Button>
            </ModalActions>
        </Modal>
    );
};


export default IssueDetailsModal;
