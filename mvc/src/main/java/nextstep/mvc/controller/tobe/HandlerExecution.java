package nextstep.mvc.controller.tobe;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import nextstep.mvc.view.ModelAndView;

public class HandlerExecution {

    private final Object handler;
    private final Method method;

    public HandlerExecution(final Object handler, final Method method) {
        this.handler = handler;
        this.method = method;
    }

    public ModelAndView handle(final HttpServletRequest request, final HttpServletResponse response)
            throws InvocationTargetException, IllegalAccessException {
        return (ModelAndView) method.invoke(handler, request, response);
    }
}
