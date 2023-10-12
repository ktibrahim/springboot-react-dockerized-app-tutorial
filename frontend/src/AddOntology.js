import React, { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';

const AddOntology = () => {
  const initialFormState = {
    ontologyId: '',
    title: '',
    description: '',
    definitionProperties: [],
    synonymProperties: []
  };
  const setForm = {
    definitionProperties: [],
    synonymProperties: []
  };
  const [ontology, setOntology] = useState(initialFormState);
  const navigate = useNavigate();
  const { id } = useParams();


  const handleChange = (event) => {
    const { name, value } = event.target
    var currentVal = value
    if(name === 'definitionProperties') {
        var values=value.split(",");
        setForm.definitionProperties=values
        currentVal=setForm.definitionProperties
    }
    if(name === 'synonymProperties') {
        var values=value.split(",");
        setForm.synonymProperties=values
        currentVal=setForm.synonymProperties
    }
    setOntology({ ...ontology, [name]: currentVal })
  }

  const handleSubmit = async (event) => {
    event.preventDefault();

    await fetch(`/api/ontologies`, {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(ontology)
    });
    setOntology(initialFormState);
    navigate('/ontologies');
  }

  const title = <h2>Add Ontologies</h2>;

  return (<div>
      <AppNavbar/>
      <Container>
        {title}
        <Form onSubmit={handleSubmit}>
          <FormGroup>
            <Label for="ontologyId">Ontology ID</Label>
            <Input type="text" name="ontologyId" id="ontologyId" value={ontology.ontologyId}
                   onChange={handleChange} autoComplete="ontologyId" required/>
          </FormGroup>
          <FormGroup>
            <Label for="title">Title</Label>
            <Input type="text" name="title" id="title" value={ontology.title}
                   onChange={handleChange} autoComplete="title" required/>
          </FormGroup>
          <FormGroup>
            <Label for="description">Description</Label>
            <Input type="text" name="description" id="description" value={ontology.description}
                   onChange={handleChange} autoComplete="description" required/>
          </FormGroup>
          <FormGroup>
            <Label for="definitionProperties">Definition Properties</Label>
            <Input type="text" name="definitionProperties" id="definitionProperties" value={ontology.definitionProperties}
                   onChange={handleChange} autoComplete="definitionProperties" placeholder='IAO_0000115, definition' />
          </FormGroup>
          <FormGroup>
            <Label for="synonymProperties">Synonym Properties</Label>
            <Input type="text" name="synonymProperties" id="synonymProperties" value={ontology.synonymProperties}
                   onChange={handleChange} autoComplete="synonymProperties" placeholder='alternative_term, hasExactSynonym' />
          </FormGroup>
          <FormGroup>
            <Button color="primary" type="submit">Save</Button>{' '}
            <Button color="secondary" tag={Link} to="/ontologies">Cancel</Button>
          </FormGroup>
        </Form>
      </Container>
    </div>
  )
};

export default AddOntology;