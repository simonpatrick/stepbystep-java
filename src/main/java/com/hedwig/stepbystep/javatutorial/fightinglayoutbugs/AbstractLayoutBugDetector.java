/*
 * Copyright 2009-2012 Michael Tamm
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hedwig.stepbystep.javatutorial.fightinglayoutbugs;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.Nullable;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Base class for {@link LayoutBugDetector} implementations.
 */
public abstract class AbstractLayoutBugDetector implements LayoutBugDetector {

    private static final Log LOG = LogFactory.getLog(AbstractLayoutBugDetector.class);

    /** The directory where screenshots of erroneous pages will be saved. */
    protected File screenshotDir = new File(System.getProperty("java.io.tmpdir"));

    public void setScreenshotDir(@Nullable File screenshotDir) {
        this.screenshotDir = screenshotDir;
    }

    protected LayoutBug createLayoutBug(String message, WebPage webPage, boolean saveScreenshot) {
        return createLayoutBug(message, webPage, null, saveScreenshot, null);
    }

    protected LayoutBug createLayoutBug(String message, WebPage webPage, @Nullable Marker marker) {
        return createLayoutBug(message, webPage, null, true, marker);
    }

    protected LayoutBug createLayoutBug(String message, WebPage webPage, Screenshot screenshot, @Nullable Marker marker) {
        return createLayoutBug(message, webPage, screenshot, true, marker);
    }

    private LayoutBug createLayoutBug(String message, WebPage webPage, Screenshot screenshot, boolean saveScreenshot, @Nullable Marker marker) {
        File screenshotFile = null;
        if (saveScreenshot && screenshotDir != null) {
            if (screenshot == null) {
                screenshot = webPage.getScreenshot();
            }
            if (marker == null) {
                screenshotFile = saveScreenshot(screenshot.pixels);
            } else {
                final int[][] screenshotPixels = ImageHelper.copyOf(screenshot.pixels);
                try {
                    marker.mark(screenshotPixels);
                } catch (Exception e) {
                    LOG.error("Failed to mark screenshot.", e);
                }
                screenshotFile = saveScreenshot(screenshotPixels);
            }
        }
        return new LayoutBug(message, webPage, screenshotFile);
    }

    private File saveScreenshot(int[][] pixels) {
        File screenshotFile = null;
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String prefix = getClass().getSimpleName();
        if (prefix.startsWith("Detect")) {
            prefix = prefix.substring("Detect".length());
        }
        boolean success = false;
        try {
            if (!screenshotDir.exists()) {
                FileUtils.forceMkdir(screenshotDir);
            }
            screenshotFile = File.createTempFile(prefix + "_" + df.format(new Date()) + ".", ".png", screenshotDir);
            ImageHelper.pixelsToPngFile(pixels, screenshotFile);
            success = true;
        } catch (Exception e) {
            LOG.error("Failed to save screenshot.", e);
        } finally {
            if (!success) {
                screenshotFile = null;
            }
        }
        return screenshotFile;
    }
}
