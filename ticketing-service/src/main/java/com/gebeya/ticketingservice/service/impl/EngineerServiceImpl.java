package com.gebeya.ticketingservice.service.impl;

import com.gebeya.ticketingservice.dto.requestDto.AddEngineerDto;
import com.gebeya.ticketingservice.enums.Authority;
import com.gebeya.ticketingservice.model.Address;
import com.gebeya.ticketingservice.model.Engineer;
import com.gebeya.ticketingservice.repository.EngineerRepository;
import com.gebeya.ticketingservice.service.AddressService;
import com.gebeya.ticketingservice.service.EngineerService;
import com.gebeya.ticketingservice.service.WorkTitleService;
import com.gebeya.ticketingservice.util.UserUtil;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
public class EngineerServiceImpl implements EngineerService {

    private final EngineerRepository engineerRepository;
    private final WorkTitleService workTitleService;
    private final UserUtil userUtil;
    private final AddressService addressService;

    public ResponseEntity<List<Engineer>> getAll() {
        return new ResponseEntity<>(engineerRepository.findAll(), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Engineer> add(AddEngineerDto addEngineerDto) {
        Engineer engineer = new Engineer();
        engineer.setFirst_name(addEngineerDto.getFirst_name());
        engineer.setMiddle_name(addEngineerDto.getMiddle_name());
        engineer.setLast_name(addEngineerDto.getLast_name());
        engineer.setGender(addEngineerDto.getGender());
        engineer.setDate_of_birth(addEngineerDto.getDate_of_birth());
        engineer.setWorkTitle(workTitleService.getById2(addEngineerDto.getWork_title_id()));
        engineer.setAddress(setAddress(addEngineerDto));
        engineer.setCreated_By(getUsername());
        engineer.getAddress().setCreated_date(new Date().toInstant());
        if (isEngineerExisted(engineer)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already Registered");
        }
        if (workTitleService.isExisted(Math.toIntExact(engineer.getWorkTitle().getId()))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The Work Title Is Not Registered.");
        }
        String authority = String.valueOf(Authority.ENGINEER);
        if(addEngineerDto.getIs_manager()){
            authority = String.valueOf(Authority.MANAGER);
        }

        engineer.setCreated_date(new Date().toInstant());
        engineer.setIs_active(true);
        Address address = addressService.add(engineer.getAddress());
        engineer.setAddress(addressService.getById(Math.toIntExact(address.getId())));
        Engineer saved_engineer = save(engineer);
        userUtil.createUser(saved_engineer.getFirst_name(),saved_engineer.getMiddle_name(), Math.toIntExact(saved_engineer.getId()), Authority.valueOf(authority),saved_engineer.getAddress().getEmail());
        return new ResponseEntity<>(saved_engineer, HttpStatus.CREATED);
    }
    private Address setAddress(AddEngineerDto dto)
    {
        Address address = new Address();
        address.setWoreda(dto.getWoreda());
        address.setCity(dto.getCity());
        address.setEmail(dto.getEmail());
        address.setPhoneNumber(dto.getPhoneNumber());
        address.setSubCity(dto.getSubCity());
        address.setPhoneNumber(dto.getPhoneNumber());
        address.setHouseNumber(dto.getHouseNumber());
        return address;
    }
    public ResponseEntity<Engineer> update(AddEngineerDto engineer, int id) {
        if (!isEngineerIdExisted(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Engineer ID is not found");
        }
        Optional<Engineer> optionalEngineer = engineerRepository.findById((long) id);
        if (optionalEngineer.isPresent()) {
            Engineer existedEngineer = optionalEngineer.get();
            if (engineer.getWork_title_id() != 0) {
                if (workTitleService.isExisted(Math.toIntExact(engineer.getWork_title_id()))) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Work Title Is Not Registered.");
                }
                existedEngineer.getWorkTitle().setId((long) engineer.getWork_title_id());
            }
            if (!engineer.getFirst_name().isEmpty()) {
                existedEngineer.setFirst_name(engineer.getFirst_name());
            }
            if (!engineer.getMiddle_name().isEmpty()) {
                existedEngineer.setMiddle_name(engineer.getMiddle_name());
            }
            if (!engineer.getLast_name().isEmpty()) {
                existedEngineer.setLast_name(engineer.getLast_name());
            }
            if (!engineer.getDate_of_birth().toString().isEmpty()) {
                existedEngineer.setDate_of_birth(engineer.getDate_of_birth());
            }
            if (!engineer.getGender().isEmpty()) {
                existedEngineer.setGender(engineer.getGender());
            }
            if (!engineer.getCountry().isEmpty()) {
                existedEngineer.getAddress().setCountry(engineer.getCountry());
            }
            if (!engineer.getCity().isEmpty()) {
                existedEngineer.getAddress().setCity(engineer.getCity());
            }
            if (engineer.getWork_title_id() != 0) {
                existedEngineer.getWorkTitle().setId((long) engineer.getWork_title_id());
            }
            if (!engineer.getEmail().isEmpty()) {
                existedEngineer.getAddress().setEmail(engineer.getEmail());
            }
            if (!engineer.getSubCity().isEmpty()) {
                existedEngineer.getAddress().setEmail(engineer.getSubCity());
            }
            if (!engineer.getWoreda().isEmpty()) {
                existedEngineer.getAddress().setWoreda(engineer.getWoreda());
            }
            if (!engineer.getHouseNumber().isEmpty()) {
                existedEngineer.getAddress().setHouseNumber(engineer.getHouseNumber());
            }
            if (!engineer.getPhoneNumber().isEmpty()) {
                existedEngineer.getAddress().setPhoneNumber(engineer.getPhoneNumber());
            }


            existedEngineer.setUpdated_at(new Date().toInstant());
            Engineer updatedClient = save(existedEngineer);

            return new ResponseEntity<>(updatedClient, HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Engineer Id is not found");
    }

    public boolean isEngineerIdExisted(int id) {
        return engineerRepository.existsById((long) id);
    }

    public boolean isEngineerExisted(int id) {
        return engineerRepository.existsById((long) id);
    }

    public Engineer getById(int id) {
        Optional<Engineer> optionalEngineer = engineerRepository.findById((long) id);
        return optionalEngineer.orElse(null);
    }
    private String getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    private boolean isEngineerExisted(Engineer engineer) {
        List<Engineer> engineerList = engineerRepository.findAll();
        boolean isExisted = false;
        for (Engineer existedEngineer : engineerList) {
            if (existedEngineer.getFirst_name().equalsIgnoreCase(engineer.getFirst_name()) &&
                    existedEngineer.getMiddle_name().equalsIgnoreCase(engineer.getMiddle_name()) &&
                    existedEngineer.getLast_name().equalsIgnoreCase(engineer.getLast_name())) {
                isExisted = true;
                break;
            }
        }
        return isExisted;
    }

    public Engineer save(Engineer engineer) {
        return engineerRepository.save(engineer);
    }

    public boolean delete(int id) {
        Optional<Engineer> optionalEngineer = engineerRepository.findById((long) id);
        if (optionalEngineer.isPresent()) {
            engineerRepository.delete(optionalEngineer.get());
            return true;
        }
        return false;
    }

    public List<Engineer> getAll(int pageNum, int pageSize, String sortBy) {
        Sort sort = Sort.by(sortBy).ascending();
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize, sort);
        Page<Engineer> engineerPage = engineerRepository.findAll(pageRequest);
        return engineerPage.getContent();
    }

}
