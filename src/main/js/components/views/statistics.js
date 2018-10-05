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

                <li id="liRest"> Featured Restaurant:</li>
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
      categories: [],
      chart: {
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
            colors: ['#000000', '#222222', '#444444', '#666666', '#888888',
              '#999999']
          },
          xaxis: {
            categories: [
              moment().subtract(11, 'months').format('MMM'),
              moment().subtract(10, 'months').format('MMM'),
              moment().subtract(9, 'months').format('MMM'),
              moment().subtract(8, 'months').format('MMM'),
              moment().subtract(7, 'months').format('MMM'),
              moment().subtract(6, 'months').format('MMM'),
              moment().subtract(5, 'months').format('MMM'),
              moment().subtract(4, 'months').format('MMM'),
              moment().subtract(3, 'months').format('MMM'),
              moment().subtract(2, 'months').format('MMM'),
              moment().subtract(1, 'months').format('MMM'),
              moment().format('MMM')]
          }
        },
        series: [{
          name: 'JAN - co2e',
          data: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
        }]
      }
    };

    this.date = {
      toDate: moment().format('YYYY-MM-DD'),
      fromDate: moment().subtract(7, 'days').format('YYYY-MM-DD'),
    };

    this.statsGoalUrl = "https://localhost:8443/v1/emission/goal?from="
        + this.date.fromDate + "&to=" + this.date.toDate;
    this.statActualUrl = "https://localhost:8443/v1/emission/statistics?from="
        + this.date.fromDate + "&to=" + this.date.toDate;
    this.categoriesUrl = "https://localhost:8443/v1/categories/";

  }

  updateChart(actual) {
    const {categoryStatistics} = actual || {};

    const {chart} = this.state;
    //const chart = this.state.chart

    this.setState({
      // create new immutible object to update state
      chart: Object.assign({}, chart, {
        series: chart.series.map((serie, index) => {
          return Object.assign({}, serie,
              {data: [categoryStatistics[index].co2e]})
        })
      })
    });
  }

  componentDidUpdate() {
    console.log('Render');
  }

  componentDidMount() {
    fetch(this.statActualUrl)
    .then(response => response.json())
    .then(data => {
      console.log('actual: ' + data);
      this.updateChart(data)
    })
    .catch(err => console.log(err));

    fetch(this.statsGoalUrl)
    .then(response => response.json())
    .then(data => {
      console.log('goal: ' + data);
      this.setState({goal: data})
    })
    .catch(err => console.log(err));

    fetch(this.categoriesUrl)
    .then(response => response.json())
    .then(data => {
      console.log('categories: ' + data);
      this.setState({categories: data})
    })
    .catch(err => console.log(err));
  }

  render() {
    const {categories, goal, chart = {}} = this.state;
    return (
        <div className="statsTable">
          <div id="statsTable">
              <Chart options={chart.options} series={chart.series} type="bar"
                     width="90%" height={320}/>
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
        from = '2018-01-01';
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
        from = '2018-01-01';
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