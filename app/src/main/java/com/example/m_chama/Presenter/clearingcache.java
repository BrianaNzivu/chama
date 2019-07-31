package com.example.m_chama.Presenter;

import android.content.Context;

import java.io.File;

public class clearingcache
{

    public static void deletecache(Context context)
    {
        try
        {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
    }
//Clearing cache
    public static boolean deleteDir(File dir)
    {
        if (dir != null && dir.isDirectory())
        {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++)
            {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success)
                {

                    return false;

                }
            }
            return dir.delete();

        } else if

        (dir!= null && dir.isFile())
        {

            return dir.delete();

        } else
            {

            return false;

            }
    }
}
