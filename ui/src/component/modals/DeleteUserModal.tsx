import React, { useState } from 'react';
import { Button, Modal, ModalActions, Icon } from 'semantic-ui-react'

import { deleteUserByUserId } from '../../api/GarageApi';
import { store } from 'react-notifications-component';
import DeleteButton from '../DeleteButton';

interface DeleteUserModalProps {
    userId: number,
    username: string,
    refreshList: () => void
}

const DeleteUserModal: React.FC<DeleteUserModalProps> = ({ userId, username, refreshList }) => {
    const [open, setOpen] = useState<boolean>(false);

    const handleDeleteUser = async () => {
        await deleteUserByUserId(userId).then(response => {
            store.addNotification({
                title: 'Klienci',
                type: 'success',
                message: response.data.message,
                insert: "bottom",
                container: "bottom-left",
                dismiss: {
                    duration: 4000
                }
            })
            setOpen(false);
            refreshList()
        }).catch(error => {
            store.addNotification({
                title: 'Klienci',
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

    return (
        <Modal
            size="tiny"
            onClose={() => setOpen(false)}
            onOpen={() => setOpen(true)}
            open={open}
            trigger={<Button color="red" icon><Icon name="trash alternate" /></Button>}
        >
            <Modal.Header>Usuwanie użytkownika</Modal.Header>
            <Modal.Content>
                <div>
                    Czy na pewno chcesz usunąć użytkownika {username} ?
                </div>
            </Modal.Content>
            <ModalActions>
                <Button onClick={() => setOpen(false)}>
                    Zamknij
                </Button>
                <DeleteButton text="Usuń użytkownika" onClick={() => handleDeleteUser()} />
            </ModalActions>
        </Modal>
    )
}

export default DeleteUserModal;