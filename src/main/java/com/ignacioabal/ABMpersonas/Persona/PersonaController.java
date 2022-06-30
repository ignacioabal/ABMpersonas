package com.ignacioabal.ABMpersonas.Persona;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("")
public class PersonaController {

    @Autowired
    public PersonaService personaService;

    @GetMapping("/{id}")
    public ResponseEntity<Persona> getPersona(@PathVariable long id){
        return personaService.getPersona(id);
    }

    @PostMapping
    public ResponseEntity<Persona> createPersona(@RequestBody Persona persona){
        return personaService.createPersona(persona);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Persona> deletePersona(@PathVariable long id){
        return personaService.deletePersona(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persona> modifyPersona(@PathVariable long id,@RequestBody Persona persona){
        return personaService.modifyPersona(id,persona);
    }
}
