
public class BankAccount
{
    int accountBalance;

    public BankAccount(int accountBalance)
    {
        this.accountBalance = accountBalance;
    }

    void deposit(int amount)
    {
        accountBalance += amount;
    }

    int getAccountBalance()
    {
        return accountBalance;
    }

    int withdraw(int amount)
    {
        if (amount <= accountBalance)
            accountBalance -= accountBalance;
        else
            return 0; // return 0 if the amount is too big
        return 1;
    }
}
