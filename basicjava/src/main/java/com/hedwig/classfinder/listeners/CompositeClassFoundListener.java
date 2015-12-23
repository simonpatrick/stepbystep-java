package com.hedwig.classfinder.listeners;

import com.google.common.collect.Lists;
import com.hedwig.classfinder.model.ClassItem;

import java.util.Collections;
import java.util.List;

/**
 * Created by patrick on 15/9/24.
 */
public class CompositeClassFoundListener implements ClassFoundListener {

    private List<ClassFoundListener> listeners = Lists.newArrayList();

    @Override
    public void classFound(String filePath, ClassItem classItem) {
        if (listeners.size() == 0) throw new RuntimeException("classfoundListener doesn't exist" +
                ",please set at least one");

        for (ClassFoundListener listener : listeners) {
            listener.classFound(filePath, classItem);
        }
    }

    /**
     * Add cutomer class found listeners
     *
     * @param custlisteners custom listeners
     * @return this
     */
    public CompositeClassFoundListener addListeners(ClassFoundListener... custlisteners) {
        Collections.addAll(listeners, custlisteners);
        return this;
    }
}
