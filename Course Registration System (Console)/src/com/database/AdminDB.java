package com.database;

import com.users.Admin;

import java.util.*;

public class AdminDB implements java.io.Serializable{

	/** Arraylist that stores admin object. */
	private List<Admin> adminList = new ArrayList<Admin>();

	/**
	 * Instantiates a new admin database.
	 *
	 * @param adminList the admin list
	 */
	public AdminDB(List<Admin> adminList)
	{
		this.adminList=adminList;
	}

	/**
	 * Gets the admin list.
	 *
	 * @return the admin list
	 */
	public List<Admin> getAdminList() {
		return adminList;
	}

	/**
	 * Find admin.
	 *
	 * @param name admin's username
	 * @return the admin object
	 */
	public Admin findAdmin(String name)
	{
		for(Admin a:adminList)
		{
			if(a.getName().equals(name))
			{
				return a;
			}
		}
		return null;
	}

	/**
	 * Delete admin.
	 *
	 * @param name admin's username
	 * @return true, if successful
	 */
	public boolean deleteAdmin(String name)
	{
		for(Admin a:adminList)
		{
			if(a.getName().equals(name))
			{
				adminList.remove(a);
				return true;
			}
		}
		return false;
	}
}
