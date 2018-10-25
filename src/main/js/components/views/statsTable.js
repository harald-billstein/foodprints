import React from 'react';
import moment from 'moment';
import Chart from 'react-apexcharts';
import StatsPercent from './statsPercent';

export default class StatsTable extends React.Component {
  constructor() {
    super();
    this.timer = null;

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
            offsetY: 2,
            style: {
              fontSize: '8px',
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
          },colors: ['#D3D3D3']
        },
        series: [{
          name: 'CO2e/kg per portion',
          id: 'emission',
          data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
        }],
      }
    };
    this.statsGoalUrl = "/v1/emission/goal?from=2018-01-01&to=2019-01-01";
    this.actualUrl = "/v1/emission/statistics/year/per/month?year=2018";
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

  fetchGrafData(){
    fetch(this.actualUrl)
    .then(response => response.json())
    .then(data => {
      console.log('actual: ' + data);
      this.updateChart(data)
    })
    .catch(err => console.log(err));
  }

  componentDidMount() {
    this.fetchGrafData();
    this.timer = setInterval(() => this.fetchGrafData(), 10000);
    //fetch(this.statsGoalUrl)
    //.then(response => response.json())
    //.then(data => {
    //  console.log('goal: ' + data);
    //  this.setState({goal: data})
    //})
    //.catch(err => console.log(err));
  }

  render() {
    const {chart = {}} = this.state;
    return (
        <div className="statsTable" id="statsTable">
          <div id="statsTableInfo">
            <StatsPercent />
          </div>
          <div>
              <Chart options={chart.options} series={chart.series} type="bar" width="100%" height={300}/>
          </div>
        </div>
    )
  }
}
