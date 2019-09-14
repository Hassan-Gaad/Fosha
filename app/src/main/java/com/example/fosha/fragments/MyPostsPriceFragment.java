package com.example.fosha.fragments;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class MyPostsPriceFragment extends PostListFragment {

    public MyPostsPriceFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // My place price
        String myUserId = getUid();
        Query myCheapPriceQuery = databaseReference.child("user-posts").child(myUserId)
                .orderByChild("price");

        return myCheapPriceQuery;
    }
}