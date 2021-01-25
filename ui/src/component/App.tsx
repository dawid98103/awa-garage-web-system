import React from 'react';
import { Router, Route, Switch, Redirect } from 'react-router-dom';
import { Container } from 'semantic-ui-react'
import ReactNotification from 'react-notifications-component'

import Navbar from '../component/Navbar'
import LoginPage from '../page/LoginPage';
import HomePage from '../page/HomePage';
import ClientsCarPage from '../page/ClientsCarPage';
import RepairHistory from '../page/RepairHistory';
import ClientsPage from '../page/ClientsPage';
import PrivateRoute from '../component/PrivateRoute'
import history from '../history';

const App: React.FC = () => {

  const LoginContainer = () => (
    <>
      <Route path="/login" component={LoginPage} />
    </>
  )

  const DefaultContainer = () => (
    <>
      <Container style={{ paddingTop: "80px" }} >
        <Navbar />
        <Route path="/home" component={HomePage} exact />
        <Route path="/cars" component={ClientsCarPage} exact />
        <Route path="/history" component={RepairHistory} exact />
        <Route path="/clients" component={ClientsPage} exact />
      </Container>
    </>
  )

  return (
    <div>
      <ReactNotification />
      <Container>
        <Router history={history} >
          <Switch>
            <PrivateRoute path="/home" component={DefaultContainer} exact />
            <PrivateRoute path="/cars" component={DefaultContainer} exact />
            <PrivateRoute path="/history" component={DefaultContainer} exact />
            <PrivateRoute path="/clients" component={DefaultContainer} exact />
            <Route path="/login" component={LoginContainer} exact />
            <Redirect from="/" to="/home" />
          </Switch>
        </Router>
      </Container>
    </div>
  )
}

export default App;