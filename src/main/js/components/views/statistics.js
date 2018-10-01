import React from 'react';

export default class Statistics extends React.Component {

    render() {
        return (
            <div>
                <Header />
                <StatsTable />
                <StatsInfo />
            </div>
        )
    }
}


class Header extends React.Component {

    constructor() {
        super()
        this.state={restSuggestion: []};
        this.restUrl = "https://localhost:8443/v1/restaurants/suggestion";
    }

    componentDidMount() {
        fetch(this.restUrl)
            .then(response => response.json())
            .then(data => {
                this.setState({restSuggestion: data})})
            .catch(err => console.log(err));
    }

    render() {
        return(
            <div className="header">
                <div id="row">
                    <div id="col"> <h1 id="headerName"> Unknown. </h1> </div>
                    <div id="pushRight">
                        <ul id="restList">
                            <li> Featured Restaurant: </li>
                            <li> { this.state.restSuggestion.name } </li>
                            <li> { this.state.restSuggestion.grade } </li>
                        </ul>
                    </div>
                </div>
            </div>
        )
    }
}

class StatsTable extends React.Component {

    constructor() {
        super()
        this.state = {
            stats: [],
            foods: []
        };
        this.statsUrl = "https://localhost:8443/v1/restaurants/suggestion";
        this.foodsUrl = "https://localhost:8443/v1/categories/";
    }

    componentDidMount() {
        fetch(this.foodsUrl)
            .then(response => response.json())
            .then(data => {
                this.setState({foods: data})})
            .catch(err => console.log(err));

        fetch(this.statsUrl)
            .then(response => response.json())
            .then(data => {
                this.setState({stats: data})})
            .catch(err => console.log(err));
    }

    render() {
        return(
          <div className="statsTable">
            <div id="statsTable">
                <div>
                  <ul id="statOptions">
                    <li id="li"><a id="a" href="/stats"> all. </a> </li>
                    {this.state.foods.map(food => (
                    <li id="li"><a id="a" href="/stats"> {food}. </a> </li>))}
                  </ul>
                </div>
                <div>
                    <h1 id="statTable"> HÄR SKA DE VA NÅT JA EJ KAN </h1>
                </div>
            </div>
          </div>
        )
    }
}

class StatsInfo extends React.Component {

    constructor() {
        super()
        this.state = {
            stats: [],
            foods: []
        };
        this.statsUrl = "https://localhost:8443/v1/restaurants/suggestion";
    }

    componentDidMount() {
        fetch(this.statsUrl)
            .then(response => response.json())
            .then(data => {
                this.setState({stats: data})})
            .catch(err => console.log(err));
    }

    render() {
        return(
          <div className="statOptions">
              <ul id="statOptions">
                <li id="li"><a id="a" href="/stats"> all. </a> </li>
                <li id="li"><a id="a" href="/stats"> day. </a> </li>
                <li id="li"><a id="a" href="/stats"> week. </a> </li>
                <li id="li"><a id="a" href="/stats"> month. </a> </li>
              </ul>
          </div>
        )
    }
}