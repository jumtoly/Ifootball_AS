package com.ifootball.app.entity;

import java.util.Collection;

public interface HasCollection<T> {
	
	public Collection<T> getList();
}