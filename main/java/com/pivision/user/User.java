package com.pivision.user;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.pivision.display.Display;
import com.pivision.presentation.Presentation;
import com.pivision.schedule.ActiveSchedule;
import com.pivision.schedule.Schedule;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name = "user")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue
	@Column(name="user_id")
	private String userId;

	@Temporal(TemporalType.DATE)
	@Column(name="create_date")
	private Date createDate;

	@Column(name="email_id")
	private String emailId;

	@Temporal(TemporalType.DATE)
	@Column(name="last_logon")
	private Date lastLogon;

	@Column(name="num_logons")
	private int numLogons;

	private String password;

	@Temporal(TemporalType.DATE)
	@Column(name="update_date")
	private Date updateDate;

	@Column(name="user_type")
	private String userType;

	

	//bi-directional many-to-one association to Display
	@OneToMany(mappedBy="user")
	private List<Display> displays;

	//bi-directional many-to-one association to Presentation
	@OneToMany(mappedBy="user")
	private List<Presentation> presentations;
	
	
	//bi-directional many-to-one association to Presentation
		@OneToMany(mappedBy="user")
		private List<Schedule> schedules;
		
	
	//bi-directional one-to-many association to Presentation
	@OneToMany(mappedBy="user")
	private List<ActiveSchedule> activSschedules;
		
	//bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name="role_id")
	private Role role;
	
	//bi-directional many-to-one association to UserAddressbook
	@OneToOne(mappedBy="user")
	private UserAddressbook userAddressbooks;

	public User() {
		
	}

	
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Date getLastLogon() {
		return this.lastLogon;
	}

	public void setLastLogon(Date lastLogon) {
		this.lastLogon = lastLogon;
	}

	public int getNumLogons() {
		return this.numLogons;
	}

	public void setNumLogons(int numLogons) {
		this.numLogons = numLogons;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}



	public List<Display> getDisplays() {
		return this.displays;
	}

	public void setDisplays(List<Display> displays) {
		this.displays = displays;
	}
	
	

	public List<Schedule> getSchedules() {
		return schedules;
	}


	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}


	public Display addDisplay(Display display) {
		getDisplays().add(display);
		display.setUser(this);

		return display;
	}

	public Display removeDisplay(Display display) {
		getDisplays().remove(display);
		display.setUser(null);

		return display;
	}

	public List<Presentation> getPresentations() {
		return this.presentations;
	}

	public void setPresentations(List<Presentation> presentations) {
		this.presentations = presentations;
	}

	public Presentation addPresentation(Presentation presentation) {
		getPresentations().add(presentation);
		presentation.setUser(this);

		return presentation;
	}

	public Presentation removePresentation(Presentation presentation) {
		getPresentations().remove(presentation);
		presentation.setUser(null);

		return presentation;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}


	public UserAddressbook getUserAddressbooks() {
		return userAddressbooks;
	}


	public void setUserAddressbooks(UserAddressbook userAddressbooks) {
		this.userAddressbooks = userAddressbooks;
	}
	
	


	public List<ActiveSchedule> getActivSschedules() {
		return activSschedules;
	}


	public void setActivSschedules(List<ActiveSchedule> activSschedules) {
		this.activSschedules = activSschedules;
	}


	@Override
	public String toString() {
		return "User [userId=" + userId + ", createDate=" + createDate + ", emailId=" + emailId + ", lastLogon="
				+ lastLogon + ", numLogons=" + numLogons + ", password=" + password + ", updateDate=" + updateDate
				+ ", userType=" + userType + "]";
	}


}