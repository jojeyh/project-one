package com.revature.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.revature.dto.ReimbursementDTO;
import com.revature.model.Reimbursement;
import com.revature.service.JWTService;
import com.revature.service.ReimbursementService;
import com.revature.service.UserService;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.json.JSONObject;

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

        Integer user_id = Integer.parseInt(ctx.pathParam("user_id"));

        if (!token.getBody().get("user_role").equals("employee")) {
            throw new UnauthorizedResponse("Only employees can access this endpoint");
        }

        if (!token.getBody().get("user_id").equals(user_id)) {
            throw new UnauthorizedResponse("You may only submit reimbursements for yourself");
        }

        Timestamp submitted = new Timestamp(System.currentTimeMillis());

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        Reimbursement reimbursement = gson.fromJson(ctx.body(), Reimbursement.class);
        reimbursement.setSubmitted(submitted);

        Reimbursement added = this.reimbursementService.addReimbursement(reimbursement);

        ctx.json(added);
    };

    private Handler getAllReimbursements = ctx -> {

        String jwt = ctx.header("Authorization").split(" ")[1];

        Jws<Claims> token = this.jwtService.parseJwt(jwt);

        if (!token.getBody().get("user_role").equals("finmanager")) {
            throw new UnauthorizedResponse("You must be a manager to access all reimbursements");
        }

        List<Reimbursement> reimbursements = reimbursementService.getAllReimbursements();

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

        List<Reimbursement> reimbursements = reimbursementService.getEmployeeReimbursements(user_id);

        ctx.json(reimbursements);
    };

    private Handler updateReimbStatus = ctx -> {

        String jwt = ctx.header("Authorization").split(" ")[1];

        Jws<Claims> token = this.jwtService.parseJwt(jwt);

        Integer user_id = Integer.parseInt(ctx.pathParam("user_id"));
        if (!token.getBody().get("user_role").equals("finmanager") || !token.getBody().get("user_id").equals(user_id)) {
            throw new UnauthorizedResponse("You are not authorized to update this reimbursement");
        }

        JSONObject json = new JSONObject(ctx.body());
        Integer reimb_id = Integer.parseInt(ctx.pathParam("reimb_id"));
        String status = json.getString("status");

        Reimbursement reimbursement = this.reimbursementService.updateReimbStatus(reimb_id, status);

        ctx.json(reimbursement);
    };

    @Override
    public void mapEndpoints(Javalin app) {
        app.get("/reimbursements", getAllReimbursements);
        app.post("/user/{user_id}/reimbursements", addReimbursement);
        app.get("/user/{user_id}/reimbursements", getEmployeeReimbursements);
        app.patch("/user/{user_id}/reimbursements/{reimb_id}", updateReimbStatus);
    }
}
