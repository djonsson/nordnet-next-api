package com.next2.rest.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * AccountList
 * Returns an account list containing user accounts.
 *
 * accountList (ArrayList):
 *      Can contain one or more user accounts
 *
 * @see Account
 * @see AccountSummary
 *
 */

public class AccountList {

    List<Account> accountList = new ArrayList<>();

    public AccountList(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            Account account = new Account(item);
            add(account);
        }
    }

    public void add(Account account) {
        this.accountList.add(account);
    }

    public Account getDefaultAccount() {
        for (Account account : this.accountList) {
            if (account.getIsDefault()) {
                return account;
            }
        }
        return null;
    }

    public int getNumberOfAccounts() {
        return this.accountList.size();
    }

}
