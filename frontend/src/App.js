import React from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import OntologyList from './OntologyList.js';
import AddOntology from './AddOntology';
import ViewOntology from './ViewOntology'

const App = () => {
  return (
    <Router>
      <Routes>
        <Route exact path="/" element={<Home/>}/>
        <Route path='/ontologies' exact={true} element={<OntologyList/>}/>
        <Route path='/ontologies/:id' element={<ViewOntology/>}/>
        <Route path='/ontologies/new' element={<AddOntology/>}/>
      </Routes>
    </Router>
  )
}

export default App;