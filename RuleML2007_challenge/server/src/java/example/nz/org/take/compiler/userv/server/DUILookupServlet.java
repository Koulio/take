/*
 * DUILookupServlet.java
 * 
 * Created on 19/10/2007, 20:25:58
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package example.nz.org.take.compiler.userv.server;

import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author jens
 */
public class DUILookupServlet extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id");
        out.print((id!=null && id.contains("42"))?"true":"false");
    } 


    /** 
    * Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.sendError(response.SC_METHOD_NOT_ALLOWED);
    }

    /** 
    * Returns a short description of the servlet.
    */
    public String getServletInfo() {
        return "servlet for DUI information lookup";
    }
    // </editor-fold>
}
