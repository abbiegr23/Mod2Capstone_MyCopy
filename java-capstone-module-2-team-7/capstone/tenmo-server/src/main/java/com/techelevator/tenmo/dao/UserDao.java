package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserAccountDTO;

import java.math.BigDecimal;
import java.util.List;

public interface UserDao {

    List<User> findAll();

    // would like something that grabs all BUT only the username and account id.
    List<UserAccountDTO> findUsernameWithCorrespondingAccount();

    User findByUsername(String username);

    int findIdByUsername(String username);

    boolean create(String username, String password);

    // boolean addToAccountBalance(Long userID, BigDecimal moneyToAdd);

    // boolean subtractFromAccountBalance(Long userID, BigDecimal moneyToSubtract);

    // create transfer
    // int initiateTransfer(Long toAccountID, Long fromAccountID, BigDecimal amountToTransfer);





    /*
    Reservation get(int reservationID) throws ReservationNotFoundException;

    Reservation create(Reservation reservation, int hotelID) throws HotelNotFoundException;

    Reservation update(Reservation reservation, int id) throws ReservationNotFoundException;

    void delete(int id) throws ReservationNotFoundException;
    */
}
