package com.learning.hello;

import java.io.IOException;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import com.learning.hello.controller.OdometerController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/odometer")
public class OdometerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private JakartaServletWebApplication application;
	private TemplateEngine templateEngine;
    private OdometerController odometer;
	
	 @Override
    public void init() throws ServletException {
	application = JakartaServletWebApplication.buildApplication(getServletContext());
	    final WebApplicationTemplateResolver templateResolver = 
	        new WebApplicationTemplateResolver(application);
	    templateResolver.setTemplateMode(TemplateMode.HTML);
	    templateResolver.setPrefix("/WEB-INF/templates/");
	    templateResolver.setSuffix(".html");
	    templateEngine = new TemplateEngine();
	    templateEngine.setTemplateResolver(templateResolver);
        int size = 4;
        String sizeParam = getInitParameter("size");
        if (sizeParam != null) {
            size = Integer.parseInt(sizeParam);
        }
        odometer = new OdometerController(size);
    }
	 
	 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
	        final IWebExchange webExchange = this.application.buildExchange(request, response);
	        final WebContext ctx = new WebContext(webExchange);
	        ctx.setVariable("reading", odometer.getReading());
	        templateEngine.process("odometer", ctx, response.getWriter());
	 }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("Next Reading".equals(action)) {
            odometer.incrementReading();
            System.out.println("yes");
        } else if ("Prev Reading".equals(action)) {
            odometer.decrementReading();
        } else if ("Next Kth Reading".equals(action)) {
            int k = Integer.parseInt(request.getParameter("k")); 
            for (int i = 0; i < k; i++) {
                odometer.incrementReading();
            }
        } else if ("Prev Kth Reading".equals(action)) {
            int k = Integer.parseInt(request.getParameter("k")); 
            for (int i = 0; i < k; i++) {
                odometer.decrementReading();
            }
        }

        String digitInput = request.getParameter("digit");
        if (digitInput != null && !digitInput.isEmpty()) {
            int size = Integer.parseInt(digitInput);
            odometer = new OdometerController(size);
        }
        
        response.sendRedirect(request.getContextPath() + "/odometer");
    }
}

