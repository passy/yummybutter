package net.rdrei.android.yummybutter.app;

import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.TestCase;

import net.rdrei.android.yummybutter.app.dummy.DummyContent;

import org.mockito.Mock;

import static org.mockito.Matchers.any;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.Mockito.when;

import java.util.Random;

public class SillyUsernameFormatterTest extends InstrumentationTestCase {
    @Mock
    Random mRandomMock;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Dexmaker needs a place to store its generated mocks. This happens automatically when we
        // create an activity, but since we only run a headless unit test here, we have to apply
        // this ugly hack ourselves.
        System.setProperty("dexmaker.dexcache",
                getInstrumentation().getTargetContext().getCacheDir().getPath());

        initMocks(this);
    }

    @SmallTest
    public void testDummyItemFormat() {
        when(mRandomMock.nextInt(any(Integer.class))).thenReturn(1);

        final SillyUsernameFormatter formatter = new SillyUsernameFormatter(mRandomMock);
        final DummyContent.DummyItem item = new DummyContent.DummyItem("42", "Tom Ashworth");
        assertEquals(formatter.formatName(item), "The Magnificent Tom Ashworth");
    }
}
