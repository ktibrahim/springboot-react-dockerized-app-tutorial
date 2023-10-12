package com.ontology.tools.developertest;

import com.ontology.tools.developertest.controller.OntologyController;
import com.ontology.tools.developertest.service.OntologyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DeveloperTestApplicationTests {

	@Autowired
	private OntologyController ontologyController;

	@Autowired
	private OntologyService ontologyService;

	@Test
	public void contextLoads() throws Exception {
		assertThat(ontologyService).isNotNull();
		assertThat(ontologyController).isNotNull();
	}

}
