const React = require('react');
const ReactDOM = require('react-dom')

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {foods: []};
        this.url = "https://localhost:8443/v1/categories/";
    }

    componentDidMount() {
        fetch(this.url)
            .then(response => response.text())
            .then(data => {
                this.setState({foods: data})})
            .catch(err => console.log(err));
    }

    render() {
        return (
            <div>
                <h1> Unknown. </h1>
                <p> { this.state.foods } </p>
            </div>
        )
    }
}

ReactDOM.render(
    <App />,
    document.getElementById('react')
)