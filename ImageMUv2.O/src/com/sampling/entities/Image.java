package com.sampling.entities;

import java.io.File;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Image {
	@Id 
	@GenericGenerator(name="gen",strategy="increment")
	@GeneratedValue(generator="gen")
	int id ;
	@Transient
	File file ;
	@Column(name="Image_Name")
	String imgName ;
	long imgSize ;
	@Lob
	private byte[] imageData;
	
	@ManyToOne
	User user ;
	
	/**
	 * 
	 * @return id : Image id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * 
	 * @return file : Image as file type
	 */
	public File getFile() {
		return file;
	}
	
	/**
	 * 
	 * @return imgName : Image name
	 */
	public String getImgName() {
		return imgName;
	}
	
	/**
	 * 
	 * @return imgSize : Image size of type long
	 */
	public long getImgSize() {
		return imgSize;
	}
	
	/**
	 * @param file: Image file 
	 * 
	 * Sets the file size and name s
	 */
	public void setImg(File file) {
		this.file = file;
		this.imgSize =  file.length();
		this.imgName = file.getName();
	}
	
	/**
	 * 
	 * @return user : an instance of User type
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * @param : user of type User
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * @param String: Image Name 
	 */
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	
	/**
	 * @return byte[]: Image data as byte stream
	 */
	public byte[] getImageData() {
		return imageData;
	}
	
	/**
	 * @param byte[]: Image data as byte stream
	 */
	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}
	
	/**
	 * @param id: sets User id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @param FILE: File type object
	 */
	public void setFile(File file) {
		this.file = file;
	}
	
	/**
	 * @param long: Image size
	 */
	public void setImgSize(long imgSize) {
		this.imgSize = imgSize;
	}
	
	
}