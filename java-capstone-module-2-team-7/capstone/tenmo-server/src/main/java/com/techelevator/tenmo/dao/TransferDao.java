package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    List<Transfer> listAllTransfers();

    List<Transfer> findTransfersByUsername(String username);

    List<Transfer> findTransfersByUserId(long id);

    Transfer findTransferByTransferId(Long id);

    BigDecimal getBalanceByAccountId(Long accountId);


}
