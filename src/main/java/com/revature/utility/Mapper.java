package com.revature.utility;

import com.revature.dto.AddReimbursementDTO;
import com.revature.model.Reimbursement;

public class Mapper {
    public static Reimbursement dtoToReimb(AddReimbursementDTO dto) {
        Reimbursement reimbursement = new Reimbursement();

        reimbursement.setReimb_amount(dto.getAmount());
        reimbursement.setReimb_author(dto.getUserId());
        reimbursement.setReimb_description(dto.getDescription());
        reimbursement.setReimb_submitted(dto.getSubmitted());
        reimbursement.setReimb_status_id(Status.pending.num);
        reimbursement.setReimb_type_id(Type.valueOf(dto.getType()).num);
        reimbursement.setReimb_receipt(dto.getReceipt());
        return reimbursement;
    }

    public enum Status {
        pending(1),
        approved(2),
        rejected(3);

        public final int num;

        private Status(int num) {
            this.num = num;
        }
    }

    public enum Type {
        lodging(1),
        travel(2),
        food(3),
        other(4);

        public final int num;

        private Type(int num) {
            this.num = num;
        }
    }
}
