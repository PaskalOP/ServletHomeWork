package org.example.servletHomeWork;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        LocalDate data = LocalDate.now();
        LocalTime time;
        String timezone="";
        try {
            timezone = req.getParameter("timezone");
            time = LocalTime.now().plusHours(getTimeFromParam(timezone)) ;
        } catch (Exception e){
           time = LocalTime.now();
           timezone ="";
        }


        PrintWriter out = resp.getWriter();
        out.println("Поточний час прямо зараз: "+data + " " +time+ " " + timezone);
        out.close();
    }

    private int getTimeFromParam(String req){
        int result = 0;
        String[] data = req.split("");
        int hours = Integer.parseInt(req.substring(4));
        if (data[3].equals("+")) result +=hours;
        if(data[3].equals("-")) result -=hours;
        return result ;
    }
}
