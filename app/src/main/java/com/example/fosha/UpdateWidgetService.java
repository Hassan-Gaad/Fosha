package com.example.fosha;

import java.util.Random;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.fosha.models.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class UpdateWidgetService extends Service {
    private static final String LOG = "UpdateWidgetService";
    Post post;
    String price;
    String title;
    String body;
    String author;
    int starCount;
    private DatabaseReference mDatabase;

    @Override
    public void onStart(Intent intent, int startId) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this
                .getApplicationContext());

        int[] allWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);

        for (int widgetId : allWidgetIds) {

            RemoteViews  remoteViews = new RemoteViews(this
                    .getApplicationContext().getPackageName(),
                    R.layout.widget_layout);
            // Set the text
            mDatabase = FirebaseDatabase.getInstance().getReference();
            if (FirebaseAuth.getInstance().getCurrentUser().getUid().isEmpty()) {
                Log.d(LOG, "cant get user id ");
            }
            String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Log.d(LOG, "user id " + uId);

            Query myTopPostsQuery = mDatabase.child("posts")
                    .orderByChild("starCount").limitToLast(1);
            myTopPostsQuery.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    post = dataSnapshot.getValue(Post.class);
                    if (dataSnapshot.exists()) {
                        Log.d(LOG, "onDataChanged data snapshot is exist");
                        if (post!=null) {

                            remoteViews.setTextViewText(R.id.NumStars, String.valueOf(post.starCount));
                            Log.d(LOG, "star count" + post.starCount);
                            remoteViews.setTextViewText(R.id.Author,post.author);
                            Log.d(LOG, "author " + post.author);
                            remoteViews.setTextViewText(R.id.Body,post.description);
                            remoteViews.setTextViewText(R.id.Title,post.placeName);

                            Log.d(LOG, "desc " + post.description);
                        }else {
                            Log.d(LOG, "post null");

                        }
                    } else {
                        Log.d(LOG, "onDataChanged datasnap not exist");
                    }

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });




            // Register an onClickListener
            Intent clickIntent = new Intent(this.getApplicationContext(),
                    MyWidgetProvider.class);

            clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
                    allWidgetIds);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    getApplicationContext(), 0, clickIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.Title, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
        stopSelf();

        super.onStart(intent, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}