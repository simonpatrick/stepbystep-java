package com.hedwig.stepbystep.javatutorial.junit.rules;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

public class RulesTest {
    private static final Logger logger = LogManager.getLogger(RulesTest.class.getName());
//    Rules allow very flexible addition or redefinition of the behavior of each test
//    method in a test class. Testers can reuse or extend one of the provided
//    Rules below, or write their own.
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * https://github.com/junit-team/junit/wiki/Rules
     */
 /*   @Test
    public void countsAssets() throws IOException {
        File icon = tempFolder.newFile("icon.png");
        File assets = tempFolder.newFolder("assets");
        createAssets(assets, 3);

        DigitalAssetManager dam = new DigitalAssetManager(icon, assets);
        assertEquals(3, dam.getAssetCount());
    }

    private void createAssets(File assets, int numberOfAssets) throws IOException {
        for (int index = 0; index < numberOfAssets; index++) {
            File asset = new File(assets, String.format("asset-%d.mpg", index));
            Assert.assertTrue("Asset couldn't be created.", asset.createNewFile());
        }
    }

    @Test
    public void throwsIllegalArgumentExceptionIfIconIsNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Icon is null, not a file, or doesn't exist.");
        new DigitalAssetManager(null, null);
    }*/
}
