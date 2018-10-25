import React from 'react';
import Popup from './popup';

export default class HandleVote extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            vote: null,
            showResponse: false
        }
        this.postVoteUrl =  "/v1/votes/";
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