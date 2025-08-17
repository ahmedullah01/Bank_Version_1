import java.util.Scanner;

public class BankProgram {
    private static final int MAX_ACCOUNTS = 1000;
    private long[] accountBalances = new long[MAX_ACCOUNTS];
    private boolean[] isActive = new boolean[MAX_ACCOUNTS];
    private boolean[] hasLoan = new boolean[MAX_ACCOUNTS];
    private boolean[] interestApplied = new boolean[MAX_ACCOUNTS];
    private int currentAccountIndex = -1;
    private int nextAccountIndex = 0;
    private Scanner scanner = new Scanner(System.in);
     private long AccCode1 = 1019;
     private long AccCode2 = 5979;
     private long AccCode3 = 7209;
     private long AccCode4 = 4901;
    private static final double INTEREST_RATE = 0.14;

    public static void main(String[] args) {
        BankProgram bank = new BankProgram();
        bank.run();
    }

    public void run() {
        boolean done = false;
        while (!done) {
            System.out.print("Enter command (0 = new, 1 = select, 2 = deposit, 3 = show, 4 = quit, 5 = loan, 6 = add interest): ");
            int command = scanner.nextInt();
            scanner.nextLine();

            switch (command) {
                case 0:
                    newAccount();
                    break;
                case 1:
                    selectAccount();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    showAllAccounts();
                    break;
                case 4:
                    done = true;
                    System.out.println("Thank you for using the bank. Goodbye!");
                    break;
                case 5:
                    takeLoan();
                    break;
                case 6:
                    applyInterest();
                    break;
                default:
                    System.out.println("Invalid command. Please try again.");
            }
        }
    }

    private void newAccount() {
        if (nextAccountIndex >= MAX_ACCOUNTS) {
            System.out.println("Cannot create more accounts.");
            return;
        }
        currentAccountIndex = nextAccountIndex;
        accountBalances[currentAccountIndex] = 0;
        isActive[currentAccountIndex] = true;
        nextAccountIndex++;

        AccCode1 += 33;
        AccCode2 += 19;
        AccCode3 += 6;

        Long accountNumber = AccCode4 + currentAccountIndex;
        System.out.println("Your new account number is: PK92 BAHL " + AccCode1 + " " + AccCode2 + " " + AccCode3 + " " + accountNumber);
    }

    private void selectAccount() {
        System.out.print("Enter your full account number: ");
        String accountInput = scanner.nextLine();

        if (accountInput.length() < 4) {
            System.out.println("Invalid account number format.");
            return;
        }

        String lastFourDigits = accountInput.substring(accountInput.length() - 4);
        int accountIndex = Integer.parseInt(lastFourDigits) - 4901;

        if (accountIndex < 0 || accountIndex >= MAX_ACCOUNTS || !isActive[accountIndex]) {
            System.out.println("Account not found.");
        } else {
            currentAccountIndex = accountIndex;
            System.out.println("Account selected. Current balance: " + accountBalances[currentAccountIndex]);
        }
    }

    private void deposit() {
        if (currentAccountIndex == -1 || !isActive[currentAccountIndex]) {
            System.out.println("No account selected. Please select an account first.");
            return;
        }

        System.out.print("Enter deposit amount: ");
        int amount = scanner.nextInt();

        if (amount <= 0) {
            System.out.println("Deposit amount must be greater than zero.");
            return;
        }

        accountBalances[currentAccountIndex] += amount;
        System.out.println("Successfully deposited: " + amount);
    }

    private void showAllAccounts() {
        if (currentAccountIndex == -1 || !isActive[currentAccountIndex]) {
            System.out.println("No account selected. Please select an account first.");
            return;
        }

        for (int i = 0; i < accountBalances.length; i++) {
            if (isActive[i]) {
                System.out.println("----------------------------------");
                System.out.println("Account Number: **** **** **** " + (4901 + i));
                System.out.println("Balance: " + accountBalances[i]);
                System.out.println("Loan Taken: " + hasLoan[i]);
                System.out.println("Interest Applied: " + interestApplied[i]);
            }
        }
    }

    public void takeLoan() {
        System.out.println("Would you like to take a loan?");
        System.out.print("1) Yes\n2) No\nYour choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 2) {
            System.out.println("No loan taken.");
            return;
        }

        System.out.print("Enter desired loan amount: ");
        int loanAmount = scanner.nextInt();
        scanner.nextLine();

        if (accountBalances[currentAccountIndex] > loanAmount / 2) {
            System.out.println("Loan approved.");
            accountBalances[currentAccountIndex] += loanAmount;
            hasLoan[currentAccountIndex] = true;
        } else {
            System.out.println("Not eligible for loan.");
        }
    }

    public void applyInterest() {
        System.out.println("Current interest rate: 14%");
        System.out.print("Would you like to apply interest?\n1) Yes\n2) No\nYour choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            double interest = accountBalances[currentAccountIndex] * INTEREST_RATE;
            accountBalances[currentAccountIndex] += interest;
            interestApplied[currentAccountIndex] = true;
            System.out.println("Interest of " + interest + " added to your account.");
        } else {
            System.out.println("Interest not applied.");
        }
    }
}
