imgLoad=LoadImage("Loading.png")
DrawImage imgload,GraphicsWidth() / 2 - 192,GraphicsHeight() / 2 - 73

; Loading the sounds
     phaser=LoadSound("phaser.wav")
     explosion=LoadSound("explode.wav")
;Creating a bookmark
     .start
;Setting the graphics for the program
     SetBuffer BackBuffer()
;Setting the type values
     type_player=1
     type_scenery=2
     type_gallery=3
     type_bullet=4; Creating the camera
     camera=CreateCamera()
     CameraClsColor camera ,0,125,255
     EntityType camera, type_player
     EntityRadius camera, 5
; Creating a light
     light=CreateLight()
; Creating a the water plane
     water=CreatePlane() 
     PositionEntity water, 0,-15,0
     watertexture=LoadTexture ("water.jpg")
     EntityTexture water, watertexture
     ScaleTexture watertexture,15,15
     EntityType water, type_scenery
EntityPickMode water,2

; Loading the heightmap
     terrain=LoadTerrain ( "ground.jpg" )
     ScaleEntity terrain,5,50,5
     PositionEntity terrain,-500,-20,-500
     tex=LoadTexture( "greenery.jpg" )
     ScaleTexture tex, 50,50
     EntityTexture terrain,tex
     EntityType terrain, type_scenery
	 EntityPickMode terrain,2
	 plane=CreatePlane ()
     PositionEntity plane,-500,-21,-500
     EntityTexture plane,tex
     EntityType plane, type_scenery
	
	
;Creating the gallery items
     Dim ducks(5)
          For x = 1 To 5
               ducks(x)= LoadMesh ("duck.3ds")
               PositionEntity ducks(x), x*15,0,90
               EntityColor ducks(x), 255,255,0
               ScaleEntity ducks(x), 0.2,0.2,0.2
               EntityType ducks(x), type_gallery
               EntityRadius ducks(x), 3
				EntityPickMode ducks(x),2
          Next
     Dim seahorse(5)
          For x = 1 To 5
               seahorse(x)= LoadMesh ("seahorse.3ds")
               PositionEntity seahorse(x), x*6,10,45
               EntityColor seahorse(x), 255,102,0
               ScaleEntity seahorse(x), 0.03,0.03,0.03
               EntityType seahorse(x), type_gallery
               EntityRadius seahorse(x), 3
				EntityPickMode seahorse(x),2
          Next

     Dim donkey(5)
          For x = 1 To 5
               donkey(x)= LoadMesh ("donkey.3ds")
               PositionEntity donkey(x), x*5,35,85
               EntityColor donkey(x), 204,204,204
               ScaleEntity donkey(x), 0.3,0.3,0.3
               EntityType donkey(x), type_gallery
               EntityRadius donkey(x), 3
				EntityPickMode donkey(x),2
          Next
     Dim flamingo(5)
          For y = 1 To 5
               flamingo(y)= LoadMesh ("flamingo.3ds")
               PositionEntity flamingo(y), y*6,8,25
               EntityColor flamingo(y), 102,51,51
               ScaleEntity flamingo(y), 0.003,0.003,0.003
               EntityType flamingo(y), type_gallery
               EntityRadius flamingo(y), 3
				EntityPickMode flamingo(y),2
          Next

     Dim dolphin(5)
          For x = 1 To 5
               dolphin(x)= LoadMesh ("dolphin.3ds")
               PositionEntity dolphin(x), x*6,45,0
               EntityColor dolphin(x), 102,153,255
               ScaleEntity dolphin(x), 0.3,0.3,0.3
               EntityType dolphin(x), type_gallery
               EntityRadius dolphin(x), 3
				EntityPickMode dolphin(x),2
          Next

     Dim snake(5)
          For x = 1 To 5
               snake(x)= LoadMesh ("snake.3ds")
               PositionEntity snake(x), x*16,10,10
               EntityColor snake(x), 153,255,153
               ScaleEntity snake(x), 0.06,0.06,0.06
               EntityType snake(x), type_gallery
               EntityRadius snake(x), 5
				EntityPickMode snake(x),2
          Next

     
;Creating the gun
	 gun=LoadMesh("pistol.3ds")
	 ScaleEntity gun,0.5,0.5,0.5
	 PositionEntity gun, EntityX(camera)-3,EntityY(camera)-2, EntityZ(camera)+10
     RotateEntity gun,-20,-10,0
     EntityOrder gun, -1
     EntityParent gun,camera
     EntityColor gun, 100,100,100 
	 EntityPickMode gun,2 

;Creating the bullets
     maxbull = 100
     Dim bullet(maxbull)
          For i=0 To maxbull
		   bullet(i)=LoadMesh ("bullet.3ds")
               EntityColor bullet(i), 100,100,100
               EntityType bullet(i), type_bullet
          Next
; Defining the Collisions
     Collisions type_gallery,type_scenery,2,2
     Collisions type_bullet,type_gallery,2,1
     Collisions type_player,type_scenery,2,2
     Collisions type_scenery, type_gallery, 2,2

Line  511,343,513,345
; 512 344

;Pausing the game
     If KeyDown(25) Then
          Cls
          pause=LoadImage ("pause.bmp")
          DrawImage pause,0,0
          Flip
          WaitMouse()
     EndIf

;Defining the starting time of the game
    krsec=MilliSecs()