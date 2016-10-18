/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Author;
import model.AuthorDao;
import model.AuthorDaoStrategy;
import model.AuthorService;
import model.MySqlDbStrategy;

/**
 *
 * @author Tim
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/authors"})
public class AuthorController extends HttpServlet {

    private final String LIST_VIEW = "/authorList.jsp";
    private final String ADD_OR_UPDATE_VIEW = "/addOrUpdateForm.jsp";
    private String driverClass;
    private String url;
    private String userName;
    private String password;
//    private String webmasterEmail;

    @Inject
    private AuthorService service;

    @Override
    public void init() throws ServletException {
        driverClass = getServletContext().getInitParameter("db-driver-class");
        url = getServletContext().getInitParameter("db-url");
        userName = getServletContext().getInitParameter("db-username");
        password = getServletContext().getInitParameter("db-password");
//        webmasterEmail = getServletContext().getInitParameter("webmaster-email");
    }

    private void configDbConnection() {
        service.getDao().initDao(driverClass, url, userName, password);
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        if (session.getAttribute("updated") == null){
            session.setAttribute("updated", 0);
        }
        if (session.getAttribute("created") == null){
            session.setAttribute("created", 0);
        }
        if (session.getAttribute("deleted") == null){
            session.setAttribute("deleted", 0);
        }
        ServletContext ctx = request.getServletContext();
        ctx.setAttribute("date", new Date());
        String destination = LIST_VIEW;
        Author author;
        try {
            configDbConnection();


            String formAction = request.getParameter("userAction");
            if (formAction == null) {
                formAction = "";
            }
            String authId;
            String authName;
            

            switch (formAction) {
                case "Add":
                    author = null;
                    refreshList(request, service);
                    destination = ADD_OR_UPDATE_VIEW;
                    break;

                case "Update":

                    if (request.getParameter("authorPk") != null) {

                        author = service.getAuthorById(request.getParameter("authorPk"));
                        request.setAttribute("author", author);
                        destination = ADD_OR_UPDATE_VIEW;
                    } else {
                        refreshList(request, service);
                        destination = LIST_VIEW;
                    }
                    break;
                case "Delete":
                    if (request.getParameter("authorPk") != null) {
                        authId = request.getParameter("authorPk");
                        service.deleteAuthorById(authId);
                        session.setAttribute("deleted", (int)session.getAttribute("deleted") + 1);
                        refreshList(request, service);
                        destination = LIST_VIEW;
                    } else {
                        refreshList(request, service);
                        destination = LIST_VIEW;
                    }
                    break;
                case "Create":
                    authName = request.getParameter("authorName");
                    service.createAuthor(authName);
                    session.setAttribute("created", (int)session.getAttribute("created") + 1);
                    refreshList(request, service);
                    destination = LIST_VIEW;
                    break;

                case "Submit":
                    authId = request.getParameter("authorId");
                    String authorName = request.getParameter("authorName");
                    service.updateAuthorById(authId, authorName);
                    session.setAttribute("updated", (int)session.getAttribute("updated") + 1);
                    refreshList(request, service);
                    destination = LIST_VIEW;
                    break;
                    
                case "Help":
                    response.sendRedirect("help.jsp");

                default:
                    refreshList(request, service);
                    destination = LIST_VIEW;
                    break;
            }
        } catch (Exception e) {
            request.setAttribute("errMsg", e.getCause().getMessage());
        }
        RequestDispatcher view = getServletContext().getRequestDispatcher(response.encodeURL(destination));
        view.forward(request, response);
    }

    public void refreshList(HttpServletRequest request, AuthorService service) throws ClassNotFoundException, SQLException {
        List<Author> authorList = service.getAuthorList();
        request.setAttribute("authors", authorList);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
