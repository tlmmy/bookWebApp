/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author Tim
 */
public class Author {
    private int authorId;
    private String authorName;
    private Date dateAdded;

    public Author(int authorId) {
        setAuthorId(authorId);
    }

    public Author() {
    }

    public Author(int authorId, String authorName, Date dateAdded) {
        setAuthorId(authorId);
        setAuthorName(authorName);
        setDateAdded(dateAdded);
    }
    
    

    public final int getAuthorId() {
        return authorId;
    }

    public final void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public final String getAuthorName() {
        return authorName;
    }

    public final void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public final Date getDateAdded() {
        return dateAdded;
    }

    public final void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + this.authorId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Author other = (Author) obj;
        if (this.authorId != other.authorId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "authorId=" + authorId + ", authorName=" + authorName + ", dateAdded=" + dateAdded;
    }
    
    
}
