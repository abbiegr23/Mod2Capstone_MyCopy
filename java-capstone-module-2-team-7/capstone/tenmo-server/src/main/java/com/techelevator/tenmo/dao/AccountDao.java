package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {

    List<Account> listAllAccounts();

    Account findAccountByUsername(String username);

    BigDecimal getBalance(Long userID);

    List<Account> getAccounts();

    Account findAccountByID(int id);

    Account subtractTransferAmount(Long accountId, BigDecimal amountToSubtract);

    Account addTransferAmount(Long accountId, BigDecimal amountToSubtract);
}
