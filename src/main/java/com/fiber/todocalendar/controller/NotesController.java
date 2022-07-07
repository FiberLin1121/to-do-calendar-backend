package com.fiber.todocalendar.controller;

import com.fiber.todocalendar.dto.NotesPatchRequest;
import com.fiber.todocalendar.dto.NotesRequest;
import com.fiber.todocalendar.model.Notes;
import com.fiber.todocalendar.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class NotesController {

    @Autowired
    private NotesService notesService;

//    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/users/{userId}/notes")
    public ResponseEntity<Notes> getNotes(@PathVariable String userId) {
        Notes notes = notesService.getNotesByUserId(userId);
        if (notes != null) {
            return ResponseEntity.status(HttpStatus.OK).body(notes);
        } else {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    @PatchMapping("/users/{userId}/notes")
    public ResponseEntity<Notes> patchNotes(@PathVariable String userId, @RequestBody NotesPatchRequest notesPatchRequest) {
        Notes notes = notesService.patchNote(userId, notesPatchRequest);
        return ResponseEntity.status(HttpStatus.OK).body(notes);
    }

    @PutMapping("/users/{userId}/notes")
    public ResponseEntity<Notes> putNotesOrder(@PathVariable String userId, @RequestBody @Valid NotesRequest notesRequest) {
        Notes notes = notesService.putNotesOrder(userId, notesRequest);
        return ResponseEntity.status(HttpStatus.OK).body(notes);
    }
}
