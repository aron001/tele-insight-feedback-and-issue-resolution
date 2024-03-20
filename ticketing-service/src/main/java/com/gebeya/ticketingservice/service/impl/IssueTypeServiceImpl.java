package com.gebeya.ticketingservice.service.impl;

import com.gebeya.ticketingservice.dto.requestDto.AddIssueTypeDto;
import com.gebeya.ticketingservice.model.IssueType;
import com.gebeya.ticketingservice.repository.IssueTypeRepository;
import com.gebeya.ticketingservice.service.IssueTypeService;
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
public class IssueTypeServiceImpl implements IssueTypeService {

    private final IssueTypeRepository issueTypeRepository;

    public ResponseEntity<List<IssueType>> getAll() {
        return new ResponseEntity<>(issueTypeRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<IssueType> add(AddIssueTypeDto dto) {
            IssueType issueType = new IssueType();
            issueType.setName(dto.getName());
            issueType.setDescription(dto.getDescription());
            issueType.setIs_active(true);
        if (isExisted(issueType)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Issue Type is already registered");
        }
        issueType.setCreated_By("admin"); //will do after authentication
        issueType.setCreated_date(new Date().toInstant());
        IssueType savedIssueType = save(issueType);
        return new ResponseEntity<>(savedIssueType, HttpStatus.CREATED);
    }

    public ResponseEntity<IssueType> update(AddIssueTypeDto issueType, int id) {
        if (isIdExisted(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Issue Type Id is not found");
        }
        Optional<IssueType> optionalIssueType = issueTypeRepository.findById((long) id);
        if (optionalIssueType.isPresent()) {
            IssueType existedIssueType = optionalIssueType.get();

            if (!issueType.getName().isEmpty()) {
                existedIssueType.setName(issueType.getName());
            }
            if (!issueType.getDescription().isEmpty()) {
                existedIssueType.setDescription(issueType.getDescription());
            }
            existedIssueType.setUpdated_at(new Date().toInstant());
            existedIssueType.setUpdated_by("admin"); //will change to the actual user
            existedIssueType = save(existedIssueType);

            return new ResponseEntity<>(existedIssueType, HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Issue Type Id is not found");
    }

    public boolean delete(int id) {
        if (isIdExisted(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Issue Type Id is not found");
        }
        issueTypeRepository.deleteById((long) id);
        return true;
    }

    private IssueType save(IssueType issueType) {
        return issueTypeRepository.save(issueType);
    }

    private boolean isExisted(IssueType issueType) {
        List<IssueType> issueTypeList = issueTypeRepository.findAll();
        boolean isExisted = false;
        for (IssueType issueType1 : issueTypeList) {
            if (issueType1.getName().equalsIgnoreCase(issueType.getName())) {
                isExisted = true;
                break;
            }
        }
        return isExisted;
    }

    public IssueType getIssueTypeById(int id) {
        Optional<IssueType> issueType = issueTypeRepository.findById((long) id);
        return issueType.orElse(null);
    }

    public boolean isIdExisted(int id) {
        return !issueTypeRepository.existsById((long) id);
    }
}
