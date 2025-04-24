package com.enrique.curso.springboot.calendar.interceptor.springboot_horario.interceptors;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CalendarInterceptor implements HandlerInterceptor{

    @Value("${calendar.hour.open}")
    private Integer open;
    
    @Value("${calendar.hour.close}")
    private Integer close;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
                Calendar calendar = Calendar.getInstance();
                Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
                // System.out.println(">>>>>>" + hour);
                if(hour>open && hour<close){
                    StringBuilder message = new StringBuilder("Welcome");
                    message.append("Our buisiness hours is from ");
                    message.append(open);
                    message.append(" hrs to ");
                    message.append(close);

                    request.setAttribute("message", message.toString());
                    return true;
                }

                return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
                return;
    }


}
