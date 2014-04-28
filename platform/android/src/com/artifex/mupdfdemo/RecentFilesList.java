package com.artifex.mupdfdemo;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import android.content.SharedPreferences;
import java.io.File;

public class RecentFilesList extends LinkedList<String> implements List<String> { //Probably not the most appropriate list type...

    static final int MAX_RECENT_FILES=20;

    public RecentFilesList() {
        super();
    }
    
    public RecentFilesList(SharedPreferences prefs) {
        for (int i = MAX_RECENT_FILES-1; i>=0; i--) //Read in revers order because we use push
        {
            String recentFileString = prefs.getString("recentfile"+i,null);
            if(recentFileString != null)
            {
                    //Make sure we add only actual files
//                File recentFile = new File(recentFileString);
//                if(recentFile != null && recentFile.isFile() && recentFile.canRead()) push(recentFileString);
//                if(recentFile != null)
                    push(recentFileString);
            }
        }
    }

    void write(SharedPreferences.Editor edit) {
        for (int i = 0; i<size(); i++)
        {
            edit.putString("recentfile"+i, get(i));
        }
    }
        
    @Override
    public void push(String recentFileString) {
            //Make sure we don't put duplicates
        remove(recentFileString);
            //Add
        super.addFirst(recentFileString);
            //Remove elements until lenght is short enough
        while (size() > MAX_RECENT_FILES) { removeLast(); }
    }
}
