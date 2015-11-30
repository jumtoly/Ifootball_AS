package com.ifootball.app.framework.cache;

public interface MyCache {

	public abstract <T> T get(String key);
    
    public abstract <T> T get(String key, T defaultValue);
    
    public abstract <T> void put(String key, T value);
    
    public abstract void remove(String key);
    
    public abstract boolean exists(String key);
}