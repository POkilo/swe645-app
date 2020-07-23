package com.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "SUR_TBL")
@XmlRootElement
@NamedQueries({
			@NamedQuery(name="Survey.findAll",query="SELECT s FROM Survey s")
//			@NamedQuery(name="Survey.findAddress",query="SELECT s FROM SUR_TBL WHERE s.address=:address") 
})
public class Survey implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name="F_NAME",length=255,nullable=false)
	private String fName;
	public void setFirstName(String s) {
		this.fName = s;
	}
	public String getFirstName() {
		return fName;
	}
	
	@Column(name="L_NAME",length=255 ,nullable=false)
	private String lName;
	public void setLastName(String s) {
		this.lName = s;
	}
	public String getLastName() {
		return lName;
	}
	
	@Column(name="ADDRESS",length=255 ,nullable=false)
	private String address;
	public void setAddress(String s) {
		this.address = s;
	}
	public String getAddress() {
		return address;
	}

	@Column(name="CITY",length=255 ,nullable=false)
	private String city;
	public void setCity(String s) {
		this.city = s;
	}
	public String getCity() {
		return city;
	}

	@Column(name="STATE",length=255 ,nullable=false)
	private String state;
	public void setState(String s) {
		this.state = s;
	}
	public String getState() {
		return state;
	}
	
	@Column(name="ZIP",length=255 ,nullable=false)
	private String zip;
	public void setZip(String s) {
		this.zip = s;
	}
	public String getZip() {
		return zip;
	}
	
	
	@Column(name="PHONE",length=255 ,nullable=false)
	private String phone;
	public void setPhone(String s) {
		this.phone = s;
	}
	public String getPhone() {
		return phone;
	}

	@Column(name="EMAIL",length=255 ,nullable=false)
	private String email;
	public void setEmail(String s) {
		this.email = s;
	}
	public String getEmail() {
		return this.email;
	}
	
	@Column(name="DATE",length=255 ,nullable=false)
	private String date;
	public void setDate(String s) {
		this.date = s;
	}
	public String getDate(String s) {
		return this.date;
	}
	//=======================================================
	@Column(name="CAMPUS",length=255 ,nullable=false)
	private String campus;
	public void setCampus(String s) {
		this.campus = s;
	}
	public String getCampus(String s) {
		return this.campus;
	}
	
	@Column(name="REASON",length=255 ,nullable=false)
	private String reason;
	public void setReason(String s) {
		this.reason = s;
	}
	public String getReason(String s) {
		return this.reason;
	}
	
	@Column(name="LIKELIHOOD",length=255 ,nullable=false)
	private String likelihood;
	public void setLikelihood(String s) {
		this.likelihood = s;
	}
	public String getLikelihood(String s) {
		return this.likelihood;
	}
	
}
