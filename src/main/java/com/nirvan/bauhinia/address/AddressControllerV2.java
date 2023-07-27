package com.nirvan.bauhinia.address;

import com.nirvan.bauhinia.customer.CustomerServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/addresses")
@RequiredArgsConstructor
@CrossOrigin
public class AddressControllerV2 {
    private final AddressServiceV2 ADDRESS_SERVICE;
    private final CustomerServiceV2 CUSTOMER_SERVICE;

    /**
     * Provides information regarding a specific address
     * @param addressId the id of the address
     * */
    @GetMapping("/find")
    public ResponseEntity<Address> fetchAddressById(@RequestParam("id") int addressId) {
        return new ResponseEntity<>(ADDRESS_SERVICE.fetchAddressById(addressId), HttpStatus.OK);
    }

    /**
     * Provides information regarding the address(s) of a particular customer
     * @param customerId the id of the customer
     * */
    @GetMapping("/find/customer/{id}")
    public ResponseEntity<List<Address>> fetchAddressByCustomer(@PathVariable("id") int customerId) {
        return new ResponseEntity<>(CUSTOMER_SERVICE.fetchCustomerById(customerId).getAddresses(), HttpStatus.OK);
    }

    /**
     * Use this to add a new address and assign ownership of the address to a customer
     * @param address the new address to be added
     * @param customerId the id of the customer who owns the address
     * */
    @PostMapping("/customer/{customerId}")
    public ResponseEntity<Boolean> addAddress(
            @RequestBody Address address,
            @PathVariable("customerId") int customerId
    ) {
        return new ResponseEntity<>(ADDRESS_SERVICE.addAddress(address, customerId), HttpStatus.CREATED);
    }

    /**
     * Use this to update each parameter of an address individually or in unison.
     * @param addressId the id of the address that needs to be updated
     * @param province the new province name. Optional
     * @param city the new city name. Optional
     * @param streetName the new street name. Optional
     * @param buildingNumber the new building number. Optional
     * @param postalCode the new postal code. Optional
     * */
    @PutMapping
    public ResponseEntity<Boolean> updateAddress(
            @RequestParam("id") int addressId,
            @RequestParam(value = "province", required = false) String province,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "street", required = false) String streetName,
            @RequestParam(value = "building", required = false) String buildingNumber,
            @RequestParam(value = "postal", required = false) String postalCode
    ) {
        return new ResponseEntity<>(
                ADDRESS_SERVICE.updateAddress(addressId,province,city,streetName,buildingNumber,postalCode),
                HttpStatus.OK
        );
    }

    /**
     * Use this to delete an existing address
     * @param addressId the id of an existing address
     * */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteAddress(@RequestParam("id") int addressId) {
        return new ResponseEntity<>(ADDRESS_SERVICE.deleteAddress(addressId), HttpStatus.OK);
    }
}
