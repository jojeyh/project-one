package com.revature.controller;

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

import java.util.List;

public class ReimbursementController implements Controller {

    private JWTService jwtService;
    private ReimbursementService reimbursementService;

    public ReimbursementController() {
        this.reimbursementService = new ReimbursementService();
        this.jwtService = JWTService.getInstance();
    }

    private Handler getAllReimbursements = ctx -> {

        String jwt = ctx.header("Authorization").split(" ")[1];

        Jws<Claims> token = this.jwtService.parseJwt(jwt);

        if (!token.getBody().get("user_role").equals("finmanager")) {
            throw new UnauthorizedResponse("You must be a manager to access all reimbursements");
        }

        List<Reimbursement> reimbursements = reimbursementService.getAllReimbursements();
        ctx.json(reimbursements);
    };

    @Override
    public void mapEndpoints(Javalin app) {
        app.get("/reimbursements", getAllReimbursements);
    }
}
