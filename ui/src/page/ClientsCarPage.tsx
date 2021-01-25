import React, { useState, useEffect } from 'react';
import { Segment, Loader, Button } from 'semantic-ui-react';

import { fetchCarsForUserId } from '../api/GarageApi';
import { ICarDto } from '../api/types';
import { IUserDto } from '../store/auth/types';
import GridGenerator from '../util/GridGenerator';
import CreateCarModal from '../component/modals/CreateCarModal';
import ClientCarDetailsModal from '../component/modals/ClientCarDetailsModal';

const ClientsCarPage: React.FC = () => {
    const [userCars, setUserCars] = useState<ICarDto[]>([]);
    const [refresh, setRefresh] = useState<boolean>(false);

    useEffect(() => {
        const { userId }: IUserDto = JSON.parse(localStorage.getItem('user') || '{}');

        fetchCarsForUserId(userId)
            .then(response => {
                setUserCars(response.data);
            });
    }, [refresh])

    const refreshComponent = () => {
        setRefresh(!refresh);
    }

    return (
        <Segment>
            <div>
                {(userCars.length === 0 ?
                    <Loader />
                    :
                    <Segment>
                        <GridGenerator cols={4}>
                            {userCars.map(car => {
                                return (
                                    <div>
                                        <ClientCarDetailsModal car={car} key={car.carId} refreshList={() => refreshComponent()} />
                                    </div>
                                )
                            })}
                        </GridGenerator>
                    </Segment>
                )}
                <div style={{ display: 'flex', justifyContent: 'flex-end' }}>
                    <CreateCarModal refreshList={() => refreshComponent()} />
                </div>
            </div>
        </Segment>
    )
}

export default ClientsCarPage;