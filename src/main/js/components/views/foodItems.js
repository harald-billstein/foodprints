import React from 'react';
import Popup from './popup';

export default class FoodItems extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            foods: [],
            vote: null,
            showResponse: false
        }
        this.url = "/v1/categories/";
        this.postVoteUrl = "/v1/votes/";
        this.statsUrl = window.location.origin + "/stats";
    }

    toggleResponse() {
        this.setState({showResponse: !this.state.showResponse})
    }

    postVote() {
        if (this.state.vote !== null) {
            fetch(this.postVoteUrl, {
                method: 'POST',
                headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    vote: this.state.vote
                })
            })
            .then(response => response.json())
            .then(this.toggleResponse.bind(this))
            .catch(err => console.log(err));
        }
    }

    componentDidMount() {
        fetch(this.url)
            .then(response => response.json())
            .then(data => {
                this.setState({foods: data})})
            .catch(err => console.log(err));
    }

    render() {
        return(
            <div className="voteList">
                <div>
                <ul id="voteList">
                    { this.state.foods.map(item =>
                    <li id="li" key={item}><button id="button" onClick={() => { this.setState({vote: item}, () => this.postVote())}} disabled={this.state.showResponse}>
                        {item}.
                    </button></li>
                    )}
                </ul>
                </div>
                <div>
                    { this.state.showResponse ?
                        <Popup
                            stopResponse = {this.toggleResponse.bind(this)}
                        />
                        : null
                    }
                </div>
            <div>
              <a  id="stats-link" href={this.statsUrl} className="button">Stats</a>
            </div>
            </div>
        )
    }
}