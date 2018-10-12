import React from 'react';
import SVG from 'react-inlinesvg';
import CheckMarkLogo from './icons/verify-sign2.svg';

export default class Vote extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return(
            <div className="voteList">
                <h1 id="headerName"> foodprint. </h1>
                <FoodItems />
            </div>
        )
    }
}

class HandleVote extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            vote: null,
            showResponse: false
        }
        this.postVoteUrl = "https://localhost:8443/v1/votes/";
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

    render() {
        return(
            <div id="voteComponent">
                    <li id="li"><button id="button" onClick={() => { this.setState({vote: this.props.food}, () => this.postVote())}} disabled={this.state.showResponse}>
                        {this.props.food}.
                    </button></li>&nbsp;
                    { this.state.showResponse ?
                        <Popup
                            stopResponse = {this.toggleResponse.bind(this)}
                        />
                        : null
                    }
            </div>
        )
    }
}

class FoodItems extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            foods: []
        }
        this.url = "https://localhost:8443/v1/categories/";
    }

    componentDidMount() {
        fetch(this.url)
            .then(response => response.json())
            .then(data => {
                this.setState({foods: data})})
            .catch(err => console.log(err));
    }

    render() {
        let items = this.state.foods.map(item => (<HandleVote key={item} food={item} />))
        return(
            <div className="voteList">
                <ul id="voteList"> {items} </ul>
            </div>
        )
    }
}

class Popup extends React.Component {

    closeResponse() {
        setTimeout(this.props.stopResponse, 700);
    }

    render() {
        return(
        <div className="response" id="response">
            <div id="responseLogo">
                <SVG src={CheckMarkLogo} />
                {this.closeResponse()}
            </div>
        </div>
        )
    }
}

