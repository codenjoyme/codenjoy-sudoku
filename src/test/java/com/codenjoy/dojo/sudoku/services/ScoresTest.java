package com.codenjoy.dojo.sudoku.services;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2012 - 2022 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import com.codenjoy.dojo.services.event.ScoresMap;
import com.codenjoy.dojo.sudoku.TestGameSettings;
import com.codenjoy.dojo.utils.scorestest.AbstractScoresTest;
import org.junit.Test;

import static com.codenjoy.dojo.sudoku.services.GameSettings.Keys.*;

public class ScoresTest extends AbstractScoresTest {

    @Override
    public GameSettings settings() {
        return new TestGameSettings()
                .integer(WIN_SCORE, 1)
                .integer(LOSE_PENALTY, -1)
                .integer(SUCCESS_SCORE, 2)
                .integer(FAIL_PENALTY, -2);
    }

    @Override
    protected Class<? extends ScoresMap> scores() {
        return Scores.class;
    }

    @Override
    protected Class<? extends Enum> eventTypes() {
        return Event.class;
    }

    @Test
    public void shouldCollectScores() {
        assertEvents("100:\n" +
                "SUCCESS > +2 = 102\n" +
                "SUCCESS > +2 = 104\n" +
                "SUCCESS > +2 = 106\n" +
                "SUCCESS > +2 = 108\n" +
                "FAIL > -2 = 106\n" +
                "WIN > +1 = 107\n" +
                "LOSE > -1 = 106");
    }

    @Test
    public void shouldNotBeLessThanZero() {
        assertEvents("4:\n" +
                "FAIL > -2 = 2\n" +
                "FAIL > -2 = 0\n" +
                "FAIL > +0 = 0");
    }

    @Test
    public void shouldCleanScore() {
        assertEvents("100:\n" +
                "WIN > +1 = 101\n" +
                "(CLEAN) > -101 = 0\n" +
                "WIN > +1 = 1");
    }

    @Test
    public void shouldCollectScores_whenSuccess() {
        // given
        settings.integer(SUCCESS_SCORE, 2);

        // when then
        assertEvents("100:\n" +
                "SUCCESS > +2 = 102\n" +
                "SUCCESS > +2 = 104");
    }

    @Test
    public void shouldCollectScores_whenFail() {
        // given
        settings.integer(LOSE_PENALTY, -1);

        // when then
        assertEvents("100:\n" +
                "FAIL > -2 = 98\n" +
                "FAIL > -2 = 96");
    }

    @Test
    public void shouldCollectScores_whenWin() {
        // given
        settings.integer(WIN_SCORE, 1);

        // when then
        assertEvents("100:\n" +
                "WIN > +1 = 101\n" +
                "WIN > +1 = 102");
    }
}