import React from 'react';
import './App.css';
import logo from './logo.svg';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';
import { Button, Container } from 'reactstrap';

const Home = () => {
  return (
    <div className="App">
      <AppNavbar/>
      {/* <Container fluid>
        <Button color="link"><Link to="/ontologies">Manage Ontologies</Link></Button>
      </Container> */}
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <a
          className="App-link"
          href="https://github.com/ktibrahim/ontology-tools-developer-test"
          target="_blank"
          rel="noopener noreferrer"
        >
          Ontology Tools Developer Test
        </a>
      </header>
    </div>
  );
}

export default Home;