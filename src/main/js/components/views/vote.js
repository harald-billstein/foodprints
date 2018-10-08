import React from 'react';

export default class Vote extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            foods: [],
            vote: null,
            response: null};
        this.url = "https://localhost:8443/v1/categories/";
        this.postVoteUrl = "https://localhost:8443/v1/votes/";
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
            .then(response => {
                response.json();
                console.log(response);})
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
        return (
            <div className="voteList">
                <h1 id="headerName"> Unknown. </h1>
                <ul id="voteList">
                    {this.state.foods.map(food => (
                    <li id="li"><button id="button" onClick={() => { this.setState({vote: food}, () => this.postVote())}}> {food}. </button></li>))}
                </ul>
            </div>
        )
    }
}