package com.makeid.makeflow.workflow.process.activity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class ActivityImplTest {

    @Test
    void findDefaultOutgoingTransition() {
        String s = new String("222");
        String substring = s.substring(0, 2);
        Assertions.assertTrue(substring.equals("sub_string"));
    }

    public static class Mock {
        private String substring(int i, int j) {
            return "sub_string";
        }
    }
}