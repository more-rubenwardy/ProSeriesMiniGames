	AppTitle "Racing Pro - Welcome"
Include "welcome.bb"
AppTitle "Racing Pro - Loading"
Include "Start.bb"
Delay 500

DebugLog "Running" 

Global M#=0.01
Global P1spd#=0
Global P2spd#=0
Global topspeed#=1.5
Global Player1won=0
Global Player2won=0

AppTitle "Racing Pro"

;Countdown
Color 255,0,0
font=LoadFont("Times",100)
SetFont font

;3
RenderWorld
Text GraphicsWidth()/2,GraphicsHeight()/2,"3",True,True
Flip
Delay 1000

;2
Cls
RenderWorld
Text GraphicsWidth()/2,GraphicsHeight()/2,"2",True,True
Flip
Delay 1000

;1
Cls
Color 255,128,0
RenderWorld
Text GraphicsWidth()/2,GraphicsHeight()/2,"1",True,True
Flip
Delay 1000

Color 255,255,255
Global timer=MilliSecs()

While Not KeyDown( 1 )
; Ctrl
UpdateControls(camera,camera2,gl,cam,cam2)	
UpdateWorld

RenderWorld


;Line GraphicsWidth()/2,0,GraphicsWidth()/2,GraphicsHeight()

If Player1won
	font=LoadFont("Times",75)
	Color 255,255,0
	SetFont font
	Text 30,GraphicsHeight()/2,"WINNER!!!"
	FreeFont font
Else If Player2won
	Color 255,0,0
	font=LoadFont("Times",75)
	SetFont font
	Text GraphicsWidth()/2+30,GraphicsHeight()/2,"WINNER!!!"
	FreeFont font
End If

If KeyDown(68)
	Color 255,0,0
	Text 0,0,"Camera  X"+EntityX(camera)+" Y"+EntityY(camera)+" Z"+EntityZ(camera)
	Text 0,15,"Camera2  X"+EntityX(camera2)+" Y"+EntityY(camera2)+" Z"+EntityZ(camera2)
	Text 0,30,"Player1speed (P1spd) '"+P1spd#+"'"
	Text 250,30,"Player2speed(P1spd) '"+P2spd#+"'"
	Text 0,45,"terrain X"+EntityX(terr)+" Y"+EntityY(terr)+" Z"+EntityZ(terr)
	Color 255,255,255
End If
If KeyDown(87)
	ClearCollisions
	Collisions type_player,type_scene,2,3
End If

If MilliSecs() < timer+500
	font=LoadFont("Times",100)
	SetFont font
	Color 0,255,0
	Text GraphicsWidth()/2,GraphicsHeight()/2,"GO",True,True
	FreeFont font
	Color 255,255,255
End If

If KeyDown(183)
Graphics 322,120,2
img=LoadImage("PrtScr.jpg")
DrawImage img,0,0
Delay 1000
End If
Flip

Wend
DebugLog "END"
End

 
Function UpdateControls(camera,camera2,gl,cam,cam2)

If Player1won=0
If KeyDown( 31 )=True Then TurnEntity camera,0,-1,0 ; Turn Right D
If KeyDown( 30 )=True Then TurnEntity camera,0,1,0	; Turn Left A
If KeyDown( 44 )=True Then P1spd#=P1spd#-0.01;MoveEntity camera,0,0,-.5
If KeyDown( 17 )=True Then P1spd#=P1spd#+0.01;MoveEntity camera,0,0,.5 ; Foward W
If KeyDown( 18 )=True Then MoveEntity camera,0,1,0  ; Up
End If

If Player2won=0
	If KeyDown( 205 )=True Then TurnEntity camera2,0,-1,0 ; Turn Right
	If KeyDown( 203 )=True Then TurnEntity camera2,0,1,0	; Turn Left
	If KeyDown( 208 )=True Then P2spd#=P2spd#-0.01 ;MoveEntity camera2,0,0,-.5 ; Back 
	If KeyDown( 200 )=True Then P2spd#=P2spd#+0.01;MoveEntity camera2,0,0,.5 ; Foward
	If KeyDown( 211 )=True Then MoveEntity camera2,0,1,0  ; Up
End If

If KeyDown( 25 )
Color 255,0,0
Text 320,240,"PAUSED CLICK TO CONTINUE",True,True
Flip
WaitMouse()
Color 255,255,255
End If



If KeyHit(38)
	If play=0
		play=1
	Else
		play=0
	End If
RenderWorld
Print play
Delay 1000
End If

UpdateGame(camera,camera2,gl,cam,cam2)
End Function

Function UpdateGame(camera,camera2,gl,cam,cam2)
; Gravity
If Not KeyDown(2)
MoveEntity camera,0,-0.2,0
MoveEntity camera2,0,-0.2,0
End If

; MoveMent
If P1spd#>topspeed#
P1spd#=topspeed#
Else If P1spd#<-1
P1spd#=-1
End If

If P2spd#>topspeed#
P2spd#=topspeed#
Else If P2spd#<-1
P2spd#=-1
End If

If P1spd>0
P1spd#=P1spd#-0.005
Else If P1spd<0
P1spd#=P1spd#+0.005
End If

If P2spd>0
P2spd#=P2spd#-0.005
Else If P2spd<0
P2spd#=P2spd#+0.005
End If



MoveEntity camera,0,0,P1spd#
MoveEntity camera2,0,0,P2spd#



;If EntityY(cam)<>EntityY(camera)+5
;PositionEntity cam,EntityX(camera),EntityY(camera)+5,EntityZ(camera)
;End If

;If EntityY(cam2)<>EntityY(camera2)+5
;PositionEntity cam2,EntityX(camera2),EntityY(camera2)+5,EntityZ(camera2)
;End If



If EntityZ(camera) > 1370
	Player1won=1
	P1spd#=0
	If Player2won=1
		Won(2)
	End If
End If

If EntityZ(camera2) > 1370
	Player2won=1
	P2spd#=0
	If Player1won=1
		Won(1)
	End If
End If



End Function
 
Function Won(plr)
Graphics 1024,768,scrtype
timer=MilliSecs()-timer
timer=timer/1000
img=LoadImage("finish.png")
DrawImage img,0,0
Delay 100
font=LoadFont("Times",20)
SetFont font
Text 485,295,plr

If plr=1
plr=2
Else If plr=2
plr=1
End If

Text 485,315,plr
Text 470,352,timer
Flip
WaitMouse()
End
End Function