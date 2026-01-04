package mx.ipn.upiicsa.web.accesscontrol.infrastructure.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import mx.ipn.upiicsa.web.accesscontrol.domain.Persona;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class SecurityInterceptor implements HandlerInterceptor {

    // Role mapping: 1=ADMIN, 2=EMPLOYEE, 3=CLIENT

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull Object handler)
            throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RequiresRole requiresRole = handlerMethod.getMethodAnnotation(RequiresRole.class);
        if (requiresRole == null) {
            requiresRole = handlerMethod.getBeanType().getAnnotation(RequiresRole.class);
        }

        if (requiresRole == null) {
            return true;
        }

        HttpSession session = request.getSession();
        Persona persona = (Persona) session.getAttribute("persona");

        if (persona == null) {
            log.warn("Access denied: User not authenticated for URL {}", request.getRequestURI());
            response.sendRedirect("/");
            return false;
        }

        List<String> allowedRoles = Arrays.asList(requiresRole.value());
        String userRole = getUserRoleName(persona.getUsuario().getIdRol());

        if (allowedRoles.contains(userRole)) {
            return true;
        }

        log.warn("Access denied: User role {} not authorized for URL {}", userRole, request.getRequestURI());
        response.sendRedirect("/access-denied"); // Assuming you will add a mapping for this
        return false;
    }

    private String getUserRoleName(Integer roleId) {
        // Mapping based on DB Ids found in 01-create.sql
        if (roleId == null)
            return "UNKNOWN";
        switch (roleId) {
            case 1:
                return "ADMIN";
            case 2:
                return "EMPLOYEE"; // Mapped 'Empleado' to 'EMPLOYEE' for annotation use
            case 3:
                return "CLIENT"; // Mapped 'Cliente' to 'CLIENT' for annotation use
            default:
                return "UNKNOWN";
        }
    }
}
