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
            <div><h1 id="headerName"> foodprint. </h1></div>
            <div id="headerRestSuggestion">
              <ul id="restList">

                <li id="liRestHeader"> featured restaurant </li>
                <li id="liRest"> {this.state.restSuggestion.name} </li>
                <li id="liRest"> {this.state.restSuggestion.address} </li>
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
          tooltip: {
            enabled: false,
          },
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
            textAnchor: 'middle',
            offsetX: 0,
            offsetY: 0,
            style: {
              fontSize: '10px',
              colors: undefined
            },
          },
          fill: {
            colors: ['#000000']
          },
          xaxis: {
            categories: [
                'jan',
                'feb',
                'mar',
                'apr',
                'may',
                'jun',
                'jul',
                'aug',
                'sep',
                'oct',
                'nov',
                'dec',
            ]
          },colors: ['#ffffff']
        },
        series: [{
          name: 'CO2e/kg per portion',
          id: 'emission',
          data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
        }],
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
              <Chart options={chart.options} series={chart.series} type="bar" width="100%" height={300}/>
          </div>
          <div id="chartText">
              <p> Co<sub>2</sub>e kg per portion. </p>
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
            thisMonthFrom: moment().startOf('month').format('YYYY-MM-DD'),
            thisMonthTo: moment().format('YYYY-MM-DD'),
            lastMonthFrom: moment().subtract(1, 'month').startOf('month').format('YYYY-MM-DD'),
            lastMonthTo: moment().subtract(1, 'month').endOf('month').format('YYYY-MM-DD'),
            difference: 1,
            logo: GreenIcon
        }
        this.timer = null;
        this.statsUrl = "https://localhost:8443/v1/emission/statistics";
    }

    calculateDiff() {
        if (this.state.thisMonth.totalCo2e !== 0 && this.state.lastMonth.totalCo2e !== 0) {
           this.setState({difference: (this.state.thisMonth.totalCo2e/this.state.thisMonth.totalPortions)/(this.state.lastMonth.totalCo2e/this.state.lastMonth.totalPortions)})
        }

        if (this.state.difference > 1) {
            this.setState({
                    logo: RedIcon,
                    difference: (this.state.difference - 1)*100})
        } if (this.state.difference < 1) {
            this.setState({
                    logo: GreenIcon,
                    difference: (1 - this.state.difference)*100})
        } if (this.state.difference === 1) {
            this.setState({
                    logo: GreenIcon,
                    difference: 0})
        }
    }

    fetchStats() {
        let thisMonth = fetch(this.statsUrl+ '/?from=' + this.state.thisMonthFrom +'&to=' + this.state.thisMonthTo)
            .then(response => response.json());

        let lastMonth = fetch(this.statsUrl+ '/?from=' + this.state.lastMonthFrom +'&to=' + this.state.lastMonthTo)
            .then(response => response.json());

        Promise.all([thisMonth, lastMonth])
            .then(response =>
                this.setState({
                    thisMonth: response[0],
                    lastMonth: response[1]}))
            .then(() => this.calculateDiff());
    }

    componentDidMount() {
        this.timer = setInterval(() => this.fetchStats(), 10000);
    }

    render() {
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
            today: moment().format('YYYY-MM-DD')
        }
        this.timer = null;
        this.statsUrl = "https://localhost:8443/v1/emission/statistics";
    }

    fetchStats(timeInterval) {
        let from;
        switch (timeInterval) {
            case 1:
                from = this.state.today;
                break;
            case 2:
                from = moment().startOf('week').format('YYYY-MM-DD');
                break;
            case 3:
                from = moment().startOf('month').format('YYYY-MM-DD');
                break;
            case 0:
            default:
                from = moment().startOf('year').format('YYYY-MM-DD');
                break;
        }

        fetch(this.statsUrl+ '/?from=' + from +'&to=' + this.state.today)
            .then(response => response.json())
            .then(data => {
                console.log(data);
                this.setState({
                        stats: data,
                        timeInterval})})
            .catch(err => console.log(err));
    }

    componentDidMount() {
        this.fetchStats(TimeInterval.All);

        this.timer = setInterval(() => this.fetchStats((this.state.timeInterval + 1) % 4), 7000);
    }

    render() {
        const {timeInterval} = this.state;
        console.log()
        return(
          <div className="statOptions">
            <div>
              <ul id="statOptions">
                <li id="li" onClick={() => this.fetchStats(TimeInterval.All) }>
                    <button id="button" style={{color: timeInterval === TimeInterval.All ? "black" : "lightgray"}}> all </button>
                </li>
                <li id="li" onClick={() => this.fetchStats(TimeInterval.Day) }>
                    <button id="button" style={{color: timeInterval === TimeInterval.Day ? "black" : "lightgray"}}> day </button>
                </li>
                <li id="li" onClick={() => this.fetchStats(TimeInterval.Week) }>
                    <button id="button" style={{color: timeInterval === TimeInterval.Week ? "black" : "lightgray"}}> week </button>
                </li>
                <li id="li" onClick={() => this.fetchStats(TimeInterval.Month) }>
                    <button id="button" style={{color: timeInterval === TimeInterval.Month ? "black" : "lightgray"}}> month </button>
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
                    <p id="categoryTitle"> {Math.floor(item.co2e+0.5)} kg. </p>
                    <p id="categoryUnderTitle"> {item.category}. </p>
                  </div>
                ))}
              </div>
            </div>
          </div>
        )
    }
}