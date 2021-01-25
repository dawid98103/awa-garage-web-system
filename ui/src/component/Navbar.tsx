import React from 'react';
import { useDispatch } from 'react-redux';
import { Container, Icon, Menu } from 'semantic-ui-react'
import { logout } from '../store/auth/actions'
import { withRouter } from 'react-router-dom';
import { RouteComponentProps } from 'react-router-dom';
import { store } from 'react-notifications-component';

import history from '../history';

const Navbar: React.FC<RouteComponentProps> = (props) => {

    const user = JSON.parse(localStorage.getItem('user') || 'null');
    const currentLocation: string = history.location.pathname;

    const dispatch = useDispatch();

    const handleLogOut = () => {
        dispatch(logout())
        store.addNotification({
            title: 'Wylogowanie',
            type: 'success',
            message: "Wylogowano pomyślnie",
            insert: "bottom",
            container: "bottom-left",
            dismiss: {
                duration: 4000
            }
        })
        props.history.push("/login");
    }

    const handleChangeToCarsPage = () => {
        console.log(props.history);
        props.history.push("/cars");
    }

    const handleChangeToHomePage = () => {
        props.history.push("/home");
    }

    const handleChangeToRepairHistory = () => {
        props.history.push("/history");
    }

    const handleChangeToClientsPage = () => {
        props.history.push("/clients");
    }

    return (
        <Menu fixed='top' inverted style={{ backgroundColor: "#DE4B56" }}>
            <Container>
                <Menu.Item header>
                    <Icon size='big' name="cogs" style={{ marginRight: '1.5em' }} />
                        Warsztat u Andrzeja
                </Menu.Item>
                <Menu.Item onClick={() => handleChangeToHomePage()} active={currentLocation === '/home'}>Strona główna</Menu.Item>
                {
                    (user.roles[0] === 'ROLE_MECHANIK') ?
                        <Menu.Item onClick={() => handleChangeToRepairHistory()} active={currentLocation === '/history'}>Historia spraw</Menu.Item>
                        :
                        <Menu.Item onClick={() => handleChangeToCarsPage()} active={currentLocation === '/cars'}>Moje pojazdy</Menu.Item>
                }
                {
                    (user.roles[0] === 'ROLE_MECHANIK') ?
                        <Menu.Item onClick={() => handleChangeToClientsPage()} active={currentLocation === '/clients'}>Klienci</Menu.Item>
                        :
                        null
                }
                {
                    (user.roles[0] === 'ROLE_MECHANIK') ?
                        <Menu.Item active={currentLocation === '/schedule'}>Terminarz</Menu.Item>
                        :
                        null
                }
                <Menu.Item position="right" onClick={() => handleLogOut()}>Wyloguj</Menu.Item>
            </Container>
        </Menu>
    );
}

export default withRouter(Navbar);