package org.example.servletHomeWork;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;
import org.thymeleaf.context.Context;
import org.thymeleaf.web.servlet.JavaxServletWebApplication;
import org.thymeleaf.web.IWebApplication;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
    private TemplateEngine templateEngine;
    @Override
    public void init() throws ServletException {
        templateEngine = new TemplateEngine();
        JakartaServletWebApplication jswa = JakartaServletWebApplication.buildApplication(this.getServletContext());

        WebApplicationTemplateResolver resolver = new WebApplicationTemplateResolver(jswa);
        resolver.setPrefix("/templates");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(templateEngine.getTemplateResolvers().size());
        resolver.setCacheable(false);
        templateEngine.addTemplateResolver(resolver);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
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
        Context dataToApp = new Context(req.getLocale(), Map.of("dataTh",data,"timeTh",time, "timezoneTh",timezone));

        templateEngine.process("Timezone",dataToApp, resp.getWriter());
        resp.getWriter().close();
//
//        PrintWriter out = resp.getWriter();
//        out.println("Поточний час прямо зараз: "+data + " " +time+ " " + timezone);
//        out.close();
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
