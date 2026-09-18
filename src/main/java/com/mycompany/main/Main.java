package com.mycompany.main;
/**
 *
 * @ST10538709
 */
//P.O.E PART 1
import java.util.Scanner;

public class Main{
   public static void main(String[] args){
      Scanner input = new Scanner(System.in); //Scanner input for user input
      LogIn login = new LogIn(); //login object

      System.out.print("Enter your User Name:");//User name input
      login.userName = input.nextLine();
      while(!login.checkUserName()){
            System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
            System.out.print("Enter your User Name:");//User name input
            login.userName = input.nextLine(); 
      }
      System.out.println("Username successfully entered.");
      
      System.out.print("Enter your Password:");//Password input
      login.passWord = input.nextLine();
      while(!login.checkPasswordComplexity()){
            System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            System.out.print("Enter your Password:");//Password input
            login.passWord = input.nextLine();
      }
      System.out.println("Password successfully captured.");
      
      System.out.print("Enter your Cell phone number:");//Cell phone number input 
      login.cellPhoneNumber = input.nextLine();
      while(!login.checkCellPhoneNumber()){
            System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
            System.out.print("Enter your Cell phone number:");//Cell phone number input 
            login.cellPhoneNumber = input.nextLine();
      }
      System.out.println("Cell phone number successfully added.");


      
      //Saves the validated details as the registered account, if all checks pass
      System.out.println(login.registerUser());


      
      //Now the user logs in separately, using the credentials they just registered
      System.out.print("\nEnter your User Name to log in:");
      String loginUserName = input.nextLine();

      System.out.print("Enter your Password to log in:");
      String loginPassWord = input.nextLine();

      boolean loginSuccessful = login.loginUser(loginUserName, loginPassWord);
      System.out.println(login.returnLoginStatus(loginSuccessful));


      input.close();
    }
}
class LogIn{

   String userName;
   String passWord;
   String cellPhoneNumber;

   //Stores the details of the successfully registered user
   String registeredUserName;
   String registeredPassWord;
   String registeredCellPhoneNumber;

   public boolean checkUserName(){ //checking user name method
      if(userName.contains("_") && userName.length() <= 5){ //Evaluates the user name
         return true;
      }
      else{
         return false;
      }
   }
   public boolean checkPasswordComplexity(){ //Password complexity check method
      String specialChars = "!@#$%^&*()\":{}|<>";
      
      boolean hasUpper = false;
      boolean hasDigit =  false;
      boolean hasSpecial = false;
      
      if(passWord.length() < 8){ //Evaluating the passwword length
         return false;
      }
      for(char c : passWord.toCharArray()){ //Iterating through the character array
         if(Character.isUpperCase(c)){ // Evaluating if there is atleast one uppercase character
            hasUpper = true;
         }
         if(specialChars.indexOf(c) >= 0){ // Evaluating if there is atleast one special character
            hasSpecial = true;
         }
         if(Character.isDigit(c)){ // Evaluating if there is atleast one digit in the password
            hasDigit = true;
         }
      }
      return hasUpper && hasDigit && hasSpecial;
        
   }
   public boolean checkCellPhoneNumber(){ //Evaluate the phone number
 
   
      if(cellPhoneNumber.startsWith("+27") && cellPhoneNumber.length() == 12){ //Evalautes if the number contains +27 and the length is 9(+27 is the first 3)
         return true; //return value if true
      }
      else{
         return false; //return value if false
      }
   }

   //Registers the user by saving their validated details as the "registered" account
   public String registerUser(){
      if(checkUserName() && checkPasswordComplexity() && checkCellPhoneNumber()){ //Evaluates if the username, password and cell number are all correct
         registeredUserName = userName;
         registeredPassWord = passWord;
         registeredCellPhoneNumber = cellPhoneNumber;
         return "User registered successfully. Welcome " + userName + "!";
      }
      else{
         return "Registration failed, please check your details and try again.";
      }
   }

   //Logs the user in by comparing entered details against the registered account
   public boolean loginUser(String enteredUserName, String enteredPassWord){
      if(registeredUserName == null || registeredPassWord == null){
         return false; //No one has registered yet
      }
      if(enteredUserName.equals(registeredUserName) && enteredPassWord.equals(registeredPassWord)){
         return true;
      }
      else{
         return false;
      }
   }

   //Returns the login status message based on the result of loginUser()
   public String returnLoginStatus(boolean loginSuccessful){
      if(loginSuccessful){
         return "Login successful, welcome back " + registeredUserName + ", it is great to see you again.";
      }
      else{
         return "Username or password incorrect, please try again.";
      }
   }
}
