import React, { useState } from 'react';
import { Button, Modal, ModalActions, Form, Message } from 'semantic-ui-react';
import { useForm, Controller, ValidateResult } from 'react-hook-form';
import { store } from 'react-notifications-component';

import AddButton from '../AddButton';
import { signup } from "../../api/GarageApi";

interface IFormData {
    username: string,
    email: string,
    password: string,
    repeatPassword: string,
    userAvatarUrl: string
}

const CreateAccountModal: React.FC = () => {

    const [open, setOpen] = useState<boolean>(false);
    const { control, errors, handleSubmit } = useForm<IFormData>({
        criteriaMode: "all",
        mode: "onChange"
    });

    const isEquals = (repeatPassword: string): ValidateResult => {
        return repeatPassword === control.getValues().password;
    }

    const handleRegisterAccount = async ({ username, email, password, userAvatarUrl }: IFormData) => {
        signup({ username, password, email, userAvatarUrl })
            .then(response => {
                store.addNotification({
                    title: 'Rejestracja',
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
                    title: 'Rejestracja',
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
            trigger={<a className="cursor-pointer">tutaj</a>}
        >
            <Modal.Header>Rejestracja użytkownika</Modal.Header>
            <Modal.Content>
                <Form>
                    <Controller
                        name="username"
                        as={<Form.Input label="Nazwa użytkownika:" placeholder="Nazwa użytkownika" />}
                        control={control}
                        defaultValue=""
                        rules={{ required: true }}
                    />
                    {errors.username &&
                        <Message warning visible>Wymagana nazwa użytkownika!</Message>}

                    <Controller
                        name="email"
                        as={<Form.Input label="Adres email:" placeholder="Adres email" type="email" />}
                        control={control}
                        defaultValue=""
                        rules={{ required: true }}
                    />
                    {errors.email &&
                        <Message warning visible>Wymagany adres email!</Message>}

                    <Controller
                        name="userAvatarUrl"
                        as={<Form.Input label="Link do awatara:" placeholder="Link do awataru" />}
                        control={control}
                        defaultValue=""
                    />

                    <Controller
                        name="password"
                        as={<Form.Input label="Hasło:" placeholder="Hasło" type="password" />}
                        control={control}
                        defaultValue=""
                        rules={{ required: true, minLength: 8 }}
                    />
                    {errors.password?.types?.required &&
                        <Message warning visible>Wymagane podanie hasła!</Message>}
                    {errors.password?.types?.minLength &&
                        <Message warning visible>Hasło powinno zawierać minimum 8 znaków!</Message>}

                    <Controller
                        name="repeatPassword"
                        as={<Form.Input label="Powtórz hasło:" placeholder="Powtórz hasło" type="password" />}
                        control={control}
                        defaultValue=""
                        rules={{ required: true, minLength: 8, validate: isEquals }}
                    />
                    {errors.repeatPassword?.types?.required &&
                        <Message warning visible>Wymagane podanie hasła!</Message>}
                    {errors.repeatPassword?.types?.minLength &&
                        <Message warning visible>Hasło powinno zawierać minimum 8 znaków!</Message>}
                    {errors.repeatPassword?.types?.validate &&
                        <Message warning visible>Hasła powinny być takie same!</Message>}

                </Form>
            </Modal.Content>
            <ModalActions>
                <AddButton text="Zarejestruj" handleClick={handleSubmit(handleRegisterAccount)} />
                <Button onClick={() => setOpen(false)}>
                    Zamknij
            </Button>
            </ModalActions>
        </Modal>
    );
}

export default CreateAccountModal;