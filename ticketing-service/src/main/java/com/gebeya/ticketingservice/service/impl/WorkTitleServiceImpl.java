package com.gebeya.ticketingservice.service.impl;

import com.gebeya.ticketingservice.dto.requestDto.AddWorkTitleDto;
import com.gebeya.ticketingservice.model.WorkTitle;
import com.gebeya.ticketingservice.repository.WorkTitleRepository;
import com.gebeya.ticketingservice.service.WorkTitleService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class WorkTitleServiceImpl implements WorkTitleService {
    private final WorkTitleRepository workTitleRepository;

    public ResponseEntity<WorkTitle> add(AddWorkTitleDto workTitleDto) {

        WorkTitle newWorkTitle = new WorkTitle();
        newWorkTitle.setCreated_date(new Date().toInstant());
        newWorkTitle.setTitle_name(workTitleDto.getTitle_name());
        newWorkTitle.setDescription(workTitleDto.getDescription());
        newWorkTitle.setIs_active(true);
        newWorkTitle.setCreated_By(getUsername());
        return new ResponseEntity<>(save(newWorkTitle), HttpStatus.CREATED);

    }

    public ResponseEntity<List<WorkTitle>> getAll() {
        return new ResponseEntity<>(workTitleRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<WorkTitle> getById(int id) {
        Optional<WorkTitle> optionalWorkTitle = workTitleRepository.findById((long) id);
        if (optionalWorkTitle.isPresent()) {
            WorkTitle workTitle = optionalWorkTitle.get();
            return new ResponseEntity<>(workTitle, HttpStatus.OK);
        }
        return null;
    }
    public WorkTitle getById2(int id) {
        Optional<WorkTitle> optionalWorkTitle = workTitleRepository.findById((long) id);
        return optionalWorkTitle.orElse(null);
    }

    public ResponseEntity<WorkTitle> update(AddWorkTitleDto workTitleDto, int id) {

        if (isExisted(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Work title is not found");
        }
        Optional<WorkTitle> optionalWorkTitle = workTitleRepository.findById((long) (id));
        if (optionalWorkTitle.isPresent()) {
            WorkTitle existedWorkTitle = optionalWorkTitle.get();
            if (!workTitleDto.getTitle_name().isEmpty()) {
                existedWorkTitle.setTitle_name(workTitleDto.getTitle_name());
            }
            if (!workTitleDto.getDescription().isEmpty()) {
                existedWorkTitle.setDescription(workTitleDto.getDescription());
            }
            existedWorkTitle.setUpdated_at(new Date().toInstant());
            WorkTitle updatedWorkTitle = save(existedWorkTitle);
            return new ResponseEntity<>(updatedWorkTitle, HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Work title is not found");
    }
    private String getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public ResponseEntity<Boolean> delete(int id) {
        if (isExisted(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Work title is not found");
        }
        workTitleRepository.deleteById((long) id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    public boolean isExisted(int id) {
        return !workTitleRepository.existsById((long) id);
    }

    private WorkTitle save(WorkTitle workTitle) {
        return workTitleRepository.save(workTitle);
    }
}
