package com.example.CyProject.config;

import com.example.CyProject.user.model.UserEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthFailureHandler  implements AuthenticationFailureHandler {
    private final String DEFAULT_FAILURE_URL = "main/?error=true";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errorMessage = null;

        if(exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException){
            errorMessage = "아이디 또는 비밀번호를 다시 확인하세요.";
        }

        request.setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher(DEFAULT_FAILURE_URL).forward(request,response);
    }
}
