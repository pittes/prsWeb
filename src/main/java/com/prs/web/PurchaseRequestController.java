package com.prs.web;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prs.business.purchaserequest.PurchaseRequest;
import com.prs.business.purchaserequest.PurchaseRequestRepository;
import com.prs.util.PRSMaintenanceReturn;

@CrossOrigin
@Controller
@RequestMapping(path="/PurchaseRequests")
public class PurchaseRequestController extends BaseController {
	@Autowired // This means to get the bean called purchaseRequestRepository
	           // Which is auto-generated by Spring, we will use it to handle the data
	private PurchaseRequestRepository purchaseRequestRepository;
	
	@GetMapping(path="/List")
	public @ResponseBody Iterable<PurchaseRequest> getAllPurchaseRequests() {
		return purchaseRequestRepository.findAll();
	}
	
	@GetMapping(path="/Get")
	public @ResponseBody List<PurchaseRequest> getPurchaseRequest(@RequestParam int id) {
		Optional<PurchaseRequest> pr = purchaseRequestRepository.findById(id);
		return getReturnArray(pr.get());
	}
	
	@PostMapping(path="/Add")
	public @ResponseBody PRSMaintenanceReturn addNewPurchaseRequest(@RequestBody PurchaseRequest purchaseRequest) {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		System.out.println("PurchaseRequestController - add method called");
		purchaseRequest.setSubmittedDate(ts);
		purchaseRequest.setStatus(PurchaseRequest.STATUS_NEW);
		try {
			purchaseRequestRepository.save(purchaseRequest);
			System.out.println("PurchaseRequest saved: " + PurchaseRequest.STATUS_NEW + " for " + purchaseRequest.getUser().getUserName());
			return PRSMaintenanceReturn.getMaintReturn(purchaseRequest);
		}
		catch (DataIntegrityViolationException dive) {
			return PRSMaintenanceReturn.getMaintReturnError(purchaseRequest, dive.getRootCause().toString());
		}
		catch (Exception e) {
			e.printStackTrace();
			return PRSMaintenanceReturn.getMaintReturnError(purchaseRequest, e.getMessage());
		}
	}
	
	@GetMapping(path="Remove")
	public @ResponseBody PRSMaintenanceReturn deletePurchaseRequest(@RequestParam int id) {
		Optional<PurchaseRequest> purchaseRequest = purchaseRequestRepository.findById(id);
		try {
			purchaseRequestRepository.delete(purchaseRequest.get());
			System.out.println("PurchaseRequest deleted: " + purchaseRequest.get());
			return PRSMaintenanceReturn.getMaintReturn(purchaseRequest.get());
		}
		catch (DataIntegrityViolationException dive) {
			return PRSMaintenanceReturn.getMaintReturnError(purchaseRequest, dive.getRootCause().toString());
		}
		catch (Exception e) {
			e.printStackTrace();
			return PRSMaintenanceReturn.getMaintReturnError(purchaseRequest, e.getMessage());
		}
	}
	
	@PostMapping(path="Change")
	public @ResponseBody PRSMaintenanceReturn updatePurchaseRequest(@RequestBody PurchaseRequest purchaseRequest) {
		try {
			purchaseRequestRepository.save(purchaseRequest);
			System.out.println("PurchaseRequest updated: " + purchaseRequest.getUser().getUserName() + " || " + purchaseRequest.getDescription());
			return PRSMaintenanceReturn.getMaintReturn(purchaseRequest);
		}
		catch (DataIntegrityViolationException dive) {
			return PRSMaintenanceReturn.getMaintReturnError(purchaseRequest, dive.getRootCause().toString());
		}
		catch (Exception e) {
			e.printStackTrace();
			return PRSMaintenanceReturn.getMaintReturnError(purchaseRequest, e.getMessage());
		}
	}
	
	@PostMapping(path="/SubmitForReview")
	public @ResponseBody PRSMaintenanceReturn submitForReview(@RequestBody PurchaseRequest pr) {
		try {
			if (pr.getTotal() <= 50) {
				pr.setStatus(PurchaseRequest.STATUS_APPROVED);
			} else {
				pr.setStatus(PurchaseRequest.STATUS_REVIEW);
			}
			pr.setSubmittedDate(new Timestamp(System.currentTimeMillis()));
			purchaseRequestRepository.save(pr);
			System.out.println("PurchaseRequest for Review: " + pr.getUser().getUserName() + " || " + pr.getDescription()
								+ " || " + pr.getStatus());
			return PRSMaintenanceReturn.getMaintReturn(pr);
		}
		catch (DataIntegrityViolationException dive) {
			return PRSMaintenanceReturn.getMaintReturnError(pr, dive.getRootCause().toString());
		}
		catch (Exception e) {
			e.printStackTrace();
			return PRSMaintenanceReturn.getMaintReturnError(pr, e.getMessage());
		}
	}
	
	// When listing PR's for Review, show all except those belonging to logged-in User
	@GetMapping(path="/ReviewRequests")
	public @ResponseBody List<PurchaseRequest> getReviewRequests(@RequestParam int id) {
		List<PurchaseRequest> pr = purchaseRequestRepository.findAllByUserIdNot(id);
		return pr;
	}
}