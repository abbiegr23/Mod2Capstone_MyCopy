package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

    // can a transfer occur?
    // transfer_type_id : 1 is request, 2 is send
    // transfer_status_id : 1 pending, 2 approved, 3 rejected

    private TransferDao transferDao;

    public TransferController(TransferDao transferDao) {
        this.transferDao = transferDao;
    }

    public TransferDao getTransferDao() {
        return transferDao;
    }

    @RequestMapping(path = "/users/{id}/transfers", method = RequestMethod.GET)
    public List<Transfer> getTransferHistory(@PathVariable long id, Principal principal) {
        List<Transfer> transfers = new ArrayList<>();
        transfers = transferDao.findTransfersByUserId(id);
        return transfers;
    }



}
