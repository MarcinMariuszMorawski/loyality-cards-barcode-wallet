package pl.lodz.uni.math.loyalitycardsbarcodewalletapi.security.filters;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.dao.entity.User;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.manager.UserManager;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.security.service.JwtTokenService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Service
public final class JwtResourcesFilter implements Filter {

    private UserManager userManager;
    private JwtTokenService jwtTokenService;

    public void init(FilterConfig cfg) {
        ApplicationContext ctx = WebApplicationContextUtils
                .getRequiredWebApplicationContext(cfg.getServletContext());
        this.userManager = ctx.getBean(UserManager.class);
        this.jwtTokenService = ctx.getBean(JwtTokenService.class);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            String header = httpServletRequest.getHeader("authorization");

            if (!header.startsWith("Bearer ")) {
                throw new ServletException("Wrong authorization header");
            } else {
                String token = header.substring(7);
                String username = jwtTokenService.getEmailFromToken(token);

                if (!userManager.findByEmail(username).isPresent()) {
                    throw new ServletException("Request error");
                }

                User user = userManager.findByEmail(username).get();

                if (!jwtTokenService.validateUserToken(token, user)) {
                    throw new ServletException("Wrong token");
                }
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (NullPointerException e) {
            throw new ServletException("Empty authorization header");
        }
    }
}