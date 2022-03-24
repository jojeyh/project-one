package com.revature.dao;

import com.revature.model.Reimbursement;
import com.revature.utility.ConnectionUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementDAO {

    Logger logger = LoggerFactory.getLogger(ReimbursementDAO.class);

    // TODO going to have to handle author, type conversions between int/string somewhere, possibly in front end
    public Reimbursement addReimbursement(Reimbursement reimbursement) {
        try (Connection conn = ConnectionUtility.getConnection()) {
            String query = "INSERT INTO ers_reimbursement " +
                    "(reimb_amount, reimb_submitted, reimb_description, reimb_receipt, " +
                    "reimb_author, reimb_status_id, reimb_type_id) " +
                    "VALUES " +
                    "(?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, reimbursement.getAmount());
            stmt.setTimestamp(2, reimbursement.getSubmitted());
            stmt.setString(3, reimbursement.getDescription());
            stmt.setBytes(4, reimbursement.getImage());
            stmt.setInt(5, reimbursement.getAuthor());
            stmt.setInt(6, reimbursement.getStatus());
            stmt.setInt(7, reimbursement.getReimb_type());

            if (stmt.executeUpdate() == 1) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    logger.info("Reimbursement added to database with ID " + rs.getInt(1));

                    Reimbursement added = getReimbursementById(rs.getInt(1));
                    return added;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Reimbursement getReimbursementById(int id) {
        try (Connection conn = ConnectionUtility.getConnection()) {

            String query = "SELECT * FROM ers_reimbursement WHERE reimb_id = ?";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                logger.info("Reimbursement with ID " + id + " was selected from database");
                return new Reimbursement(
                        rs.getInt("reimb_id"),
                        rs.getInt("reimb_amount"),
                        rs.getTimestamp("reimb_submitted"),
                        rs.getTimestamp("reimb_resolved"),
                        rs.getString("reimb_description"),
                        rs.getBytes("reimb_receipt"),
                        rs.getInt("reimb_author"),
                        rs.getInt("reimb_resolver"),
                        rs.getInt("reimb_status_id"),
                        rs.getInt("reimb_type_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

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
                        rs.getInt("reimb_type_id")
                ));
            }

            return reimbursements;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Reimbursement> getEmployeeReimbursements(Integer user_id) {
        try (Connection conn = ConnectionUtility.getConnection()) {
            String query = "SELECT * FROM ers_reimbursement WHERE reimb_author = ?";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, user_id);

            ResultSet rs = stmt.executeQuery();

            List<Reimbursement> reimbursements = new ArrayList<>();
            while (rs.next()) {
                reimbursements.add(new Reimbursement(
                        rs.getInt("reimb_id"),
                        rs.getInt("reimb_amount"),
                        rs.getTimestamp("reimb_submitted"),
                        rs.getTimestamp("reimb_resolved"),
                        rs.getString("reimb_description"),
                        rs.getBytes("reimb_receipt"),
                        rs.getInt("reimb_author"),
                        rs.getInt("reimb_resolver"),
                        rs.getInt("reimb_status_id"),
                        rs.getInt("reimb_type_id")
                ));
            }
            return reimbursements;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Reimbursement updateReimbStatus(Integer reimb_id, String status) {
        try (Connection conn = ConnectionUtility.getConnection()) {
            String query = "UPDATE ers_reimbursement SET reimb_status_id = " +
                    "(SELECT reimb_status_id FROM ers_reimbursement_status WHERE reimb_status = ?) " +
                    " WHERE reimb_id = ?";

            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, status);
            stmt.setInt(2, reimb_id);

            if (stmt.executeUpdate() == 1) {
                PreparedStatement _stmt = conn.prepareStatement(
                        "SELECT * FROM ers_reimbursement WHERE reimb_id = ?"
                );
                _stmt.setInt(1, reimb_id);
                ResultSet rs = _stmt.executeQuery();
                if (rs.next()) {
                    return new Reimbursement(
                            rs.getInt("reimb_id"),
                            rs.getInt("reimb_amount"),
                            rs.getTimestamp("reimb_submitted"),
                            rs.getTimestamp("reimb_resolved"),
                            rs.getString("reimb_description"),
                            rs.getBytes("reimb_receipt"),
                            rs.getInt("reimb_author"),
                            rs.getInt("reimb_resolver"),
                            rs.getInt("reimb_status_id"),
                            rs.getInt("reimb_type_id")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
