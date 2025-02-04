/*
 * Copyright (C) 2013 Paul Burke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ipaulpro.afilechooser;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.io.File;
import java.util.List;

/**
 * Fragment that displays a list of Files in a given path.
 *
 * @author paulburke (ipaulpro)
 * @version 2013-12-11
 */
public class FileListFragment extends ListFragment implements
        LoaderManager.LoaderCallbacks<List<File>> {

    private static final int LOADER_ID = 0;
    private FileListAdapter mAdapter;
    private String mPath;
    private Callbacks mListener;

    /**
     * Create a new instance with the given file path.
     *
     * @param path The absolute path of the file (directory) to display.
     * @return A new Fragment with the given file path.
     */
    public static FileListFragment newInstance(String path) {
        FileListFragment fragment = new FileListFragment();
        Bundle args = new Bundle();
        args.putString(FileChooserActivity.PATH, path);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (Callbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement FileListFragment.Callbacks");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new FileListAdapter(getActivity());
        mPath = getArguments() != null ? getArguments().getString(
                FileChooserActivity.PATH) : Environment
                .getExternalStorageDirectory().getAbsolutePath();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        setEmptyText(getString(R.string.empty_directory));
        setListAdapter(mAdapter);
        setListShown(false);

        getLoaderManager().initLoader(LOADER_ID, null, this);

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        FileListAdapter adapter = (FileListAdapter) l.getAdapter();
        if (adapter != null) {
            File file = (File) adapter.getItem(position);
            mPath = file.getAbsolutePath();
            mListener.onFileSelected(file);
        }
    }

    @Override
    public Loader<List<File>> onCreateLoader(int id, Bundle args) {
        return new FileLoader(getActivity(), mPath);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<File>> loader, List<File> data) {
        mAdapter.setListItems(data);

        if (isResumed())
            setListShown(true);
        else
            setListShownNoAnimation(true);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<File>> loader) {
        mAdapter.clear();
    }

    /**
     * Interface to listen for events.
     */
    public interface Callbacks {
        /**
         * Called when a file is selected from the list.
         *
         * @param file The file selected
         */
        public void onFileSelected(File file);
    }
}
