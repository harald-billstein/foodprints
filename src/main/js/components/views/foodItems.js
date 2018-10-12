import React from 'react';
import HandleVote from './handleVote';

export default class FoodItems extends React.Component {
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