package com.codesquad.oauthgithublibrary.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    // replace with Github login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthCredential authCredential, HttpSession session) {
        logger.info("authCredential1 : {}", authCredential);
        if ("codesquad".equals(authCredential.getCredential())) {
            logger.info("authCredential2 : {}", authCredential);
            return ResponseEntity.ok(new AuthResponse(true));
        } else {
            logger.info("authCredential3 : {}", authCredential);
            session.invalidate();
            return ResponseEntity.ok(new AuthResponse(false));
        }
    }

}
