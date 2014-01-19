.start
;Graphics3D 1024,768,0,0
Graphics3D 1366,768,0,1
scene = LoadMesh("scene.b3d")
ScaleEntity scene,1.5,1,1.5
PositionEntity scene,2,0,0
SetBuffer BackBuffer()
pivot=CreatePivot()
jmpivot=CreatePivot()
PositionEntity pivot,10,0,-8
PositionEntity jmPivot,10,0,-8
pointer=CreateSphere(20)
PositionEntity pointer,10,10,-8
ScaleEntity pointer,.5,.5,.5
EntityColor pointer,150,150,150
Const Type_Player=1,Type_Scene=2,Type_jmPole=3,JumpMaxHeight=15
Global massivefnt=LoadFont("AR BLANCA",72),bigfnt=LoadFont("AR BLANCA",45)
Global smallfnt=LoadFont("Arial",12),normfnt=LoadFont("AR BLANCA",20)

camera=CreateCamera(pivot)

PositionEntity camera,6,15,13
EntityType camera,type_player
;EntityRadius camera,2
CameraClsColor camera,0,200,255
TurnEntity camera,20,155,0

;PointEntity camera,scene
player=CreateSphere(20)
;EntityAlpha player,0.75
PositionEntity player,8,15,-2
EntityType player,Type_Player
EntityType scene,type_scene

light=CreateLight(1,pivot)
EntityParent light,0
MoveEntity light,-10,20,10
PointEntity light,player
;plane=CreatePlane()

;EntityRadius scene,10
Type Cloud
	Field Sprite,Xspd#,Yspd#;,Zspd
End Type
SeedRnd MilliSecs()
CldXmin=-5000
CldXmax=100
;For a=0 To 100
;	c.cloud=New cloud
;	c\sprite=LoadSprite("cloud.bmp")
;	PositionEntity c\sprite,Rand(CldXmin,CldXmax),Rand(-100,50),Rand(-500,-550)
;	If Rand(0,1)=0
;		c\xspd=Rnd(0.10,0.50)
;	Else
;		c\xspd=Rnd(-0.10,-0.50)
;	EndIf
;Next
JumpComence=0        
Plane=CreatePlane()
PositionEntity plane,0,-300,0
tex=LoadTexture("greenery.jpg")
ScaleTexture tex,50,50
EntityTexture plane,tex
Global jmPivotSpd#=0

pole = CreateCylinder(20,True,jmPivot)
EntityColor pole,255,0,0
ScaleEntity pole,.5,3,.5
PointEntity pole,jmPivot
PositionEntity pole,0,10.5,6
RotateEntity pole,90,0,0
EntityType pole,Type_jmPole
HideEntity pole

TurnEntity jmPivot,0,90,0
pole3 = CreateCylinder(20,True,jmPivot)
EntityColor pole3,255,0,0
ScaleEntity pole3,.5,3,.5
PointEntity pole3,jmPivot
PositionEntity pole3,6,10.5,0
RotateEntity pole3,90,90,0
EntityType pole3,Type_jmPole
HideEntity pole3

TurnEntity jmPivot,0,90,0
pole2 = CreateCylinder(20,True,jmPivot)
EntityColor pole2,255,0,0
ScaleEntity pole2,.5,3,.5
PointEntity pole2,jmPivot
PositionEntity pole2,0,10.5,-6
RotateEntity pole2,90,0,0
EntityType pole2,Type_jmPole
HideEntity pole2

TurnEntity jmPivot,0,90,0
pole4 = CreateCylinder(20,True,jmPivot)
EntityColor pole4,255,0,0
ScaleEntity pole4,.5,3,.5
PointEntity pole4,jmPivot
PositionEntity pole4,-6,10.5,0
RotateEntity pole4,90,90,0
EntityType pole4,Type_jmPole
HideEntity pole4

TurnEntity jmPivot,0,-90,0
Collisions type_player,type_scene,2,2 
Collisions type_player,type_jmPole,2,2 
NextStageTime$="0m:1s:0ms"
b#=25
SetFont massivefnt
Color 255,0,0

While b>13
	UpdateWorld
	PositionEntity camera,6,15,b
	RenderWorld
		Text GraphicsWidth()/2,GraphicsHeight()/2,"Jump For The Longest!",True,True
	Flip
	b=b-0.1
	For c.cloud=Each cloud
		MoveEntity c\sprite,c\xspd,c\yspd,0
		If EntityX(c\sprite)<CldXmin Or EntityX(c\sprite)>CldXmax Then c\xspd=-c\xspd
	Next
	MoveEntity plane,0.1,0,0.1
	If CountCollisions(player)=True
		MoveEntity player,0,.2,0
	Else
		MoveEntity player,0,-.2,0
	EndIf
Wend
Global Timer=MilliSecs()
EntityParent camera,0
camSpd#=0.1
camAddSpd#=-0.01
While Not KeyDown(1)
	If KeyHit(17)
	wire=Not wire
	WireFrame wire
	EndIf
	SetFont normfnt
	;DebugLog "Updating World..."
	UpdateWorld
	If CountCollisions(player);=True Then 
		;DebugLog "CC POLE: Started..."
		For a=1 To CountCollisions(player)
			;DebugLog "CC PLYR: "+a+"/"+CountCollisions(player)
			If CollisionEntity(player,a)=pole Or CollisionEntity(player,a)=pole2 Or CollisionEntity(player,a)=pole3 Or CollisionEntity(player,a)=pole4
				;DebugLog "CC PLYR: "+a+"/"+CountCollisions(player)+" Triggered"
				;Timer=MilliSecs()-Timer
				;Goto EndSc
			EndIf
		Next
	EndIf
	;If KeyHit(2)
		For c.cloud=Each cloud
			MoveEntity c\sprite,c\xspd,c\yspd,0
			If EntityX(c\sprite)<CldXmin Or EntityX(c\sprite)>CldXmax Then c\xspd=-c\xspd
		Next
	;EndIf
	;If KeyDown(200)=True Then MoveEntity camera,0,0,.5
	;If KeyDown(203)=True Then TurnEntity camera,0,1,0
	;If KeyDown(205)=True Then TurnEntity camera,0,-1,0
	;If KeyDown(57)=True And CountCollisions(player)=True Then JumpComence=1
	If EntityDistance(player,pole)<2 And CountCollisions(player)=True Then jumpcomence=1
	If EntityDistance(player,pole2)<2 And CountCollisions(player)=True And stage>3 Then jumpcomence=1
	If EntityDistance(player,pole3)<2 And CountCollisions(player)=True And stage>4 Then jumpcomence=1
	If EntityDistance(player,pole4)<2 And CountCollisions(player)=True And stage>5 Then jumpcomence=1
	MoveEntity plane,0.1,0,0.1
	If CountCollisions(player)=True
		MoveEntity player,0,.2,0
	Else
		MoveEntity player,0,-.2,0
	EndIf
	;EndIf
	If JumpComence=1
		If EntityY(player)>JumpMaxHeight
			JumpComence=0
		EndIf
		MoveEntity player,0,.5,0
	EndIf
	RenderWorld
		;TurnEntity pivot,0,-1,0
		Color 255,255,0
		Text 10,0,"Time: "+ConvertToComplex(MilliSecs()-timer)
		Color 255,255,255
		SetFont smallfnt
		Text 10,16,"Next Level At: "+NextStageTime$
		SetFont normfnt
		Color 255,200,0
		
		
		Text 10,25,"Record: "+ConvertToComplex(0)
		SetFont smallfnt
		Text GraphicsWidth()/2,GraphicsHeight()-20,"see www.multa.webs.com for more info",True
		Color 255,0,0
			SetFont massivefnt
			Text GraphicsWidth()/2+20,0,"Jumping Pro - Demo Video [Lvl "+stage+"/6]",True,False
			SetFont normfnt
			Color 255,255,255
		;Text 10,15,"X"+EntityX(camera)+" Y"+EntityY(camera)+" Z"+EntityZ(camera)
		;Text 10,30,"X"+EntityX(player)+" Y"+EntityY(player)+" Z"+EntityZ(player)
		If stageup<>0
			If MilliSecs()>stageup+1000
				stageup=0
			EndIf
			Color 255,0,0
			SetFont massivefnt
			Text GraphicsWidth()/2,GraphicsHeight()/4,"LEVEL "+Stage,True,False
			SetFont normfnt
			Color 255,255,255
		EndIf
	Flip
	If Stage<>0
		TurnEntity jmpivot,0,jmPivotSpd,0
	EndIf
	
	;DebugLog MilliSecs()-Timer
	;Change Stages
	If MilliSecs()-Timer>130000
		Timer=MilliSecs()
		HideEntity pole2
		HideEntity pole3
		HideEntity pole4
		stage=1
	ElseIf MilliSecs()-Timer>120000
		If jmPivotSpd<>1.5=True Then stageup=MilliSecs()
		jmPivotSpd=1.5
		Stage=6
		ShowEntity pole4
		DebugLog "Stage 4 - polespd: "+jmPivotSpd
		NextStageTime$="-m:-s:-ms"
	ElseIf MilliSecs()-Timer>90000
		If jmPivotSpd<>1.6=True Then stageup=MilliSecs()
		jmPivotSpd=1.6
		Stage=5
		ShowEntity pole3
		DebugLog "Stage 4 - polespd: "+jmPivotSpd
		NextStageTime$="2m:0s:0ms"
	ElseIf MilliSecs()-Timer>65000
		If jmPivotSpd<>1.5=True Then stageup=MilliSecs()
		jmPivotSpd=1.5
		Stage=4
		ShowEntity pole2
		DebugLog "Stage 4 - polespd: "+jmPivotSpd
		NextStageTime$="1m:30s:0ms"
	ElseIf MilliSecs()-Timer>30000
		If jmPivotSpd<>-2=True Then stageup=MilliSecs()
		jmPivotSpd=-2
		Stage=3	
		DebugLog "Stage 3 - polespd: "+jmPivotSpd
		NextStageTime$="1m:5s:0ms"
	ElseIf MilliSecs()-Timer>15000
		If jmPivotSpd<>2.5=True Then stageup=MilliSecs()
		jmPivotSpd=2.5	
		Stage=2
		DebugLog "Stage 2 - polespd: "+jmPivotSpd
		NextStageTime$="0m:30s:0ms"
	ElseIf MilliSecs()-Timer>1000
		If jmPivotSpd<>1.5=True Then stageup=MilliSecs()
		jmPivotSpd=1.5	
		Stage=1
		ShowEntity pole
		DebugLog "Stage 1 - polespd: "+jmPivotSpd
		NextStageTime$="0m:15s:0ms"
	EndIf
Wend
End

.EndSc
	If jmPivotSpd>0
		For a=-8 To 5
			PositionEntity player,-a,EntityY(player),EntityZ(player)
			RenderWorld
			Flip
			If Stage<>0
				TurnEntity jmpivot,0,jmPivotSpd,0
			EndIf		
		Next
	Else
		For a=0 To 9
			PositionEntity player,8+a,EntityY(player),EntityZ(player)
			RenderWorld
			Flip
			If Stage<>0
				TurnEntity jmpivot,0,jmPivotSpd,0
			EndIf		
		Next
	EndIf
	b#=-11
	While EntityInView(player,camera)
		PositionEntity player,EntityX(player),-b,EntityZ(player)
		RenderWorld
		Flip
		b=b+0.5
		If Stage<>0
			TurnEntity jmpivot,0,jmPivotSpd,0
		EndIf	
	Wend
	HideEntity pole
	HideEntity pole2
	HideEntity pole3
	HideEntity pole4
.EndScBack	
	RenderWorld
	SetFont massivefnt
	Color 255,0,0
	Text GraphicsWidth()/2,GraphicsHeight()/2-15,"GAME OVER",True,True
	SetFont normfnt
	Color 255,150,0
	Text GraphicsWidth()/2,GraphicsHeight()/2+30,"Time: "+ConvertToComplex$(Timer),True,True
	Color 255,255,0
	Text GraphicsWidth()/2,GraphicsHeight()/2+50,"Play Again? [Y/N]",True,True
	Flip
	WaitKey()
	If KeyDown(21)
		For c.cloud=Each cloud
			Delete c
		Next
		Goto start
	ElseIf KeyDown(49)
		End
	Else
		Goto endscback
	EndIf
	
Function ConvertToComplex$(Millis)
	Milli=Millis
	While Milli>1000
		Sec=Sec+1
		Milli=Milli-1000
	Wend
	While Sec>59
		Min=Min+1
		Sec=Sec-60
	Wend
	Return Min+"m:"+Sec+"s:"+Milli+"ms"
End Function