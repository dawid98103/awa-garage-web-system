import React from 'react';
import { Grid } from 'semantic-ui-react';
import { chunk } from 'lodash';

type GridGeneratorProps = {
    cols: 1 | 2 | 3 | 4 | 6 | 12
}

const GridGenerator: React.FC<GridGeneratorProps> = ({ cols, children }) => {
    const colWidth = 12 / cols;

    const rows = chunk(React.Children.toArray(children), cols);

    return (
        <Grid columns={cols} divided>
            {rows.map((cols) => (
                <Grid.Row>
                    {cols.map((col) => (
                        <Grid.Column>
                            {col}
                        </Grid.Column>
                    ))}
                </Grid.Row>
            ))}
        </Grid>
    );
}

export default GridGenerator;
