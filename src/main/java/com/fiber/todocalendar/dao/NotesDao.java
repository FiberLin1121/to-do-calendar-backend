package com.fiber.todocalendar.dao;

import com.fiber.todocalendar.dto.NotesPatchRequest;
import com.fiber.todocalendar.dto.NotesRequest;
import com.fiber.todocalendar.model.Notes;

public interface NotesDao {
    Notes getNotesByUserId(String userId);

    Notes addNote(String userId, NotesPatchRequest notesPatchRequest);

    Notes replaceNote(String userId, NotesPatchRequest notesPatchRequest);

    Notes removeNote(String userId, NotesPatchRequest notesPatchRequest);

    Notes replaceNotes(String userId, NotesRequest notesRequest);
}
