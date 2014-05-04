package net.rdrei.android.yummybutter.app;

import net.rdrei.android.yummybutter.app.dummy.DummyContent;

import java.util.Random;

import javax.inject.Inject;

import dagger.Provides;

public class SillyUsernameFormatter {
    private final Random mRandom;

    private final String[] PREFIXES = {
            "The Great",
            "The Magnificent",
            "The Epic",
            "The Grand"
    };

    @Inject
    public SillyUsernameFormatter(final Random random) {
        mRandom = random;
    }

    public String formatName(final DummyContent.DummyItem item) {
        return String.format("%s %s", PREFIXES[mRandom.nextInt(PREFIXES.length)], item.content);
    }
}
