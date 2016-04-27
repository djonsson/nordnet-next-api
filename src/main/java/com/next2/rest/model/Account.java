package com.next2.rest.model;

import org.json.JSONObject;

/**
 * Account
 * Returns a specific user account (account ID). A user can have several trading accounts.
 *
 * @see AccountList
 * @see AccountSummary
 *
 * accountId (integer):
 *      The account number of the account to use.
 *
 * TYPE (string):
 *      Translated account type. Can be displayed for the end user.
 *
 * isDefault (boolean):
 *      True if this is the default account.
 *
 * alias (string):
 *      Account alias can be set on Nordnet Web by the end user.
 *
 * isBlocked (boolean, optional):
 *      True if the account is blocked. No queries can be made to this account,
 *
 * blocked_reason (string, optional):
 *      Description to why the account is blocked.
 *      The language specified in the request is used in this reply so it can be displayed to the end user
 *
 */

public class Account {
    Integer accountId;
    String  accountType;
    Boolean isDefault;
    String  alias;
    Boolean isBlocked;
    String  blockedReason;

    public Account(JSONObject jsonObject) {
        this.accountId       = jsonObject.getInt("accno");
        this.accountType     = jsonObject.getString("type");
        this.isDefault       = jsonObject.getBoolean("default");
        this.alias           = jsonObject.getString("alias");
        this.isBlocked       = jsonObject.optBoolean("is_blocked", false);
        this.blockedReason   = jsonObject.optString("blocked_reason", "");
    }

    public int getAccountId() {
        return this.accountId;
    }

    public String getAccountIdAsString() {
        return String.valueOf(this.accountId);
    }

    public String getAccountType() {
        return this.accountType;
    }

    public boolean getIsDefault() {
        return this.isDefault;
    }

    public String getAlias() {
        return this.alias;
    }

    public boolean getIsBlocked() {
        return this.isBlocked;
    }

    public String getBlockedReason() {
        return this.blockedReason;
    }
}

