/*
 * Copyright (C) 2013 DroidDriver committers
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

package com.google.android.droiddriver.uiautomation;

import android.app.UiAutomation;
import android.os.SystemClock;
import android.view.accessibility.AccessibilityNodeInfo;

import com.google.android.droiddriver.UiElement;
import com.google.android.droiddriver.base.AbstractDroidDriver;
import com.google.android.droiddriver.exceptions.ElementNotFoundException;
import com.google.common.base.Preconditions;

/**
 * Implementation of a DroidDriver that is driven via the accessibility layer.
 */
public class UiAutomationDriver extends AbstractDroidDriver {

  private final UiAutomation uiAutomation;

  public UiAutomationDriver(UiAutomation uiAutomation) {
    this.uiAutomation = Preconditions.checkNotNull(uiAutomation);
  }

  @Override
  public UiElement getRootElement() {
    return UiAutomationDrivers.newUiAutomationElement(uiAutomation, getRootNode());
  }

  private AccessibilityNodeInfo getRootNode() {
    for (int i = 0; i < 3; i++) {
      AccessibilityNodeInfo root = uiAutomation.getRootInActiveWindow();
      if (root != null) {
        return root;
      }
      SystemClock.sleep(250);
    }
    throw new ElementNotFoundException("Could not find root node!");
  }
}