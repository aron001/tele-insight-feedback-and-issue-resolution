package com.gebeya.ticketingservice.service;

import com.gebeya.ticketingservice.dto.requestDto.AddIssueTypeDto;
import com.gebeya.ticketingservice.model.IssueType;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IssueTypeService {
    ResponseEntity<List<IssueType>> getAll();
    ResponseEntity<IssueType> add(AddIssueTypeDto issueType);
    ResponseEntity<IssueType> update(AddIssueTypeDto issueType, int id);
    boolean delete(int id);
    boolean isIdExisted(int id);
    IssueType getIssueTypeById(int id);
}
