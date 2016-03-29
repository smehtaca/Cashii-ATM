/**
 *  The main bank account class, this will hold all the info required after
 *  user login.
 */
public class BankAccount
{
    int accountBalance;

    public BankAccount(int accountBalance)
    {
        this.accountBalance = accountBalance;
    }

    // Deposit to account
    void deposit(int amount)
    {
        accountBalance += amount;
    }

    // Return the balance
    int getAccountBalance()
    {
        return accountBalance;
    }

    // Withdraw only if account has the money, else return 0
    int withdraw(int amount)
    {
        if (amount <= accountBalance)
            accountBalance -= accountBalance;
        else
            return 0; // return 0 if the amount is too big
        return 1;
    }

    // @TODO: Needs to return a full statement to the user
    void createStatement()
    {

    }
}
