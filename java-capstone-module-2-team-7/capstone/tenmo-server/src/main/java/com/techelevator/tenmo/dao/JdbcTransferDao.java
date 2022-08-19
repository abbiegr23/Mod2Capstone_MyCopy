package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Transfer> listAllTransfers() {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "FROM transfer;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            transfers.add(transfer);
        }
        return transfers;
    }

    @Override
    public List<Transfer> findTransfersByUsername(String username) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT u.username, u.user_id, t.transfer_id, t.transfer_type_id, t.transfer_status_id, t.account_from, t.account_to, t.amount" +
                "FROM tenmo_user u" +
                "JOIN account a ON a.user_id = u.user_id " +
                "JOIN transfer t ON a.account_id IN (t.account_from, t.account_to)" +
                "WHERE u.username = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, username);
        while (rowSet.next()) {
            Transfer transfer = mapRowToTransfer(rowSet);
            transfers.add(transfer);
        }
        //throw new UsernameNotFoundException("User " + username + " was not found.");

        return transfers;
    }

    @Override
    public List<Transfer> findTransfersByUserId(long id) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT u.username, u.user_id, t.transfer_id, t.transfer_type_id, t.transfer_status_id, t.account_from, t.account_to, t.amount" +
                "FROM tenmo_user u" +
                "JOIN account a ON a.user_id = u.user_id " +
                "JOIN transfer t ON a.account_id IN (t.account_from, t.account_to)" +
                "WHERE u.user_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);
        while (rowSet.next()) {
            Transfer transfer = mapRowToTransfer(rowSet);
            transfers.add(transfer);
        }
        //throw new UsernameNotFoundException("User ID " + id + " was not found.");

        return transfers;
    }

    @Override
    public Transfer findTransferByTransferId(Long id) {
        Transfer transfer = new Transfer();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "FROM transfer " +
                "WHERE transfer_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);
        if (rowSet.next()) {
            transfer = mapRowToTransfer(rowSet);
        }
        return transfer;
    }

    @Override
    public BigDecimal getBalanceByAccountId(Long accountId) {
        BigDecimal balance;
        String sql = "SELECT balance FROM account WHERE account_id = ?;";
        balance = jdbcTemplate.queryForObject(sql, BigDecimal.class, accountId);
        return balance;
    }

    // TODO need to be able to initiate a transfer
    // create a transferDto
    public Transfer initiateSendTransfer(Long fromAccount, Long toAccount, BigDecimal amountToSend) {

        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "VALUES (2, ?, ?, ?, ?) RETURNING transfer_id;";
        Long newId;

        if (amountToSend.compareTo(new BigDecimal(0)) < 0) {
            newId = jdbcTemplate.queryForObject(sql, Long.class, 2, 3, fromAccount, toAccount, amountToSend);
        } else if (fromAccount.equals(toAccount)) { //dont let yourself send money to yourself
            newId = jdbcTemplate.queryForObject(sql, Long.class, 2, 3, fromAccount, toAccount, amountToSend);
        }  else if (getBalanceByAccountId(fromAccount).compareTo(amountToSend) < 0 ) {
            newId = jdbcTemplate.queryForObject(sql, Long.class, 2, 3, fromAccount, toAccount, amountToSend);
        } else {
            newId = jdbcTemplate.queryForObject(sql, Long.class, 2, 2, fromAccount, toAccount, amountToSend);
        }
        return findTransferByTransferId(newId);
    }
    //private Long typeId; // 1 is request, 2 is send
    //private Long statusId; // 1 pending, 2 approved, 3 rejected


    private Transfer mapRowToTransfer(SqlRowSet rowSet) {
        Transfer transfer = new Transfer();
        transfer.setId(rowSet.getLong("transfer_id"));
        transfer.setTypeId(rowSet.getLong("transfer_type_id"));
        transfer.setStatusId(rowSet.getLong("transfer_status_id"));
        transfer.setAccountFrom(rowSet.getLong("account_from"));
        transfer.setAccountTo(rowSet.getLong("account_to"));
        transfer.setTransferAmount(rowSet.getBigDecimal("amount"));
        return transfer;
    }

}
