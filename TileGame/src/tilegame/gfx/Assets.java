package tilegame.gfx;

import java.awt.Font;
import java.awt.image.BufferedImage;

public class Assets {
	
	private static final int width = 32, height = 32;
	private static final int pWidth = 120, pHeight = 130;
	private static final int qgWidth = 32, qgHeight = 32;
	private static final int swordWidth = 31, swordHeight = 30;
	// --- MENU ---
	
	public static BufferedImage menuBackground;
	public static BufferedImage menuStart, menuStartHover;
	public static BufferedImage menuExit, menuExitHover;
	// --- MENU ---
	
	// --- TILES ---
		//GRASS TILES
	public static BufferedImage grassEmpty, grassGrass1, grassGrass2, grassGrass3,
								grassFlower1, grassFlower2, grassFlower3, grassFlower4, grassFlower5,
								grassStone1, grassStone2, grassStone3, grassStone4,
								grassSignRight, grassSignLeft, grassSignDouble,
								grassFenceBL, grassFenceBM, grassFenceBR,
								grassFenceML, grassFenceMR,
								grassFenceTL, grassFenceTM, grassFenceTR,
								grassFenceLeftEnd,
								grassBush;
	// --- TILES ---
	
	// --- STATIC ENTITIES ---
	
	public static BufferedImage[] fire;
	public static BufferedImage[] heart;
	// --- STATIC ENTITIES ---
	// --- CREATURES ---
	
		//PLAYER
	public static BufferedImage[] playerUp, playerDown, playerLeft, playerRight, playerIdleDown, playerIdleUp, playerIdleLeft, playerIdleRight;
	
		//QUEST GIVER
	public static BufferedImage[] questGiver1Idle, questGiver1Waiting, questGiver1Down, questGiver1Up, questGiver1Left, questGiver1Right,
								  questGiver2Idle, questGiver2Waiting, questGiver2Down, questGiver2Up, questGiver2Left, questGiver2Right;
	
	public static BufferedImage[] skeletonBossFirst, skeletonBossSecond, skeletonBossThird;
	
	// --- CREATURES
	// --- ITEMS ---
		// WEAPONS - METAL SWORD
	public static BufferedImage[] swordDownIdle, swordUpIdle, swordLeftIdle, swordRightIdle,
								  swordDownAttack, swordUpAttack, swordLeftAttack, swordRightAttack;
	public static BufferedImage[] swordGoldDownIdle, swordGoldUpIdle, swordGoldLeftIdle, swordGoldRightIdle,
	  swordGoldDownAttack, swordGoldUpAttack, swordGoldLeftAttack, swordGoldRightAttack;

	// --- ITEMS ---
	
	
	// FONTS
	public static Font smallFont, mediumFont, largeFont, hugeFont;
	
	// FONTS
	public static void init(){
		
		initMenu();
		
		initTiles();
		initStaticEntities();
		initPlayer();
		initQuestGivers();
		initMonsters();
		initBosses();
		initWeapons();
		
		initFonts();
	
	}
	
	private static void initFonts(){
		smallFont = new Font("Serif", Font.BOLD, 10);
		mediumFont = new Font("Serif", Font.BOLD, 20);
		largeFont = new Font("Serif", Font.BOLD, 30);
		hugeFont = new Font("Serif", Font.BOLD, 40);
	}
	
	private static void initMenu(){
		
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/mainMenu/mainMenu.jpg"));
		menuBackground = sheet.crop(0, 0, 604, 423);
		
		sheet = new SpriteSheet(ImageLoader.loadImage("/mainMenu/menuStart.png"));
		menuStart = sheet.crop(0, 0, 199, 41);
		
		sheet = new SpriteSheet(ImageLoader.loadImage("/mainMenu/menuStartHover.png"));
		menuStartHover = sheet.crop(0, 0, 199, 41);
		
		sheet = new SpriteSheet(ImageLoader.loadImage("/mainMenu/menuExit.png"));
		menuExit = sheet.crop(0, 0, 147, 40);
		
		sheet = new SpriteSheet(ImageLoader.loadImage("/mainMenu/menuExitHover.png"));
		menuExitHover = sheet.crop(0, 0, 147, 40);

	}
	
	private static void initTiles(){
		
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/grass sprite sheet.png"));
		grassEmpty = sheet.crop( 0*width, 0*height , width, height);
		grassGrass1 = sheet.crop( 1*width, 0*height , width, height);
		grassGrass2 = sheet.crop( 5*width, 0*height , width, height);
		grassGrass3 = sheet.crop( 6*width, 0*height , width, height);
		grassFlower1 = sheet.crop( 2*width, 0*height , width, height);
		grassFlower2 = sheet.crop( 3*width, 0*height , width, height);
		grassFlower3 = sheet.crop( 4*width, 0*height , width, height);
		grassFlower4 = sheet.crop( 7*width, 0*height , width-2, height);
		grassFlower5 = sheet.crop( 0*width, 1*height , width, height);
		grassStone1 = sheet.crop( 1*width, 1*height , width, height);
		grassStone2 = sheet.crop( 2*width, 1*height , width, height);
		grassStone3 = sheet.crop( 3*width, 1*height , width, height);
		grassStone4 = sheet.crop( 4*width, 1*height , width, height);
		grassSignRight = sheet.crop( 5*width, 1*height , width, height);
		grassSignLeft = sheet.crop( 6*width, 1*height , width, height);
		grassSignDouble = sheet.crop( 7*width, 1*height , width-2, height);
		grassFenceBL = sheet.crop( 0*width, 2*height , width, height); //Dole-Levo
		grassFenceBM = sheet.crop( 1*width, 2*height , width, height); // Dole-Sredina
		grassFenceBR = sheet.crop( 2*width, 2*height , width, height); // Dole-Desno
		grassFenceML = sheet.crop( 4*width, 2*height , width, height); // Sredina-Levo
		grassFenceMR = sheet.crop( 3*width, 2*height , width, height); // Sredina-Desno
		grassFenceTL = sheet.crop( 6*width, 2*height , width, height); // Gore-Levo
		grassFenceTM = sheet.crop( 7*width, 2*height , width-2, height); // Gore-Sredina
		grassFenceTR = sheet.crop( 0*width, 3*height , width, height); // Gore-Desno
		grassFenceLeftEnd = sheet.crop( 5*width, 2*height , width, height);
		// FALI grassFenceRightEnd
		grassBush = sheet.crop( 1*width, 3*height , width, height);
	}
	
	private static void initStaticEntities(){
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/fire.png"));
		
		fire = new BufferedImage[4];
		fire[0] = sheet.crop(0, 0, 32, 32);
		fire[1] = sheet.crop(32, 0, 32, 32);
		fire[2] = sheet.crop(0, 0, 32, 32);
		fire[3] = sheet.crop(0, 32, 32, 32);
		
		sheet = new SpriteSheet(ImageLoader.loadImage("/textures/heart.png"));
		heart = new BufferedImage[4];
		heart[0] = sheet.crop(0, 0, 32, 32);
		heart[1] = sheet.crop(32, 0, 32, 32);
		heart[2] = sheet.crop(0, 32, 32, 32);
		heart[3] = sheet.crop(32, 32, 32, 32);
	}
	
	private static void initPlayer(){
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/playerSheet.png"));
		
		playerUp = new BufferedImage[10];
		playerUp[0] = sheet.crop( 0 , 6*pHeight, pWidth, pHeight);
		playerUp[1] = sheet.crop( 1*pWidth , 6*pHeight, pWidth, pHeight);
		playerUp[2] = sheet.crop( 2*pWidth , 6*pHeight, pWidth, pHeight);
		playerUp[3] = sheet.crop( 3*pWidth , 6*pHeight, pWidth, pHeight);
		playerUp[4] = sheet.crop( 4*pWidth , 6*pHeight, pWidth, pHeight);
		playerUp[5] = sheet.crop( 5*pWidth , 6*pHeight, pWidth, pHeight);
		playerUp[6] = sheet.crop( 6*pWidth , 6*pHeight, pWidth, pHeight);
		playerUp[7] = sheet.crop( 7*pWidth , 6*pHeight, pWidth, pHeight);
		playerUp[8] = sheet.crop( 8*pWidth , 6*pHeight, pWidth, pHeight);
		playerUp[9] = sheet.crop( 9*pWidth , 6*pHeight, pWidth, pHeight);
		
		playerDown = new BufferedImage[10];
		playerDown[0] = sheet.crop( 0 , 4*pHeight, pWidth, pHeight);
		playerDown[1] = sheet.crop( 1*pWidth , 4*pHeight, pWidth, pHeight);
		playerDown[2] = sheet.crop( 2*pWidth , 4*pHeight, pWidth, pHeight);
		playerDown[3] = sheet.crop( 3*pWidth , 4*pHeight, pWidth, pHeight);
		playerDown[4] = sheet.crop( 4*pWidth , 4*pHeight, pWidth, pHeight);
		playerDown[5] = sheet.crop( 5*pWidth , 4*pHeight, pWidth, pHeight);
		playerDown[6] = sheet.crop( 6*pWidth , 4*pHeight, pWidth, pHeight);
		playerDown[7] = sheet.crop( 7*pWidth , 4*pHeight, pWidth, pHeight);
		playerDown[8] = sheet.crop( 8*pWidth , 4*pHeight, pWidth, pHeight);
		playerDown[9] = sheet.crop( 9*pWidth , 4*pHeight, pWidth, pHeight);

		playerIdleDown = new BufferedImage[20];
		playerIdleDown[0] = sheet.crop( 0 , 0, pWidth, pHeight);
		playerIdleDown[1] = sheet.crop( 0 , 0, pWidth, pHeight);
		playerIdleDown[2] = sheet.crop( 0 , 0, pWidth, pHeight);
		playerIdleDown[3] = sheet.crop( 0 , 0, pWidth, pHeight);
		playerIdleDown[4] = sheet.crop( 0 , 0, pWidth, pHeight);
		playerIdleDown[5] = sheet.crop( 0 , 0, pWidth, pHeight);
		playerIdleDown[6] = sheet.crop( 0 , 0, pWidth, pHeight);
		playerIdleDown[7] = sheet.crop( 0 , 0, pWidth, pHeight);
		playerIdleDown[8] = sheet.crop( 0 , 0, pWidth, pHeight);
		playerIdleDown[9] = sheet.crop( 0 , 0, pWidth, pHeight);
		playerIdleDown[10] = sheet.crop( 0 , 0, pWidth, pHeight);
		playerIdleDown[11] = sheet.crop( 0 , 0, pWidth, pHeight);
		playerIdleDown[12] = sheet.crop( 0 , 0, pWidth, pHeight);
		playerIdleDown[13] = sheet.crop( 0 , 0, pWidth, pHeight);
		playerIdleDown[14] = sheet.crop( 0 , 0, pWidth, pHeight);
		playerIdleDown[15] = sheet.crop( 1*pWidth , 0, pWidth, pHeight);
		playerIdleDown[16] = sheet.crop( 2*pWidth , 0, pWidth, pHeight);
		playerIdleDown[17] = sheet.crop( 1*pWidth , 0, pWidth, pHeight);
		playerIdleDown[18] = sheet.crop( 0 , 0, pWidth, pHeight);
		playerIdleDown[19] = sheet.crop( 0 , 0, pWidth, pHeight);
		
		playerIdleUp = new BufferedImage[1];
		playerIdleUp[0] = sheet.crop( 0, 2*pHeight, pWidth, pHeight);
		
		playerIdleLeft = new BufferedImage[20];
		playerIdleLeft[0] = sheet.crop( 0 , pHeight, pWidth, pHeight);
		playerIdleLeft[1] = sheet.crop( 0 , pHeight, pWidth, pHeight);
		playerIdleLeft[2] = sheet.crop( 0 , pHeight, pWidth, pHeight);
		playerIdleLeft[3] = sheet.crop( 0 , pHeight, pWidth, pHeight);
		playerIdleLeft[4] = sheet.crop( 0 , pHeight, pWidth, pHeight);
		playerIdleLeft[5] = sheet.crop( 0 , pHeight, pWidth, pHeight);
		playerIdleLeft[6] = sheet.crop( 0 , pHeight, pWidth, pHeight);
		playerIdleLeft[7] = sheet.crop( 0 , pHeight, pWidth, pHeight);
		playerIdleLeft[8] = sheet.crop( 0 , pHeight, pWidth, pHeight);
		playerIdleLeft[9] = sheet.crop( 0 , pHeight, pWidth, pHeight);
		playerIdleLeft[10] = sheet.crop( 0 , pHeight, pWidth, pHeight);
		playerIdleLeft[11] = sheet.crop( 0 , pHeight, pWidth, pHeight);
		playerIdleLeft[12] = sheet.crop( 0 , pHeight, pWidth, pHeight);
		playerIdleLeft[13] = sheet.crop( 0 , pHeight, pWidth, pHeight);
		playerIdleLeft[14] = sheet.crop( 0 , pHeight, pWidth, pHeight);
		playerIdleLeft[15] = sheet.crop( 1*pWidth , pHeight, pWidth, pHeight);
		playerIdleLeft[16] = sheet.crop( 2*pWidth , pHeight, pWidth, pHeight);
		playerIdleLeft[17] = sheet.crop( 1*pWidth , pHeight, pWidth, pHeight);
		playerIdleLeft[18] = sheet.crop( 0 , pHeight, pWidth, pHeight);
		playerIdleLeft[19] = sheet.crop( 0 , pHeight, pWidth, pHeight);
		
		playerIdleRight = new BufferedImage[20];
		playerIdleRight[0] = sheet.crop( 0 , 3*pHeight, pWidth, pHeight);
		playerIdleRight[1] = sheet.crop( 0 , 3*pHeight, pWidth, pHeight);
		playerIdleRight[2] = sheet.crop( 0 , 3*pHeight, pWidth, pHeight);
		playerIdleRight[3] = sheet.crop( 0 , 3*pHeight, pWidth, pHeight);
		playerIdleRight[4] = sheet.crop( 0 , 3*pHeight, pWidth, pHeight);
		playerIdleRight[5] = sheet.crop( 0 , 3*pHeight, pWidth, pHeight);
		playerIdleRight[6] = sheet.crop( 0 , 3*pHeight, pWidth, pHeight);
		playerIdleRight[7] = sheet.crop( 0 , 3*pHeight, pWidth, pHeight);
		playerIdleRight[8] = sheet.crop( 0 , 3*pHeight, pWidth, pHeight);
		playerIdleRight[9] = sheet.crop( 0 , 3*pHeight, pWidth, pHeight);
		playerIdleRight[10] = sheet.crop( 0 , 3*pHeight, pWidth, pHeight);
		playerIdleRight[11] = sheet.crop( 0 , 3*pHeight, pWidth, pHeight);
		playerIdleRight[12] = sheet.crop( 0 , 3*pHeight, pWidth, pHeight);
		playerIdleRight[13] = sheet.crop( 0 , 3*pHeight, pWidth, pHeight);
		playerIdleRight[14] = sheet.crop( 0 , 3*pHeight, pWidth, pHeight);
		playerIdleRight[15] = sheet.crop( 1*pWidth , 3*pHeight, pWidth, pHeight);
		playerIdleRight[16] = sheet.crop( 2*pWidth , 3*pHeight, pWidth, pHeight);
		playerIdleRight[17] = sheet.crop( 1*pWidth , 3*pHeight, pWidth, pHeight);
		playerIdleRight[18] = sheet.crop( 0 , 3*pHeight, pWidth, pHeight);
		playerIdleRight[19] = sheet.crop( 0 , 3*pHeight, pWidth, pHeight);
		
		playerLeft = new BufferedImage[10];
		playerLeft[0] = sheet.crop( 0 , 5*pHeight, pWidth, pHeight);
		playerLeft[1] = sheet.crop( 1*pWidth , 5*pHeight, pWidth, pHeight);
		playerLeft[2] = sheet.crop( 2*pWidth , 5*pHeight, pWidth, pHeight);
		playerLeft[3] = sheet.crop( 3*pWidth , 5*pHeight, pWidth, pHeight);
		playerLeft[4] = sheet.crop( 4*pWidth , 5*pHeight, pWidth, pHeight);
		playerLeft[5] = sheet.crop( 5*pWidth , 5*pHeight, pWidth, pHeight);
		playerLeft[6] = sheet.crop( 6*pWidth , 5*pHeight, pWidth, pHeight);
		playerLeft[7] = sheet.crop( 7*pWidth , 5*pHeight, pWidth, pHeight);
		playerLeft[8] = sheet.crop( 8*pWidth , 5*pHeight, pWidth, pHeight);
		playerLeft[9] = sheet.crop( 9*pWidth , 5*pHeight, pWidth, pHeight);
		
		playerRight = new BufferedImage[10];
		playerRight[0] = sheet.crop( 0 , 7*pHeight, pWidth, pHeight);
		playerRight[1] = sheet.crop( 1*pWidth , 7*pHeight, pWidth, pHeight);
		playerRight[2] = sheet.crop( 2*pWidth , 7*pHeight, pWidth, pHeight);
		playerRight[3] = sheet.crop( 3*pWidth , 7*pHeight, pWidth, pHeight);
		playerRight[4] = sheet.crop( 4*pWidth , 7*pHeight, pWidth, pHeight);
		playerRight[5] = sheet.crop( 5*pWidth , 7*pHeight, pWidth, pHeight);
		playerRight[6] = sheet.crop( 6*pWidth , 7*pHeight, pWidth, pHeight);
		playerRight[7] = sheet.crop( 7*pWidth , 7*pHeight, pWidth, pHeight);
		playerRight[8] = sheet.crop( 8*pWidth , 7*pHeight, pWidth, pHeight);
		playerRight[9] = sheet.crop( 9*pWidth , 7*pHeight, pWidth, pHeight);
	}
	
	private static void initQuestGivers(){
		
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/questGiverSheet.png"));
		
		// 2
		questGiver2Idle = new BufferedImage[2];
		questGiver2Idle[0] = sheet.crop(0, 4*qgHeight, qgWidth, qgHeight);
		questGiver2Idle[1] = sheet.crop(2*qgWidth, 4*qgHeight, qgWidth, qgHeight);
		questGiver2Waiting = new BufferedImage[2];
		questGiver2Waiting[0] = sheet.crop(3*qgWidth,4*qgHeight, qgWidth, qgHeight);
		questGiver2Waiting[1] = sheet.crop(5*qgWidth, 4*qgHeight, qgWidth, qgHeight);
		
		
	}
	
	private static void initMonsters(){
		
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/questGiverSheet.png"));
		
		questGiver1Idle = new BufferedImage[2];
		questGiver1Idle[0] = sheet.crop(0, 0, qgWidth, qgHeight);
		questGiver1Idle[1] = sheet.crop(2*qgWidth, 0, qgWidth, qgHeight);
		
		questGiver1Waiting = new BufferedImage[2];
		questGiver1Waiting[0] = sheet.crop(3*qgWidth,0, qgWidth, qgHeight);
		questGiver1Waiting[1] = sheet.crop(5*qgWidth, 0, qgWidth, qgHeight);
		
		questGiver1Down = new BufferedImage[2];
		questGiver1Down[0] = sheet.crop(0, 0, qgWidth, qgHeight);
		questGiver1Down[1] = sheet.crop(2*qgWidth, 0, qgWidth, qgHeight);
		
		questGiver1Up = new BufferedImage[2];
		questGiver1Up[0] = sheet.crop(0, 3*qgHeight , qgWidth, qgHeight);
		questGiver1Up[1] = sheet.crop(2*qgWidth, 3*qgHeight , qgWidth, qgHeight);
		
		questGiver1Left = new BufferedImage[2];
		questGiver1Left[0] = sheet.crop(0*qgWidth, 1*qgHeight , qgWidth, qgHeight);
		questGiver1Left[1] = sheet.crop(2*qgWidth, 1*qgHeight , qgWidth, qgHeight);
		
		questGiver1Right = new BufferedImage[2];
		questGiver1Right[0] = sheet.crop(0*qgWidth, 2*qgHeight , qgWidth, qgHeight);
		questGiver1Right[1] = sheet.crop(2*qgWidth, 2*qgHeight , qgWidth, qgHeight);
	}
	
	private static void initBosses(){
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/questGiverSheet.png"));
		
		skeletonBossFirst = new BufferedImage[3];
		skeletonBossFirst[0] = sheet.crop(3 * qgWidth, 0, qgWidth, qgHeight);
		skeletonBossFirst[1] = sheet.crop(4 * qgWidth, 0, qgWidth, qgHeight);
		skeletonBossFirst[2] = sheet.crop(5 * qgWidth, 0, qgWidth, qgHeight);
		
		skeletonBossSecond = new BufferedImage[3];
		skeletonBossSecond[0] = sheet.crop(6 * qgWidth, 0, qgWidth, qgHeight);
		skeletonBossSecond[1] = sheet.crop(7 * qgWidth, 0, qgWidth, qgHeight);
		skeletonBossSecond[2] = sheet.crop(8 * qgWidth, 0, qgWidth, qgHeight);
		
		skeletonBossThird = new BufferedImage[3];
		skeletonBossThird[0] = sheet.crop(9 * qgWidth, 0, qgWidth, qgHeight);
		skeletonBossThird[1] = sheet.crop(10 * qgWidth, 0, qgWidth, qgHeight);
		skeletonBossThird[2] = sheet.crop(11 * qgWidth, 0, qgWidth, qgHeight);
	}
	
	private static void initWeapons(){
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/weaponMetalSwordSheet.png"));
		
		swordDownIdle = new BufferedImage[1];
		swordDownIdle[0] = sheet.crop(1*swordWidth, 2*swordHeight, swordWidth, swordHeight);
		swordLeftIdle = new BufferedImage[1];
		swordLeftIdle[0] = sheet.crop(0*swordWidth, 1*swordHeight, swordWidth, swordHeight);
		swordUpIdle = new BufferedImage[1];
		swordUpIdle[0] = sheet.crop(1*swordWidth, 0*swordHeight, swordWidth, swordHeight);
		swordRightIdle = new BufferedImage[1];
		swordRightIdle[0] = sheet.crop(2*swordWidth, 1*swordHeight, swordWidth, swordHeight);
		
		swordDownAttack = new BufferedImage[3]; 
		swordDownAttack[0] = sheet.crop(0, 0, swordWidth, swordHeight);	// dole levo
		swordDownAttack[1] = swordDownIdle[0]; 	// dole
		swordDownAttack[2] = sheet.crop(2 * swordHeight, 0, swordWidth, swordHeight);	// dole desno
		
	
		sheet = new SpriteSheet(ImageLoader.loadImage("/textures/weaponGoldenSwordSheet.png"));
		
		swordGoldDownIdle = new BufferedImage[1];
		swordGoldDownIdle[0] = sheet.crop(1*swordWidth, 2*swordHeight, swordWidth, swordHeight);
		swordGoldLeftIdle = new BufferedImage[1];
		swordGoldLeftIdle[0] = sheet.crop(0*swordWidth, 1*swordHeight, swordWidth, swordHeight);
		swordGoldUpIdle = new BufferedImage[1];
		swordGoldUpIdle[0] = sheet.crop(1*swordWidth, 0*swordHeight, swordWidth, swordHeight);
		swordGoldRightIdle = new BufferedImage[1];
		swordGoldRightIdle[0] = sheet.crop(2*swordWidth, 1*swordHeight, swordWidth, swordHeight);

	}
}
