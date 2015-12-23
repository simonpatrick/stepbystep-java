package com.hedwig.classfinder.listeners;

import com.hedwig.classfinder.model.ClassItem;

/**
 * Created by patrick on 15/9/24.
 */
public interface ClassFoundListener {
    void classFound(String filePath, ClassItem classItem);
}
