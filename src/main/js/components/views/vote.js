import React from 'react';

export default class Vote extends React.Component {

    constructor(props) {
        super(props);
        this.state = {foods: []};
        this.url = "https://localhost:8443/v1/categories/";
    }

    postVote(e) {
        e.preventDefault();
        // fetch me post ba till den andra
        // måste göra om listan till en form så man kan skicka input härifrån
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
                    <li id="li"> {food}. </li>))}
                </ul>
            </div>
        )
    }
}