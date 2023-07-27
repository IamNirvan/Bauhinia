package com.nirvan.bauhinia.orderAddress;

import com.nirvan.bauhinia.address.Address;
import com.nirvan.bauhinia.exception.AddressNotFoundException;
import com.nirvan.bauhinia.utility.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderAddressService {
    private final OrderAddressRepository ADDRESS_REPOSITORY;
    private final Validation VALIDATION;
    private static final String ID_NOT_FOUND_MESSAGE = "Address with the following id does not exist:  %s";
    private static final String INVALID_PROVINCE_MESSAGE = "Province is invalid: %s";
    private static final String INVALID_CITY_MESSAGE = "City is invalid: %s";
    private static final String INVALID_STREET_NAME_MESSAGE = "Street name is invalid: %s";
    private static final String INVALID_BUILDING_NUMBER_MESSAGE = "Building number is invalid: %s";
    private static final String INVALID_POSTAL_CODE_MESSAGE = "Postal code is invalid: %s";

    public OrderAddress fetchAddressById(int addressId) {
        return ADDRESS_REPOSITORY.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException(String.format(ID_NOT_FOUND_MESSAGE, addressId)));
    }


    public OrderAddress save(OrderAddress address) {
        return ADDRESS_REPOSITORY.save(address);
    }
}
