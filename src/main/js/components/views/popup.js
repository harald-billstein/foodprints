import React from 'react';
import SVG from 'react-inlinesvg';
import CheckMark from './icons/checkmark.svg';

export default class Popup extends React.Component {

    closeResponse() {
        setTimeout(this.props.stopResponse, 2000);
    }

    render() {
        return(
            <div className="popupOuter" id="popupOuter">
                <div id="popupInner">
                     <SVG src={CheckMark} />
                     <h1 id="popupText"> Thanks! </h1>
                     {this.closeResponse()}
                </div>
            </div>
        )
    }
}

