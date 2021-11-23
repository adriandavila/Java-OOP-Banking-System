package bankingSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.Arrays;
import java.util.Scanner;


public class BankAccountClient {

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
		//Create scanner object
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		input.useDelimiter(System.lineSeparator());
		
		
		//Initial Screen
		System.out.println("\\n\\n----------------\nStartup\n----------------\nWelcome to Adrian's Bank!\n\nSelect one of the following options:\n\t1. Login\n\t2. Create Account");
		int loginOrSignup = input.nextInt(); 
		//input.nextLine();
		
		
		
		BankAccount accountDetails;
		
		//Accessing Account
		if (loginOrSignup == 1) {
			//Login using existing account
			accountDetails = login();
			TimeUnit.MILLISECONDS.sleep(500);
			System.out.println("\n"+accountDetails);
			TimeUnit.SECONDS.sleep(1);
		} 
		else { 
			//Create new account
			accountDetails = createAccount();
			//Add new account to database
			String str = accountDetails.toFileString();
			addAccountToFile(str);
		}
		

		boolean keepGoing = true;
		int account = 0;
		
		while(keepGoing == true) {
			if(account == 0) {
				//print options
				System.out.println("\n\n----------------\nHomepage\n----------------\nWhat account would you like to access?\n\t1. Checking\n\t2. Savings\n\t3. RRSP\n\t4. Credit\n\n\t6. Check account details\n\t7. Go Forward 1 Month\n\t8. Exit");
				
				//get input
				account = input.nextInt();
				
			}
			else if (account == 1) {
				//Print options
				System.out.println("\n\n----------------\nChecking Account\n----------------\nWould you like to...\n\t1. Deposit\n\t2. Withdraw\n\t3. Make purchase\n\t4. Go Back");
				int checkingAction = input.nextInt();
				
				
				if (checkingAction == 1) {
					//Deposit
					System.out.println("How much would you like to deposit?");
					System.out.print("$");
					double depositAmount = input.nextDouble();
					
					//run deposit method and print result
					accountDetails.deposit(depositAmount, "Checking");
					System.out.println("\n\n\nDeposit Successful!\n");
					TimeUnit.SECONDS.sleep(1);
					System.out.println(accountDetails);
				}
				else if (checkingAction == 2) {
					//Withdraw
					System.out.println("How much would you like to withdraw?");
					System.out.print("$");
					double withdrawAmount = input.nextDouble();
					
					accountDetails.withdraw(withdrawAmount, "Checking");
					TimeUnit.SECONDS.sleep(1);
					System.out.println("\n"+accountDetails);
				}
				else if(checkingAction == 3) {
					//Make purchase
					System.out.println("\nWhat is the purchase amount?");
					System.out.print("$");
					double purchaseCost = input.nextDouble();
					
					//Run purchase method
					accountDetails.makeDebitPurchase(purchaseCost);
					TimeUnit.SECONDS.sleep(1);
					System.out.println(accountDetails);
					
				}
				else if (checkingAction == 4) {account = 0;}
				else {keepGoing = false;}
				
			}
			else if (account == 2) {
				System.out.println("\n\n----------------\nSavings Account\n----------------\nWould you like to...\n\t1. Deposit\n\t2. Go Back");
				int savingsAction = input.nextInt();
				
				if(savingsAction ==1) {
					//Deposit
					System.out.println("How much would you like to deposit?");
					System.out.print("$");
					double depositAmount = input.nextDouble();
					
					//Run deposit method
					accountDetails.deposit(depositAmount, "Savings");
					TimeUnit.SECONDS.sleep(1);
					System.out.println(accountDetails);
				}
				else if(savingsAction == 2) {account = 0;}
				else {keepGoing = false;}
				
			}
			else if (account == 3) {
				System.out.println("\n\n----------------\nRRSP Account\n----------------\nWould you like to...\n\t1. Deposit\n\t2. Go Back");
				int rrspAction = input.nextInt();
				
				if(rrspAction ==1) {
					
					//Deposit
					System.out.println("How much would you like to deposit?");
					System.out.print("$");
					double depositAmount = input.nextDouble();
					
					accountDetails.deposit(depositAmount, "RRSP");
					System.out.println("Deposit Successful");
					TimeUnit.SECONDS.sleep(1);
					System.out.println(accountDetails);
				}
				else if(rrspAction == 2) {account = 0;} //Go  back
				else {keepGoing = false;}
			}
			else if(account == 4) {
				System.out.println("\n\n----------------\nCredit Account\n----------------\nWould you like to...\n\t1. Make Purchase\n\t2. Pay off Credit\n\t3. Go back");
				int creditAction = input.nextInt();
				
				if(creditAction ==1) {
					//Make purchase
					System.out.println("\nWhat is the purchase amount?");
					System.out.print("$");
					double purchaseCost = input.nextDouble();
					
					accountDetails.makeCreditPurchase(purchaseCost);
					TimeUnit.SECONDS.sleep(1);
					System.out.println(accountDetails);
				}
				
				else if(creditAction == 2) {
					//Pay off credit owed
					System.out.println("How much would you like to pay off (using checking):");
					System.out.print("$");
					double transferAmount = input.nextDouble();
					accountDetails.payOffCredit(transferAmount);
					TimeUnit.SECONDS.sleep(1);
					System.out.println(accountDetails);
				}
				else if (creditAction == 3) {account = 0;} //Go back
				else {keepGoing = false;}
			}
			else if (account == 6) {
				//Print account details
				System.out.println("\n\nHere is your account:\n\n"+accountDetails+"\n");
				TimeUnit.SECONDS.sleep(1);
				account = 0;
			}
			else if (account == 7) {
				//Go forward 1 month
				accountDetails.forwardOneMonth();
				TimeUnit.MILLISECONDS.sleep(750);
				System.out.println("\n\n"+accountDetails+"\n");
				account = 0;
			}
			else {keepGoing = false;} //End program
			
			
		}
		
		
		//Write to file (updateFile)
		updateFile(accountDetails);
		System.out.println("\n\n\n\n\n\n\n--------------------------------\nThanks for using Adrian's Bank!\n--------------------------------");
		
		
		
	}
	
	
	private static void updateFile(BankAccount accountDetails) throws IOException {
		//Get file data
		StringBuilder accountsList = readFile();
		
		//Clean file data
		String accounts[] = accountsList.toString().split("\n");
		String allAccounts[][] = new String[accounts.length][];
		for(int i = 0; i < accounts.length;i++) {
			allAccounts[i]=accounts[i].split(",");
		}
		
		//System.out.println(Arrays.deepToString(allAccounts));
		
		for(int i = 0; i < accounts.length; i++) {
			if (allAccounts[i][0].equals(accountDetails.getAccountNum())) {
				allAccounts[i] = accountDetails.toArray();
			}

		}
		
		//System.out.println(Arrays.deepToString(allAccounts));
		
		StringBuilder updatedAccounts = new StringBuilder();
		
		//updatedAccounts.append(Arrays.deepToString(allAccounts));
		for(int i = 0; i < allAccounts.length; i++) {
			for(int j = 0; j < allAccounts[i].length;j++) {
				allAccounts[i][j] = allAccounts[i][j].replaceAll("\n","");
				updatedAccounts.append(allAccounts[i][j]);
				if (j != allAccounts[i].length-1) {
					updatedAccounts.append(",");
				}
			}
			updatedAccounts.append("\n");
		}
		
		
		BufferedWriter writer = new BufferedWriter(new FileWriter("accountDatabase.txt"));
		writer.append(updatedAccounts);
		writer.close();
		
	}
	
	private static void addAccountToFile(String str) throws IOException {
		//Get previous data
		StringBuilder fileData = readFile();
		//Put all data in file
		BufferedWriter writer = new BufferedWriter(new FileWriter("accountDatabase.txt"));
		fileData.append(str);
	    writer.append(fileData);
	    writer.close();
	}
	
	private static StringBuilder readFile() throws IOException {
		//Object to read file
		BufferedReader reader = new BufferedReader(new FileReader("accountDatabase.txt"));
	    
		//Get file data
	    StringBuilder builder = new StringBuilder();
	    String currentLine = reader.readLine();
	    while (currentLine != null) {
	        builder.append(currentLine);
	        builder.append("\n");
	        currentLine = reader.readLine();
	    }
	    //System.out.println(builder);
	    
	    reader.close();
	    return builder;
	}
	
	
	public static BankAccount login() throws IOException {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		
		
		//Get file data
		StringBuilder accountsList = readFile();
		
		String accounts[] = accountsList.toString().split("\n");
		String allAccounts[][] = new String[accounts.length][];
		for(int i = 0; i < accounts.length;i++) {
			allAccounts[i]=accounts[i].split(",");
		}
		//System.out.println(Arrays.deepToString(allAccounts));
		
		//Enter account # and get required pass
		String requiredPassword = "";	
		boolean keepGoing = true;
		
		//Enter initial num
		System.out.println("Enter your account number:");
		String enteredAccountNum = input.next();
		
		for(int i = 0; i < allAccounts.length;i++) {
			
			if (allAccounts[i][0].equals(enteredAccountNum) ) {
				requiredPassword = allAccounts[i][1];
				accounts = allAccounts[i];
				keepGoing = false;
			}
		}
		
		//Get valid number
		while (keepGoing == true) {
			System.out.println("Invalid number, try again:");
			enteredAccountNum = input.next();
			
			for(int i = 0; i < allAccounts.length;i++) {
				if (allAccounts[i][0].equals(enteredAccountNum)) {
					requiredPassword = allAccounts[i][1];
					accounts = allAccounts[i];
					keepGoing = false;
				}
			}
		}
		
		//Enter password & validate
		keepGoing = true;
		System.out.println("Enter password:");
		String enteredPassword = input.next();
		if (enteredPassword.equals(requiredPassword)) {keepGoing = false;}
		while(keepGoing == true) {
			System.out.println("Invalid password, try again:");
			enteredPassword = input.next();
			if (enteredPassword.equals(requiredPassword)) {keepGoing = false;}
		}
		
		System.out.println("\nYou're in!");
		
		//Transform data to BankAccount object
		BankAccount accountDetails = new BankAccount(Long.valueOf(enteredAccountNum),accounts[1],accounts[2],accounts[3],Integer.parseInt(accounts[4]),accounts[5],Double.parseDouble(accounts[6]),Double.parseDouble(accounts[7]),Double.parseDouble(accounts[8]),Double.parseDouble(accounts[9]),Double.parseDouble(accounts[10]),Double.parseDouble(accounts[11]),Double.parseDouble(accounts[12]),Integer.parseInt(accounts[13]),Integer.parseInt(accounts[14]));
		
		//Return data as BankAccount
		return accountDetails;
	}
	
	
	
	private static BankAccount createAccount() throws IOException {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		
		//Create account number (associated with visa - starts with 4)
		long newAccountNum = ThreadLocalRandom.current().nextLong(4111111111111111L, 4999999999999999L);
		//NEED TO CHECK IF DUPLICATED
		
		
		
		//First and Last Name
		System.out.println("Enter your first name:");
		String newFirstName = input.next();
		
		
		System.out.println("\nEnter your last name:");
		String newLastName = input.next();
		
		
		//Create Password
		System.out.println("\nEnter the password you want to use to login:");
		String newPassword = input.next();
		
		
		//Initial checking deposit
		System.out.println("\nEnter deposit for checking account:");
		System.out.print("$");
		double newCheckingBalance = input.nextDouble();
		
		
		//Initial savings deposit
		System.out.println("\nEnter deposit for savings account:");
		System.out.print("$");
		double newSavingsBalance = input.nextDouble();
		
		
		//initial RRSP deposit
		System.out.println("\nEnter deposit for RRSP:");
		System.out.print("$");
		double newRrspBalance = input.nextDouble();
		
		//Put into BankAccount type
		BankAccount newAccountDetails = new BankAccount(newAccountNum,newPassword,newFirstName,newLastName,newCheckingBalance,newSavingsBalance,newRrspBalance);
		
		System.out.println(newAccountDetails);
		return newAccountDetails;
	}


}


