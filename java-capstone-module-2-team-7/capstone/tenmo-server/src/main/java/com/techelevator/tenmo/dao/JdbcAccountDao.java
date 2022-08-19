package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao {

    private static final BigDecimal STARTING_BALANCE = new BigDecimal("1000.00");
    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Account> listAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT account_id, user_id, balance FROM account;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            Account account = mapRowToAccount(results);
            accounts.add(account);
        }
        return accounts;
    }

    @Override
    public Account findAccountByUsername(String username) throws UsernameNotFoundException {
        String sql = "SELECT u.username, a.user_id, a.account_id, a.balance " +
                "FROM account a " +
                "JOIN tenmo_user u ON u.user_id = a.user_id " +
                "WHERE u.username = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, username);
        if (rowSet.next()) {
            return mapRowToAccount(rowSet);
        }
        throw new UsernameNotFoundException("User " + username + "was not found.");
    }


    @Override
    public BigDecimal getBalance(Long userID) {
        BigDecimal balance;
        String sql = "SELECT balance FROM account WHERE user_id = ?;";
        balance = jdbcTemplate.queryForObject(sql, BigDecimal.class, userID);
        return balance;
    }

    @Override
    public List<Account> getAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM account";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
        while (result.next()){
            Account account = mapRowToAccount(result);
            account.add(account);
        }
        return accounts;
    }

    @Override
    public Account findAccountByID(int id) {
        Account account = null;
        String sql = "SELECT * FROM account WHERE user_id ILIKE = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
        if (result.next()) {
            return mapRowToAccount(result);
        }
        return null;
    }

    @Override
    public Account subtractTransferAmount(Long accountId, BigDecimal amountToSubtract) {
        Account account = new Account();
        String sql = "UPDATE account" +
                "SET balance = balance - ?" +
                "WHERE account_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, amountToSubtract, accountId);
        if (rowSet.next()) {
            account = mapRowToAccount(rowSet);
        }
        return account;
    }

    @Override
    public Account addTransferAmount(Long accountId, BigDecimal amountToSubtract) {
        Account account = new Account();
        String sql = "UPDATE account" +
                "SET balance = balance + ?" +
                "WHERE account_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, amountToSubtract, accountId);
        if (rowSet.next()) {
            account = mapRowToAccount(rowSet);
        }
        return account;
    }

    private Account mapRowToAccount(SqlRowSet rowSet) {
        Account account = new Account();
        account.setId(rowSet.getLong("account_id"));
        account.setUserId(rowSet.getLong("user_id"));
        account.setBalance(rowSet.getBigDecimal("balance"));
        return account;
    }
}
