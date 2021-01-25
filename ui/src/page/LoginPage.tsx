import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { Button, Form, Grid, Header, Icon, Message, Segment, Input } from 'semantic-ui-react'
import { RouteComponentProps } from 'react-router-dom';
import { store } from 'react-notifications-component';

import { LOGIN_SUCCESS, LOGIN_FAILURE } from '../store/auth/types';
import CreateAccountModal from '../component/modals/CreateAccountModal';
import IssueDetailsModal from '../component/modals/IssueDetailsModal';

import { authService } from '../service/authService';
import 'react-notifications-component/dist/theme.css'

const loginPageStyles = {
    issueNumberMessage: {
        display: "flex",
        justifyContent: "space-evenly",
        flexWrap: "wrap"
    }
}

const LoginPage: React.FC<RouteComponentProps> = (props) => {

    const dispatch = useDispatch();

    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    const [issueNumber, setIssueNumber] = useState<string>("");

    const handleUsernameChange = (event: any): void => {
        setUsername(event.target.value);
    }

    const handlePasswordChange = (event: any): void => {
        setPassword(event.target.value);
    }

    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setIssueNumber(event.target.value);
    }

    const handleClearIssueNumber = () => {
        setIssueNumber("");
    }

    function handleSignIn(): void {
        authService.login(username, password).then(user => {
            store.addNotification({
                title: 'Logowanie',
                type: 'success',
                message: "Zalogowano pomyślnie",
                insert: "bottom",
                container: "bottom-left",
                dismiss: {
                    duration: 4000
                }
            })
            dispatch({ type: LOGIN_SUCCESS, payload: user })
            props.history.push("/home");
        }).catch(error => {
            store.addNotification({
                title: 'Logowanie',
                type: 'warning',
                message: error.response.data.message,
                insert: "bottom",
                container: "bottom-left",
                dismiss: {
                    duration: 4000
                }
            })
            
            dispatch({ type: LOGIN_FAILURE, payload: error });
        })
    }

    return (
        <Grid textAlign='center' style={{ height: '100vh' }} verticalAlign='middle'>
            <Grid.Column style={{ maxWidth: 450 }}>
                <Header as='h2' textAlign='center' style={{ color: "#DE4B56" }}>
                    <Icon name="wrench" /> Zaloguj się na swoje konto
                 </Header>
                <Form size='large'>
                    <Segment stacked>
                        <Form.Input fluid icon='user' iconPosition='left' placeholder='Nazwa użytkownika' onChange={handleUsernameChange} />
                        <Form.Input
                            fluid
                            icon='lock'
                            iconPosition='left'
                            placeholder='Hasło'
                            type='password'
                            onChange={handlePasswordChange}
                        />

                        <Button fluid size='large' onClick={handleSignIn} style={{ backgroundColor: "#E5313C", color: "white" }}>
                            Zaloguj
                        </Button>
                    </Segment>
                </Form>
                <Message>
                    Jeśli chcesz utworzyć konto, kliknij <CreateAccountModal />
                </Message>
                <Message style={loginPageStyles.issueNumberMessage}>
                    <div style={{ marginBottom: "1rem" }}>
                        Chcesz sprawdzić status naprawy? Wpisz kod sprawy:
                    </div>
                    <Input type={"number"} value={issueNumber} onChange={(e) => handleInputChange(e)} />
                    <IssueDetailsModal
                        issueId={null}
                        issueNumber={issueNumber}
                        trigger={
                            (issueNumber !== "") &&
                                <Button icon labelPosition="right">
                                    Szukaj
                                    <Icon name="search"/>
                                </Button>
                        }
                        clearInput={handleClearIssueNumber}
                        refreshIssueList={null}
                    />
                </Message>
            </Grid.Column>
        </Grid>
    )
}

export default LoginPage;