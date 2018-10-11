import React from 'react';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faStar, faStarHalf} from "@fortawesome/free-solid-svg-icons";
import moment from 'moment';
import Chart from 'react-apexcharts';
import GreenIcon from './icons/caret-symbol-green.svg';
import RedIcon from './icons/caret-symbol-red.svg';
import SVG from 'react-inlinesvg';

export default class Statistics extends React.Component {

  render() {
    return (
        <div>
          <Header/>
          <StatsTable />
          <StatsInfo />
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
                'Jan',
                'Feb',
                'Mar',
                'Apr',
                'May',
                'Jun',
                'Jul',
                'Aug',
                'Sep',
                'Okt',
                'Nov',
                'Dec',
            ]
          }
        },
        series: [{
          name: 'CO2e/kg per portion',
          id: 'emission',
          data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
        }]
      }
    };
    this.statsGoalUrl = "https://localhost:8443/v1/emission/goal?from=2018-01-01&to=2019-01-01";
    this.actualUrl = "https://localhost:8443/v1/emission/statistics/year/per/month?year=2018";
  }

  updateChart(fetchData) {
    const {chart} = this.state;
      this.setState({
        chart: Object.assign({}, chart, {
          series: chart.series.map((serie, index) => {

            if (serie.id === 'emission') {
              return Object.assign({}, serie,
                {data: fetchData.map(item => parseFloat(item.emissionPerPortion).toFixed(2))}
              )
            }
            return serie;
          })
        })
      });
  }

  componentDidMount() {
    fetch(this.actualUrl)
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
  }

  render() {
    const {chart = {}} = this.state;
    return (
        <div className="statsTable">
          <div id="statsTableInfo">
            <StatsPercent />
          </div>
          <div id="statsTable">
              <Chart options={chart.options} series={chart.series} type="bar"
                     width="100%" height={320}/>
          </div>
        </div>
    )
  }
}

class StatsPercent extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            month: moment().month(),
            day: moment().date(),
            thisMonth: [],
            lastMonth: [],
            difference: 0,
            logo: GreenIcon
        }
        this.statsUrl = "https://localhost:8443/v1/emission/statistics";
    }

    calculateDiff() {
        let daysThisMonth = 11;
        let daysLastMonth = 30;
        this.setState({difference: (this.state.thisMonth.totalCo2e/this.state.thisMonth.totalPortions)/((this.state.lastMonth.totalCo2e/this.state.lastMonth.totalPortions)*(daysThisMonth/daysLastMonth))})

        console.log("this co2:", this.state.thisMonth.totalCo2e);
        console.log("this port:", this.state.thisMonth.totalPortions);
        console.log("last co2:", this.state.lastMonth.totalCo2e);
        console.log("last total:",this.state.lastMonth.totalPortions);

        console.log((this.state.thisMonth.totalCo2e/this.state.thisMonth.totalPortions)/(this.state.lastMonth.totalCo2e/this.state.lastMonth.totalPortions));
        if (this.state.difference >= 1) {
            this.setState({
                    logo: RedIcon,
                    difference: (this.state.difference - 1)*100})
        } if (this.state.difference < 1) {
            this.setState({
                    logo: GreenIcon,
                    difference: (1 - this.state.difference)*100})
        }
    }

    componentDidMount() {
        let thisMonthFrom = '2018-10-01';
        let thisMonthTo = '2018-10-11';

        let thisMonth = fetch(this.statsUrl+ '/?from=' + thisMonthFrom +'&to=' + thisMonthTo)
            .then(response => response.json());

        let lastMonthFrom = '2018-09-01';
        let lastMonthTo = '2018-09-30';

        let lastMonth = fetch(this.statsUrl+ '/?from=' + lastMonthFrom +'&to=' + lastMonthTo)
            .then(response => response.json());

        Promise.all([thisMonth, lastMonth])
            .then(response =>
                this.setState({
                    thisMonth: response[0],
                    lastMonth: response[1]}))
            .then(() => this.calculateDiff());
    }

    render() {
        console.log(this.state.difference)
        return(
            <div className="statPercent">
                <p id="statPercent"> <SVG src={this.state.logo} /> {Math.round(this.state.difference * 10)/10} % from last month </p>
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
            today: moment().format('YYYY-MM-DD'),
            buttonColor: {
                all: "black",
                day: "lightgray",
                week: "lightgray",
                month: "lightgray"
            }
        }
        this.statsUrl = "https://localhost:8443/v1/emission/statistics";
    }

    fetchStats() {
        let from;
        switch (this.state.timeInterval) {
            case 1:
                this.setState({buttonColor: {
                                    all: "lightgray",
                                    day: "black",
                                    week: "lightgray",
                                    month: "lightgray"
                                    }})
                from = moment().subtract(1, 'days').format('YYYY-MM-DD')
                break;
            case 2:
                this.setState({buttonColor: {
                                    all: "lightgray",
                                    day: "lightgray",
                                    week: "black",
                                    month: "lightgray"
                                    }})
                from = moment().subtract(7, 'days').format('YYYY-MM-DD')
                break;
            case 3:
                this.setState({buttonColor: {
                                    all: "lightgray",
                                    day: "lightgray",
                                    week: "lightgray",
                                    month: "black"
                                    }})
                from = moment().subtract(1, 'month').format('YYYY-MM-DD')
                break;
            case 0:
            default:
                this.setState({buttonColor: {
                                    all: "black",
                                    day: "lightgray",
                                    week: "lightgray",
                                    month: "lightgray"
                                    }})
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

    componentDidMount() {
        this.fetchStats();
    }

    render() {
        return(
          <div className="statOptions">
            <div>
              <ul id="statOptions">
                <li id="li" onClick={() => { this.setState({timeInterval: TimeInterval.All}, () => { this.fetchStats() })}}>
                    <button id="button" style={{color: this.state.buttonColor.all}}> all </button>
                </li>
                <li id="li" onClick={() => { this.setState({timeInterval: TimeInterval.Day}, () => { this.fetchStats() })}}>
                    <button id="button" style={{color: this.state.buttonColor.day}}> day </button>
                </li>
                <li id="li" onClick={() => { this.setState({timeInterval: TimeInterval.Week}, () => { this.fetchStats() })}}>
                    <button id="button" style={{color: this.state.buttonColor.week}}> week </button>
                </li>
                <li id="li" onClick={() => { this.setState({timeInterval: TimeInterval.Month}, () => { this.fetchStats() })}}>
                    <button id="button" style={{color: this.state.buttonColor.month}}> month </button>
                </li>
              </ul>
            </div>
            <div id="co2Stats">
              <div id="totalCo2">
                <p id="totalPortions"> {this.state.stats.totalPortions} portions. </p>
                <p id="coTotalTitle"> { Math.floor(this.state.stats.totalCo2e)/1000} tons. </p>
                <p id="coTotalUnderTitle"> carbon footprint. </p>
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