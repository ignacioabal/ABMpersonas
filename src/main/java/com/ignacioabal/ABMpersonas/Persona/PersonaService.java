package com.ignacioabal.ABMpersonas.Persona;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class PersonaService {

    @Autowired
    public PersonaRepository personaRepository;

    public ResponseEntity<Persona> getPersona(Long id) {

        Optional<Persona> foundPersona = personaRepository.findById(id);

        if (!foundPersona.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(foundPersona.get(), HttpStatus.OK);
    }

    public ResponseEntity<Persona> createPersona(Persona persona) {

        if(personaRepository.existsByDni(persona.getDni())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Ya existe Persona con ese DNI.");
        }

        Persona savedPersona = personaRepository.save(persona);

        return new ResponseEntity<>(savedPersona,HttpStatus.CREATED);
    }

    public ResponseEntity<Persona> deletePersona(Long id) {
        if(!personaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        personaRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Persona> modifyPersona(Long id, Persona persona) {
        if(!personaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }


        persona.setId(id);
        personaRepository.save(persona);

        return new ResponseEntity<>(persona,HttpStatus.OK);
    }


}
