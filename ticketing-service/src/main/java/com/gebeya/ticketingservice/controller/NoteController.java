package com.gebeya.ticketingservice.controller;

import com.gebeya.ticketingservice.dto.NoteRequestDto;
import com.gebeya.ticketingservice.model.Note;
import com.gebeya.ticketingservice.service.NoteService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/v1/ticketing/note")
public class NoteController {

    private final NoteService noteService;

    @GetMapping("/{id}")
    public List<Note> getTicketNote(@PathVariable(value = "id") int id) {
        return noteService.getNotes(id);
    }

    @PostMapping
    public Note addNote(@RequestBody NoteRequestDto note) {

        return noteService.addNote(note);
    }

    @PutMapping("/{id}")
    public Note update(@RequestBody NoteRequestDto note, @PathVariable(value = "id") int id) {
        return noteService.update(note, id);
    }

}

