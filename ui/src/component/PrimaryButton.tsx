import React from 'react';
import { Button, Icon } from 'semantic-ui-react';
import { SemanticICONS } from 'semantic-ui-react/dist/commonjs/generic';

interface PrimaryButtonProps {
    text: string,
    iconName: SemanticICONS,
    onClick: () => void
}

const PrimaryButton: React.FC<PrimaryButtonProps> = ({ text, iconName, onClick }) => {

    return (
        <Button primary icon labelPosition="right" onClick={onClick}>
            {text}
            <Icon name={iconName} />
        </Button>
    )
}

export default PrimaryButton;