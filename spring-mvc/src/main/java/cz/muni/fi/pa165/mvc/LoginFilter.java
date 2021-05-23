package cz.muni.fi.pa165.mvc;

import cz.muni.fi.pa165.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/home/*", "/user/*", "/timeline/*"})
public class LoginFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOG.debug("login filter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        UserDTO authUser = (UserDTO)session.getAttribute("authUser");
        if (authUser == null) {
            String redirectURL = request.getContextPath() + "/auth/login";
            LOG.debug("login filter - not authenticated - redirecting to {}", redirectURL);
            response.setStatus(HttpStatus.TEMPORARY_REDIRECT.value());
            response.setHeader("Location", redirectURL);
        }
        filterChain.doFilter(request, response);
    }
}
