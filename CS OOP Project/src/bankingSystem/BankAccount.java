package bankingSystem;

import java.math.BigDecimal; 
import java.math.RoundingMode;
import java.util.concurrent.TimeUnit;

public class BankAccount {
	//Constructors
	long accountNum;
	String password;
	String Fname;
	String Lname;
	int monthNum;
	String month;
	double checkingBalance;
	double savingsBalance;
	double savingsInterest;
	double rrspBalance;
	double rrspInterest;
	double creditOwed;
	double creditInterest;
	int creditLimit;
	int debitLimit;
	
	
	
	public BankAccount() {
		//Default bank account values (was mainly for testing)
		this.accountNum = 5835101270087108L;
		this.password = "";
		this.Fname = "John";
		this.Lname = "Doe";
		this.monthNum = 1;
		this.month = "January 1 2017";
		this.checkingBalance = 0.0;
		this.savingsBalance = 0.0;
		this.savingsInterest = 1.0015;  //0.15% monthly
		this.rrspBalance = 0.0;
		this.rrspInterest = 1.002; //0.20% monthly
		this.creditOwed = 0.0;
		this.creditInterest = 1.013; //1.3% monthly
		this.creditLimit = 8000;
		this.debitLimit = 1000;
		
	}
		
	
	
	public BankAccount(long newAccountNum, String newPassword, String newFirstName, String newLastName,double newCheckingBalance, double newSavingsBalance, double newRrspBalance) {
		//Creating a bankAccount with data
		this.accountNum = newAccountNum;
		this.password = newPassword;
		this.Fname = newFirstName;
		this.Lname = newLastName;
		this.monthNum = 1;
		this.month = "January 1 2017";
		this.checkingBalance = newCheckingBalance;
		this.savingsBalance = newSavingsBalance;
		this.savingsInterest = 1.0015;
		this.rrspBalance = newRrspBalance;
		this.rrspInterest = 1.002;
		this.creditOwed = 0.0;
		this.creditInterest = 1.013;
		this.creditLimit = 8000;
		this.debitLimit = 1000;
	}

	


	public BankAccount(long a, String b, String c, String d, int e, String f,double g, double h, double i, double j, double k,double l, double m, int n, int o) {
		//Creating a BankAccount with data
		this.accountNum = a;
		this.password = b;
		this.Fname = c;
		this.Lname = d;
		this.monthNum = e;
		this.month = f;
		this.checkingBalance = g;
		this.savingsBalance = h;
		this.savingsInterest = i;
		this.rrspBalance = j;
		this.rrspInterest = k;
		this.creditOwed = l;
		this.creditInterest = m;
		this.creditLimit = n;
		this.debitLimit = o;
		
	}


	public String getAccountNum() {
		return String.valueOf(this.accountNum);
	}
	
	public String toString() {
		//to String method for printing
		return "-------------------\n"+this.Fname+" "+this.Lname+"\n"+this.month+"\n\nAccount Number: "+this.accountNum+"\n\nChecking Balance: $"+this.checkingBalance+"\nSavings Balance: $"+this.savingsBalance+"\nRRSP Balance: $"+this.rrspBalance+"\nCredit Owed: $"+this.creditOwed+"\n\nSavings Interest: 0.15% monthly\nRRSP Interest: 0.2% monthly\nCredit interest: 1.3% monthly\n\nDebit Limit: "+this.debitLimit+"\nCredit Limit: "+this.creditLimit +"\n-------------------";
		
	}
	
	public String toFileString() {
		//extra to string method for writing to ifles
		return this.accountNum+","+this.password+","+this.Fname+","+this.Lname+","+this.monthNum+","+this.month+","+this.checkingBalance+","+this.savingsBalance+","+this.savingsInterest+","+this.rrspBalance+","+this.rrspInterest+","+this.creditOwed+","+this.creditInterest+","+this.creditLimit+","+this.debitLimit+"\n";
	}
	
	public String[] toArray() {
		//Change bank account to array
		String newReturnThing[] = toFileString().split(",");
		return newReturnThing;
	}
	
	public void deposit(double depositAmount,String account) {
		//Deposit method based on account name
		if(account =="Checking") {
			this.checkingBalance += depositAmount;
			
		}	
		else if(account == "Savings") {
			this.savingsBalance += depositAmount;
		}
		else if(account == "RRSP") {
			this.rrspBalance += depositAmount;
		}
		
		
	}
	
	public void withdraw (double withdrawAmount, String account) {
		deposit(0-withdrawAmount,account);
		
		//If not enough funds, don't complete withdrawl
		if (checkingBalance < 0) {
			checkingBalance += withdrawAmount;
			System.out.println("\n\n\nWithdrawl Unsuccessful. Not enough funds.");
			
		}
		
		else {
			
			System.out.println("\n\n\nWithdrawl successful!\n");
		}
	}
	
	public void makeCreditPurchase(double cost) {
		//make a purchase on credit and owe money
		if (cost+creditOwed > creditLimit) {
			System.out.println("Card declined. Cannot exceed credit limit.");
		}
		else {
			//Add money owed to bank
			this.creditOwed += cost;
			System.out.println("\n\n\nPurchase successful!\n");
		}
	}
	public void makeDebitPurchase(double cost) {
		//Make a purchase using checking balance
		if(cost > checkingBalance) {
			System.out.println("Card declined. Cannot exceed current balance of $"+this.checkingBalance);
		}
		else {
			this.checkingBalance -= cost;
			System.out.println("\n\n\nPurchase successful!\n");
		}
	}
	
	public void payOffCredit(double transferAmount) {
		//Method to pay off credit owed using checking balance
		if (transferAmount > this.checkingBalance) {
			System.out.println("\n\n\nInsufficient funds.\n\n");
		}
		else {
			this.checkingBalance -= transferAmount;
			this.creditOwed -= transferAmount;
			System.out.println("\n\n\nTransfer successful!\n");
		}
	}
	
	public void forwardOneMonth() {
		//Method to go forward 1 month, affects balances with interest and month
		if(monthNum < 24) {
			String monthText[] = {"January 1 2017","February 1 2017","March 1 2017","April 1 2017","May 1 2017","June 1 2017","July 1 2017","August 1 2017","September 1 2017","October 1 2017","November 1 2017","December 1 2017","January 1 2018", "February 1 2018","March 1 2018","April 1 2018","May 1 2018","June 1 2018","July 1 2018","August 1 2018","September 1 2018","October 1 2018","November 1 2018","December 1 2018"};
			this.savingsBalance = round(this.savingsBalance*this.savingsInterest,2);
			this.rrspBalance = round(this.rrspBalance*this.rrspInterest,2);
			this.creditOwed = round(this.creditOwed*this.creditInterest,2);
			this.monthNum++;		
			this.month = monthText[monthNum-1];
			System.out.println("New month: "+month);
		}
		else{System.out.println("You cannot go forward any more months");}
		
		
	}
	
	public static double round(double value, int places) {
		//Method to round to 2DP
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = BigDecimal.valueOf(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	
}
