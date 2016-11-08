/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.Date;
import java.util.List;
import model.Author;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Timothy
 */
@Stateless
public class AuthorFacade extends AbstractFacade<Author> {

    @PersistenceContext(unitName = "book_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AuthorFacade() {
        super(Author.class);
    }
    
        public List<Author> findByName(String name){
        List<Author> authorList = null;
        TypedQuery<Author> query = getEntityManager().createNamedQuery("findByName", Author.class);
        query.setParameter(1, name);
        authorList = query.getResultList();
        
        return authorList;
    }
    
    public List<Author> findByDate(Date date){
        List<Author> authorList = null;
        TypedQuery<Author> query = getEntityManager().createNamedQuery("findByDate", Author.class);
        query.setParameter(1, date);
        authorList = query.getResultList();
        return authorList;
    }
    
    public void deleteById(int id){
        TypedQuery<Author> query = getEntityManager().createNamedQuery("deleteById", Author.class);
        query.setParameter(1, id);
        query.executeUpdate();
    }

    
}
