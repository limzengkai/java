import java.util.Scanner;

class MyException extends Exception{
    // private int value;
    private String message;

    public MyException(String i) {
        // value = i;
        // message = new String("MyException occurs at value: " + value); 
        message = i;
    }

    public String getMessage() {
        return message;
    }
}

// class InputOut
abstract class Account{
    protected String accNo;
    protected double balance;

    Account(){
        accNo = "";
        balance = 0;
    }

    Account(String accNo, double balance){
        this.accNo = accNo;
        this.balance = balance;
    }

    public abstract void displayReport();

    public void deposit(double d){
        balance += d;
    }

    public void withdraw(double request_amount){
        // if(request_amount < 0){
        //     System.out.println("Invalid amount requested");
        // }else if((balance - request_amount) < 0){
        //     System.out.println("No enough money");
        // }else{
        //     balance -= request_amount;
        // }
        try{
            if(request_amount < 0){
                throw new MyException("Invalid amount requested");
            }else if((balance - request_amount) < 0){
                throw new MyException("No enough money");
            }else{
                balance -= request_amount;
            }
        }catch (MyException ex){
            System.out.println(ex.getMessage());
        }
            // balance -= request_amount;
    }
    public double getBalance(){
        return balance;
    }

}

interface Charges{
    double HIBAH_RATE = 0.02;
    double calcHibah();
}

class Savings extends Account implements Charges{
    private String accName;

    public Savings(String accName){
        super("001",2000.00);
        this.accName=accName;
    }

    @Override
    public void displayReport() {
        double hibah = calcHibah();
        System.out.printf("Monthly Hibah Earned: RM%.2f\n", hibah);
        System.out.printf("Ending Balance: RM%.2f\n",balance+hibah);
        
    }

    @Override
    public double calcHibah() {
        return balance * Charges.HIBAH_RATE;
    }
}

public class TestAccount{
    public static void main(String[] args) {
        Savings s = new Savings("Ahamad");
        Scanner input = new Scanner(System.in);
        int choice;
        double amount;
        do{
            System.out.println("\n******** BANK ACCOUNT MENU *********\n");
            System.out.println("1. Savings Account Deposit");
            System.out.println("2. Savings Account Withdrawal");
            System.out.println("3. Display Report");
            System.out.println("4. Exit\n");
            System.out.print("Please enter your choice (1-4) :");
            choice = input.nextInt();
            System.out.println();
            switch(choice){
                case 1:

                    System.out.print("Enter amount to deposit: ");
                    amount = input.nextDouble();
                    s.deposit(amount);
                    System.out.println("Please insert your cash.");
                    System.out.println("Deposit accepted");
                    System.out.printf("Your current balance is RM%.2f%n", s.getBalance());
                    break;

                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    amount = input.nextDouble();
                    try{
                        if(amount < 0){
                            throw new MyException("Invalid amount requested");
                        }else if((s.getBalance() - amount) < 0){
                            throw new MyException("No enough money");
                        }else{
                            s.withdraw(amount);
                            System.out.println("Please take your cash.");
                        }
                    }catch (MyException ex){
                        System.out.println(ex.getMessage());
                    }

                    System.out.printf("Your current balance is RM%.2f%n", s.getBalance());
                    break;
            
                case 3:
                    s.displayReport();
                    break;

                case 4:
                    System.exit(0);
            }
        }while(choice !=4 );
        input.close();
    }
}