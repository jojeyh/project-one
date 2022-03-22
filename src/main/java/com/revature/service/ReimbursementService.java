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

    public List<Reimbursement> getAllReimbursements() {
        List<Reimbursement> reimbursements = this.reimbursementDAO.getAllReimbursements();

        return reimbursements;
    }
}
