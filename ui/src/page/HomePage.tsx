import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import IssueList from '../component/IssueList';
import { Card, Image, Grid, Segment, CardContent, List } from 'semantic-ui-react';
import { fetchUserAvatar } from '../api/GarageApi';
import { RootState } from '../store';

const HomePage: React.FC = (props: any) => {

    const currentUser = props.currentUser.user;

    const [userAvatar, setUserAvatarUrl] = useState<string>("");

    useEffect(() => {
        fetchUserAvatar(currentUser.userId)
            .then(response => {
                setUserAvatarUrl(response.data);
            });
    }, [])

    return (
        <Segment>
            <Grid columns={2}>
                <Grid.Column width={5}>
                    <Card>
                        <Image src={userAvatar} wrapped ui={false} />
                        <CardContent>
                            <List divided>
                                <List.Item>
                                    <Card.Header>
                                        <h3>
                                            {currentUser.username}
                                        </h3>
                                    </Card.Header>
                                </List.Item>
                                <List.Item>
                                    <Card.Meta>
                                        {currentUser.roles[0].substring(currentUser.roles[0].search('_') + 1)}
                                    </Card.Meta>
                                </List.Item>
                            </List>
                        </CardContent>
                    </Card>
                </Grid.Column>
                <Grid.Column width={11}>
                    <div className="homepage-header">
                        <h2>Aktualne sprawy</h2>
                    </div>
                    <IssueList userId={currentUser.userId} isMechanic={currentUser.roles[0] === 'ROLE_MECHANIK'} />
                </Grid.Column>
            </Grid>
        </Segment>
    )
}

const mapStateToProps = (state: RootState) => {
    return {
        currentUser: state.authorization
    }
}

export default connect(mapStateToProps)(HomePage);
