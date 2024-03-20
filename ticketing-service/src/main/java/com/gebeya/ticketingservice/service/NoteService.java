package com.gebeya.ticketingservice.service;

import com.gebeya.ticketingservice.dto.NoteRequestDto;
import com.gebeya.ticketingservice.model.Note;

import java.util.List;

public interface NoteService {
    List<Note> getNotes(int ticketId);
    Note addNote(NoteRequestDto note);
    Note update(NoteRequestDto note, int id);
}
