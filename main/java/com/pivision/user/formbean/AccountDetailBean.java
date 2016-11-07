package com.pivision.user.formbean;

import java.math.BigInteger;

import org.hibernate.validator.constraints.NotEmpty;

public class AccountDetailBean {

	private String userId;
	private int addressBookId;
	private String addressType;
	@NotEmpty(message="Required")
	private String firstName;
	@NotEmpty(message="Required")
	private String lastName;
	private String gender;
	@NotEmpty(message="Required")
	private String buildingName;
	@NotEmpty(message="Required")
	private String street1;
	private String street2;
	@NotEmpty(message="Required")
	private String city;
	@NotEmpty(message="Required")
	private String company;
	@NotEmpty(message="Required")
	private String phone;
	private BigInteger pinCode;
	@NotEmpty(message="Required")
	private String state;
	@NotEmpty(message="Required")
	private String country;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId.trim();
	}

	/*public int getAddressBookId() {
		return addressBookId;
	}

	public void setAddressBookId(int addressBookId) {
		this.addressBookId = addressBookId;
	}*/

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType.trim();
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city.trim();
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company.trim();
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country.trim();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName.trim();
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender.trim();
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName.trim();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone.trim();
	}

	public BigInteger getPinCode() {
		return pinCode;
	}

	public void setPinCode(BigInteger pinCode) {
		this.pinCode = pinCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state.trim();
	}

	public String getStreet1() {
		return street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1.trim();
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2.trim();
	}

}
