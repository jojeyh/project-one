package com.revature.service;

import com.revature.controller.ReimbursementController;
import com.revature.dao.ReimbursementDAO;
import com.revature.model.Reimbursement;

import java.util.List;

public class ReimbursementService {

    private ReimbursementDAO reimbursementDAO;

    public ReimbursementService() {
        this.reimbursementDAO = new ReimbursementDAO();
    }

    public ReimbursementService(ReimbursementDAO mockDao) {
        this.reimbursementDAO = mockDao;
    }

    public Reimbursement addReimbursement(Reimbursement reimbursement) {
        Reimbursement added = this.reimbursementDAO.addReimbursement(reimbursement);

        return added;
    };

    public List<Reimbursement> getAllReimbursements() {
        List<Reimbursement> reimbursements = this.reimbursementDAO.getAllReimbursements();

        return reimbursements;
    }

    public List<Reimbursement> getEmployeeReimbursements(Integer user_id) {
        List<Reimbursement> reimbursements = this.reimbursementDAO.getEmployeeReimbursements(user_id);

        return reimbursements;
    }
}
