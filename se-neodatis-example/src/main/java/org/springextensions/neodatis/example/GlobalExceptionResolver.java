package org.springextensions.neodatis.example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;
import org.springframework.web.servlet.view.RedirectView;

@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);

    private final ResponseStatusExceptionResolver annotatedExceptionHandler = new ResponseStatusExceptionResolver();

    private final DefaultHandlerExceptionResolver defaultExceptionHandler = new DefaultHandlerExceptionResolver();

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        try {
            if (ex instanceof UsernameExistException) {
                return handleEmptyResultDataAccessException((UsernameExistException) ex, request, response, handler);
            }
        } catch (Exception e) {
            logger.warn("Handling of [" + ex.getClass().getName() + "] resulted in Exception", ex);
            return null;
        }
        ModelAndView result = annotatedExceptionHandler.resolveException(request, response, handler, ex);
        if (result != null) {
            return result;
        }
        return defaultExceptionHandler.resolveException(request, response, handler, ex);
    }

    // internal helpers

    private ModelAndView handleEmptyResultDataAccessException(UsernameExistException ex, HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setView(new RedirectView());
        mav.setViewName("error");
        return mav;
    }

}
