import React, { useState } from 'react';
import { Button, Modal, ModalActions, Icon } from 'semantic-ui-react'

import { ICarDto } from '../../api/types';
import GridGenerator from '../../util/GridGenerator';
import CarCard from '../../component/CarCard';

interface ClientsCarModalProps {
    username: string,
    userCars: ICarDto[]
}

const ClientsCarModal: React.FC<ClientsCarModalProps> = ({ userCars, username }) => {
    const [open, setOpen] = useState<boolean>(false);

    return (
        <Modal
            onClose={() => setOpen(false)}
            onOpen={() => setOpen(true)}
            open={open}
            trigger={<Button icon><Icon name="car" /></Button>}
        >
            <Modal.Header>{`Pojazdy użytkownika: ${username}`}</Modal.Header>
            <Modal.Content>
                <GridGenerator cols={3}>
                    {userCars.map(car => {
                        return (
                            <CarCard car={car} />
                        )
                    })}
                </GridGenerator>
            </Modal.Content>
            <ModalActions>
                <Button onClick={() => setOpen(false)}>
                    Zamknij
                </Button>
            </ModalActions>
        </Modal>
    );
}

export default ClientsCarModal;

