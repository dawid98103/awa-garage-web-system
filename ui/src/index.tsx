import React from 'react';
import configureStore from './store/index';
import { Provider } from 'react-redux';
import ReactDOM from 'react-dom';
import App from './component/App';
import "../public/styles/styles.css"
import "../public/styles/notification-styles.css"
import "../public/styles/semantic-styles.css"

const store = configureStore();

ReactDOM.render(
  <Provider store={store}>
    <App />
  </Provider>,
  document.getElementById('root')
);
