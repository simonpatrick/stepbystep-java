package com.hedwig.classfinder.filters;

import com.hedwig.classfinder.model.ClassItem;

/**
 * Created by patrick on 15/9/24.
 */
public class SearchTermFilter implements ClassFinderFilter {
    @Override
    public boolean accept(String filePath, ClassItem classItem) {
        return false;
    }
}
