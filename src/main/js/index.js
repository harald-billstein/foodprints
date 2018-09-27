import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router, Route, IndexRoute, browserHistory } from 'react-router-dom';
import App from './components/app';
import Vote from './components/views/vote';
import Statistics from './components/views/statistics';

require('./stylesheets/base.scss');
require('./stylesheets/vote.scss');

ReactDOM.render((
    <Router>
        <App>
            <Route exact={true} path="/" component={Vote} />
            <Route path="/vote" component={Vote} />
            <Route path="/stats" component={Statistics} />
        </App>
    </Router>
), document.querySelector('#app'))