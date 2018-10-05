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

                <li id="liRest"> Featured Restaurant:</li>
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
                <li id="li"><button id="button"> all </button></li>
                {this.state.foods.map(food => (
                    <li key={food} id="li"><button id="button"> {food} </button></li>))}
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
            </div>
          </div>
        </div>
    )
  }
}

const TimeInterval = {
    All: 0,
    Day: 1,
    Week: 2,
    Month: 3
}

class StatsInfo extends React.Component {
    constructor() {
        super()
        this.state = {
            stats: [],
            timeInterval: TimeInterval.All,
            today: moment().format('YYYY-MM-DD')
        }
        this.statsUrl = "https://localhost:8443/v1/emission/statistics";
    }

    fetchStats() {
        let from;
        switch (this.state.timeInterval) {
            case 0:
                from = moment().subtract(1, 'days').format('YYYY-MM-DD');
                break;
            case 1:
                from = moment().subtract(1, 'days').format('YYYY-MM-DD')
                break;
            case 2:
                from = moment().subtract(7, 'days').format('YYYY-MM-DD')
                break;
            case 3:
                from = moment().subtract(1, 'month').format('YYYY-MM-DD')
                break;
            default:
                from = moment().subtract(1, 'days').format('YYYY-MM-DD');
                break;
        }

        fetch(this.statsUrl+ '/?from=' + from +'&to=' + this.state.today)
            .then(response => response.json())
            .then(data => {
                console.log(data);
                this.setState({stats: data})})
            .catch(err => console.log(err));

    }

    getTotalCo2() {
        console.log("here")
        if (this.state.stats.totalCo2e > 1000) {
            return  (<p> {Math.floor(this.state.stats.totalCo2/1000)} " tons." </p> )
        } else {
            return (<p> {Math.floor(this.state.stats.totalCo2)}  " kg." </p> )
        }
    }

    componentDidMount() {
        this.fetchStats();
    }

    render() {
        return(
          <div className="statOptions">
            <div>
              <ul id="statOptions">
                <li id="li" onClick={() => { this.setState({timeInterval: TimeInterval.All}, () => { this.fetchStats() })}}><button id="button"> all </button> </li>
                <li id="li" onClick={() => { this.setState({timeInterval: TimeInterval.Day}, () => { this.fetchStats() })}}><button id="button"> day </button> </li>
                <li id="li" onClick={() => { this.setState({timeInterval: TimeInterval.Week}, () => { this.fetchStats() })}}><button id="button"> week </button> </li>
                <li id="li" onClick={() => { this.setState({timeInterval: TimeInterval.Month}, () => { this.fetchStats() })}}><button id="button"> month </button> </li>
              </ul>
            </div>
            <div id="co2Stats">
              <div id="totalCo2">
                <p id="totalPortions"> {this.state.stats.totalPortions} portions. </p>
                <p id="coTotalTitle"> { Math.floor(this.state.stats.totalCo2e)/1000} tons. </p>
                <p id="coTotalUnderTitle"> carbon footprint </p>
              </div>
              <div id="categoryCo2">
                {this.state.stats.categoryStatistics !== undefined && this.state.stats.categoryStatistics.map(item => (
                    <div key={item.category} id="categoryTitle">
                        <p id="categoryPortions"> {item.numPortions} portions. </p>
                        <p id="categoryTitle"> {Math.floor(item.co2e)} kg. </p>
                        <p id="categoryUnderTitle"> {item.category}. </p>
                    </div>
                 ))}
              </div>
            </div>
          </div>
        )
    }
}