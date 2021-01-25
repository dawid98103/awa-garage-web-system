import { on } from 'process';
import React from 'react';
import { Button, Icon } from 'semantic-ui-react';

interface DeleteButtonProps {
    text: string,
    onClick: () => void
}

const DeleteButton: React.FC<DeleteButtonProps> = ({ text, onClick }) => {

    return (
        <Button negative icon labelPosition="right" onClick={onClick}>
            {text}
            <Icon name="trash alternate" />
        </Button>
    )
}

export default DeleteButton;