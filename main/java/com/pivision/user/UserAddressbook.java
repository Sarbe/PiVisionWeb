package com.pivision.user;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * The persistent class for the user_addressbook database table.
 * 
 */
@Entity
@Table(name="user_addressbook")
@NamedQuery(name="UserAddressbook.findAll", query="SELECT u FROM UserAddressbook u")
public class UserAddressbook implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="address_id")
	private int addressBookId;

	@Column(name="address_type")
	private String addressType;

	@Column(name="building_name")
	private String buildingName;
	
	@Column(name="city")
	private String city;

	@Column(name="company")
	private String company;
	
	@Column(name="country")
	private String country;

	@Column(name="first_name")
	private String firstName;


	@Column(name="last_name")
	private String lastName;

	@Column(name="pin_code")
	private BigInteger pinCode;

	private String state;

	@Column(name="street_1")
	private String street1;

	@Column(name="street_2")
	private String street2;
	
	@Column(name="phone")
	private String phone;

	//bi-directional many-to-one association to User
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;

	public UserAddressbook() {
	}

	public int getAddressBookId() {
		return this.addressBookId;
	}

	public void setAddressBookId(int addressBookId) {
		this.addressBookId = addressBookId;
	}

	public String getAddressType() {
		return this.addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getBuildingName() {
		return this.buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public BigInteger getPinCode() {
		return this.pinCode;
	}

	public void setPinCode(BigInteger pinCode) {
		this.pinCode = pinCode;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStreet1() {
		return this.street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getStreet2() {
		return this.street2;
	}
	
	

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}