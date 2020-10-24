/*
 * Copyright © 2020 Property of Rían Errity Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007. See <LICENSE.md>
 */

package io.paradaux.autobroadcast.api;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class VersionCheckerTest {

    VersionChecker vChecker;
    String currentVersion = "1.1.1";

    @Test
    public void testVersionChecker() {
        new VersionChecker( 69377).getVersion(version -> {
            assertEquals("1.1.1", version);
            if (currentVersion.equalsIgnoreCase(version)) {
                System.out.println("There are no new updates available");
            } else {
                System.out.println("There is a new update available. \n Please update: https://www.spigotmc.org/resources/autobroadcast.69377/");
            }
        });
    }

}