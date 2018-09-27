import React from 'react';

export default class Statistics extends React.Component {

    constructor(props) {
        super(props);
        this.state = {};
        this.restUrl = "https://localhost:8443/v1/restaurants/suggestion";
        this.statUrl = "https://localhost:8443/v1/restaurants/suggestion"; // byt den
    }

    componentDidMount() {
        // här får vi fetcha lite statistik osv
    }

    render() {
        return (
            <div>
                <h1> Unknown. </h1>
            </div>
        )
    }
}