package com.prs.business.purchaserequest;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called purchaseRequestRepository
// CRUD refers Create, Read, Update, Delete

public interface PurchaseRequestRepository extends CrudRepository<PurchaseRequest, Integer> {
	List<PurchaseRequest> findAllByUserIdNotAndStatus(int id, String status);

}