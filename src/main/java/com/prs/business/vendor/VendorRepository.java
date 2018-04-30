package com.prs.business.vendor;

import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called vendorRepository
// CRUD refers Create, Read, Update, Delete

public interface VendorRepository extends CrudRepository<Vendor, Integer> {

}