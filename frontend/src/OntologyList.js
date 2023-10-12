import React, { useEffect, useState } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

const OntologyList = () => {

    const [ontologies, setOntologies] = useState([]);
    const [loading, setLoading] = useState(false);

  useEffect(() => {
    setLoading(true);

    fetch('api/ontologies')
      .then(response => response.json())
      .then(data => {
        setOntologies(data);
        setLoading(false);
      })
  }, []);

  if (loading) {
    return <p>Loading...</p>;
  }

  const ontologyList = ontologies.map(ont => {
    return <tr key={ont.id}>
      <td style={{whiteSpace: 'nowrap'}}>{ont.ontologyId}</td>
      <td>{ont.title}</td>
      <td>{ont.description}</td>
      <td>{ont.definitionProperties.map(txt => <p>{txt}</p>)}</td>
      <td>{ont.synonymProperties.map(txt => <p>{txt}</p>)}</td>
      <td>
        <ButtonGroup>
          <Button size="sm" color="primary" tag={Link} to={"/ontologies/" + ont.ontologyId}>View</Button>
        </ButtonGroup>
      </td>
    </tr>
  });

  return (
    <div>
      <AppNavbar/>
      <Container fluid>
        <div className="float-end">
          <Button color="success" tag={Link} to="/ontologies/new">Add Ontology</Button>
        </div>
        <h1 className="mainListHeading">Ontologies</h1>
        <Table className="mt-4">
          <thead>
          <tr>
            <th width="10%">Ontology Id</th>
            <th>Title</th>
            <th>Description</th>
            <th>Definition Properties</th>
            <th>Synonym Properties</th>
          </tr>
          </thead>
          <tbody>
          {ontologyList}
          </tbody>
        </Table>
      </Container>
    </div>
  );
};

export default OntologyList;