package com.droplay.nova;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.droplay.nova.objects.King;

public class NovaResources {
	public Bitmap BINARY;
	
	// Oh
	public AnimationFrames OH, PAUSE;
	
	// Music/sfx
	public Bitmap MUSIC_ON, MUSIC_OFF;
	public Bitmap SOUND_ON, SOUND_OFF;
	
	// Stars
	public AnimationFrames SHINY_STAR;
	public AnimationFrames HIDDEN_STAR;
	public Bitmap FAR_STARS_GROUP[];
	public Bitmap FAR_STAR[];
	public Bitmap CLOSER_STAR[];
	public Bitmap NEAR_STAR[];
	
	// Menus
	public Bitmap NOVA_LOGO;
	public Bitmap NEW_GAME, NEW_GAME_PRESSED;
	public Bitmap RATE_US, RATE_US_PRESSED;
	public Bitmap PLAY_AGAIN, PLAY_AGAIN_PRESSED;
	public Bitmap SCORES, SCORES_PRESSED;
	public Bitmap CREDITS, CREDITS_PRESSED;
	public Bitmap SHARE, SHARE_PRESSED;
	public Bitmap NEW_BEST, NO_NEW_BEST;
	
	// Crowns
	public AnimationFrames BLUE_CROWN, GREEN_CROWN, PURPLE_CROWN, ORANGE_CROWN;
	
	// Meteor
	public AnimationFrames METEOR_STARTING;
	public AnimationFrames METEOR_STARTED;
	
	// Portals
	public AnimationFrames BLUE_PORTAL;
	public AnimationFrames GREEN_PORTAL;
	public AnimationFrames PURPLE_PORTAL;
	public AnimationFrames ORANGE_PORTAL;
	
	public AnimationFrames BLUE_PARTICLES;
	public AnimationFrames GREEN_PARTICLES;
	public AnimationFrames PURPLE_PARTICLES;
	public AnimationFrames ORANGE_PARTICLES;
	
	// King
	public AnimationFrames NORMAL_DEATH;
	public AnimationFrames COLORFUL_DEATH;
	
	public Bitmap NORMAL_TO_NORMAL;
	
	public AnimationFrames[][] KING;
	
	// Loading Screen
	public AnimationFrames DROPLAY_LOGO;
	
	public AnimationFrames SWEAT, MOUTH;
	
	public Bitmap SHARE_PIC;
	
	// Paints
	public Paint SMALL_PAINT;
	public Paint COMMON_PAINT;
	
	public Bitmap getBitmap(int id) {
		Bitmap desiredBitmap = null;
		
		while (desiredBitmap == null)
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return desiredBitmap;
	}
	
	public boolean isOpeningDone() {
		return SHINY_STAR != null && FAR_STAR != null && FAR_STARS_GROUP != null &&
				HIDDEN_STAR != null && CLOSER_STAR != null && NEAR_STAR != null &&
				METEOR_STARTED != null && METEOR_STARTING != null && DROPLAY_LOGO != null &&
				SMALL_PAINT != null && KING[King.Crown.BLUE.ordinal()][King.Crown.GREEN.ordinal()] != null;
	}
	
	public boolean isMenuDone() {
		return NEW_GAME != null && NEW_GAME_PRESSED != null &&
				RATE_US != null && RATE_US_PRESSED != null &&
				SCORES != null && SCORES_PRESSED != null &&
				CREDITS != null && CREDITS_PRESSED != null &&
				SOUND_ON != null && SOUND_OFF != null &&
				MUSIC_ON != null && MUSIC_OFF != null &&
				NOVA_LOGO != null;
	}
	
	public boolean isCreditsDone() {
		return BINARY != null;
	}
	
	public boolean isGameDone() {
		return OH != null && BLUE_CROWN != null && GREEN_CROWN != null && ORANGE_CROWN != null && PURPLE_CROWN != null &&
				BLUE_PARTICLES != null && GREEN_PARTICLES != null && ORANGE_PARTICLES != null && PURPLE_PARTICLES != null &&
				BLUE_PORTAL != null && GREEN_PORTAL != null && ORANGE_PORTAL != null && PURPLE_PORTAL != null &&
				NORMAL_DEATH != null && COLORFUL_DEATH != null && KING != null && COMMON_PAINT != null &&
				NEW_BEST != null && NO_NEW_BEST != null && PLAY_AGAIN != null && PLAY_AGAIN_PRESSED != null &&
				SHARE != null && SHARE_PRESSED != null;
	}
	
	public NovaResources(final Resources resources) {
		final Options options = new Options();
		options.inPreferredConfig = Config.RGB_565;
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				// Opening screen
				// Stars
				SHINY_STAR = new AnimationFrames(1000, true,
						BitmapFactory.decodeResource(resources, R.drawable.star_so1, options),
						BitmapFactory.decodeResource(resources, R.drawable.star_so2, options),
						BitmapFactory.decodeResource(resources, R.drawable.star_so3, options),
						BitmapFactory.decodeResource(resources, R.drawable.star_so4, options),
						BitmapFactory.decodeResource(resources, R.drawable.star_so5, options));
				
				HIDDEN_STAR = new AnimationFrames(200, true,
						BitmapFactory.decodeResource(resources, R.drawable.star_ho1, options),
						BitmapFactory.decodeResource(resources, R.drawable.star_ho2, options),
						BitmapFactory.decodeResource(resources, R.drawable.star_ho3, options),
						BitmapFactory.decodeResource(resources, R.drawable.star_ho4, options));
				
				FAR_STARS_GROUP = new Bitmap[3];
				FAR_STARS_GROUP[0] = BitmapFactory.decodeResource(resources, R.drawable.fa1, options);
				FAR_STARS_GROUP[1] = BitmapFactory.decodeResource(resources, R.drawable.fa2, options);
				FAR_STARS_GROUP[2] = BitmapFactory.decodeResource(resources, R.drawable.fa3, options);
				
				FAR_STAR = new Bitmap[6];
				FAR_STAR[0] = BitmapFactory.decodeResource(resources, R.drawable.far1, options);
				FAR_STAR[1] = BitmapFactory.decodeResource(resources, R.drawable.far2, options);
				FAR_STAR[2] = BitmapFactory.decodeResource(resources, R.drawable.far3, options);
				FAR_STAR[3] = BitmapFactory.decodeResource(resources, R.drawable.far4, options);
				FAR_STAR[4] = BitmapFactory.decodeResource(resources, R.drawable.far5, options);
				FAR_STAR[5] = BitmapFactory.decodeResource(resources, R.drawable.far6, options);
				
				CLOSER_STAR = new Bitmap[5];
				CLOSER_STAR[0] = BitmapFactory.decodeResource(resources, R.drawable.mid1, options);
				CLOSER_STAR[1] = BitmapFactory.decodeResource(resources, R.drawable.mid2, options);
				CLOSER_STAR[2] = BitmapFactory.decodeResource(resources, R.drawable.mid3, options);
				CLOSER_STAR[3] = BitmapFactory.decodeResource(resources, R.drawable.mid4, options);
				CLOSER_STAR[4] = BitmapFactory.decodeResource(resources, R.drawable.mid5, options);
				
				NEAR_STAR = new Bitmap[2];
				NEAR_STAR[0] = BitmapFactory.decodeResource(resources, R.drawable.close1, options);
				NEAR_STAR[1] = BitmapFactory.decodeResource(resources, R.drawable.close2, options);
				
				// Meteor
				METEOR_STARTING = new AnimationFrames(22, false,
						BitmapFactory.decodeResource(resources, R.drawable.smet1, options),
						BitmapFactory.decodeResource(resources, R.drawable.smet2, options),
						BitmapFactory.decodeResource(resources, R.drawable.smet3, options),
						BitmapFactory.decodeResource(resources, R.drawable.smet4, options),
						BitmapFactory.decodeResource(resources, R.drawable.smet5, options),
						BitmapFactory.decodeResource(resources, R.drawable.smet6, options),
						BitmapFactory.decodeResource(resources, R.drawable.smet7, options),
						BitmapFactory.decodeResource(resources, R.drawable.smet8, options),
						BitmapFactory.decodeResource(resources, R.drawable.smet9, options));
				
				METEOR_STARTED = new AnimationFrames(22, false,
						BitmapFactory.decodeResource(resources, R.drawable.met1, options),
						BitmapFactory.decodeResource(resources, R.drawable.met2, options),
						BitmapFactory.decodeResource(resources, R.drawable.met3, options),
						BitmapFactory.decodeResource(resources, R.drawable.met4, options),
						BitmapFactory.decodeResource(resources, R.drawable.met5, options),
						BitmapFactory.decodeResource(resources, R.drawable.met6, options),
						BitmapFactory.decodeResource(resources, R.drawable.met7, options),
						BitmapFactory.decodeResource(resources, R.drawable.met8, options),
						BitmapFactory.decodeResource(resources, R.drawable.met9, options));
				
				// Droplay logo
				DROPLAY_LOGO = new AnimationFrames(30, false,
						BitmapFactory.decodeResource(resources, R.drawable.droplay1, options),
						BitmapFactory.decodeResource(resources, R.drawable.droplay2, options),
						BitmapFactory.decodeResource(resources, R.drawable.droplay3, options),
						BitmapFactory.decodeResource(resources, R.drawable.droplay4, options),
						BitmapFactory.decodeResource(resources, R.drawable.droplay5, options),
						BitmapFactory.decodeResource(resources, R.drawable.droplay6, options),
						BitmapFactory.decodeResource(resources, R.drawable.droplay7, options),
						BitmapFactory.decodeResource(resources, R.drawable.droplay8, options),
						BitmapFactory.decodeResource(resources, R.drawable.droplay9, options),
						BitmapFactory.decodeResource(resources, R.drawable.droplay10, options),
						BitmapFactory.decodeResource(resources, R.drawable.droplay11, options),
						BitmapFactory.decodeResource(resources, R.drawable.droplay12, options),
						BitmapFactory.decodeResource(resources, R.drawable.droplay13, options),
						BitmapFactory.decodeResource(resources, R.drawable.droplay14, options),
						BitmapFactory.decodeResource(resources, R.drawable.droplay15, options),
						BitmapFactory.decodeResource(resources, R.drawable.droplay16, options),
						BitmapFactory.decodeResource(resources, R.drawable.droplay17, options),
						BitmapFactory.decodeResource(resources, R.drawable.droplay18, options),
						BitmapFactory.decodeResource(resources, R.drawable.droplay19, options),
						BitmapFactory.decodeResource(resources, R.drawable.droplay20, options),
						BitmapFactory.decodeResource(resources, R.drawable.droplay21, options),
						BitmapFactory.decodeResource(resources, R.drawable.droplay22, options),
						BitmapFactory.decodeResource(resources, R.drawable.droplay23, options));
				
				// Load one shape of the king cause it's being used in velocity.
				KING = new AnimationFrames[5][5];
				KING[King.Crown.BLUE.ordinal()][King.Crown.GREEN.ordinal()] = new AnimationFrames(100, false,
						BitmapFactory.decodeResource(resources, R.drawable.bg1, options),
						BitmapFactory.decodeResource(resources, R.drawable.bg2, options),
						BitmapFactory.decodeResource(resources, R.drawable.bg3, options),
						BitmapFactory.decodeResource(resources, R.drawable.bg4, options));
				
				SMALL_PAINT = new Paint();
				SMALL_PAINT.setTypeface(Typeface.createFromAsset(resources.getAssets(), "fonts/small_pixel.ttf"));
				
				MUSIC_ON = BitmapFactory.decodeResource(resources, R.drawable.music, options);
				MUSIC_OFF = BitmapFactory.decodeResource(resources, R.drawable.music_off, options);
				SOUND_ON = BitmapFactory.decodeResource(resources, R.drawable.sfx, options);
				SOUND_OFF = BitmapFactory.decodeResource(resources, R.drawable.sfx_off, options);
				
				NOVA_LOGO = BitmapFactory.decodeResource(resources, R.drawable.logo, options);
				
				CREDITS = BitmapFactory.decodeResource(resources, R.drawable.cre1, options);
				CREDITS_PRESSED = BitmapFactory.decodeResource(resources, R.drawable.cre2, options);
				
				NEW_GAME = BitmapFactory.decodeResource(resources, R.drawable.play1, options);
				NEW_GAME_PRESSED = BitmapFactory.decodeResource(resources, R.drawable.play2, options);
				
				RATE_US = BitmapFactory.decodeResource(resources, R.drawable.rtus1, options);
				RATE_US_PRESSED = BitmapFactory.decodeResource(resources, R.drawable.rtus2, options);
				
				
				PLAY_AGAIN = BitmapFactory.decodeResource(resources, R.drawable.pagn1, options);
				PLAY_AGAIN_PRESSED = BitmapFactory.decodeResource(resources, R.drawable.pagn2, options);
				
				SCORES = BitmapFactory.decodeResource(resources, R.drawable.rec1, options);
				SCORES_PRESSED = BitmapFactory.decodeResource(resources, R.drawable.rec2, options);
				
				
				// Credits
				BINARY = BitmapFactory.decodeResource(resources, R.drawable.binary, options);
				
				// Game
				OH = new AnimationFrames(30, false,
						BitmapFactory.decodeResource(resources, R.drawable.oh1, options),
						BitmapFactory.decodeResource(resources, R.drawable.oh2, options),
						BitmapFactory.decodeResource(resources, R.drawable.oh3, options),
						BitmapFactory.decodeResource(resources, R.drawable.oh4, options),
						BitmapFactory.decodeResource(resources, R.drawable.oh5, options),
						BitmapFactory.decodeResource(resources, R.drawable.oh6, options));
				
				NEW_BEST = BitmapFactory.decodeResource(resources, R.drawable.best, options);
				NO_NEW_BEST = BitmapFactory.decodeResource(resources, R.drawable.no_best, options);
				
				SHARE = BitmapFactory.decodeResource(resources, R.drawable.shr1, options);
				SHARE_PRESSED = BitmapFactory.decodeResource(resources, R.drawable.shr2, options);
				
				// Crown
				BLUE_CROWN = new AnimationFrames(30, true,
						BitmapFactory.decodeResource(resources, R.drawable.ccb1, options),
						BitmapFactory.decodeResource(resources, R.drawable.ccb2, options),
						BitmapFactory.decodeResource(resources, R.drawable.ccb3, options),
						BitmapFactory.decodeResource(resources, R.drawable.ccb4, options));
				
				GREEN_CROWN = new AnimationFrames(30, true,
						BitmapFactory.decodeResource(resources, R.drawable.ccg1, options),
						BitmapFactory.decodeResource(resources, R.drawable.ccg2, options),
						BitmapFactory.decodeResource(resources, R.drawable.ccg3, options),
						BitmapFactory.decodeResource(resources, R.drawable.ccg4, options));
				
				PURPLE_CROWN = new AnimationFrames(30, true,
						BitmapFactory.decodeResource(resources, R.drawable.ccp1, options),
						BitmapFactory.decodeResource(resources, R.drawable.ccp2, options),
						BitmapFactory.decodeResource(resources, R.drawable.ccp3, options),
						BitmapFactory.decodeResource(resources, R.drawable.ccp4, options));
				
				ORANGE_CROWN = new AnimationFrames(30, true,
						BitmapFactory.decodeResource(resources, R.drawable.cco1, options),
						BitmapFactory.decodeResource(resources, R.drawable.cco2, options),
						BitmapFactory.decodeResource(resources, R.drawable.cco3, options),
						BitmapFactory.decodeResource(resources, R.drawable.cco4, options));
				
				// King
				NORMAL_TO_NORMAL = BitmapFactory.decodeResource(resources, R.drawable.fg5, options);
				
				NORMAL_DEATH = new AnimationFrames(30, false,
						BitmapFactory.decodeResource(resources, R.drawable.dnl1, options),
						BitmapFactory.decodeResource(resources, R.drawable.dnl2, options),
						BitmapFactory.decodeResource(resources, R.drawable.dnl3, options),
						BitmapFactory.decodeResource(resources, R.drawable.dnl4, options),
						BitmapFactory.decodeResource(resources, R.drawable.dnl5, options),
						BitmapFactory.decodeResource(resources, R.drawable.dnl6, options),
						BitmapFactory.decodeResource(resources, R.drawable.dnl7, options),
						BitmapFactory.decodeResource(resources, R.drawable.dnl8, options),
						BitmapFactory.decodeResource(resources, R.drawable.dnl9, options));
				
				COLORFUL_DEATH = new AnimationFrames(30, false,
						BitmapFactory.decodeResource(resources, R.drawable.dcl1, options),
						BitmapFactory.decodeResource(resources, R.drawable.dcl2, options),
						BitmapFactory.decodeResource(resources, R.drawable.dcl3, options),
						BitmapFactory.decodeResource(resources, R.drawable.dcl4, options),
						BitmapFactory.decodeResource(resources, R.drawable.dcl5, options),
						BitmapFactory.decodeResource(resources, R.drawable.dcl6, options),
						BitmapFactory.decodeResource(resources, R.drawable.dcl7, options),
						BitmapFactory.decodeResource(resources, R.drawable.dcl8, options),
						BitmapFactory.decodeResource(resources, R.drawable.dcl9, options));
				
				KING[King.Crown.NORMAL.ordinal()][King.Crown.BLUE.ordinal()] = new AnimationFrames(100, false,
						BitmapFactory.decodeResource(resources, R.drawable.tb1, options),
						BitmapFactory.decodeResource(resources, R.drawable.tb2, options),
						BitmapFactory.decodeResource(resources, R.drawable.tb3, options),
						BitmapFactory.decodeResource(resources, R.drawable.tb4, options),
						BitmapFactory.decodeResource(resources, R.drawable.tb5, options),
						BitmapFactory.decodeResource(resources, R.drawable.tb6, options),
						BitmapFactory.decodeResource(resources, R.drawable.tb7, options),
						BitmapFactory.decodeResource(resources, R.drawable.tb8, options));
						
				KING[King.Crown.NORMAL.ordinal()][King.Crown.GREEN.ordinal()] = new AnimationFrames(100, false,
						BitmapFactory.decodeResource(resources, R.drawable.tg1, options),
						BitmapFactory.decodeResource(resources, R.drawable.tg2, options),
						BitmapFactory.decodeResource(resources, R.drawable.tg3, options),
						BitmapFactory.decodeResource(resources, R.drawable.tg4, options),
						BitmapFactory.decodeResource(resources, R.drawable.tg5, options),
						BitmapFactory.decodeResource(resources, R.drawable.tg6, options),
						BitmapFactory.decodeResource(resources, R.drawable.tg7, options),
						BitmapFactory.decodeResource(resources, R.drawable.tg8, options));
				
				KING[King.Crown.NORMAL.ordinal()][King.Crown.PURPLE.ordinal()] = new AnimationFrames(100, false,
						BitmapFactory.decodeResource(resources, R.drawable.tp1, options),
						BitmapFactory.decodeResource(resources, R.drawable.tp2, options),
						BitmapFactory.decodeResource(resources, R.drawable.tp3, options),
						BitmapFactory.decodeResource(resources, R.drawable.tp4, options),
						BitmapFactory.decodeResource(resources, R.drawable.tp5, options),
						BitmapFactory.decodeResource(resources, R.drawable.tp6, options),
						BitmapFactory.decodeResource(resources, R.drawable.tp7, options),
						BitmapFactory.decodeResource(resources, R.drawable.tp8, options));
				
				KING[King.Crown.NORMAL.ordinal()][King.Crown.ORANGE.ordinal()] = new AnimationFrames(100, false,
						BitmapFactory.decodeResource(resources, R.drawable.to1, options),
						BitmapFactory.decodeResource(resources, R.drawable.to2, options),
						BitmapFactory.decodeResource(resources, R.drawable.to3, options),
						BitmapFactory.decodeResource(resources, R.drawable.to4, options),
						BitmapFactory.decodeResource(resources, R.drawable.to5, options),
						BitmapFactory.decodeResource(resources, R.drawable.to6, options),
						BitmapFactory.decodeResource(resources, R.drawable.to7, options),
						BitmapFactory.decodeResource(resources, R.drawable.to8, options));
				
				KING[King.Crown.BLUE.ordinal()][King.Crown.NORMAL.ordinal()] = new AnimationFrames(100, false,
						BitmapFactory.decodeResource(resources, R.drawable.fb1, options),
						BitmapFactory.decodeResource(resources, R.drawable.fb2, options),
						BitmapFactory.decodeResource(resources, R.drawable.fb3, options),
						BitmapFactory.decodeResource(resources, R.drawable.fb4, options),
						BitmapFactory.decodeResource(resources, R.drawable.fb5, options));
				
				KING[King.Crown.BLUE.ordinal()][King.Crown.PURPLE.ordinal()] = new AnimationFrames(100, false,
						BitmapFactory.decodeResource(resources, R.drawable.bp1, options),
						BitmapFactory.decodeResource(resources, R.drawable.bp2, options),
						BitmapFactory.decodeResource(resources, R.drawable.bp3, options),
						BitmapFactory.decodeResource(resources, R.drawable.bp4, options));
				
				KING[King.Crown.BLUE.ordinal()][King.Crown.ORANGE.ordinal()] = new AnimationFrames(100, false,
						BitmapFactory.decodeResource(resources, R.drawable.bo1, options),
						BitmapFactory.decodeResource(resources, R.drawable.bo2, options),
						BitmapFactory.decodeResource(resources, R.drawable.bo3, options),
						BitmapFactory.decodeResource(resources, R.drawable.bo4, options));
				
				KING[King.Crown.GREEN.ordinal()][King.Crown.NORMAL.ordinal()] = new AnimationFrames(100, false,
						BitmapFactory.decodeResource(resources, R.drawable.fg1, options),
						BitmapFactory.decodeResource(resources, R.drawable.fg2, options),
						BitmapFactory.decodeResource(resources, R.drawable.fg3, options),
						BitmapFactory.decodeResource(resources, R.drawable.fg4, options),
						BitmapFactory.decodeResource(resources, R.drawable.fg5, options));
				
				KING[King.Crown.GREEN.ordinal()][King.Crown.PURPLE.ordinal()] = new AnimationFrames(100, false,
						BitmapFactory.decodeResource(resources, R.drawable.gp1, options),
						BitmapFactory.decodeResource(resources, R.drawable.gp2, options),
						BitmapFactory.decodeResource(resources, R.drawable.gp3, options),
						BitmapFactory.decodeResource(resources, R.drawable.gp4, options));
				
				KING[King.Crown.GREEN.ordinal()][King.Crown.ORANGE.ordinal()] = new AnimationFrames(100, false,
						BitmapFactory.decodeResource(resources, R.drawable.go1, options),
						BitmapFactory.decodeResource(resources, R.drawable.go2, options),
						BitmapFactory.decodeResource(resources, R.drawable.go3, options),
						BitmapFactory.decodeResource(resources, R.drawable.go4, options));
				
				KING[King.Crown.GREEN.ordinal()][King.Crown.BLUE.ordinal()] = new AnimationFrames(100, false,
						BitmapFactory.decodeResource(resources, R.drawable.gb1, options),
						BitmapFactory.decodeResource(resources, R.drawable.gb2, options),
						BitmapFactory.decodeResource(resources, R.drawable.gb3, options),
						BitmapFactory.decodeResource(resources, R.drawable.gb4, options));
				
				KING[King.Crown.PURPLE.ordinal()][King.Crown.NORMAL.ordinal()] = new AnimationFrames(100, false,
						BitmapFactory.decodeResource(resources, R.drawable.fp1, options),
						BitmapFactory.decodeResource(resources, R.drawable.fp2, options),
						BitmapFactory.decodeResource(resources, R.drawable.fp3, options),
						BitmapFactory.decodeResource(resources, R.drawable.fp4, options),
						BitmapFactory.decodeResource(resources, R.drawable.fp5, options));
				
				KING[King.Crown.PURPLE.ordinal()][King.Crown.GREEN.ordinal()] = new AnimationFrames(100, false,
						BitmapFactory.decodeResource(resources, R.drawable.pg1, options),
						BitmapFactory.decodeResource(resources, R.drawable.pg2, options),
						BitmapFactory.decodeResource(resources, R.drawable.pg3, options),
						BitmapFactory.decodeResource(resources, R.drawable.pg4, options));
				
				KING[King.Crown.PURPLE.ordinal()][King.Crown.ORANGE.ordinal()] = new AnimationFrames(100, false,
						BitmapFactory.decodeResource(resources, R.drawable.po1, options),
						BitmapFactory.decodeResource(resources, R.drawable.po2, options),
						BitmapFactory.decodeResource(resources, R.drawable.po3, options),
						BitmapFactory.decodeResource(resources, R.drawable.po4, options));
				
				KING[King.Crown.PURPLE.ordinal()][King.Crown.BLUE.ordinal()] = new AnimationFrames(100, false,
						BitmapFactory.decodeResource(resources, R.drawable.pb1, options),
						BitmapFactory.decodeResource(resources, R.drawable.pb2, options),
						BitmapFactory.decodeResource(resources, R.drawable.pb3, options),
						BitmapFactory.decodeResource(resources, R.drawable.pb4, options));
				
				KING[King.Crown.ORANGE.ordinal()][King.Crown.NORMAL.ordinal()] = new AnimationFrames(100, false,
						BitmapFactory.decodeResource(resources, R.drawable.fo1, options),
						BitmapFactory.decodeResource(resources, R.drawable.fo2, options),
						BitmapFactory.decodeResource(resources, R.drawable.fo3, options),
						BitmapFactory.decodeResource(resources, R.drawable.fo4, options),
						BitmapFactory.decodeResource(resources, R.drawable.fo5, options));
				
				KING[King.Crown.ORANGE.ordinal()][King.Crown.GREEN.ordinal()] = new AnimationFrames(100, false,
						BitmapFactory.decodeResource(resources, R.drawable.og1, options),
						BitmapFactory.decodeResource(resources, R.drawable.og2, options),
						BitmapFactory.decodeResource(resources, R.drawable.og3, options),
						BitmapFactory.decodeResource(resources, R.drawable.og4, options));
				
				KING[King.Crown.ORANGE.ordinal()][King.Crown.PURPLE.ordinal()] = new AnimationFrames(100, false,
						BitmapFactory.decodeResource(resources, R.drawable.op1, options),
						BitmapFactory.decodeResource(resources, R.drawable.op2, options),
						BitmapFactory.decodeResource(resources, R.drawable.op3, options),
						BitmapFactory.decodeResource(resources, R.drawable.op4, options));
				
				KING[King.Crown.ORANGE.ordinal()][King.Crown.BLUE.ordinal()] = new AnimationFrames(100, false,
						BitmapFactory.decodeResource(resources, R.drawable.ob1, options),
						BitmapFactory.decodeResource(resources, R.drawable.ob2, options),
						BitmapFactory.decodeResource(resources, R.drawable.ob3, options),
						BitmapFactory.decodeResource(resources, R.drawable.ob4, options),
						BitmapFactory.decodeResource(resources, R.drawable.ob5, options));
				
				
				
				// Portals
				BLUE_PORTAL = new AnimationFrames(30, true,
						BitmapFactory.decodeResource(resources, R.drawable.portal_blue1, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_blue2, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_blue3, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_blue4, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_blue5, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_blue6, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_blue7, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_blue8, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_blue9, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_blue10, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_blue11, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_blue12, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_blue13, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_blue14, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_blue15, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_blue16, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_blue17, options));
				
				ORANGE_PORTAL = new AnimationFrames(30, true,
						BitmapFactory.decodeResource(resources, R.drawable.portal_orange1, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_orange2, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_orange3, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_orange4, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_orange5, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_orange6, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_orange7, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_orange8, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_orange9, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_orange10, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_orange11, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_orange12, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_orange13, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_orange14, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_orange15, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_orange16, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_orange17, options));
				
				GREEN_PORTAL = new AnimationFrames(30, true,
						BitmapFactory.decodeResource(resources, R.drawable.portal_green1, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_green2, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_green3, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_green4, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_green5, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_green6, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_green7, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_green8, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_green9, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_green10, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_green11, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_green12, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_green13, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_green14, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_green15, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_green16, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_green17, options));
				
				PURPLE_PORTAL = new AnimationFrames(30, true,
						BitmapFactory.decodeResource(resources, R.drawable.portal_purple1, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_purple2, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_purple3, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_purple4, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_purple5, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_purple6, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_purple7, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_purple8, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_purple9, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_purple10, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_purple11, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_purple12, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_purple13, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_purple14, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_purple15, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_purple16, options),
						BitmapFactory.decodeResource(resources, R.drawable.portal_purple17, options));
				
				BLUE_PARTICLES = new AnimationFrames(10, true,
						BitmapFactory.decodeResource(resources, R.drawable.ppb1, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppb2, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppb3, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppb4, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppb5, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppb6, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppb7, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppb8, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppb9, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppb10, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppb11, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppb12, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppb13, options));
				
				PURPLE_PARTICLES = new AnimationFrames(10, true,
						BitmapFactory.decodeResource(resources, R.drawable.ppp1, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppp2, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppp3, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppp4, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppp5, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppp6, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppp7, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppp8, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppp9, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppp10, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppp11, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppp12, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppp13, options));
				
				GREEN_PARTICLES = new AnimationFrames(10, true,
						BitmapFactory.decodeResource(resources, R.drawable.ppg1, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppg2, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppg3, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppg4, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppg5, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppg6, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppg7, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppg8, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppg9, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppg10, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppg11, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppg12, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppg13, options));
				
				ORANGE_PARTICLES = new AnimationFrames(10, true,
						BitmapFactory.decodeResource(resources, R.drawable.ppo1, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppo2, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppo3, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppo4, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppo5, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppo6, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppo7, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppo8, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppo9, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppo10, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppo11, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppo12, options),
						BitmapFactory.decodeResource(resources, R.drawable.ppo13, options));
				
				SWEAT = new AnimationFrames(50, false,
						BitmapFactory.decodeResource(resources, R.drawable.swet1, options),
						BitmapFactory.decodeResource(resources, R.drawable.swet2, options),
						BitmapFactory.decodeResource(resources, R.drawable.swet3, options),
						BitmapFactory.decodeResource(resources, R.drawable.swet4, options));
				
				MOUTH = new AnimationFrames(70, false,
						BitmapFactory.decodeResource(resources, R.drawable.omth1, options),
						BitmapFactory.decodeResource(resources, R.drawable.omth2, options),
						BitmapFactory.decodeResource(resources, R.drawable.omth3, options));
				
				PAUSE = new AnimationFrames(30, false,
						BitmapFactory.decodeResource(resources, R.drawable.pse1, options),
						BitmapFactory.decodeResource(resources, R.drawable.pse2, options),
						BitmapFactory.decodeResource(resources, R.drawable.pse3, options),
						BitmapFactory.decodeResource(resources, R.drawable.pse4, options),
						BitmapFactory.decodeResource(resources, R.drawable.pse5, options),
						BitmapFactory.decodeResource(resources, R.drawable.pse6, options),
						BitmapFactory.decodeResource(resources, R.drawable.pse7, options),
						BitmapFactory.decodeResource(resources, R.drawable.pse8, options));
				
				SHARE_PIC = BitmapFactory.decodeResource(resources, R.drawable.share_pic, options);
				
				// Paints
				COMMON_PAINT = new Paint();
				COMMON_PAINT.setTypeface(Typeface.createFromAsset(resources.getAssets(), "fonts/COMMP__.TTF"));
			}
		}).start();
	}
}
