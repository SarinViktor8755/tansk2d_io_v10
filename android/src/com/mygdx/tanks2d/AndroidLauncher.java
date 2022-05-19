package com.mygdx.tanks2d;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.InterstitialAd;


public class AndroidLauncher extends AndroidApplication {

	private InterstitialAd mInterstitialAd;
	private static final int PERMISSION_REQUEST_CODE = 1;


	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();


		initialize(new MainGame(1), config);
	}
}
