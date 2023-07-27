package com.nirvan.bauhinia.address;

import com.nirvan.bauhinia.customer.Customer;
import com.nirvan.bauhinia.customer.CustomerServiceV2;
import com.nirvan.bauhinia.exception.AddressNotFoundException;
import com.nirvan.bauhinia.exception.InvalidAddressTypeException;
import com.nirvan.bauhinia.exception.InvalidParameterException;
import com.nirvan.bauhinia.utility.Validation;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceV2 {
    private final AddressRepository ADDRESS_REPOSITORY;
    private final CustomerServiceV2 CUSTOMER_SERVICE;
    private final Validation VALIDATION;
    private static final String ID_NOT_FOUND_MESSAGE = "Address with the following id does not exist:  %s";
    private static final String INVALID_PROVINCE_MESSAGE = "Province is invalid: %s";
    private static final String INVALID_CITY_MESSAGE = "City is invalid: %s";
    private static final String INVALID_STREET_NAME_MESSAGE = "Street name is invalid: %s";
    private static final String INVALID_BUILDING_NUMBER_MESSAGE = "Building number is invalid: %s";
    private static final String INVALID_POSTAL_CODE_MESSAGE = "Postal code is invalid: %s";

    public Address selectById(int addressId) {
        return ADDRESS_REPOSITORY.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException(String.format(ID_NOT_FOUND_MESSAGE, addressId)));
    }

    public boolean addAddress(
            @NotNull Address address,
            @NotNull int customerId
    ) throws InvalidParameterException {
        final String PROVINCE = address.getProvince();
        final String CITY = address.getCity();
        final String STREET_NAME = address.getStreetName();
        final String BUILDING_NUMBER = address.getBuildingNumber();
        final String POSTAL_CODE = address.getPostalCode();
        final AddressType ADDRESS_TYPE = address.getAddressType();
        //
        // Validate the province
        //
        if(!VALIDATION.validNonNullAndNonBlankParam(PROVINCE)) {
            throw new InvalidParameterException(String.format(INVALID_PROVINCE_MESSAGE, PROVINCE));
        }
        //
        // Validate the city
        //
        if(!VALIDATION.validNonNullAndNonBlankParam(CITY)) {
            throw new InvalidParameterException(String.format(INVALID_CITY_MESSAGE, CITY));
        }
        //
        // Validate the street name
        //
        if(!VALIDATION.validNonNullAndNonBlankParam(STREET_NAME)) {
            throw new InvalidParameterException(String.format(INVALID_STREET_NAME_MESSAGE, STREET_NAME));
        }
        //
        // Validate the building number
        //
        if(!VALIDATION.validNonNullAndNonBlankParam(BUILDING_NUMBER)) {
            throw new InvalidParameterException(String.format(INVALID_BUILDING_NUMBER_MESSAGE, BUILDING_NUMBER));
        }
        //
        // Validate the postal code
        //
        if(!VALIDATION.validNonNullAndNonBlankParam(POSTAL_CODE)) {
            throw new InvalidParameterException(String.format(INVALID_POSTAL_CODE_MESSAGE, POSTAL_CODE));
        }
        //
        // Validate the address type
        //
        if(!ADDRESS_TYPE.toString().toUpperCase().equals(AddressType.BILLING.toString()) &&
                !ADDRESS_TYPE.toString().toUpperCase().equals(AddressType.SHIPPING.toString())) {
            throw new InvalidAddressTypeException(String.format("Address type is invalid: %s", ADDRESS_TYPE));
        }
        //
        // Set up association between customer and address
        //
        Customer customer = CUSTOMER_SERVICE.fetchCustomerById(customerId);
        setAssociation(address, customer);
        CUSTOMER_SERVICE.saveCustomer(customer);
        ADDRESS_REPOSITORY.save(address);
        return true;
    }

    private void setAssociation(Address address, Customer customer) {
        List<Address> addressList = customer.getAddresses();
        addressList.add(address);
        customer.setAddresses(addressList);
        address.setCustomer(customer);
    }

    @Transactional
    public boolean updateAddress(
            int addressId,
            String province,
            String city,
            String streetName,
            String buildingNumber,
            String postalCode
    ) throws InvalidParameterException {
        final Address persistedAddress = selectById(addressId);
        //
        // Validate the province
        //
        if(province != null) {
            if(!VALIDATION.validNonBlankParam(province)) {
                throw new InvalidParameterException(String.format(INVALID_PROVINCE_MESSAGE, province));
            }
            persistedAddress.setProvince(province);
        }
        //
        // Validate the city
        //
        if(city != null) {
            if(!VALIDATION.validNonBlankParam(city)) {
                throw new InvalidParameterException(String.format(INVALID_CITY_MESSAGE, city));
            }
            persistedAddress.setCity(city);
        }
        //
        // Validate the street name
        //
        if(streetName != null) {
            if(!VALIDATION.validNonBlankParam(streetName)) {
                throw new InvalidParameterException(String.format(INVALID_STREET_NAME_MESSAGE, streetName));
            }
            persistedAddress.setStreetName(streetName);
        }
        //
        // Validate the building number
        //
        if(buildingNumber != null) {
            if(!VALIDATION.validNonBlankParam(buildingNumber)) {
                throw new InvalidParameterException(String.format(INVALID_BUILDING_NUMBER_MESSAGE, buildingNumber));
            }
            persistedAddress.setBuildingNumber(buildingNumber);
        }
        //
        // Validate the postal code
        //
        if(postalCode != null) {
            if(!VALIDATION.validNonBlankParam(postalCode)) {
                throw new InvalidParameterException(String.format(INVALID_POSTAL_CODE_MESSAGE, postalCode));
            }
            persistedAddress.setPostalCode(postalCode);
        }
        ADDRESS_REPOSITORY.save(persistedAddress);
        return true;
    }

    public boolean deleteAddress(int addressId) throws AddressNotFoundException {
        boolean exists = ADDRESS_REPOSITORY.existsById(addressId);
        if(!exists) {
            throw new AddressNotFoundException(String.format(ID_NOT_FOUND_MESSAGE, addressId));
        }
        ADDRESS_REPOSITORY.deleteById(addressId);
        return true;
    }
}
