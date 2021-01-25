import React from "react";
import { Route, Redirect } from "react-router-dom";

const PrivateRoute: React.FC<{
    component: React.FC;
    path: string;
    exact: boolean;
}> = (props) => {

    const condition = JSON.parse(localStorage.getItem('user') || 'null');

    return condition ? (<Route path={props.path} exact={props.exact} component={props.component} />) :
        (<Redirect to="/login" />);
};
export default PrivateRoute;


// import React from 'react';
// import { Route, Redirect, RouteProps } from 'react-router-dom';

// export interface ProtectedRouteProps extends RouteProps {
//     component: React.FC;
//     restrictedPath: string;
// }

// export const ProtectedRoute: React.FC<ProtectedRouteProps> = props => {
//     let redirectPath = '';
//     let isAuthenticated = JSON.parse(localStorage.getItem('user') || 'null');

//     console.log(props);

//     if (isAuthenticated) {
//         redirectPath = props.restrictedPath;
//     } else {
//         redirectPath = "/login"
//     }

//     console.log(redirectPath);
//     console.log("render: " + props.component);

//     if (redirectPath) {
//         const renderComponent = () => <Redirect to={{ pathname: redirectPath }} />;
//         return <Route {...props} component={renderComponent} />;
//     } else {
//         return <Route {...props} />;
//     }
// };

// export default ProtectedRoute;