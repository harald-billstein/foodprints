import React from 'react';
import FoodItems from './foodItems';

export default class Vote extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return(
            <div className="vote">
                <div id="header">
                    <h1 id="headerTitle"> foodprint. </h1>
                    <h2 id="headerUnderTitleNoFlex"> by jayway. </h2>
                </div>
                <div>
                    <FoodItems />
                </div>
            </div>
        )
    }
}



