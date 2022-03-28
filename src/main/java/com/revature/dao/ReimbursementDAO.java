package com.revature.dao;

import com.revature.dto.ResponseReimbursementDTO;
import com.revature.model.Reimbursement;
import com.revature.utility.ConnectionUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementDAO {

    Logger logger = LoggerFactory.getLogger(ReimbursementDAO.class);

    public boolean addReimbursement(Reimbursement reimbursement) {
        try (Connection conn = ConnectionUtility.getConnection()) {
            String query = "INSERT INTO ers_reimbursement " +
                    "(reimb_amount, reimb_submitted, reimb_description, " +
                    "reimb_receipt, reimb_author, reimb_status_id, reimb_type_id) " +
                    "VALUES " +
                    "(?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, reimbursement.getReimb_amount());
            stmt.setTimestamp(2, reimbursement.getReimb_submitted());
            stmt.setString(3, reimbursement.getReimb_description());
            stmt.setBinaryStream(4, reimbursement.getReimb_receipt());
            stmt.setInt(5, reimbursement.getReimb_author());
            stmt.setInt(6, reimbursement.getReimb_status_id());
            stmt.setInt(7, reimbursement.getReimb_type_id());

            if (stmt.executeUpdate() == 1) {
                ResultSet rs = stmt.getGeneratedKeys();
                logger.info("Reimbursement added to database with ID " + rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ResponseReimbursementDTO getReimbursementById(int id) {
        try (Connection conn = ConnectionUtility.getConnection()) {

            String query = "SELECT * FROM page_render WHERE reimb_id = ?";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                logger.info("Reimbursement with ID " + id + " was selected from database");
                ResponseReimbursementDTO r = new ResponseReimbursementDTO();
                r.setId(id);
                r.setEmployee_id(rs.getInt("reimb_author"));
                r.setEmployee_name(rs.getString("user_first_name") +
                        rs.getString("user_last_name"));
                r.setType(rs.getString("reimb_type"));
                r.setDescription(rs.getString("reimb_description"));
                r.setStatus(rs.getString("reimb_status"));
                r.setSubmitted(rs.getTimestamp("reimb_submitted"));
                r.setResolved(rs.getTimestamp("reimb_resolved"));
                r.setAmount(rs.getInt("reimb_amount"));
                return r;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ResponseReimbursementDTO> getAllReimbursements() {
        try (Connection conn = ConnectionUtility.getConnection()) {
            String query = "SELECT * FROM page_render";
            PreparedStatement stmt = conn.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
            logger.info("Database op: " + query);

            List<ResponseReimbursementDTO> reimbursements = new ArrayList<>();

            while (rs.next()) {
                ResponseReimbursementDTO r = new ResponseReimbursementDTO();
                r.setId(rs.getInt("reimb_id"));
                r.setEmployee_id(rs.getInt("reimb_author"));
                r.setEmployee_name(rs.getString("user_first_name") +
                        rs.getString("user_last_name"));
                r.setType(rs.getString("reimb_type"));
                r.setDescription(rs.getString("reimb_description"));
                r.setStatus(rs.getString("reimb_status"));
                r.setSubmitted(rs.getTimestamp("reimb_submitted"));
                r.setResolved(rs.getTimestamp("reimb_resolved"));
                r.setAmount(rs.getInt("reimb_amount"));

                reimbursements.add(r);
            }

            return reimbursements;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<ResponseReimbursementDTO> getEmployeeReimbursements(Integer user_id) {
        try (Connection conn = ConnectionUtility.getConnection()) {
            String query = "SELECT * FROM page_render WHERE reimb_author = ?";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, user_id);

            ResultSet rs = stmt.executeQuery();
            logger.info("Database op: " + query);

            List<ResponseReimbursementDTO> reimbursements = new ArrayList<>();
            while (rs.next()) {
                ResponseReimbursementDTO r = new ResponseReimbursementDTO();
                r.setId(rs.getInt("reimb_id"));
                r.setEmployee_id(rs.getInt("reimb_author"));
                r.setEmployee_name(rs.getString("user_first_name") + " " +
                        rs.getString("user_last_name"));
                r.setType(rs.getString("reimb_type"));
                r.setDescription(rs.getString("reimb_description"));
                r.setStatus(rs.getString("reimb_status"));
                r.setSubmitted(rs.getTimestamp("reimb_submitted"));
                r.setResolved(rs.getTimestamp("reimb_resolved"));
                r.setAmount(rs.getInt("reimb_amount"));

                reimbursements.add(r);
            }
            return reimbursements;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateReimbStatus(Integer reimb_id, String status) {
        try (Connection conn = ConnectionUtility.getConnection()) {
            String query = "UPDATE ers_reimbursement SET reimb_status_id = " +
                    "(SELECT reimb_status_id FROM ers_reimbursement_status WHERE reimb_status = ?) " +
                    " WHERE reimb_id = ?";

            PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

            stmt.setString(1, status);
            stmt.setInt(2, reimb_id);

            if (stmt.executeUpdate() == 1) {
                logger.info("Database op: " + query);
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    logger.info("Updated reimbursement with ID " + rs.getInt(1) + " to status " + status);
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public InputStream getReimbursementImage(int reimbursementId, int userId) throws SQLException {
        try (Connection conn = ConnectionUtility.getConnection()) {
            String query = "SELECT reimb_receipt FROM ers_reimbursement " +
                    "WHERE reimb_id = ? AND reimb_author = ?";

            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, reimbursementId);
            stmt.setInt(2, userId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                InputStream is = rs.getBinaryStream("reimb_receipt");
                return is;
            } else {
                return null;
            }
        }
    }
}
