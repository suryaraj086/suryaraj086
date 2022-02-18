package db;

import java.io.Serializable;

public class AccountInfo implements Serializable{

/**
	 * 
	 */
private static final long serialVersionUID = 1L;
	
private long id;
private String name;
private long accountNumber;
private String branch;
private long balance=1000;
private boolean status=true;

public String getBranch() {
	return branch;
}
public void setBranch(String branch) {
	this.branch = branch;
}
public Long getBalance() {
	return balance;
}
public void setBalance(long balance) {
	this.balance = balance;
}
public void setAccountNumber(long accountNumber) {
	this.accountNumber = accountNumber;
}
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
@Override
public String toString() {
	return "AccountInfo [id=" + id + ", name=" + name + ", accountNumber=" + accountNumber + ", branch=" + branch
			+ ", status=" + status + "]";
}
public boolean isStatus() {
	return status;
}
public void setStatus(boolean status) {
	this.status = status;
}
}
