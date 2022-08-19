package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {

    private AccountDao accountDao;

    public AccountController(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    @RequestMapping(path = "/account", method = RequestMethod.GET)
    public List<Account> getAccounts(){
        return accountDao.getAccounts();
    }

    @RequestMapping(path = "/account/{id}", method = RequestMethod.GET)
    public Account account(@PathVariable int id){
        return accountDao.findAccountByID(id);
    }

    @RequestMapping(path = "/account/{id}/balance", method = RequestMethod.GET)
    public BigDecimal getAccountBalance(@PathVariable long id, Principal principal) {
        return accountDao.getBalance(id);
    }

    @RequestMapping(path = "/account/{id}", method = RequestMethod.PUT)
    public void subtractTransferFromBalance(@PathVariable long id, @RequestParam double amountToSubtract) {
        accountDao.subtractTransferAmount(id, BigDecimal.valueOf(amountToSubtract));
    }

    @RequestMapping(path = "/account/{id}", method = RequestMethod.PUT)
    public void addTransferToBalance(@PathVariable long id, @RequestParam double amountToAdd) {
        accountDao.addTransferAmount(id, BigDecimal.valueOf(amountToAdd));
    }

}
