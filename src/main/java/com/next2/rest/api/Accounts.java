package com.next2.rest.api;

import com.next2.rest.model.*;
import com.next2.rest.util.RequestHandler;
import com.next2.rest.util.ResponseHandler;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;

/**
 * ACCOUNTS - Customer account operations
 *
 * The main class for customer account operations with capabilities to perform various actions on the user account
 * such as placing orders and checking account balance. For a full set of capabilities please consult the official
 * documentation.
 *
 *
 * @see <a href="https://api.test.nordnet.se/api-docs/index.html#!/accounts/">Accounts</a>
 *
 * {@link #getUserAccounts()}          - Returns a list of Accounts that a user has access to
 * {@link #getAccountSummary(Account)} - Returns a a summary of an  account
 *
 */
public class Accounts extends Session {
    private final static Logger LOG = Logger.getLogger(Accounts.class);
    private final static String API = "accounts";

    AccountList accountList;
    TTL accountListTTL = new TTL(1000);

    AccountSummary accountSummary;
    TTL accountSummaryTTL = new TTL(1000);

    AccountLedgers accountLedgers;
    TTL accountLedgersTTL = new TTL(1000);

    /**
     * This method return an AccountList of all Accounts associated with a specific user. The response is returned
     * from the API as a JSONArray with each account as an own entry. The API will return one or more accounts.
     *
     * Example of response:
     * [{"accno":integer,"type":"string","default":boolean,"alias":"string"}]

     * The JSONArray response is parsed by this method and returned as an AccountList.
     *
     * @see <a href="https://api.test.nordnet.se/api-docs/index.html#!/accounts/get_accounts">Get User Accounts</a>
     * @see Account
     * @see AccountList
     *
     * @return {@link com.next2.rest.model.AccountList}
     */
    public AccountList getUserAccounts() {
        LOG.info("Entering getUserAccounts");

        if (accountListTTL.isStale()) {
            accountListTTL.resetRequestTime();

            Invocation.Builder invocation = webTarget.path(API).request(responseType);

            Response response = RequestHandler.GET(invocation);
            JSONArray jsonArray = ResponseHandler.asJsonArray(response);

            this.accountList = new AccountList(jsonArray);
        }
        return this.accountList;
    }

    /**
     * Returns the AccountSummary for a specific Account. The response is returned from the API as a JSONObject,
     * detailing various account balances for the current user. Each balance is returned as an Amount object,
     * with the monetary value a a float, and the currency as a String (SEK).
     *
     * @param account An account object
     * @see Account
     * @see AccountSummary
     *
     * @return {@link com.next2.rest.model.AccountSummary}
     */
    public AccountSummary getAccountSummary(Account account) {
        LOG.info("Entering getAccountSummary");
        if (accountSummaryTTL.isStale()) {
            accountSummaryTTL.resetRequestTime();

            String accountId = account.getAccountIdAsString();
            Invocation.Builder invocation = webTarget.path(API).path(accountId).request(responseType);

            Response response = RequestHandler.GET(invocation);
            JSONObject jsonObject = ResponseHandler.asJsonObject(response);

            this.accountSummary = new AccountSummary(jsonObject);
        }
        return this.accountSummary;
    }

    /**
     * Helper method that finds the Account that is set to default in the AccountList.
     *
     * @return Account
     * @see Account
     */
    public Account getDefaultAccount() {
        return getUserAccounts().getDefaultAccount();
    }

    /**
     * The ledger returns a summary of all monetary amounts entered in the accounts.
     * It is the principal API call for getting the total overview of the monetary status of the current user.
     *
     * The API will return the response as a JSONObject with monetary totals for account int
     *
     * @param account A valid account
     */

    public AccountLedgers getAccountLedgers(Account account) {
        LOG.info("Entering getAccountLedgers");
        if (accountLedgersTTL.isStale()) {
            accountLedgersTTL.resetRequestTime();

            String accountId = account.getAccountIdAsString();
            Invocation.Builder invocation = webTarget.path(API).path(accountId).path("ledgers").request(responseType);

            Response response = RequestHandler.GET(invocation);
            JSONObject jsonObject = ResponseHandler.asJsonObject(response);

            this.accountLedgers = new AccountLedgers(jsonObject);
        }
        return this.accountLedgers;
    }

        /*

    public JSONObject accountOrders(int accountNumber) {
        return accountOrders(accountNumber, false);
    }

    public JSONObject accountOrders(int accountNumber, boolean deleted) {
        String accno = String.valueOf(accountNumber);
        Response response = api.path("accounts").path(accno).path("orders").request(json).get();
        return ResponseHandler.asJsonObject(response);
    } */

    public void createOrder() {
        //https://api.test.nordnet.se/api-docs/index.html?#!/accounts/create_order
    }

    public void activateOrder() {
        //https://api.test.nordnet.se/api-docs/index.html?#!/accounts/order_activation
    }

    public void modifyOrder() {
        //https://api.test.nordnet.se/api-docs/index.html?#!/accounts/modify_order
    }

    public void deleteOrder() {
        //https://api.test.nordnet.se/api-docs/index.html?#!/accounts/delete_order
    }

    public void getPosition() {
        //https://api.test.nordnet.se/api-docs/index.html?#!/accounts/get_positions
    }

    public void getTrades() {
        //https://api.test.nordnet.se/api-docs/index.html?#!/accounts/get_trades
    }



}
