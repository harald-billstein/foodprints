import React from 'react';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faStar, faStarHalf} from "@fortawesome/free-solid-svg-icons";
import moment from 'moment';

export default class Statistics extends React.Component {

  render() {
    return (
        <div>
          <Header/>
          <StatsTable/>
          <StatsInfo/>
        </div>
    )
  }
}

class Header extends React.Component {

  constructor() {
    super()
    this.state = {restSuggestion: []};
    this.restUrl = "https://localhost:8443/v1/restaurants/suggestion";
  }

  getStars(grade) {

    let test = [];
    let gradeFloored = Math.floor(grade);
    let gradeDif = grade - gradeFloored;

    for (let i = 0; i < gradeFloored; i++) {
      if (gradeFloored >= i) {
        test.push(<FontAwesomeIcon key={i} icon={faStar}/>);
      }
    }

    if (gradeDif > 0) {
      test.push(<FontAwesomeIcon key={gradeDif} icon={faStarHalf}/>)
    }
    console.log(typeof grade + " restaurant grade : " + grade);
    return test;
  }

  componentDidMount() {
    fetch(this.restUrl)
    .then(response => response.json())
    .then(data => {
      this.setState({restSuggestion: data})
    })
    .catch(err => console.log(err))
  };

  render() {

    return (
        <div className="header">
          <div id="row">
            <div><h1 id="headerName"> Unknown. </h1></div>
            <div id="headerRestSuggestion">
              <ul id="restList">

                <li> Featured Restaurant:</li>
                <li> {this.state.restSuggestion.name} </li>
                <li> {this.state.restSuggestion.address} </li>
                <li> {this.getStars(this.state.restSuggestion.grade)}</li>

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
      statsGoal: [],
      statsActual: [],
      foods: []

    };

    this.date = {
      toDate: moment().format('YYYY-MM-DD'),
      fromDate: moment().subtract(7, 'days').format('YYYY-MM-DD')
    };


    this.statsGoal = "https://localhost:8443/v1/emission/goal?from=" + this.date.fromDate + "&to=" + this.date.toDate;
    this.statActual = "https://localhost:8443/v1/emission/statistics?from=" + this.date.fromDate + "&to=" + this.date.toDate;
    this.foodsUrl = "https://localhost:8443/v1/categories/";
  }

  componentDidMount() {
    fetch(this.foodsUrl)
    .then(response => response.json())
    .then(data => {
      this.setState({foods: data})
    })
    .catch(err => console.log(err));

    fetch(this.statsGoal)
    .then(response => response.json())
    .then(data => {
      this.setState({statsGoal: data})
    })
    .catch(err => console.log(err));

    fetch(this.statActual)
    .then(response => response.json())
    .then(data => {
      this.setState({statsActual: data})
    })
    .catch(err => console.log(err));

  }

  render() {
    return (
        <div className="statsTable">
          <div id="statsTable">
            <div>
              <ul id="statOptions">
                <li id="li"><a id="a" href="/stats/all"></a></li>
                {this.state.foods.map(food => (
                    <li key={food} id="li"><a id="a" href="/stats/{food}"> {food} </a></li>))}
              </ul>
            </div>
            <div>
              <ul>
                <li>  goalCo2ePerPortion : {this.state.statsGoal.goalCo2ePerPortion}</li>
                <li>  goalCo2e : {this.state.statsGoal.goalCo2e}</li>

                <li>  totalCo2e : {this.state.statsActual.totalCo2e}</li>
                <li>  totalPortions : {this.state.statsActual.totalPortions}</li>

                <li>toDate : {this.date.toDate} </li>
                <li>fromDate : {this.date.fromDate} </li>




              </ul>




              <h1 id="statTable"> LÄGG IN STATS </h1>
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
      this.setState({stats: data})
    })
    .catch(err => console.log(err));
  }

  render() {
    return (
        <div className="statOptions">
          <ul id="statOptions">
            <li id="li"><a id="a" href="/stats"> all </a></li>
            <li id="li"><a id="a" href="/stats"> day </a></li>
            <li id="li"><a id="a" href="/stats"> week </a></li>
            <li id="li"><a id="a" href="/stats"> month </a></li>
          </ul>
        </div>
    )
  }
}