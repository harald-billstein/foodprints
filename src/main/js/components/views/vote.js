import React from 'react';
import FoodItems from './foodItems';

export default class Vote extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return(
            <div className="voteList">
                <h1 id="headerName"> foodprint. </h1>
                <h2 id="jayway"> by jayway. </h2>
                <FoodItems />
            </div>
        )
    }
}



