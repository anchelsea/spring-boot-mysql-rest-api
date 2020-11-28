package com.example.easynotes.controller;

import com.example.easynotes.exception.ResourceNotException;
import com.example.easynotes.model.Note;
import com.example.easynotes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class NoteController {
    @Autowired
    NoteRepository noteRepository;

    // GET all Notes
    @GetMapping("/notes")
    public List<Note> getAllNotes(){
        return noteRepository.findAll();
    }
    // Create a new Note
    @PostMapping("/notes")
    public Note createNote(@Valid @RequestBody Note note){
        return  noteRepository.save(note);
    }

    // Get a Single Note
    @GetMapping("/notes/{id}")
    public  Note getNoteById(@PathVariable(value = "id") Long noteId){
        return noteRepository.findById(noteId).orElseThrow(()->new ResourceNotException("Note","id",noteId));
    }

    // Update a Note
    @PutMapping("/notes/{id}")
    public  Note updateNote(@PathVariable(value = "id") Long noteId,
                            @Valid @RequestBody Note noteDetails){
        Note note = noteRepository.findById(noteId)
                .orElseThrow(()->new ResourceNotException("Note","id",noteId));
        note.setTitle(noteDetails.getTitle());
        note.setContent((noteDetails.getContent()));
        Note updateNote=noteRepository.save(note);
        return  updateNote;
    }

    // Delete a Note
    @DeleteMapping("/notes/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long noteId){
        Note note=noteRepository.findById(noteId)
                .orElseThrow(()->new ResourceNotException("Note","Id",noteId));
        noteRepository.delete(note);
        return  ResponseEntity.ok().build();
    }
}
