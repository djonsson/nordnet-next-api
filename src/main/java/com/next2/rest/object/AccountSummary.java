package com.next2.rest.object;

import org.json.JSONObject;

/**
 * AccountSummary
 * Returns information about a specific Account
 *
 * @see Account
 *
 * accountCurrency (string):
 *          The account currency.
 *
 * accountCredit (long):
 *          The amount of credit allowed on the account.
 *
 * accountSum (long):
 *          The amount of cash on all ledgers combined.
 *
 * collateralSum (long):
 *          The amoun of collateral claim for options.
 *
 * creditAccountSum (long, optional):
 *          Sum for credit account if available.
 *
 * forwardSum (long):
 *          Locked amount for forwards.
 *
 * futureSum (long):
 *          Not realized profit/loss for future.
 *
 * unrealizedFutureProfitLoss (long):
 *          Unrealized profit and loss for futures.
 *
 * fullMarketValue (long):
 *          Total amount market value.
 *
 * interest (long):
 *          Amount of interest on the account.
 *
 * intradayCredit (long, optional):
 *          Intraday credit if available.
 *
 * loanLimit (long):
 *          Max loan limit (regardless of pawnvalue).
 *
 * ownCapital (long):
 *          The full amount of capital on the account. Calculated like so:
 *          accountSum + fullMarketValue + interest + forwardSum + futureSum + unrealizedFutureProfitLoss
 *
 * ownCapitalMorning (long):
 *          Own capital calculated in the morning. Updated once each day.
 *
 * pawnValue (long):
 *          Pawn value of all positions combined.
 *
 * tradingPower (long):
 *          Available amount of money for trading
 */

public class AccountSummary extends Amount {
    String accountCurrency;

    Long accountCredit;
    String accountCreditCurrency;

    Long accountSum;
    String accountSumCurrency;

    Long collateralSum;
    String collateralCurrency;

    Long creditAccountSum;
    String creditAccountCurrency;

    Long forwardSum;
    String forwardCurrency;

    Long futureSum;
    String futureSumCurrency;

    Long unrealizedFutureProfitLoss;
    String unrealizedFutureProfitLossCurrency;

    Long fullMarketValue;
    String fullMarketValueCurrency;

    Long interest;
    String interestCurrency;

    Long intradayCredit;
    String intradayCreditCurrency;

    Long loanLimit;
    String loanLimitCurrency;

    Long ownCapital;
    String ownCapitalCurrency;

    Long ownCapitalMorning;
    String ownCapitalMorningCurrency;

    Long pawnValue;
    String pawnValueCurrency;

    Long tradingPower;
    String tradingPowerCurrency;

    public AccountSummary(JSONObject jsonObject) {
        JSONObject accountCredit     = jsonObject.getJSONObject("account_credit");
        JSONObject accountSum        = jsonObject.getJSONObject("account_sum");
        JSONObject collateral        = jsonObject.getJSONObject("collateral");
        JSONObject creditAccountSum  = jsonObject.optJSONObject("credit_account_sum");
        JSONObject forwardSum        = jsonObject.getJSONObject("forward_sum");
        JSONObject futureSum         = jsonObject.getJSONObject("future_sum");
        JSONObject unrealizedFutureProfitLoss   = jsonObject.getJSONObject("unrealized_future_profit_loss");
        JSONObject fullMarketValue   = jsonObject.getJSONObject("full_marketvalue");
        JSONObject interest          = jsonObject.getJSONObject("interest");
        JSONObject intradayCredit    = jsonObject.optJSONObject("intraday_credit");
        JSONObject loanLimit         = jsonObject.getJSONObject("loan_limit");
        JSONObject ownCapital        = jsonObject.getJSONObject("own_capital");
        JSONObject ownCapitalMorning = jsonObject.getJSONObject("own_capital_morning");
        JSONObject pawnValue         = jsonObject.getJSONObject("pawn_value");
        JSONObject tradingPower      = jsonObject.getJSONObject("trading_power");

        this.accountCurrency        = jsonObject.getString("account_currency");
        this.accountCredit          = accountCredit.getLong("value");
        this.accountCreditCurrency  = accountCredit.getString("currency");
        this.accountSum             = accountSum.getLong("value");
        this.accountSumCurrency     = accountSum.getString("currency");
        this.collateralSum          = collateral.getLong("value");
        this.collateralCurrency     = collateral.getString("currency");
        this.forwardSum             = forwardSum.getLong("value");
        this.forwardCurrency        = forwardSum.getString("currency");
        this.futureSum              = futureSum.getLong("value");
        this.futureSumCurrency      = futureSum.getString("currency");
        this.unrealizedFutureProfitLoss         = unrealizedFutureProfitLoss.getLong("value");
        this.unrealizedFutureProfitLossCurrency = unrealizedFutureProfitLoss.getString("currency");
        this.fullMarketValue            = fullMarketValue.getLong("value");
        this.fullMarketValueCurrency    = fullMarketValue.getString("currency");
        this.interest               = interest.getLong("value");
        this.interestCurrency       = interest.getString("currency");
        this.loanLimit              = loanLimit.getLong("value");
        this.loanLimitCurrency      = loanLimit.getString("currency");
        this.ownCapital             = ownCapital.getLong("value");
        this.ownCapitalCurrency     = ownCapital.getString("currency");
        this.ownCapitalMorning          = ownCapitalMorning.getLong("value");
        this.ownCapitalMorningCurrency  = ownCapitalMorning.getString("currency");
        this.pawnValue              = pawnValue.getLong("value");
        this.pawnValueCurrency      = pawnValue.getString("currency");
        this.tradingPower           = tradingPower.getLong("value");
        this.tradingPowerCurrency   = tradingPower.getString("currency");
        if (!(creditAccountSum == null)) {
            this.creditAccountSum       = creditAccountSum.getLong("value");
            this.creditAccountCurrency  = creditAccountSum.getString("currency");
        }
        if (!(intradayCredit == null)) {
            this.intradayCredit         = intradayCredit.getLong("value");
            this.intradayCreditCurrency = intradayCredit.getString("currency");
        }
    }

    public String getAccountCurrency() {
        return accountCurrency;
    }

    public Amount getAccountCreditAmount() {
        return new Amount(accountCredit, accountCreditCurrency);
    }

    public Amount getAccountAmount() {
        return new Amount(accountSum, accountSumCurrency);
    }

    public Amount getCollateralAmount() {
        return new Amount(collateralSum, collateralCurrency);
    }

    public Amount getFutureAmount() {
        return new Amount(futureSum, futureSumCurrency);
    }

    public Amount getForwardAmount() {
        return new Amount(forwardSum, forwardCurrency);
    }

    public Amount getUnrealizedFutureProfitOrLoss() {
        return new Amount(unrealizedFutureProfitLoss, unrealizedFutureProfitLossCurrency);
    }

    public Amount getFullMarketValueAmount() {
        return new Amount(fullMarketValue, fullMarketValueCurrency);
    }

    public Amount getInterestAmount() {
        return new Amount(interest, interestCurrency);
    }

    public Amount getLoanLimitAmount() {
        return new Amount(loanLimit, loanLimitCurrency);
    }

    public Amount getOwnCapitalAmount() {
        return new Amount(ownCapital, ownCapitalCurrency);
    }

    public Amount getOwnCapitalMorningAmount() {
        return new Amount(ownCapitalMorning, ownCapitalMorningCurrency);
    }

    public Amount getPawnValueAmount() {
        return new Amount(pawnValue, pawnValueCurrency);
    }

    public Amount getTradingPowerAmount() {
        return new Amount(tradingPower, tradingPowerCurrency);
    }

    public Amount getCreditAccountAmount() {
        return new Amount(creditAccountSum, creditAccountCurrency);
    }

    public Amount getIntradayCreditAmount() {
        return new Amount(intradayCredit, intradayCreditCurrency);
    }

    public boolean checkIfIntradayCreditIsAvailable(){
        return intradayCredit != null;
    }

    public boolean checkIfCreditIsAvailable(){
        return creditAccountSum != null;
    }
}
