Include "load.bb"
; * CONTROLS
; Arrows - Move
; Enter - Birds eye view
; 1 - No gravity
; R - Reset
;HidePointer()
time=0
change=1
sky=01
SecondCount=MilliSecs()
;Img_B=LoadImage("square.png")
Img_P=LoadImage("player.png")
Img_G=LoadImage("goal.png")
While Not KeyDown( 1 )
If MilliSecs()>SecondCount+1000
 CheatsActive=CheatsActive-1
 SecondCount=MilliSecs()
End If

If KeyDown(29)
 PositionEntity player,170,3,680
End If

; Turn
If MouseOrKeys=1
	movecam(player,s)	
Else If MouseOrKeys=2
	freecam(player)
Else
	MouseOrKeys=1
End If

If KeyHit(61)
	If MouseOrKeys=1
		MouseOrKeys=2
	Else
		MouseOrKeys=1
	End If
End If

; Sky
If sky>254
	change=0
	If time=0
		time=MilliSecs()
	End If
	If MilliSecs()>time+5000
		change=-1
		time=0
	End If
Else If sky<1
	change=0
	If time=0
		time=MilliSecs()
	End If
	If MilliSecs()>time+5000
		change=1
		time=0
	End If
End If
sky=sky+change
CameraClsColor camera,0,sky,sky

If KeyDown(211)  And CheatsActive>0
TranslateEntity player,0,1,0
End If 

; Reset
If KeyHit(19)
	FreeEntity camera
	FreeEntity player
	; Camera
	camera=CreateCamera()
	PositionEntity camera,-420,15,1500
	;EntityType camera,type_player
	CameraClsColor camera,0,sky,sky
	
	; Blob
	player=CreateSphere()
	PositionEntity player,-420,6,1500
	EntityType player,type_player
	EntityParent camera,player
	
	; Light
	light=CreateLight(2,camera)
End If

If KeyDown(156)
CheatsActive=20
End If

If Not KeyDown(2)
		TranslateEntity player,0,-0.2,0
End If

; Speed (SuperSonic)
If KeyDown(31)
s=10
Else
s=1
End If

; Next Color (Walls)
If KeyHit(59)
If tx>4
tx=1
Else
tx=tx+1
End If
If tx=1
tex=tx1
Else If tx=2
tex=tx2
Else If tx=3
tex=tx3
Else If tx=4
tex=tx4
Else If tx=5
tex=tx5
End If
ScaleTexture tex, 50,50
EntityTexture terrain,tex
End If 


; Next Color (Ground)
If KeyHit(60)
If txg>4
txg=1
Else
txg=txg+1
End If
If txg=1
tex=tx1
Else If txg=2
tex=tx2
Else If txg=3
tex=tx3
Else If txg=4
tex=tx4
Else If txg=5
tex=tx5
End If
ScaleTexture tex, 50,50
EntityTexture plane,tex
End If 

RenderWorld

;DrawImage Img_B,0,0
DrawImage Img_p,(EntityX(player)+421)/5,(EntityZ(player)/5)-130
DrawImage Img_g,(EntityX(start)+421)/5,(EntityZ(start)/5)-130
If MouseOrKeys=1
	Text 0,0,"MODE: Keys (Default)"

Else
	Text 0,0,"MODE: Mouse"
End If	
If KeyDown(62)
	Text 0,30,"Player X"+EntityX(player)+" Y"+EntityY(player)+" Z"+EntityZ(player)
	Text 0,45,"Mouse X"+MouseX()+" Y "+MouseY()
	Text 0,60,"CheatsActive: "+CheatsActive
End If


Flip
UpdateWorld
Wend

End
Function freecam(player)
; Turn (X)
	; Mouse Re-locate
	If MouseX() > GraphicsWidth()-10
		MoveMouse 10,MouseY()
	Else If MouseX() < 10
		MoveMouse GraphicsWidth()-10,MouseY()
	End If

	;Turn
	trn=-MouseXSpeed()
	TurnEntity player,0,trn,0	
	
; Move (Y)
	;Mouse Re-locate
	If MouseY() > GraphicsHeight()-10
		MoveMouse MouseX(),10
	Else If MouseY() < 10
		MoveMouse MouseX(),GraphicsHeight()-10
	End If
	
	; Detect
	s=-MouseYSpeed()
	
	; Speed Limit
	If s>1
		s=1
	Else If s<-1
		s=-1
	End If
	
	; Move
	MoveEntity player,0,0,s	
End Function

Function movecam(player,s)
	If KeyDown( 205 ); Right
		TurnEntity player,0,-1,0
	End If
	If KeyDown( 203 ); Left
		TurnEntity player,0,1,0
	End If
	If KeyDown( 208 ); Down
		MoveEntity player,0,0,-0.5
	End If
	If KeyDown( 200 ); Up
		MoveEntity player,0,0,0.5
	End If
End Function