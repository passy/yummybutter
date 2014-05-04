package net.rdrei.android.yummybutter.app;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.TestCase;

import net.rdrei.android.yummybutter.app.dummy.DummyContent;

import java.util.Random;

public class SillyUsernameFormatterTest extends TestCase {
    @SmallTest
    public void testDummyItemFormat() {
        final SillyUsernameFormatter formatter = new SillyUsernameFormatter(new FakeRandom());
        final DummyContent.DummyItem item = new DummyContent.DummyItem("42", "Tom Ashworth");
        assertEquals(formatter.formatName(item), "The Magnificent Tom Ashworth");
    }

    public class FakeRandom extends Random {
        @Override
        public int nextInt(int n) {
            return 1;
        }
    }
}
