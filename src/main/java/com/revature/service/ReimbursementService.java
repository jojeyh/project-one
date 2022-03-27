package com.revature.service;

import com.revature.controller.ReimbursementController;
import com.revature.dao.ReimbursementDAO;
import com.revature.dto.AddReimbursementDTO;
import com.revature.dto.ResponseReimbursementDTO;
import com.revature.model.Reimbursement;
import com.revature.utility.Mapper;

import java.util.List;

public class ReimbursementService {

    private ReimbursementDAO reimbursementDAO;

    public ReimbursementService() {
        this.reimbursementDAO = new ReimbursementDAO();
    }

    public ReimbursementService(ReimbursementDAO mockDao) {
        this.reimbursementDAO = mockDao;
    }

    public boolean addReimbursement(Reimbursement reimbursement) {
        boolean added = this.reimbursementDAO.addReimbursement(reimbursement);

        return added;
    };

    public List<ResponseReimbursementDTO> getAllReimbursements() {
        List<ResponseReimbursementDTO> reimbursements = this.reimbursementDAO.getAllReimbursements();

        return reimbursements;
    }

    public List<ResponseReimbursementDTO> getEmployeeReimbursements(Integer user_id) {
        List<ResponseReimbursementDTO> reimbursements = this.reimbursementDAO.getEmployeeReimbursements(user_id);

        return reimbursements;
    }

    public boolean updateReimbStatus(Integer reimb_id, String status) {
        boolean ok = this.reimbursementDAO.updateReimbStatus(reimb_id, status);

        return ok;
    }
}
