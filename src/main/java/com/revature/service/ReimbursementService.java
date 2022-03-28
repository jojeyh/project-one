package com.revature.service;

import com.revature.dao.ReimbursementDAO;
import com.revature.dto.ResponseReimbursementDTO;
import com.revature.exception.ImageNotFoundException;
import com.revature.model.Reimbursement;

import java.io.InputStream;
import java.sql.SQLException;
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

    public InputStream getReimbursementImage(String reimbursementId, String userId) throws ImageNotFoundException {
        try {
            int rId = Integer.parseInt(reimbursementId);
            int uId = Integer.parseInt(userId);

            InputStream is = this.reimbursementDAO.getReimbursementImage(rId, uId);

            if (is == null) {
                throw new ImageNotFoundException("Reimbursement " + reimbursementId + " does not have an image");
            }

            return is;
        } catch(NumberFormatException e) {
            throw new IllegalArgumentException("Reimbursement and/or user id must be an int value");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
