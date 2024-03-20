package com.gebeya.ticketingservice.service.impl;

import com.gebeya.ticketingservice.dto.NoteRequestDto;
import com.gebeya.ticketingservice.model.Note;
import com.gebeya.ticketingservice.repository.NoteRepository;
import com.gebeya.ticketingservice.service.*;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final TicketService ticketService;
    private final TicketStatusService ticketStatusService;
    private final EngineerServiceTicketService engineerServiceTicketService;
    private final WorkTypeService workTypeService;

    public List<Note> getNotes(int ticketId) {
        if (!ticketService.isTicketIdExisted(ticketId)) {
            throw new RuntimeException("Ticket Id is not found");
        }
        List<Note> allNotes = noteRepository.findAll();
        List<Note> ticketNote = new ArrayList<>();
        for (Note note : allNotes) {
            if (note.getTicket().getId() == ticketId) {
                ticketNote.add(note);
            }
        }
        return ticketNote;
    }

    public Note addNote(NoteRequestDto note) {
        if (!ticketService.isTicketIdExisted(Math.toIntExact(note.getTicketId()))) {
            throw new RuntimeException("Ticket Id is not found");
        }
        if (engineerServiceTicketService.isTicketIdExisted(Math.toIntExact(note.getEngineerServiceTicketId()))) {
            throw new RuntimeException("Engineer Service ticket Id not found.");
        }
        if (!workTypeService.isIdExisted(Math.toIntExact(note.getWorkTypeId()))) {
            throw new RuntimeException("Work Type Id is not found.");
        }
        Note newNote = new Note();

        newNote.setCreated_By(getUsername()); // will change in authentication
        newNote.setCreated_date(new Date().toInstant());
        newNote.setNote(note.getNote());
        newNote.setEngineerServiceTicket(engineerServiceTicketService.getByESTId2(note.getEngineerServiceTicketId()));
        // newNote.getEngineerServiceTicket().setId((long) note.getEngineerServiceTicketId());
        newNote.setTicket(ticketService.getById(note.getTicketId()));
        //newNote.getTicket().setId((long) note.getTicketId());
        newNote.setWorkType(workTypeService.getById2(note.getWorkTypeId()));
        //newNote.getWorkType().setId((long) note.getWorkTypeId());
        newNote.setDiscussion(note.getDiscussion());
        newNote.setInternal(note.getInternal());
        newNote.setResolved(note.getResolved());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
        LocalTime start_time = LocalTime.parse(note.getStart_time(), formatter);
        LocalTime end_time = LocalTime.parse(note.getEnd_time(), formatter);
        newNote.setStart_time(start_time);
        newNote.setEnd_time(end_time);

        if (newNote.getResolved()) {
            newNote.getTicket().setTicketStatus(ticketStatusService.getTicketStatusByName("resolved"));
            //call notification service
        }
        newNote.setActual_hours(calculateActualHour(newNote.getStart_time(), newNote.getEnd_time()));
        ticketService.update(newNote.getTicket());
        return save(newNote);
    }

    public Note update(NoteRequestDto note, int id) {
        if (!isIdExisted(id)) {
            throw new RuntimeException("Note Id is not found");
        }
        Optional<Note> optionalNote = noteRepository.findById((long) id);
        if (optionalNote.isPresent()) {
            Note existedNote = optionalNote.get();

            if (note.getNote() != null) {
                existedNote.setNote(note.getNote());
            }
            if (note.getStart_time() != null) {
                existedNote.setStart_time(LocalTime.parse(note.getStart_time()));
            }
            if (note.getEnd_time() != null) {
                existedNote.setEnd_time(LocalTime.parse(note.getEnd_time()));
            }
            if (note.getWorkTypeId() != 0) {
                existedNote.setWorkType(workTypeService.getById2(note.getWorkTypeId()));
            }
            if (note.getDiscussion() != null) {
                existedNote.setDiscussion(note.getDiscussion());
            }
            if (note.getInternal() != null) {
                existedNote.setInternal(note.getInternal());
            }
            if (note.getResolved() != null) {
                existedNote.setResolved(note.getResolved());
                existedNote.getTicket().setTicketStatus(ticketStatusService.getTicketStatusByName("resolved"));
            }
            existedNote.setUpdated_by(getUsername()); // will change
            existedNote.setUpdated_at(new Date().toInstant());
            ticketService.update(existedNote.getTicket());
            return save(existedNote);
        }
        throw new RuntimeException("Note Id is not found");
    }

    private static double calculateActualHour(LocalTime initialTime, LocalTime endTime) {
        LocalTime actualTime = initialTime.plusHours(endTime.getHour());
        return actualTime.getHour() + (actualTime.getMinute() / 60.0);
    }

    private Note save(Note note) {
        return noteRepository.save(note);
    }

    private boolean isIdExisted(int id) {
        return !noteRepository.existsById((long) id);
    }
    private String getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
