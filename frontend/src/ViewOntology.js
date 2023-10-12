import React, { useEffect, useState } from 'react';
import { Button, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link, useParams } from 'react-router-dom';

function ViewOntology() {
    const { id } = useParams();
    const url = `/api/ontologies/${id}`;
    const [data, setData] = useState([]);
    
    const fetchInfo = () => {
        return fetch(url)
          .then((res) => res.json())
          .then((d) => setData(d))
      }
    
    
      useEffect(() => {
        fetchInfo();
      }, []);

      return (
        <div>
            <AppNavbar/>
            <Container fluid>
                <div className="float-end">
                <Button color="success" tag={Link} to="/ontologies/new">Add Ontology</Button>
                </div>
                <h1 className="mainListHeading">Ontology {id}</h1>
                <Table className="mt-4">
                <thead>
                <tr>
                    <th>Ontology Id</th>
                    <th>Title</th>
                    <th>Description</th>
                    <th>Definition Properties</th>
                    <th>Synonym Properties</th>
                </tr>
                </thead>
                <tbody>
                    <tr key={data.ontologyId}>
                    <td style={{whiteSpace: 'nowrap'}}>{data.ontologyId}</td>
                    <td>{data.title}</td>
                    <td>{data.description}</td>
                    <td>{data.definitionProperties}</td>
                    <td>{data.synonymProperties}</td>
                </tr>
                </tbody>
                </Table>
            </Container>
        </div>
      );
}

export default ViewOntology;