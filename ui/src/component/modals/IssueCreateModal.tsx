import React, { useState, useEffect } from 'react';
import Select from 'react-select';
import { Button, Modal, ModalActions, Form, Message, Icon } from 'semantic-ui-react'
import { useForm, Controller } from 'react-hook-form';
import { store } from 'react-notifications-component';

import { ICarDto } from '../../api/types';
import { fetchCarsForUserId, saveNewIssue } from '../../api/GarageApi';
import AddButton from '../AddButton';
import { IUserDto } from '../../store/auth/types';
import EditButton from '../PrimaryButton';
import PrimaryButton from '../PrimaryButton';

const formStyles = {
    display: "flex",
    justifyContent: "center",
    flexDirection: "column",
    padding: 10,
    minWidth: "90%"
}
interface ICarOptionsDto {
    label: string,
    value: number
}

interface IFormData {
    description: string,
    carSelect: ICarOptionsDto
}

interface ICreateModalProps {
    refreshIssueList: any
}

const IssueCreateModal: React.FC<ICreateModalProps> = (props) => {
    const [open, setOpen] = useState<boolean>(false);
    const [userCars, setUserCars] = useState<ICarDto[]>([]);
    const [userRole, setUserRole] = useState<string>("");
    const { control, errors, handleSubmit } = useForm<IFormData>();

    useEffect(() => {
        const user: IUserDto = JSON.parse(localStorage.getItem('user') || '{}');

        setUserRole(user.roles[0])
        fetchCarsForUserId(user.userId)
            .then(response => {
                setUserCars(response.data);
            });
    }, [])

    const onSubmit = async ({ carSelect, description }: IFormData) => {
        const { userId }: IUserDto = JSON.parse(localStorage.getItem('user') || '{}');

        await saveNewIssue({
            userId: userId,
            carId: carSelect.value,
            description: description
        }).then(response => {
            store.addNotification({
                title: 'Sprawy',
                type: 'success',
                message: response.data.message,
                insert: "bottom",
                container: "bottom-left",
                dismiss: {
                    duration: 4000
                }
            })
            setOpen(false);
        }).catch(error => {
            store.addNotification({
                title: 'Sprawy',
                type: 'warning',
                message: error.response.data.message,
                insert: "bottom",
                container: "bottom-left",
                dismiss: {
                    duration: 4000
                }
            })
        });

        props.refreshIssueList();
    }

    function prepareOptionsDto(): ICarOptionsDto[] {
        const options: ICarOptionsDto[] = [];
        userCars.map(car => {
            options.push(
                {
                    label: `${car.carBrandName} ${car.carModelName}`,
                    value: car.carId
                }
            )
        })
        return options;
    }

    function prepareSelectButtonsForForm() {
        return (
            <Controller
                name="carSelect"
                as={<Select placeholder="Wybierz pojazd" />}
                control={control}
                options={prepareOptionsDto()}
                rules={{ required: true }}
            />
        )
    }

    return (
        <Modal
            onClose={() => setOpen(false)}
            onOpen={() => setOpen(true)}
            open={open}
            trigger={
                (userRole === "" || userRole === "ROLE_KLIENT") &&
                <PrimaryButton text="Zgłoś sprawę" onClick={() => { }} iconName={"bullhorn"} />
            }
        >
            <Modal.Header>{`Nowe zgłoszenie`}</Modal.Header>
            <Modal.Content>
                <Form style={formStyles} loading={userCars.length === 0 ? true : false}>
                    {prepareSelectButtonsForForm()}
                    {errors.carSelect &&
                        <Message warning visible>Wymagany wybór pojazdu!</Message>
                    }
                    <Controller
                        name="description"
                        as={<Form.TextArea placeholder="Opis usterki..." style={{ marginTop: 25 }} />}
                        control={control}
                        defaultValue=""
                        rules={{ required: true }}
                    />
                    {errors.description &&
                        <Message warning visible>Wymagany opis usterki!</Message>}
                </Form>
            </Modal.Content>
            <ModalActions>
                <AddButton text="Zgłoś sprawę" handleClick={handleSubmit(onSubmit)} />
                <Button onClick={() => setOpen(false)}>
                    Zamknij
                </Button>
            </ModalActions>
        </Modal>
    );
};

export default IssueCreateModal;
