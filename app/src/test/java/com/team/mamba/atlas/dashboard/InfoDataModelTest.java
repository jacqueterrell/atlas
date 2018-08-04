package com.team.mamba.atlas.dashboard;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.*;
import org.mockito.*;


@SuppressWarnings("ResultOfMethodCallIgnored")
public class InfoDataModelTest {

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void checkAllConnections() {

        List<Integer> connectionString = new ArrayList<>();
        Map<Integer,Integer> connectionsMap = new LinkedHashMap<>();

        connectionString.add(7);
        connectionString.add(7);
        connectionString.add(6);


        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH);

        for (int i = 0; i < 6; i++){

            calendar.set(Calendar.MONTH, currentMonth - i);

            connectionsMap.put(currentMonth,0); //7,6,5,4,3,2
            currentMonth -=1;

        }

            for (Integer months: connectionString){

                for (Map.Entry<Integer,Integer> entry : connectionsMap.entrySet()){

                    int monthKey = entry.getKey();
                    int count = entry.getValue();

                    if (monthKey == months){

                        entry.setValue(count + 1);
                    }
                }

        }

        assertEquals(6,connectionsMap.size());
        assertTrue(connectionsMap.get(7).equals(2));
        assertTrue(connectionsMap.get(6).equals(1));
        assertTrue(connectionsMap.get(5).equals(0));
        assertTrue(connectionsMap.get(4).equals(0));
        assertTrue(connectionsMap.get(3).equals(0));
        assertTrue(connectionsMap.get(2).equals(0));


    }
}