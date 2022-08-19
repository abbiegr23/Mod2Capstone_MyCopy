package com.techelevator.tenmo.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Transfer {

    private Long id;
    private Long typeId; // 1 is request, 2 is send
    private Long statusId; // 1 pending, 2 approved, 3 rejected
    private Long accountFrom;
    private Long accountTo;
    private BigDecimal transferAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(Long accountFrom) {
        this.accountFrom = accountFrom;
    }

    public Long getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(Long accountTo) {
        this.accountTo = accountTo;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transfer transfer = (Transfer) o;
        return Objects.equals(id, transfer.id) && Objects.equals(typeId, transfer.typeId) && Objects.equals(statusId, transfer.statusId) && Objects.equals(accountFrom, transfer.accountFrom) && Objects.equals(accountTo, transfer.accountTo) && Objects.equals(transferAmount, transfer.transferAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, typeId, statusId, accountFrom, accountTo, transferAmount);
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "id=" + id +
                ", typeId=" + typeId +
                ", statusId=" + statusId +
                ", accountFrom=" + accountFrom +
                ", accountTo=" + accountTo +
                ", transferAmount=" + transferAmount +
                '}';
    }
}
