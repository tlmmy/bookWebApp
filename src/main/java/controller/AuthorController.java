/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ejb.AuthorFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import model.Author;
import model.Book;


/**
 *
 * @author Tim
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/authors"})
public class AuthorController extends HttpServlet {

    private final String LIST_VIEW = "/authorList.jsp";
    private final String ADD_OR_UPDATE_VIEW = "/addOrUpdateForm.jsp";
    
    @Inject
    private AuthorFacade service;

    @Override
    public void init() throws ServletException {

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
        if (session.getAttribute("updated") == null) {
            session.setAttribute("updated", 0);
        }
        if (session.getAttribute("created") == null) {
            session.setAttribute("created", 0);
        }
        if (session.getAttribute("deleted") == null) {
            session.setAttribute("deleted", 0);
        }
        ServletContext ctx = request.getServletContext();
        ctx.setAttribute("date", new Date());
        String destination = LIST_VIEW;
        Author author;
        try {
            String formAction = request.getParameter("userAction");
            if (formAction == null) {
                formAction = "";
            }
            String authId;
            

            switch (formAction) {
                case "Add":
                    author = null;
                    session.setAttribute("author", author);
                    refreshList(request, service);
                    destination = ADD_OR_UPDATE_VIEW;
                    break;

                case "Update":

                    if (request.getParameter("authorPk") != null) {

                        authId = request.getParameter("authorPk");
                        author = service.find(new Integer(authId));
                        session.setAttribute("bookSet", author.getBookSet());
                        session.setAttribute("author", author);
                        destination = ADD_OR_UPDATE_VIEW;
                    } else {
                        refreshList(request, service);
                        destination = LIST_VIEW;
                    }
                    break;
                case "Delete":
                    if (request.getParameter("authorPk") != null) {
                        authId = request.getParameter("authorPk");
                        author = service.find(new Integer(authId));
                        service.remove(author);
                        session.setAttribute("deleted", (int) session.getAttribute("deleted") + 1);
                        refreshList(request, service);
                        destination = LIST_VIEW;
                    } else {
                        refreshList(request, service);
                        destination = LIST_VIEW;
                    }
                    break;
                case "Create":
                    author = new Author();
                    author.setAuthorName(request.getParameter("authorName"));
                    author.setDateAdded(new Date());
                    Set<Book> bookSet = Collections.emptySet();
                    author.setBookSet(bookSet);
                    service.create(author);
                    session.setAttribute("created", (int) session.getAttribute("created") + 1);
                    destination = LIST_VIEW;                    refreshList(request, service);

                    break;

                case "Submit":
                    authId = request.getParameter("authorId");
                    author = service.find(new Integer(authId));
                    String authorName = request.getParameter("authorName");
                    author.setAuthorName(authorName);
                    service.edit(author);
                    session.setAttribute("updated", (int) session.getAttribute("updated") + 1);
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

    public void refreshList(HttpServletRequest request, AuthorFacade service) throws ClassNotFoundException, SQLException {
        List<Author> authorList = service.findAll();
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
