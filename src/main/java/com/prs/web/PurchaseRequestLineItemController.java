package com.prs.web;

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
import com.prs.business.purchaserequest.PurchaseRequestLineItem;
import com.prs.business.purchaserequest.PurchaseRequestLineItemRepository;
import com.prs.business.purchaserequest.PurchaseRequestRepository;
import com.prs.util.PRSMaintenanceReturn;

@CrossOrigin
@Controller
@RequestMapping(path="/PurchaseRequestLineItems")
public class PurchaseRequestLineItemController extends BaseController {
	@Autowired // This means to get the bean called purchaseRequestLineItemRepository
	           // Which is auto-generated by Spring, we will use it to handle the data
	private PurchaseRequestLineItemRepository prliRepository;
	
	@Autowired
	private PurchaseRequestRepository prRepository;

	@GetMapping(path="/List")
	public @ResponseBody Iterable<PurchaseRequestLineItem> getAllPurchaseRequestLineItems() {
		return prliRepository.findAll();
	}
	
	@GetMapping(path="/Get")
	public @ResponseBody List<PurchaseRequestLineItem> getPurchaseRequestLineItem(@RequestParam int id) {
		Optional<PurchaseRequestLineItem> prli = prliRepository.findById(id);
		return getReturnArray(prli);
	}
	
	@PostMapping(path="/Add")
	public @ResponseBody PRSMaintenanceReturn addNewPurchaseRequestLineItem(@RequestBody PurchaseRequestLineItem prli) {
		System.out.println("PurchaseRequestLineItemController - add method called");
		try {
			prliRepository.save(prli);
			System.out.println("PurchaseRequestLineItem saved for PurchaseRequestID=" + prli.getPurchaseRequest().getId());
			updatePRTotal(prli);
			return PRSMaintenanceReturn.getMaintReturn(prli);
		}
		catch (DataIntegrityViolationException dive) {
			return PRSMaintenanceReturn.getMaintReturnError(prli, dive.getRootCause().toString());
		}
		catch (Exception e) {
			e.printStackTrace();
			return PRSMaintenanceReturn.getMaintReturnError(prli, e.getMessage());
		}
	}
	
	@GetMapping(path="Remove")
	public @ResponseBody PRSMaintenanceReturn deletePurchaseRequestLineItem(@RequestParam int id) {
		Optional<PurchaseRequestLineItem> prli = prliRepository.findById(id);
		try {
			prliRepository.delete(prli.get());
			System.out.println("PurchaseRequestLineItem deleted: " + prli.get());
			updatePRTotal(prli.get());
			return PRSMaintenanceReturn.getMaintReturn(prli.get());
		}
		catch (DataIntegrityViolationException dive) {
			return PRSMaintenanceReturn.getMaintReturnError(prli, dive.getRootCause().toString());
		}
		catch (Exception e) {
			e.printStackTrace();
			return PRSMaintenanceReturn.getMaintReturnError(prli, e.getMessage());
		}
	}
	
	@PostMapping(path="Change")
	public @ResponseBody PRSMaintenanceReturn updatePurchaseRequestLineItem(@RequestBody PurchaseRequestLineItem prli) {
		try {
			prliRepository.save(prli);
			System.out.println("PurchaseRequestLineItem updated: item=" + prli.getProduct().getId() + " || quantity=" + prli.getQuantity());
			updatePRTotal(prli);
			return PRSMaintenanceReturn.getMaintReturn(prli);
		}
		catch (DataIntegrityViolationException dive) {
			return PRSMaintenanceReturn.getMaintReturnError(prli, dive.getRootCause().toString());
		}
		catch (Exception e) {
			e.printStackTrace();
			return PRSMaintenanceReturn.getMaintReturnError(prli, e.getMessage());
		}
	}
	
	public void updatePRTotal(PurchaseRequestLineItem prli) throws Exception {
		PurchaseRequest pr = prli.getPurchaseRequest();
		List<PurchaseRequestLineItem> prLineItem = prliRepository.findAllByPurchaseRequestId(pr.getId());
		double lineItemTotal = 0.0;
		for (PurchaseRequestLineItem lineItems : prLineItem) {
			lineItemTotal += lineItems.getProduct().getPrice() * lineItems.getQuantity();
		}
		pr.setTotal(lineItemTotal);
		prRepository.save(pr);
		System.out.println("PR Total = $" + pr.getTotal());
		
	}
}