package com.revature.dao;

import com.revature.model.User;

import javax.sql.RowSet;

public interface UserDao {
    public int insertUser();
    public boolean deleteUser();
    public User findUser();
    public boolean updateCustomer();
    public RowSet selectCustomerRS();
}
