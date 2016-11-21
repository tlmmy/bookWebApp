/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ejb.AuthorFacade;
import ejb.BookFacade;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Set;
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
import model.Book;

/**
 *
 * @author Timothy
 */
@WebServlet(name = "BookController", urlPatterns = {"/books"})
public class BookController extends HttpServlet {
private final String LIST_VIEW = "/bookList.jsp";
    private final String ADD_OR_UPDATE_VIEW = "/bookAddOrUpdateForm.jsp";
    
    @Inject
    private BookFacade service;
    
    @Inject
    private AuthorFacade authService;

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
        if (session.getAttribute("bUpdated") == null) {
            session.setAttribute("bUpdated", 0);
        }
        if (session.getAttribute("bCreated") == null) {
            session.setAttribute("bCreated", 0);
        }
        if (session.getAttribute("bDeleted") == null) {
            session.setAttribute("bDeleted", 0);
        }
        ServletContext ctx = request.getServletContext();
        ctx.setAttribute("date", new Date());
        String destination = LIST_VIEW;
        Book book;
        List<Author> authorList;
        try {
            String formAction = request.getParameter("userAction");
            if (formAction == null) {
                formAction = "";
            }
            String bookId;
            

            switch (formAction) {
                case "Add":
                    book = null;
                    authorList = authService.findAll();
                    session.setAttribute("authorList", authorList);
                    session.setAttribute("book", book);
                    refreshList(request, service);
                    destination = ADD_OR_UPDATE_VIEW;
                    break;

                case "Update":

                    if (request.getParameter("bookPk") != null) {

                        bookId = request.getParameter("bookPk");
                        book = service.find(new Integer(bookId));
                        authorList = authService.findAll();
                        session.setAttribute("authorList", authorList);
                        session.setAttribute("book", book);
                        session.setAttribute("author", authService.find(book.getAuthorId().getAuthorId()));
                        
                        destination = ADD_OR_UPDATE_VIEW;
                    } else {
                        refreshList(request, service);
                        destination = LIST_VIEW;
                    }
                    break;
                case "Delete":
                    if (request.getParameter("bookPk") != null) {
                        bookId = request.getParameter("bookPk");
                        book = service.find(new Integer(bookId));
                        service.remove(book);
                        session.setAttribute("bDeleted", (int) session.getAttribute("bDeleted") + 1);
                        refreshList(request, service);
                        destination = LIST_VIEW;
                    } else {
                        refreshList(request, service);
                        destination = LIST_VIEW;
                    }
                    break;
                case "Create":
                    book = new Book();
                    book.setTitle(request.getParameter("title"));
                    book.setIsbn(request.getParameter("isbn"));
                    String authorId = request.getParameter("authorId");
                    Author author = authService.find(new Integer(authorId));
                    book.setAuthorId(author);                    
                    service.create(book);
                    Set<Book> bookSet = author.getBookSet();
                    bookSet.add(book);
                    author.setBookSet(bookSet);                    
                    session.setAttribute("bCreated", (int) session.getAttribute("bCreated") + 1);
                    refreshList(request, service);
                    destination = LIST_VIEW;
                    break;

                case "Submit":
                    author = (Author)session.getAttribute("author");
                    bookSet = author.getBookSet();
                    bookId = request.getParameter("bookId");
                    book = service.find(new Integer(bookId));
                    bookSet.remove(book);
                    String title = request.getParameter("title");
                    String isbn = request.getParameter("isbn");
                    book.setTitle(title);
                    book.setIsbn(isbn);
                    authorId = request.getParameter("authorId");
                    author = authService.find(new Integer(authorId));
                    book.setAuthorId(author);
                    bookSet = author.getBookSet();
                    service.edit(book);
                    bookSet.add(book);
                    author.setBookSet(bookSet);
                    session.setAttribute("bUpdated", (int) session.getAttribute("bUpdated") + 1);
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

    public void refreshList(HttpServletRequest request, BookFacade service) throws ClassNotFoundException, SQLException {
        List<Book> bookList = service.findAll();
        request.setAttribute("books", bookList);
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
        Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
    } catch (Exception ex) {
        Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }

    /**
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
        Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
    } catch (Exception ex) {
        Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
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
