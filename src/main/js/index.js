import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import App from './components/app';
import Vote from './components/views/vote';
import Statistics from './components/views/statistics';

require('./stylesheets/base.scss');
require('./stylesheets/vote.scss');
require('./stylesheets/statsHeader.scss');
require('./stylesheets/statsTable.scss');
require('./stylesheets/statsInfo.scss');

ReactDOM.render((
    <Router>
        <App>
            <Route exact path="/" component={Vote} />
            <Route path="/stats" component={Statistics} />
        </App>
    </Router>
), document.querySelector('#app'))