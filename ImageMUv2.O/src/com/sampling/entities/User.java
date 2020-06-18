package com.sampling.entities;
import java.util.List;
import javax.persistence.*;

import org.hibernate.Session;
import org.hibernate.annotations.GenericGenerator;

import com.sampling.HB.HibernateUtil;

import org.hibernate.Query ;

@SuppressWarnings("deprecation")
@Entity
public class User {
	@Id 
	@GenericGenerator(name="gen",strategy="increment")
	@GeneratedValue(generator="gen")
	int id ;
	String fullName ;
	
	@Column(name="username", unique=true)
	String userName ;
	String email ;
	String Pass ;
	
	@OneToMany(mappedBy="user", cascade= CascadeType.ALL , fetch=FetchType.EAGER)
	List<Image> imageList ;
	
	public int getId() {
		return id;
	}
	
	/**
	 * @return String: User's name
	 */
	public String getFullName() {
		return fullName;
	}
	
	/**
	 * @param String: name of User
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	/**
	 * @return String: User's name
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * @param String: name of User
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * @return String: email of user
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * @param String: email of User
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return String: password of User
	 */
	public String getPass() {
		return Pass;
	}
	
	/**
	 * @param String: password of User
	 */
	public void setPass(String pass) {
		Pass = pass;
	}

	/**
	 * returns a list of Images that is
	 * associated with this User instance
	 * 
	 * @return List<Image>: List of Images 
	 */
	public List<Image> getImageList() {
		return imageList;
	}

	/**
	 * sets this user's list of images
	 * 
	 * @param List<Image>: List of Images
	 */
	public void setImageList(List<Image> imageList) {
		this.imageList = imageList;
	}
	
	
	/**
	 * returns an Image that is
	 * associated with this User instance
	 * and has been queried for update/changes.
	 * 
	 * @param String name : name of Image
	 * @return List<Image>: List of Images 
	 */
	@SuppressWarnings("rawtypes")
	public static List<Image> getImageList(String name) {
		
		Session session = HibernateUtil.sessionFactoryInstance.openSession();
		session.beginTransaction();
		Query query = session.createQuery("from User where userName = :param");
		query.setParameter("param", name);
		User u = (User) query.uniqueResult();
		List<Image> li;
		try {
			li = u.getImageList();
		} catch (Exception e) {
			li = null ;
		}
		session.getTransaction().commit();
		session.close();
		return li; 
	} 
	
}