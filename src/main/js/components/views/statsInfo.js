import React from 'react';
import moment from 'moment';

const TimeInterval = {
  All: 0,
  Day: 1,
  Week: 2,
  Month: 3
}

export default class StatsInfo extends React.Component {
    constructor() {
        super()
        this.state = {
            stats: [],
            timeInterval: TimeInterval.All,
            today: moment().format('YYYY-MM-DD')
        }
        this.timer = null;
        this.statsUrl = "/v1/emission/statistics";
    }

    calcCo(item) {
        if (item < 10) {
            return Math.floor((item)*10)/10;
        } else {
            return Math.floor(item+0.5);
        }
    }

    fetchStats(timeInterval) {
        let from;
        if (this.state.today !== moment().format('YYYY-MM-DD')) {
            this.setState({today: moment().format('YYYY-MM-DD')})
        }

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
          <div className="info" id="info">
            <hr id="breakWithMargin" />
            <div id="infoOptionLabels">
              <ul id="infoOptions">
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
            <div id="infoCo2Stats">
              <div id="infoTotalCo2">
                <p id="infoTotalPortions"> {this.state.stats.totalPortions} portions. </p>
                <p id="infoCoTotalTitle"> { Math.floor(this.state.stats.totalCo2e)/1000} tons. </p>
                <p id="infoCoTotalUnderTitle"> carbon footprint. </p>
              </div>
              <div id="infoCategoryCo2">
                {this.state.stats.categoryStatistics !== undefined && this.state.stats.categoryStatistics.map(item => (
                  <div key={item.category} id="infoCategoryTitle">
                    <p id="infoCategoryPortions"> {item.numPortions} portions. </p>
                    <p id="infoCategoryTitle"> {this.calcCo(item.co2e)} kg. </p>
                    <p id="infoCategoryUnderTitle"> {item.category}. </p>
                  </div>
                ))}
              </div>
            </div>
          </div>
        )
    }
}