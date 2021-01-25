import React from 'react';
import { Card, Image, List } from 'semantic-ui-react';

import { ICarDto } from '../api/types';
interface ICarCardProps {
    car: ICarDto,
}

const CarCard: React.FC<ICarCardProps> = ({ car }) => {
    return (
        <div className="container-overlay">
            <Card>
                <Image src={car.carPhotoUrl} wrapped ui={false} />
                <Card.Content>
                    <Card.Header>{car.carBrandName} {car.carModelName}</Card.Header>
                    <Card.Description>
                        <List divided relaxed>
                            <List.Item>
                                Rok produkcji: {car.yearOfProduction}
                            </List.Item>
                            <List.Item>
                                Przebieg: {car.carMileage} km
                            </List.Item>
                            <List.Item>
                                Ostatnia aktualizacja: {car.lastUpdated}
                            </List.Item>
                        </List>
                    </Card.Description>
                </Card.Content>
            </Card>
        </div>
    );
}

export default CarCard;