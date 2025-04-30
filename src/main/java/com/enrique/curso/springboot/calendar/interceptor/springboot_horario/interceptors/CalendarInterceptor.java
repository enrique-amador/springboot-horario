package com.enrique.curso.springboot.calendar.interceptor.springboot_horario.interceptors;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

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
                if(hour>open && hour<close){
                    StringBuilder message = new StringBuilder("Welcome. ");
                    message.append("Our business hours is from ");
                    message.append(open);
                    message.append(" hrs to ");
                    message.append(close);

                    request.setAttribute("message", message.toString());
                    return true;
                }

                StringBuilder message = new StringBuilder("Please come back in business hours. ");
                message.append("From ");
                message.append(open);
                message.append(" to ");
                message.append(close);

                Map<String, String> data = new HashMap<>();
                data.put("date", new Date().toString());
                data.put("message", message.toString());

                ObjectMapper mapper = new ObjectMapper();
                
                response.setContentType("application/json");
                response.setStatus(401);
                response.getWriter().write(mapper.writeValueAsString(data));

                return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
                return;
    }


}
