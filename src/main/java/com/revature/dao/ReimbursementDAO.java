package com.revature.dao;

import com.revature.model.Reimbursement;
import com.revature.utility.ConnectionUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementDAO {

    public List<Reimbursement> getAllReimbursements() {
        try (Connection conn = ConnectionUtility.getConnection()) {
            String query = "SELECT * FROM ers_reimbursement";
            PreparedStatement stmt = conn.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();

            List<Reimbursement> reimbursements = new ArrayList<>();

            while (rs.next()) {
                reimbursements.add(new Reimbursement(
                        rs.getInt("reimb_id"),
                        rs.getInt("reimb_amount"),
                        rs.getTimestamp("reimb_submitted"),
                        rs.getString("reimb_description"),
                        rs.getBytes("reimb_receipt"),
                        rs.getInt("reimb_author"),
                        rs.getInt("reimb_resolver"),
                        rs.getInt("reimb_status_id"),
                        rs.getString("reimb_type_id")
                ));
            }

            return reimbursements;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
