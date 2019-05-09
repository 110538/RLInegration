package com.amc.testclasses;

import org.testng.annotations.Test;

import com.amc.baseclass.AMCBaseClass;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;

@Stories("RLRegressionValidation")
@Features("RLRegressionValidation")
public class RLAssetCreation extends AMCBaseClass {
	
	@Test(priority=1)
	public void createDifferentRLAssetsForRegression() throws Exception {
		createDifferentRLAssets();
	}

}