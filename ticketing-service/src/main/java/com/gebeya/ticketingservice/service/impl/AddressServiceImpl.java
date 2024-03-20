package com.gebeya.ticketingservice.service.impl;

import com.gebeya.ticketingservice.model.Address;
import com.gebeya.ticketingservice.repository.AddressRepository;
import com.gebeya.ticketingservice.service.AddressService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    public Address add(Address address) {
        return addressRepository.save(address);
    }

    public Address getById(int id) {
        Optional<Address> optionalAddress = addressRepository.findById((long) id);
        return optionalAddress.orElse(null);
    }
}
