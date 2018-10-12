import React from 'react';
import SVG from 'react-inlinesvg';
import CheckMarkLogo from './icons/verify-sign2.svg';

export default class Popup extends React.Component {

    closeResponse() {
        setTimeout(this.props.stopResponse, 700);
    }

    render() {
        return(
        <div className="response" id="response">
            <div id="responseLogo">
                <SVG src={CheckMarkLogo} />
                {this.closeResponse()}
            </div>
        </div>
        )
    }
}