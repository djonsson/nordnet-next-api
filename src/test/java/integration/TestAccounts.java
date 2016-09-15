package integration;

import com.next2.rest.api.Accounts;
import com.next2.rest.object.Account;
import com.next2.rest.object.AccountList;
import com.next2.rest.object.Amount;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class TestAccounts {

    private static int ACCOUNT_NUMBER = 9211233;
    private static int NUMBER_OF_ACCOUNTS = 1;

    Accounts accounts = new Accounts();

    @Test
    public void defaultAccountShouldReturnValidAccountNumber() {
        AccountList userAccounts = accounts.getUserAccounts();
        Account defaultAccount = userAccounts.getDefaultAccount();
        assert(defaultAccount.getAccountId() == ACCOUNT_NUMBER) :
               "Expected account number to be "+ ACCOUNT_NUMBER;
    }

    @Test
    public void userShouldHaveOnlyOneAccount() {
        assertTrue("Got wrong number of accounts",
                accounts.getUserAccounts().getNumberOfAccounts() == NUMBER_OF_ACCOUNTS);
    }

    @Test
    public void defaultAccountShouldBeDefault() {
        assertEquals("Default account should be set to default", true,
                accounts.getDefaultAccount().getIsDefault());
    }

    @Test
    public void defaultAccountShouldHaveAlias() {
        assertThat("Expected alias to be a string",
                accounts.getDefaultAccount().getAlias(), instanceOf(String.class));
    }

    @Test
    public void accountThatIsNotBlockedShouldReturnFalse() {
        assertFalse("The account is blocked",
                accounts.getDefaultAccount().getIsBlocked());
    }

    @Test
    public void accountThatIsNotBlockedShouldHaveDefaultBlockedReason() {
        assertSame("The account is blocked", "",
                accounts.getDefaultAccount().getBlockedReason());
    }

    @Test
    public void accountWithAccountNumberReturnsAccountSummary() {
        assertThat("The currency on the account should be in SEK",
                accounts.getAccountSummary(accounts.getDefaultAccount()).getAccountCurrency(), CoreMatchers.equalTo("SEK"));
    }

    @Test
    public void defaultAccountShouldNotHaveCredit() {
        float zero = 0;
        Amount accountCredit = accounts.getAccountSummary(accounts.getDefaultAccount()).getAccountCreditAmount();
        assertThat("The test account should not have credit",
                accountCredit.getSum(), CoreMatchers.equalTo(zero));
        assertThat("The currency on the account should be in SEK",
                accountCredit.getCurrency(), CoreMatchers.equalTo("SEK"));
    }

    @Test
    public void defaultAccountShouldHaveAnAccountSum() {
        float amount = 1000000;
        Amount accountCredit = accounts.getAccountSummary(accounts.getDefaultAccount()).getAccountAmount();
        assertThat("The test account should have 1000 000 SEK",
                accountCredit.getSum(), CoreMatchers.equalTo(amount));
        assertThat("The currency on the account should be in SEK",
                accountCredit.getCurrency(), CoreMatchers.equalTo("SEK"));
    }

    @Test
    public void getAccountListThreeTimes() throws InterruptedException {
        AccountList list = accounts.getUserAccounts();
        System.out.println(list.getNumberOfAccounts());
        Thread.sleep(5000);
        AccountList list2 = accounts.getUserAccounts();
        System.out.println(list2.getNumberOfAccounts());
        AccountList list3 = accounts.getUserAccounts();
        System.out.println(list3.getNumberOfAccounts());
    }

    @Test
    public void accountLedgerReturnsLedgerOnAccount() {
        Account account = accounts.getDefaultAccount();
        accounts.getAccountLedgers(account);
    }
    /*

    @Test
    public void accountLedgerReturnsLedgerOnAccount() {
        Accounts accounts = new Accounts();
        JSONObject json = accounts.accountLedger(ACCOUNT_NUMBER);
        int value = json.getJSONArray("ledgers").getJSONObject(0).getJSONObject("account_sum").getInt("value");

        int accountValue = 1000000;
        assert(value == accountValue) :
                "Expected value to be " + accountValue;
    }

    //It seems like /orders returns a status 204 No Content when no orders are placed.
    @Test(expected=JSONException.class)
    public void accountOrdersReturnsOrdersOnAccount() {
        Accounts accounts = new Accounts();
        JSONObject json = accounts.accountOrders(ACCOUNT_NUMBER, true);
    }
    */
}
