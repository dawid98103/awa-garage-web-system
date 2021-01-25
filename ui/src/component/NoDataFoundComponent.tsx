import React from 'react';
import { Segment, Icon, Header } from 'semantic-ui-react'

const NoDataFoundComponent: React.FC<any> = () => {
    return (
        <Segment placeholder style={{ marginTop: 15 }} className="fullwidth">
            <Header icon>
                <Icon name='search' />
                Nie znaleziono spraw!
            </Header>
        </Segment>
    )
}

export default NoDataFoundComponent;