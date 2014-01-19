Graphics 640,480
img=LoadImage("menu.png")
.menu
DrawImage img,0,0
WaitKey()
If KeyDown(2)
	Graphics3D 800,640,0,2
Else If KeyDown(3)
	Graphics3D 1024,768,0,1
Else
	Goto menu
End If
;set to false if you have fog problems
;(typically with early ATI cards)
Global use_fog=False
Global bull_sprite
Global shoot=Load3DSound( "sounds\shoot.wav" )
Global boom=Load3DSound( "sounds\boom.wav" )
Global HealthBar=LoadImage("HealthBar.bmp")
Global BlackOut=LoadImage("Blank.bmp")

;game FPS
Const FPS=30
Type Player
	Field entity,camera,spawnTimer,Health,Lives
	Field ctrl_mode,cam_mode,ignition
	Field pitch#,yaw#,pitch_speed#,yaw_speed#,roll#,thrust#
	Field Arrow
End Type

Type Spark
	Field alpha#,sprite
End Type

Type Bullet
	Field rot#,sprite,time_out,shooter.Player,targeter_used,Mode,Camera
End Type

Type Hole
	Field alpha#,sprite
End Type

Type Orb
	Field Entity
End Type

Const TYPE_PLAYER=1,TYPE_BULLET=2,TYPE_TARGET=3,TYPE_SCENERY=4,Type_ORB=5,TYPE_TERRAIN=10
Global light,land,ground,water,sky
Global spark_sprite,player_model,hole_sprite
Global bull_x#=1.5

Collisions TYPE_PLAYER,TYPE_TERRAIN,2,2	;sphere-to-polygon, colliding collisions
Collisions TYPE_PLAYER,TYPE_SCENERY,2,2
Collisions TYPE_BULLET,TYPE_TERRAIN,2,1
Collisions TYPE_BULLET,TYPE_SCENERY,2,1
Global Port,Port2
 
CreateScene()

plane=LoadAirPlane( "Player1\biplane.x" )
Global player1.Player=CreatePlayer( plane,0,0,GraphicsWidth()/2,GraphicsHeight(),1 )

plane2=LoadAirPlane( "Player2\biplane.x" )
Global player2.Player=CreatePlayer( plane2,GraphicsWidth()/2,0,GraphicsWidth()/2,GraphicsHeight(),2 )
period=1000/FPS
time=MilliSecs()-period
listener=CreateListener( player1\entity,.1,1,.2 )

detail=2000:morph=True
TerrainDetail land,detail,morph
RandTimer=MilliSecs()
While Not KeyHit(1)
	If KeyDown(57)
		If KeyDown(48)
			Player1\SpawnTimer = MilliSecs()
			Player1\Health = 0
		ElseIf KeyDown(49)
			Player2\SpawnTimer = MilliSecs()
			Player2\Health = 0
		EndIf
	EndIf
	Repeat
		elapsed=MilliSecs()-time
	Until elapsed

	;how many 'frames' have elapsed	
	ticks=elapsed/period
	
	;fractional remainder
	tween#=Float(elapsed Mod period)/Float(period)
	
	For k=1 To ticks
		If k=ticks Then CaptureWorld
		time=time+period
		UpdateGame()
		UpdateWorld
	Next
	If KeyHit(88)
		wire=Not wire
		WireFrame wire
	EndIf
	If KeyHit(26)
		detail=detail-100
		If detail<100 Then detail=100
		TerrainDetail land,detail,morph
	Else If KeyHit(27)
		detail=detail+100
		If detail>10000 Then detail=10000
		TerrainDetail land,detail,morph
	EndIf
	If KeyHit(50)
		morph=Not morph
		TerrainDetail land,detail,morph
	EndIf
			
	RenderWorld tween
	;Text 0,0,"PLAYER1: X"+EntityX(player1\entity)+" Y"+EntityY(player1\entity)+" Z"+EntityZ(player1\entity)
	;Text 0,15,"PLAYER2: X"+EntityX(player2\entity)+" Y"+EntityY(player2\entity)+" Z"+EntityZ(player2\entity)
	Color 255,0,0
	Text 0,9.5,"PLANE: "+player1\Lives
	Text GraphicsWidth()/2,9.5,"PLANE: "+player2\Lives
	For a=1 To player1\Health
		DrawImage HealthBar,a*19+50,5
	Next
	For a=1 To player2\Health
		DrawImage HealthBar,a*19+(GraphicsWidth()/2+50),5
	Next
	For P.Player = Each Player
		If p\spawnTimer<>0
			spawnTime=MilliSecs()-p\spawnTimer
			spawnTime=30000-spawnTime
			spawnTime=spawnTime/1000
			
			If p\ctrl_mode=1
				DrawImage blackout,0,0
				Text GraphicsWidth()/4,GraphicsHeight()/2,"RESPAWN IN "+spawnTime,True,False
			Else
				DrawImage blackout,GraphicsWidth()/2,0
				Text GraphicsWidth()-GraphicsWidth()/4,GraphicsHeight()/2,"RESPAWN IN "+spawnTime,True,False
			EndIf
		EndIf
	Next
	Flip
Wend

End

Function UpdateGame()
	For P.Player = Each Player
		UpdatePlayer(P.Player)	
	Next
	For h.Hole=Each Hole
		UpdateHole( h )
	Next
	For b.Bullet=Each Bullet
		UpdateBullet( b )
	Next
	For s.Spark=Each Spark
		UpdateSpark( s )
	Next
End Function

Function UpdatePlayer(P.Player)
	Local x_dir,y_dir,z_dir
	If p\spawnTimer<>0
		;HideEntity p\camera
		;HideEntity p\entity
		If MilliSecs()>p\spawnTimer+30000
			;ShowEntity p\entity
			;ShowEntity p\camera
			p\spawnTimer = 0
			p\health = 10
		EndIf
	Else
		;Arrow
		;PositionEntity p\arrow,EntityX(p\entity)-2,EntityY(p\entity),EntityZ(p\entity)
		opp.Player=p
		If p = player1
			PointEntity p\arrow,player2\entity
			opp.Player=player2
		ElseIf p = player2
			PointEntity p\arrow,player1\entity
			opp.Player=player1
		EndIf
		dX=EntityX(p\entity)-EntityX(opp\entity)
		dY=EntityY(p\entity)-EntityY(opp\entity)
		dZ=EntityZ(p\entity)-EntityZ(opp\entity)
		If dX<0 Then dX=-dX
		If dY<0 Then dY=-dY
		If dZ<0 Then dZ=-dZ
		dT=dX+dY+dZ
		If dT > 500
			ShowEntity p\arrow
		Else
			HideEntity p\arrow
		EndIf
		
		;Plane


	Select p\ctrl_mode
	
	Case 1
			If KeyHit(2) CreateBullet( p , 1)
			If KeyDown(30) x_dir=-1
			If KeyDown(31) x_dir=1

			If KeyDown(17) y_dir=-1
			If KeyDown(44) y_dir=1
		
			If KeyDown(18) z_dir=1
			If KeyDown(32) z_dir=-1
			
			If KeyHit(60) p\cam_mode=p\cam_mode+1
			If p\cam_mode = 5 Then p\cam_mode = 1
				
	Case 2
			If KeyHit(209) CreateBullet( p , 1)
			If KeyDown(203) x_dir=-1
			If KeyDown(205) x_dir=1
		
			If KeyDown(200) y_dir=-1
			If KeyDown(208) y_dir=1
		
			If KeyDown(211) z_dir=1
			If KeyDown(207) z_dir=-1
		
			If KeyHit(59) p\cam_mode=p\cam_mode+1
			If p\cam_mode = 5 Then p\cam_mode = 1
		
	Case 3
		x_dir=JoyXDir()
		y_dir=JoyYDir()
		If JoyDown(1) z_dir=1
		If JoyDown(2) z_dir=-1
		
		If KeyHit(63) p\cam_mode=1
		If KeyHit(64) p\cam_mode=2
		If KeyHit(65) p\cam_mode=3
		If KeyHit(66) p\cam_mode=4
		
		
		
	End Select
	EndIf
	
	
	If x_dir<0
		p\yaw_speed=p\yaw_speed + (4-p\yaw_speed)*.04
	Else If x_dir>0
		p\yaw_speed=p\yaw_speed + (-4-p\yaw_speed)*.04
	Else
		p\yaw_speed=p\yaw_speed + (-p\yaw_speed)*.02
	EndIf
		
	If y_dir<0
		p\pitch_speed=p\pitch_speed + (2-p\pitch_speed)*.2
	Else If y_dir>0
		p\pitch_speed=p\pitch_speed + (-2-p\pitch_speed)*.2
	Else
		p\pitch_speed=p\pitch_speed + (-p\pitch_speed)*.1
	EndIf
		
	p\yaw=p\yaw+p\yaw_speed
	If p\yaw<-180 Then p\yaw=p\yaw+360
	If p\yaw>=180 Then p\yaw=p\yaw-360
	
	p\pitch=p\pitch+p\pitch_speed
	If p\pitch<-180 Then p\pitch=p\pitch+360
	If p\pitch>=180 Then p\pitch=p\pitch-360
		
	p\roll=p\yaw_speed*30
	RotateEntity p\entity,p\pitch,p\yaw,p\roll
	;see if y/p/r funcs are working...
	t_p#=EntityPitch( p\entity )
	t_y#=EntityYaw( p\entity )
	t_r#=EntityRoll( p\entity )
	RotateEntity p\entity,t_p,t_y,t_r
	
	If p\ignition
		If z_dir>0			;faster?
			p\thrust=p\thrust + (5-p\thrust)*.04	;1.5
		Else If z_dir<0		;slower?
			p\thrust=p\thrust + (-p\thrust)*.04
		EndIf
		MoveEntity p\entity,0,0,p\thrust
	Else If z_dir>0
		p\ignition=True
	EndIf
	
	If p\camera
		Select p\cam_mode
		Case 1:
			EntityParent p\camera,p\entity
			RotateEntity p\camera,0,p\yaw,0,True
			PositionEntity p\camera,EntityX(p\entity),EntityY(p\entity),EntityZ(p\entity),True
			MoveEntity p\camera,0,1,-5
			PointEntity p\camera,p\entity,p\roll/2
		Case 2:
			EntityParent p\camera,0
			PositionEntity p\camera,EntityX(p\entity),EntityY(p\entity),EntityZ(p\entity)
			TranslateEntity p\camera,0,1,-5
			PointEntity p\camera,p\entity,0
		Case 3:
			EntityParent p\camera,p\entity
			PositionEntity p\camera,0,.25,0
			RotateEntity p\camera,0,0,0
		Case 4:
			EntityParent p\camera,0
			PointEntity p\camera,p\entity,0
		End Select
	EndIf
	TranslateEntity p\entity,0,-1,0
	

End Function

Function LoadAirPlane( file$ )
	pivot=CreatePivot()
	plane=LoadMesh( file$,pivot )
	ScaleMesh plane,.250,.5,.250;125,25,125 make it more spherical!
	RotateEntity plane,0,180,0	;and align to z axis
	EntityRadius pivot,1
	EntityType pivot,1
	HideEntity pivot
	Return pivot
End Function

Function CreatePlayer.Player( plane,vp_x,vp_y,vp_w,vp_h,ctrl_mode )
	p.Player=New Player
	p\ctrl_mode=ctrl_mode
	p\cam_mode=1
	p\Health=10
	p\Lives=2
	;z#=ctrl_mode*10-1200
	Select ctrl_mode
		Case 1
			y#=51
			x#=-14
			z#=-875
		Case 2
			y#=225
			x#=7535
			z#=-811
	End Select
	p\entity=CopyEntity( plane )
	PositionEntity p\entity,x,y,z ;TerrainY( land,x,0,z )
	;RotateEntity p\entity,0,0,0
	ResetEntity p\entity
	p\camera=CreateCamera( p\entity )
	PositionEntity p\camera,0,3,-10
	CameraViewport p\camera,vp_x,vp_y,vp_w,vp_h
	CameraClsColor p\camera,0,192,255
	CameraFogColor p\camera,0,192,255
	CameraFogRange p\camera,1000,3000
	CameraRange p\camera,1,3000
	CameraZoom p\camera,1.5
	EntityType p\entity,1
	;Arrow
	Tex=LoadTexture("RedText.bmp")
	p\arrow=LoadMesh("Rarrow.b3d")
	EntityTexture p\arrow,Tex
	ScaleEntity p\arrow,.2,.2,.2
	;If Not p\arrow Then RuntimeError "Soz"
	PositionEntity p\arrow,EntityX(p\entity)-2,EntityY(p\entity)-1,EntityZ(p\entity)
	EntityParent p\arrow,p\entity
	EntityAlpha p\arrow,.5
	
	If use_fog Then CameraFogMode p\camera,1
	Return p
End Function

Function CreateScene()
	spark_sprite=LoadSprite( "sprites\bigspark.bmp" )
	HideEntity spark_sprite
	
	bull_sprite=LoadSprite( "sprites\bluspark.bmp" )
	ScaleSprite bull_sprite,3,3
	EntityRadius bull_sprite,1.5
	EntityType bull_sprite,TYPE_BULLET
	HideEntity bull_sprite
	
	hole_sprite=LoadSprite( "sprites\bullet_hole.bmp",1 )
	EntityBlend hole_sprite,2
	SpriteViewMode hole_sprite,2
	HideEntity hole_sprite
	
	;setup lighting
	l=CreateLight()
	RotateEntity l,45,45,0
	AmbientLight 32,32,32
	
	;Load terrain
	land=LoadTerrain( "hmap_1024.bmp" )
	ScaleEntity land,20,800,20
	PositionEntity land,-20*512,0,-20*512
	EntityFX land,1
	EntityType land,10

	;apply textures to terrain	
	tex1=LoadTexture( "coolgrass2.bmp",1 )
	ScaleTexture tex1,10,10
	tex2=LoadTexture( "lmap_256.bmp" )
	ScaleTexture tex2,TerrainSize(land),TerrainSize(land)
	EntityTexture land,tex1,0,0
	EntityTexture land,tex2,0,1
	
	bull_sprite=LoadSprite( "sprites\bluspark.bmp" )
	ScaleSprite bull_sprite,3,3
	EntityRadius bull_sprite,1.5
	EntityType bull_sprite,TYPE_BULLET
	HideEntity bull_sprite
	
	
	
	;Airport
	Port=LoadMesh( "Airport.b3d" )
	ScaleMesh Port,10,10,10 ;125,25,125 make it more spherical!
	;RotateEntity Port,0,180,0	;and align to z axis
	PositionEntity Port,40,50,-800
	EntityType port,4
	
	;Airport
	Port2=CopyEntity(Port)
	PositionEntity Port2,7590,220,-740
	
	;and ground plane
	plane=CreatePlane()
	ScaleEntity plane,20,1,20
	PositionEntity plane,-20*512,0,-20*512
	EntityTexture plane,tex1,0,0
	EntityOrder plane,3
	EntityFX plane,1
	EntityType plane,10
	
	;create cloud planes
	tex=LoadTexture( "cloud_2.bmp",3 )
	ScaleTexture tex,1000,1000
	p=CreatePlane()
	EntityTexture p,tex
	EntityFX p,1
	PositionEntity p,0,450,0
	p=CopyEntity( p )
	RotateEntity p,0,0,180
	
	;create water plane
	tex=LoadTexture( "water-2_mip.bmp",3 )
	ScaleTexture tex,10,10
	p=CreatePlane()
	EntityTexture p,tex
	EntityBlend p,1
	EntityAlpha p,.75
	PositionEntity p,0,10,0
	EntityFX p,1
	p2=CopyEntity(p)
	TurnEntity p2,0,180,0
End Function

Function CreateSpark.Spark( b.Bullet )
	s.Spark=New Spark
	s\alpha=-90
	s\sprite=CopyEntity( spark_sprite,b\sprite )
	EntityParent s\sprite,0
	Return s
End Function

Function UpdateSpark( s.Spark )
	If s\alpha<270
		sz#=Sin(s\alpha)*5+5
		ScaleSprite s\sprite,sz,sz
		RotateSprite s\sprite,Rnd(360)
		s\alpha=s\alpha+15
	Else
		FreeEntity s\sprite
		Delete s
	EndIf
End Function

Function CreateBullet.Bullet( p.Player,Mode )
	bull_x=-bull_x
	b.Bullet=New Bullet
	b\time_out=150
	b\sprite=CopyEntity( bull_sprite,p\entity )
	TranslateEntity b\sprite,bull_x,1,.25
	
	b\camera=CreateCamera(b\sprite)
	PositionEntity b\camera,EntityX(b\sprite),EntityY(b\sprite),EntityZ(b\sprite)
	EntityParent b\camera,b\sprite
	CameraProjMode b\camera,0
	
	EntityParent b\sprite,0
	EmitSound shoot,b\sprite
	b\shooter=p
	b\Mode=Mode
	
	If b\shooter = player1
		opp.Player=player2
	ElseIf b\shooter = player2
		opp.Player=player1
	EndIf
		
	If b\Mode=2
		PointEntity b\sprite,opp\entity
	End If
	
	Return b
End Function

Function UpdateBullet( b.Bullet )
	If CountCollisions( b\sprite )
		If EntityCollided( b\sprite,TYPE_TERRAIN )
			EmitSound boom,b\sprite
			ex#=EntityX(b\sprite)
			ey#=EntityY(b\sprite)
			ez#=EntityZ(b\sprite)
			TFormPoint( ex,ey,ez,0,land )
			hi#=TerrainHeight( land,TFormedX(),TFormedZ() )
			If hi>0
				hi=hi-.002:If hi<0 Then hi=0
				ModifyTerrain land,TFormedX(),TFormedZ(),hi,True
			EndIf
			CreateSpark( b )
			FreeEntity b\sprite
			Delete b
			Return
		EndIf
		If EntityCollided( b\sprite,TYPE_SCENERY )
			For k=1 To CountCollisions( b\sprite )
				If GetEntityType( CollisionEntity( b\sprite,k ) )=TYPE_SCENERY
					cx#=CollisionX( b\sprite,k )
					cy#=CollisionY( b\sprite,k )
					cz#=CollisionZ( b\sprite,k )
					nx#=CollisionNX( b\sprite,k )
					ny#=CollisionNY( b\sprite,k )
					nz#=CollisionNZ( b\sprite,k )
					th.Hole=New Hole
					th\alpha=1
					th\sprite=CopyEntity( hole_sprite )
					PositionEntity th\sprite,cx,cy,cz
					AlignToVector th\sprite,-nx,-ny,-nz,3
					MoveEntity th\sprite,0,0,-.1
					HideEntity b\sprite
					;Exit
				EndIf
			Next
			EmitSound boom,b\sprite
			CreateSpark( b )
			FreeEntity b\sprite
			Delete b
			Return
		EndIf
		If EntityCollided( b\sprite,TYPE_PLAYER )
			For k=1 To CountCollisions( b\sprite )
				If GetEntityType( CollisionEntity( b\sprite,k ) )=TYPE_PLAYER
					If CollisionEntity( b\sprite,k ) = player1\entity
						player1\health = player1\health - 5
						If player1\health < 1
							player1\SpawnTimer=MilliSecs()
						EndIf
					ElseIf CollisionEntity( b\sprite,k ) = player2\entity
						player2\health = player2\health - 5
						If player2\health < 1
							player2\SpawnTimer=MilliSecs()
						EndIf
					EndIf
				EndIf
			Next
			EmitSound boom,b\sprite
			CreateSpark( b )
			FreeEntity b\sprite
			Delete b
			Return
		EndIf
	EndIf
	b\time_out=b\time_out-1
	If b\time_out=0
		FreeEntity b\sprite
		Delete b
		Return
	EndIf
	
		If KeyDown(63)
			If b\shooter = player1
				PointEntity b\sprite,player2\entity
				b\targeter_used=1
			EndIf
		ElseIf KeyDown(67)
			If b\targeter_used=0
				If b\shooter = player2
					PointEntity b\sprite,player1\entity
					b\targeter_used=1
				EndIf
			EndIf
		EndIf
	
	b\rot=b\rot+30
	RotateSprite b\sprite,b\rot
	MoveEntity b\sprite,0,0,10
	
	
		If b\shooter = player1
			opp.Player=player2
		ElseIf b\shooter = player2
			opp.Player=player1
		EndIf
		dX=EntityX(b\shooter\entity)-EntityX(opp\entity)
		dY=EntityY(b\shooter\entity)-EntityY(opp\entity)
		dZ=EntityZ(b\shooter\entity)-EntityZ(opp\entity)
		If dX<0 Then dX=-dX
		If dY<0 Then dY=-dY
		If dZ<0 Then dZ=-dZ
		dT=dX+dY+dZ
		If dT < 20
			If opp\SpawnTimer=0
				opp\health = opp\health - 5
				If opp\health < 1
					opp\lives=opp\lives-1
					If opp\lives = 0
						Cls
						If b\shooter = player1
							Text GraphicsWidth()/2,GraphicsHeight()/2,"Player 1 Won!",True,True
						Else
							Text GraphicsWidth()/2,GraphicsHeight()/2,"Player 2 Won!",True,True
						EndIf
					EndIf
					opp\SpawnTimer=MilliSecs()
				EndIf
				EmitSound boom,b\sprite
				CreateSpark( b )
				FreeEntity b\sprite
				Delete b
				Return
			EndIf
		EndIf
End Function

Function UpdateHole( h.Hole )
	h\alpha=h\alpha-.005
	If h\alpha>0
		EntityAlpha h\sprite,h\alpha
	Else
		FreeEntity h\sprite
		Delete h
	EndIf
End Function

Function CreateOrb.Orb(X,Y,Z)
End Function