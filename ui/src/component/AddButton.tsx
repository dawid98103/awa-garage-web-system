import React from 'react';
import { Button,Icon } from 'semantic-ui-react';

interface IAddButtonProps {
    text: string,
    handleClick: () => void;
}

const AddButton: React.FC<IAddButtonProps> = (props) => {    
    return (
        <Button positive icon labelPosition="right" onClick={props.handleClick}>
                    {props.text}
                    <Icon name="add" />
        </Button>
    )
}

export default AddButton;
