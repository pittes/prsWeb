package com.prs.business.purchaserequest;

import java.sql.Date;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.prs.business.user.User;

@Entity
public class PurchaseRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
//	private int userID;
	@ManyToOne
	@JoinColumn(name="UserID")
	private User user;
	private String description;
	private String justification;
	private Date dateNeeded;
	private String deliveryMode;
	private String status;
	private double total;
	private Timestamp submittedDate;
	private String reasonForRejection;
//	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
//	@JoinColumn(name="PurchaseRequestID")
//	private List<PurchaseRequestLineItem> prLineItems;
	
	public static final String STATUS_NEW = "New";
	public static final String STATUS_REVIEW = "Review";
	public static final String STATUS_EDIT = "Edit";
	public static final String STATUS_APPROVED = "Approved";
	public static final String STATUS_REJECTED = "Rejected";
	
	public PurchaseRequest() {
		super();
		id = 0;
		user = null;
		description = "";
		justification = "";
		dateNeeded = new Date(System.currentTimeMillis());
		deliveryMode = "";
		status = STATUS_NEW;
		total = 0.0;
		submittedDate = new Timestamp(System.currentTimeMillis());
		reasonForRejection = "";
//		prLineItems = new ArrayList<>();
	}

	public PurchaseRequest(int id, User user, String description, String justification, Date dateNeeded,
			String deliveryMode, String status, double total, Timestamp submittedDate) {
		super();
		this.id = id;
		this.user = user;
		this.description = description;
		this.justification = justification;
		this.dateNeeded = dateNeeded;
		this.deliveryMode = deliveryMode;
		this.status = status;
		this.total = total;
		this.submittedDate = submittedDate;
//		this.prLineItems = prLineItems;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public Date getDateNeeded() {
		return dateNeeded;
	}

	public void setDateNeeded(Date dateNeeded) {
		this.dateNeeded = dateNeeded;
	}

	public String getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Timestamp getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Timestamp submittedDate) {
		this.submittedDate = submittedDate;
	}
	
	public String getReasonForRejection() {
		return reasonForRejection;
	}

	public void setReasonForRejection(String reasonForRejection) {
		this.reasonForRejection = reasonForRejection;
	}

//	public List<PurchaseRequestLineItem> getPrLineItems() {
//		return prLineItems;
//	}
//
//	public void setPrLineItems(List<PurchaseRequestLineItem> prLineItems) {
//		this.prLineItems = prLineItems;
//	}


	@Override
	public String toString() {
//		System.out.println("# of line items = "+prLineItems.size());
		return "PurchaseRequest [id=" + id + ", user=" + user.getUserName() + ", description=" + description + ", justification="
				+ justification + ", dateNeeded=" + dateNeeded + ", deliveryMode=" + deliveryMode + ", status="
				+ status + ", total=" + total + ", submittedDate=" + submittedDate + ", reasonForRejection=" 
				+ reasonForRejection + "]";
	}
	
	
	

}
