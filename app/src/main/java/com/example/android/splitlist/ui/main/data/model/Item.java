package com.example.android.splitlist.ui.main.data.model;

import java.util.ArrayList;

public class Item {

    private String mItemName;
    private double mPrice;
    private int mMasterId;
    private int mLikeNumber;
    private ArrayList<Integer> mUsers;
    private boolean mCheckout;

    //TODO: need in database?
    private boolean mFavorite;

    public Item() {

    }

    public Item(String name, double price, int id) {

        mItemName = name;
        mPrice = price;
        mMasterId = id;
        mLikeNumber = 1;
        mCheckout = false;
        mFavorite = false;

        if (mUsers == null) {
            mUsers = new ArrayList<>();
        }
    }

    public String getItemName() {
        return mItemName;
    }

    public void setItemName(String mItemName) {
        this.mItemName = mItemName;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double mPrice) {
        this.mPrice = mPrice;
    }

    public int getMasterId() {
        return mMasterId;
    }

    public void setMasterId(int mMasterId) {
        this.mMasterId = mMasterId;
    }

    public int getLikeNumber() {
        return mLikeNumber;
    }

    public void setLikeNumber(int mLikeNumber) {
        this.mLikeNumber = mLikeNumber;
    }

    public ArrayList<Integer> getUsers() {
        return mUsers;
    }

    public void addUser(int mUser) {
        mUsers.add(mUser);
    }

    public void removeUser(int mUser) {
        mUsers.remove(mUser);
    }

    public boolean getCheckout() { return mCheckout; }

    public void setCheckout(boolean mCheckout) {
        this.mCheckout = mCheckout;
    }

    public boolean getFavorite() {
        return mFavorite;
    }

    public void setFavorite(boolean mFavorite) {
        this.mFavorite = mFavorite;
    }

}
