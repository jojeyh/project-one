package com.revature.controller;

import com.revature.dto.AddReimbursementDTO;
import com.revature.dto.ResponseReimbursementDTO;
import com.revature.model.Reimbursement;
import com.revature.service.JWTService;
import com.revature.service.ReimbursementService;
import com.revature.utility.Mapper;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.http.UploadedFile;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.json.JSONObject;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;

public class ReimbursementController implements Controller {

    private JWTService jwtService;
    private ReimbursementService reimbursementService;

    public ReimbursementController() {
        this.reimbursementService = new ReimbursementService();
        this.jwtService = JWTService.getInstance();
    }

    private Handler addReimbursement = ctx -> {

        String jwt = ctx.header("Authorization").split(" ")[1];
        Jws<Claims> token = this.jwtService.parseJwt(jwt);

        if (!token.getBody().get("user_role").equals("employee")) {
            throw new UnauthorizedResponse("Only employees can access this endpoint");
        }

        Integer userId = Integer.parseInt(ctx.pathParam("user_id"));

        if (!token.getBody().get("user_id").equals(userId)) {
            throw new UnauthorizedResponse("You may only submit reimbursements for yourself");
        }

        Timestamp submitted = new Timestamp(System.currentTimeMillis());

        AddReimbursementDTO dto = new AddReimbursementDTO();
        dto.setSubmitted(submitted);
        dto.setUserId(userId);
        dto.setAmount(Integer.parseInt(ctx.formParam("amount")));
        dto.setDescription(ctx.formParam("description"));
        dto.setType(ctx.formParam("type"));

        UploadedFile file = ctx.uploadedFile("receipt");
        InputStream is = file.getContent();
        dto.setReceipt(is);

        boolean added = this.reimbursementService.addReimbursement(Mapper.dtoToReimb(dto));

        ctx.status(201);
        ctx.json(added);
    };

    private Handler getAllReimbursements = ctx -> {

        String jwt = ctx.header("Authorization").split(" ")[1];

        Jws<Claims> token = this.jwtService.parseJwt(jwt);

        if (!token.getBody().get("user_role").equals("finmanager")) {
            throw new UnauthorizedResponse("You must be a manager to access all reimbursements");
        }

        List<ResponseReimbursementDTO> reimbursements = this.reimbursementService.getAllReimbursements();

        ctx.status(201);
        ctx.json(reimbursements);
    };

    private Handler getEmployeeReimbursements = ctx -> {

        String jwt = ctx.header("Authorization").split(" ")[1];

        Jws<Claims> token = this.jwtService.parseJwt(jwt);

        Integer user_id = Integer.parseInt(ctx.pathParam("user_id"));

        // TODO refactor this into a helper function
        if (!token.getBody().get("user_role").equals("employee") || !token.getBody().get("user_id").equals(user_id)) {
            throw new UnauthorizedResponse("You are not authorized to view these reimbursements");
        }

        List<ResponseReimbursementDTO> reimbursements = reimbursementService.getEmployeeReimbursements(user_id);

        ctx.status(201);
        ctx.json(reimbursements);
    };

    private Handler updateReimbStatus = ctx -> {

        String jwt = ctx.header("Authorization").split(" ")[1];

        Jws<Claims> token = this.jwtService.parseJwt(jwt);

        Integer user_id = Integer.parseInt(ctx.pathParam("user_id"));

        if (!token.getBody().get("user_role").equals("finmanager") || !token.getBody().get("user_id").equals(user_id)) {
            throw new UnauthorizedResponse("You are not authorized to update this reimbursement");
        }

        Integer reimb_id = Integer.parseInt(ctx.pathParam("reimb_id"));
        String status = ctx.queryParam("status");

        boolean ok = this.reimbursementService.updateReimbStatus(reimb_id, status);

        ctx.status(201);
        ctx.json(ok);
    };

    @Override
    public void mapEndpoints(Javalin app) {
        app.get("/reimbursements", getAllReimbursements);
        app.post("/user/{user_id}/reimbursements", addReimbursement);
        app.get("/user/{user_id}/reimbursements", getEmployeeReimbursements);
        app.patch("/user/{user_id}/reimbursements/{reimb_id}", updateReimbStatus);
    }
}
