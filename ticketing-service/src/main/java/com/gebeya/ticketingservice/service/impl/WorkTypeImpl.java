package com.gebeya.ticketingservice.service.impl;

import com.gebeya.ticketingservice.dto.requestDto.AddWorkTypeDto;
import com.gebeya.ticketingservice.model.WorkType;
import com.gebeya.ticketingservice.repository.WorkTypeRepository;
import com.gebeya.ticketingservice.service.WorkTypeService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class WorkTypeImpl implements WorkTypeService {

    private final WorkTypeRepository workTypeRepository;

    public ResponseEntity<List<WorkType>> getAll() {
        return new ResponseEntity<>(workTypeRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<WorkType> getById(int id) {
        Optional<WorkType> optionalWorkType = workTypeRepository.findById((long) id);
        return optionalWorkType.map(workType -> new ResponseEntity<>(workType, HttpStatus.OK)).orElse(null);
    }

    public WorkType getById2(int id) {
        Optional<WorkType> optionalWorkType = workTypeRepository.findById((long) id);
        return optionalWorkType.orElse(null);
    }


    public ResponseEntity<WorkType> add(AddWorkTypeDto dto) {
        WorkType workType = new WorkType();
        workType.setName(dto.getName());
        workType.setDescription(dto.getDescription());
        if (isExisted(workType)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Work Type is already registered");
        }
        workType.setCreated_By("admin"); //will do after authentication
        workType.setCreated_date(new Date().toInstant());
        workType.setIs_active(true);
        WorkType savedWorkType = save(workType);
        return new ResponseEntity<>(savedWorkType, HttpStatus.CREATED);
    }

    public ResponseEntity<WorkType> update(AddWorkTypeDto dto, int id) {
        WorkType workType = new WorkType();
        workType.setName(dto.getName());
        workType.setDescription(dto.getDescription());
        if (isIdExisted(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Work Type Id is not found");
        }
        Optional<WorkType> optionalWorkType = workTypeRepository.findById((long) id);
        if (optionalWorkType.isPresent()) {

            WorkType existedWorkType = optionalWorkType.get();
            if (!workType.getName().isEmpty()) {
                existedWorkType.setName(workType.getName());
            }
            if (!workType.getDescription().isEmpty()) {
                existedWorkType.setDescription(workType.getDescription());
            }
            existedWorkType.setUpdated_at(new Date().toInstant());
            existedWorkType.setUpdated_by("admin"); //will change to the actual user
            existedWorkType = save(existedWorkType);

            return new ResponseEntity<>(existedWorkType, HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Work Type Id is not found");
    }

    public boolean delete(int id) {
        if (isIdExisted(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Work Type Id is not found");
        }
        workTypeRepository.deleteById((long) id);
        return true;
    }

    private WorkType save(WorkType workType) {
        return workTypeRepository.save(workType);
    }

    private boolean isExisted(WorkType workType) {
        boolean isExisted = false;
        List<WorkType> workTypeList = workTypeRepository.findAll();
        if (workTypeList.isEmpty()) {
            return isExisted;
        }

        for (WorkType type : workTypeList) {
            if (type.getName().equalsIgnoreCase(workType.getName())) {
                isExisted = true;
                break;
            }
        }
        return isExisted;
    }

    public boolean isIdExisted(int id) {
        return workTypeRepository.existsById((long) id);
    }
}
