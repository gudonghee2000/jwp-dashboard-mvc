package nextstep.mvc;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
<<<<<<< HEAD
import nextstep.mvc.view.ModelAndView;
import nextstep.mvc.view.View;
=======
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import nextstep.mvc.controller.asis.Controller;
import nextstep.mvc.controller.tobe.AnnotationHandlerMapping;
import nextstep.mvc.view.JspView;
>>>>>>> 71b065c (feat: AppWebApplicationInitializer에 애노테이션 기반 HandlerMapping 객체를 추가하는 로직 생성)
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    private final HandlerMappingRegister handlerMappingRegister;
    private final HandlerAdapterRegister handlerAdapterRegister;

    public DispatcherServlet() {
        this.handlerMappingRegister = new HandlerMappingRegister();
        this.handlerAdapterRegister = new HandlerAdapterRegister();
    }

    @Override
    public void init() {
        handlerMappingRegister.init();
    }

    public void addHandlerMapping(final HandlerMapping handlerMapping) {
        handlerMappingRegister.addHandlerMapping(handlerMapping);
    }

    public void addHandlerAdapter(final HandlerAdapter handlerAdapter) {
        handlerAdapterRegister.addHandlerAdapter(handlerAdapter);
    }

    @Override
    protected void service(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException {
        log.debug("Method : {}, Request URI : {}", request.getMethod(), request.getRequestURI());

        try {
            Object handler = handlerMappingRegister.getHandler(request);
            HandlerAdapter handlerAdapter = handlerAdapterRegister.getHandlerAdapter(handler);

            ModelAndView modelAndView = handlerAdapter.handle(request, response, handler);
            render(modelAndView, request, response);
        } catch (Throwable e) {
            log.error("Exception : {}", e.getMessage(), e);
            throw new ServletException(e.getMessage());
        }
    }

<<<<<<< HEAD
    private void render(final ModelAndView modelAndView, final HttpServletRequest request,
                        final HttpServletResponse response) throws Exception {
        View view = modelAndView.getView();
        view.render(modelAndView.getModel(), request, response);
=======
    private Controller getController(final HttpServletRequest request) {
        return handlerMappings.stream()
                .map(handlerMapping -> handlerMapping.getHandler(request))
                .filter(Objects::nonNull)
                .map(Controller.class::cast)
                .findFirst()
                .orElseThrow();
    }

    private void move(final String viewName, final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
        if (viewName.startsWith(JspView.REDIRECT_PREFIX)) {
            response.sendRedirect(viewName.substring(JspView.REDIRECT_PREFIX.length()));
            return;
        }

        final var requestDispatcher = request.getRequestDispatcher(viewName);
        requestDispatcher.forward(request, response);
>>>>>>> 71b065c (feat: AppWebApplicationInitializer에 애노테이션 기반 HandlerMapping 객체를 추가하는 로직 생성)
    }
}
