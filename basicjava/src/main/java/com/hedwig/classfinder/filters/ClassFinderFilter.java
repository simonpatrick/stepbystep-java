package com.hedwig.classfinder.filters;

import com.hedwig.classfinder.model.ClassItem;

/**
 * Created by patrick on 15/9/24.
 */
public interface ClassFinderFilter {
    boolean accept(String filePath, ClassItem classItem);
}
