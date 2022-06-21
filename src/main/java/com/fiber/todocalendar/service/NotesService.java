package com.fiber.todocalendar.service;

import com.fiber.todocalendar.dto.NotesPatchRequest;
import com.fiber.todocalendar.dto.NotesRequest;
import com.fiber.todocalendar.model.Notes;

public interface NotesService {
    Notes getNotesByUserId(String userId);

    Notes patchNote(String userId, NotesPatchRequest notesPatchRequest);

    Notes putNotesOrder(String userId, NotesRequest notesRequest);
}
