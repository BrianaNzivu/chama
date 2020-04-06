package com.example.m_chama.View;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.example.m_chama.R;

/**
 * Implementation of App Widget functionality.
 */
public class Mchamawidget extends AppWidgetProvider
{

    private static RemoteViews views;
    private static Bundle info;


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId ,Bundle info)
    {
        CharSequence widgetText = context.getString(R.string.appwidget_text);
// Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.mchamawidget);
        setTheUiData(info,context);

// Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void wiringUpTheWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds,Bundle info)
    {
        for (int appWidgetId : appWidgetIds)
        {
            updateAppWidget(context, appWidgetManager, appWidgetId,info);
        }
    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
// There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds)
        {
            updateAppWidget(context, appWidgetManager, appWidgetId,info);
        }
    }

    @Override
    public void onEnabled(Context context)
    {
// Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context)
    {
// Enter relevant functionality for when the last widget is disabled
    }

    public static void setTheUiData(Bundle info, Context context)
    {
        String name = info.getString("Name");
        String date = info.getString("Date");
        String amount = info.getString("Amount");

        SharedPreferences mPrefs = context.getSharedPreferences("TransactionDetail",Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("name", name);
        prefsEditor.putString("date", date);
        prefsEditor.putString("amount",amount);
        prefsEditor.commit();


        views.setTextViewText(R.id.appwidget_name,name);
        views.setTextViewText(R.id.appwidget_date,date);
        views.setTextViewText(R.id.appwidget_amount,amount);


// Starting up the intent that will be used to open up the intent
        Intent appIntent = new Intent(context, Transaction.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context,0,appIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.myWidget,appPendingIntent);


    }
}