import React from 'react';
import moment from 'moment';
import Chart from 'react-apexcharts';
import StatsHeader from './statsHeader';
import StatsTable from './statsTable';
import StatsInfo from './statsInfo';

export default class Statistics extends React.Component {

  render() {
    return (
        <div>
          <StatsHeader/>
          <StatsTable />
          <StatsInfo />
        </div>
    )
  }
}