import { combineReducers, createStore, applyMiddleware } from 'redux';
import { composeWithDevTools } from 'redux-devtools-extension';
import AuthReducer from './auth/AuthReducer';
import reduxThunk from 'redux-thunk';

const rootReducer = combineReducers({
    authorization: AuthReducer
})

export type RootState = ReturnType<typeof rootReducer>

export default function configureStore() {
    return createStore(
        rootReducer,
        composeWithDevTools(applyMiddleware(reduxThunk))
    )
}