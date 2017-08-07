package com.aisleconnect;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by jp on 2017/8/7.
 */

public class GlobalData {
    private static GlobalData instance;
    public static GlobalData getInstance(){
        if (instance == null){
            instance = new GlobalData();
            instance.Logindata = "";
            instance.ownerlist = new List<OwnerListData>() {
                @Override
                public int size() {
                    return 0;
                }

                @Override
                public boolean isEmpty() {
                    return false;
                }

                @Override
                public boolean contains(Object o) {
                    return false;
                }

                @NonNull
                @Override
                public Iterator<OwnerListData> iterator() {
                    return null;
                }

                @NonNull
                @Override
                public Object[] toArray() {
                    return new Object[0];
                }

                @NonNull
                @Override
                public <T> T[] toArray(@NonNull T[] ts) {
                    return null;
                }

                @Override
                public boolean add(OwnerListData ownerListData) {
                    return false;
                }

                @Override
                public boolean remove(Object o) {
                    return false;
                }

                @Override
                public boolean containsAll(@NonNull Collection<?> collection) {
                    return false;
                }

                @Override
                public boolean addAll(@NonNull Collection<? extends OwnerListData> collection) {
                    return false;
                }

                @Override
                public boolean addAll(int i, @NonNull Collection<? extends OwnerListData> collection) {
                    return false;
                }

                @Override
                public boolean removeAll(@NonNull Collection<?> collection) {
                    return false;
                }

                @Override
                public boolean retainAll(@NonNull Collection<?> collection) {
                    return false;
                }

                @Override
                public void clear() {

                }

                @Override
                public OwnerListData get(int i) {
                    return null;
                }

                @Override
                public OwnerListData set(int i, OwnerListData ownerListData) {
                    return null;
                }

                @Override
                public void add(int i, OwnerListData ownerListData) {

                }

                @Override
                public OwnerListData remove(int i) {
                    return null;
                }

                @Override
                public int indexOf(Object o) {
                    return 0;
                }

                @Override
                public int lastIndexOf(Object o) {
                    return 0;
                }

                @Override
                public ListIterator<OwnerListData> listIterator() {
                    return null;
                }

                @NonNull
                @Override
                public ListIterator<OwnerListData> listIterator(int i) {
                    return null;
                }

                @NonNull
                @Override
                public List<OwnerListData> subList(int i, int i1) {
                    return null;
                }
            };
        }
        return instance;
    }
    public String Logindata;
    public List<OwnerListData> ownerlist;
}
