/*
 * Copyright (C) 2020 The Android Open Source Project
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
package com.android.launcher3.model;

import static com.android.launcher3.util.PackageManagerHelper.hasShortcutsPermission;

import android.content.Context;

import androidx.annotation.WorkerThread;

import com.android.launcher3.LauncherAppState;
import com.android.launcher3.R;
import com.android.launcher3.util.ResourceBasedOverride;

/**
 * Class to extend LauncherModel functionality to provide extra data
 */
public class ModelDelegate implements ResourceBasedOverride {

    /**
     * Creates and initializes a new instance of the delegate
     */
    public static ModelDelegate newInstance(
            Context context, LauncherAppState app, AllAppsList appsList, BgDataModel dataModel) {
        ModelDelegate delegate = Overrides.getObject(
                ModelDelegate.class, context, R.string.model_delegate_class);

        delegate.mApp = app;
        delegate.mAppsList = appsList;
        delegate.mDataModel = dataModel;
        return delegate;
    }

    protected LauncherAppState mApp;
    protected AllAppsList mAppsList;
    protected BgDataModel mDataModel;

    public ModelDelegate() { }

    /**
     * Called periodically to validate and update any data
     */
    @WorkerThread
    public void validateData() {
        if (hasShortcutsPermission(mApp.getContext())
                != mAppsList.hasShortcutHostPermission()) {
            mApp.getModel().forceReload();
        }
    }

    /**
     * Load delegate items if any in the data model
     */
    @WorkerThread
    public void loadItems() { }

    /**
     * Called when the delegate is no loner needed
     */
    @WorkerThread
    public void destroy() { }
}
