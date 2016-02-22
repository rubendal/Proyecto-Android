package com.antiquis_sanus.antiquis.db;

import android.provider.BaseColumns;

public final class Definition {
    public Definition(){}

    public static abstract class Entry implements BaseColumns{
        public static final String NAME = "register";
        public static final String ENTRY_ID = "id";
        public static final String ENTRY_TIMESTAMP = "timestamp";
        public static final String ENTRY_VALUE = "value";
        public static final String ENTRY_PASOS = "pasos";

    }

}
