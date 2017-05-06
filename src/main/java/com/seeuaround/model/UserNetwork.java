package com.seeuaround.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_network")
public class UserNetwork
{
	@Id
	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "friend_id")
	private Integer friendId;

	public Integer getUserId()
	{
		return userId;
	}

	public void setUserId(Integer userId)
	{
		this.userId = userId;
	}

	public Integer getFriendId()
	{
		return friendId;
	}

	public void setFriendId(Integer friendId)
	{
		this.friendId = friendId;
	}	
}