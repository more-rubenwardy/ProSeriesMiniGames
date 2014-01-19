;Shooting Gallery
;________________
          Graphics 640,480,0,2
          screen=LoadImage ("welcome.png")
          DrawImage screen,0,0
		While Not KeyDown(1)
               If KeyDown(2)
				full=1 
				Goto amu
               EndIf 
			   If KeyDown(3)
				full=0
				Goto amu
               EndIf 
		Wend
		End
		M#=0.01
		.amu

; Loading the sounds
     phaser=LoadSound("phaser.wav")
     explosion=LoadSound("explode.wav")
;Creating a bookmark
     .start
;Setting the graphics for the program
     Graphics3D 1024,768,0,full
     SetBuffer BackBuffer()
;Setting the type values
imgLoad=LoadImage("Loading.png")
DrawImage imgload,GraphicsWidth() / 2 - 192,GraphicsHeight() / 2 - 73
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
     ;water=CreatePlane() 
     ;PositionEntity water, 0,-15,0
     ;watertexture=LoadTexture ("water.bmp")
     ;EntityTexture water, watertexture
     ;ScaleTexture watertexture,10,10
     ;EntityType water, type_scenery

water=CreatePlane()
ScaleEntity water,5,100,5
tex=LoadTexture( "water.bmp",2 )
ScaleTexture tex, 2.5,2.5
EntityTexture water,tex
;EntityType water,type_scene
PositionEntity water,0,-15,0
water2=CopyEntity( water )
RotateEntity water2,0,0,180
;>EntityBlend water,1
EntityAlpha water,.75
;>EntityFX water,1

; Loading the heightmap
     terrain=LoadTerrain ( "ground.jpg" )
     ScaleEntity terrain,5,50,5
     PositionEntity terrain,-500,-20,-500
     tex=LoadTexture( "greenery.jpg" )
     ScaleTexture tex, 50,50
     EntityTexture terrain,tex
     EntityType terrain, type_scenery
;Creating the gallery items
     Dim ducks(5)
          For x = 1 To 5
               ducks(x)= LoadMesh ("duck.3ds")
               PositionEntity ducks(x), x*15,0,90
               EntityColor ducks(x), 255,255,0
               ScaleEntity ducks(x), 0.2,0.2,0.2
               EntityType ducks(x), type_gallery
               EntityRadius ducks(x), 3
          Next
     Dim seahorse(5)
          For x = 1 To 5
               seahorse(x)= LoadMesh ("seahorse.3ds")
               PositionEntity seahorse(x), x*6,10,45
               EntityColor seahorse(x), 255,102,0
               ScaleEntity seahorse(x), 0.03,0.03,0.03
               EntityType seahorse(x), type_gallery
               EntityRadius seahorse(x), 3
          Next

     Dim donkey(5)
          For x = 1 To 5
               donkey(x)= LoadMesh ("donkey.3ds")
               PositionEntity donkey(x), x*5,35,85
               EntityColor donkey(x), 204,204,204
               ScaleEntity donkey(x), 0.3,0.3,0.3
               EntityType donkey(x), type_gallery
               EntityRadius donkey(x), 3
          Next
     Dim flamingo(5)
          For y = 1 To 5
               flamingo(y)= LoadMesh ("flamingo.3ds")
               PositionEntity flamingo(y), y*6,8,25
               EntityColor flamingo(y), 102,51,51
               ScaleEntity flamingo(y), 0.003,0.003,0.003
               EntityType flamingo(y), type_gallery
               EntityRadius flamingo(y), 3
          Next

     Dim dolphin(5)
          For x = 1 To 5
               dolphin(x)= LoadMesh ("dolphin.3ds")
               PositionEntity dolphin(x), x*6,45,0
               EntityColor dolphin(x), 102,153,255
               ScaleEntity dolphin(x), 0.3,0.3,0.3
               EntityType dolphin(x), type_gallery
               EntityRadius dolphin(x), 3
          Next

     Dim snake(5)
          For x = 1 To 5
               snake(x)= LoadMesh ("snake.3ds")
               PositionEntity snake(x), x*16,10,10
               EntityColor snake(x), 153,255,153
               ScaleEntity snake(x), 0.06,0.06,0.06
               EntityType snake(x), type_gallery
               EntityRadius snake(x), 5
          Next

     
;Creating the gun
     ;gun=CreateCylinder(12)
    
     
	 gun=LoadMesh("pistol.3ds")
	 ScaleEntity gun,0.5,0.5,0.5
	 PositionEntity gun, EntityX(camera)-3,EntityY(camera)-2, EntityZ(camera)+10
     RotateEntity gun,-20,-10,0
     EntityOrder gun, -1
     EntityParent gun,camera
     EntityColor gun, 100,100,100 

;Creating the bullets
     maxbull = 100
     Dim bullet(maxbull)
          For i=0 To maxbull
		     bullet(i)=LoadMesh ("bullet.3ds")
               EntityColor bullet(i), 0,0,0
               EntityType bullet(i), type_bullet
          Next
; Defining the Collisions
     Collisions type_gallery,type_scenery,2,2
     Collisions type_bullet,type_gallery,2,1
     Collisions type_player,type_scenery,2,2
     Collisions type_scenery, type_gallery, 2,2


;Pausing the game
     If KeyDown(25) Then
          Cls
          pause=LoadImage ("pause.bmp")
          DrawImage pause,0,0
          Flip
          WaitMouse()
     EndIf
;Defining the starting time of the game
     starttime=MilliSecs()


; The following code makes our program run

	While Not KeyDown( 1 )

		If KeyDown( 205 )=True Then TurnEntity camera,0,-1,0
          If KeyDown( 203 )=True Then TurnEntity camera,0,1,0
          If KeyDown( 208 )=True Then MoveEntity camera,0,0,-1
          If KeyDown( 200 )=True Then MoveEntity camera,0,0,1
          If KeyDown( 47 )=True Then TranslateEntity camera,0,3,0          
		MoveEntity camera, 0,-0.8,0
; Moving the targets
     For a = 1 To 5
          MoveEntity ducks(a), 0.1,-.02,0
          MoveEntity seahorse(a), 0,-.02,0
          MoveEntity donkey(a), 0,-.02,0
          MoveEntity flamingo(a), 0.1,-0.2,0
          MoveEntity snake(a), -0.2,-0.2,0
          MoveEntity dolphin(a), 0,-0.2,-0.3
     Next

;Firing bullets
    If KeyHit (57) And reload = 0
          PlaySound phaser 
         PositionEntity bullet(t) ,EntityX(gun,1),EntityY(gun,1),EntityZ(gun,1)
         RotateEntity bullet(t),EntityPitch#(gun,1)+20,EntityYaw#(gun,1),EntityRoll#(gun,1)
         EntityColor bullet(t),0,0,0 ;B=255 (RGB)
	t=t+1
    EndIf 
    For q = 0 To maxbull
         MoveEntity bullet(q), 0,0.8,10 ;3
	If CountCollisions (bullet(q))
            crash=CollisionEntity (bullet(q),1)
            HideEntity crash
		score#=score#+1
		PlaySound explosion
      EndIf
    Next
	bulletcount=100-t
    	If t=100 Then
         reload=1
    	EndIf 
    	If KeyDown (19) = True Then
         t=0
         reload=0
    EndIf

;Changing the position of gallery items
     For q = 0 To maxbull
          If CountCollisions (terrain)
               smash=CollisionEntity (terrain,1)
               PositionEntity smash, 0,Rand(10,100),Rand(-100,100)
          EndIf
	Next 

;Ending the game
     If score# = 35 Or timeleft < 0 Then 
     	Cls
     	End=LoadImage ("end.bmp")
     	DrawImage End,0,0
     	Flip
    	WaitKey() 
     	If KeyDown(1) Then End
    	If KeyDown (57) Then Goto start
     EndIf

	UpdateWorld 

      RenderWorld
	CurrentTime = MilliSecs() 
	timeleft= 180000 - ((CurrentTime - starttime))
	Text 100,10,"Score: " +score#,True,False
	Text GraphicsWidth()/2-112,10,"Bullets Remaining: "+bulletcount
	Text GraphicsWidth()-225,10,"Time Remaining: "+TimeLeft /1000
	If reload=1 Then Text GraphicsWidth()/2, GraphicsHeight()/2,"Press R to Reload",1,1

     Flip

	If EntityY(water) < -17
		M#=0.01
	Else If EntityY(water) > -13
		M#=-0.01
	Else If M#=0
		M#=0.01
	End If
	
	TranslateEntity water,0,M,0.01	
	TranslateEntity water2,0,M,0.01
Wend
End