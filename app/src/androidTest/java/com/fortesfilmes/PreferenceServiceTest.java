package com.fortesfilmes;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.fortesfilmes.enumeration.SortEnum;
import com.fortesfilmes.service.PreferenceService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class PreferenceServiceTest {

    private Context context;

    @Before
    public void setup() {
        context = ApplicationProvider.getApplicationContext();
    }

    @Test
    public void getAndSetSortConfigTest() {

        // Set
        PreferenceService.getInstance(context).setSortConfig(SortEnum.RATING_ASC);
        // Get
        SortEnum sortConfig = PreferenceService.getInstance(context).getSortConfig();

        assertEquals(SortEnum.RATING_ASC, sortConfig);
    }
}