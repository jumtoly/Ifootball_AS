package com.ifootball.app.framework.adapter;

import android.widget.ListAdapter;

import com.ifootball.app.framework.content.StateObserver;


public interface MyBaseAdapter extends ListAdapter {
    /**
     * Retries the last query asynchronously.
     */
    boolean retry();

    /**
     * Returns {@code true} if there are asynchronous queries pending, {@code
     * false} otherwise.
     */
    boolean isLoading();

    /**
     * Returns {@code true} if there might be more data to load, {@code false}
     * otherwise.
     */
    boolean hasMore();

    /**
     * Returns {@code true} if there was an error, {@code false} otherwise.
     */
    boolean hasError();
    
    String getErrorCode();
    String getErrorDescription();
    Exception getException();
    
    void clear();
    
    void remove(int index);
    
    void registerStateObserver(StateObserver observer);
	
	void unregisterStateObserver(StateObserver observer);
}