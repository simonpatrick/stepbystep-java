/*
 * Copyright (c) 2009-2014. 上海诺诺镑客 All rights reserved.
 * @(#) TUserStatusType.java 2014-10-27 16:48
 */

package com.springdemo.learningMVC.data.src.test.java.com.data.jdbc.types;

import com.nonobank.data.domain.TUserStatus;

/**
 * @author fuchun
 * @version $Id: TUserStatusType.java 291 2014-10-27 08:49:07Z fuchun $
 * @since 2.0
 */
public class TUserStatusType extends EnumValueType<TUserStatus, Short> {

    public TUserStatusType() {
        super(TUserStatus.class);
    }
}
