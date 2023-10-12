package com.ontology.tools.developertest.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class OntologyDto {
    @NotEmpty(message = "Ontology Id is required")
    private String ontologyId;
    @NotEmpty(message = "Title is required")
    private String title;
    @NotEmpty(message = "Description is required")
    private String description;
    private List<String> definitionProperties;
    private List<String> synonymProperties;
}
