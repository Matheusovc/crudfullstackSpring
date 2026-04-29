package com.exemplo.crudmongo.controller;

import com.exemplo.crudmongo.Model.Professor;
import com.exemplo.crudmongo.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/professores")
public class ProfessorController {
    @Autowired
    private ProfessorService service;

    @GetMapping
    public List<Professor> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public Optional<Professor> getById(@PathVariable Long id) { return service.findById(id); }

    @PostMapping
    public Professor create(@RequestBody Professor professor) { return service.save(professor); }

    @PutMapping("/{id}")
    public Professor update(@PathVariable Long id, @RequestBody Professor professor) {
        professor.setId(id);
        return service.save(professor);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { service.deleteById(id); }
}