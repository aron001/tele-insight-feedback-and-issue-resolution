package com.gebeya.ticketingservice.service;

import com.gebeya.ticketingservice.model.Address;

public interface AddressService {
    Address add(Address address);

    Address getById(int id);
}
