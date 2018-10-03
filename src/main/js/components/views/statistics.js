import React from 'react';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faStar, faStarHalf} from "@fortawesome/free-solid-svg-icons";
import moment from 'moment';
import Chart from 'react-apexcharts'

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
    super();
    this.state = {
      restSuggestion: []
    };
    this.restUrl = "https://localhost:8443/v1/restaurants/suggestion";
  }

  static getStars(grade) {

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
                <li> {Header.getStars(this.state.restSuggestion.grade)}</li>

              </ul>
            </div>
          </div>
        </div>
    )
  }
}



class StatsTable extends React.Component {
  constructor() {
    super();

    this.state = {
      goal: {},
      actual: {},
      categories: []
    };

    this.date = {
      toDate: moment().format('YYYY-MM-DD'),
      fromDate: moment().subtract(7, 'days').format('YYYY-MM-DD'),
    };

    this.chart = {
      options: {
        chart: {
          stacked: false,
          id: "cO2 Emission",
          toolbar: {
            show: false
          }
        },
        grid: {
          show: false,
        },
        dataLabels: {
          enabled: true,
          formatter: function (val) {
            return val
          },
          textAnchor: 'middle',
          offsetX: 0,
          offsetY: 0,
          style: {
            fontSize: '10px',
            colors: undefined
          },
          dropShadow: {
            enabled: false,
            top: 1,
            left: 1,
            blur: 1,
            opacity: 0.45
          }
        },
        fill: {
          colors: ['#000000', '#222222', '#444444','#666666', '#888888', '#999999']
        },
        xaxis: {
          categories: [moment().subtract(7, 'days').format('MM-DD'),
            moment().subtract(6, 'days').format('MM-DD'),
            moment().subtract(5, 'days').format('MM-DD'),
            moment().subtract(4, 'days').format('MM-DD'),
            moment().subtract(3, 'days').format('MM-DD'),
            moment().subtract(2, 'days').format('MM-DD'),
            moment().subtract(1, 'days').format('MM-DD'),
            moment().format('MM-DD')]
        }
      },
      series: [{
        name: 'VEGAN - co2e',
        data: [29]
      }, {
        name: 'VEGETARIAN - co2e',
        data: [30.4]
      }, {
        name: 'CHICKEN - co2e',
        data: [120]
      }, {
        name: 'FISH - co2e',
        data: [87]
      }, {
        name: 'PORK - co2e',
        data: [20]
      }, {
        name: 'BEEF - co2e',
        data: [988]
      }]
    };

    this.statsGoalUrl = "https://localhost:8443/v1/emission/goal?from=" + this.date.fromDate + "&to=" + this.date.toDate;
    this.statActualUrl = "https://localhost:8443/v1/emission/statistics?from=" + this.date.fromDate + "&to=" + this.date.toDate;
    this.categoriesUrl = "https://localhost:8443/v1/categories/";

  }

  updateshart() {
    console.log('update shart was called  --  NOT DONE!! FIX!!');
    console.log(this.chart.series.map(item =>  item.data));

    {this.state.actual && this.state.actual.categoryStatistics ? <div></div> : null}

    this.state.actual && this.state.actual.categoryStatistics ? console.log(this.state.actual.categoryStatistics.map(item => item.co2e)) : null;


  }

  componentDidMount() {
    fetch(this.statActualUrl)
    .then(response => response.json())
    .then(data => {
      console.log(data);
      this.setState({actual: data})
      this.updateshart();
    })
    .catch(err => console.log(err));

    fetch(this.statsGoalUrl)
    .then(response => response.json())
    .then(data => {
      console.log(data);
      this.setState({goal: data})
    })
    .catch(err => console.log(err));

    fetch(this.categoriesUrl)
    .then(response => response.json())
    .then(data => {
      console.log(data);
      this.setState({categories : data})})
    .catch(err => console.log(err));
  }

  render() {
    return (

        <div className="statsTable">
          <div id="statsTable">
            <div>
              {this.state.categories.map(food => (<a key={food}>{ food }</a> ))}
            </div>
            <div>
              goalCo2ePerPortion-->{this.state.goal.goalCo2ePerPortion}-->
              goalCo2e-->{this.state.goal.goalCo2e}-->
              totalCo2e-->{this.state.actual.totalCo2e}-->
              totalPortions-->{this.state.actual.totalPortions}-->
              difference-->{this.state.actual.difference}-->



              categoryStatistics-->{this.state.actual && this.state.actual.categoryStatistics ?
                <div>{this.state.actual.categoryStatistics.map(item =>
                    <li key={item.category}>category: {item.category} co2e: {item.co2e} numPortions: {item.numPortions} portionsPercent: {item.portionsPercent} </li>
                )}</div> : null}




                <Chart options={this.chart.options} series={this.chart.series} type="bar" width={500} height={320}/>
            </div>
          </div>
        </div>
    )
  }
}

class StatsInfo extends React.Component {

  constructor() {
    super();
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