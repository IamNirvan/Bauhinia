package com.nirvan.bauhinia.address;

import com.nirvan.bauhinia.exception.InvalidParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressRepository REPOSITORY;

    @Autowired
    public AddressService(AddressRepository repository) {
        REPOSITORY = repository;
    }

    public Address validateAddress(Address address) {

        // Validate the province
        if(address.getProvince().length() == 0) {
            throw new InvalidParameterException("Province with name ("+ address.getProvince() +") is invalid");
        }

        // Validate the city
        if(address.getCity().length() == 0) {
            throw new InvalidParameterException("City with name ("+ address.getCity() +") is invalid");
        }

        // Validate the street name
        if(address.getStreetName().length() == 0) {
            throw new InvalidParameterException("Street with name ("+ address.getStreetName() +") is invalid");
        }

        // Validate the building number
        if(address.getBuildingNumber().length() == 0) {
            throw new InvalidParameterException("Building number ("+ address.getStreetName() +") is invalid");
        }

        // Validate the postal code
        if(address.getPostalCode().length() == 0) {
            throw new InvalidParameterException("Postal code ("+ address.getStreetName() +") is invalid");
        }

        return address;
    }

}
