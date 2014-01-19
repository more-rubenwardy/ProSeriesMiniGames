 .start
AppTitle "Flying Pro : ???", ""
Graphics3D 640,460,0,2
; Read Files
;HidePointer()
;set to false if you have fog problems
;(typically with early ATI cards)
Global use_fog=True
Global cloud=True
Global speed=2
Global cam=1
Global stat$="Ready"
outset=125
sonic=False
warning=0
;game FPS
Const FPS=30

Include "file.bb"
Include "start.bb"

imgLoad=LoadImage("Loading.png")
DrawImage imgload,GraphicsWidth() / 2 - 192,GraphicsHeight() / 2 - 73

t=MilliSecs()

Type Player
	Field entity,camera
	Field ctrl_mode,cam_mode,ignition
	Field pitch#,yaw#,pitch_speed#,yaw_speed#,roll#,thrust#
End Type
 
Collisions 1,10,2,2	;sphere-to-polygon, colliding collisions

Global terr

CreateScene()

plane=LoadAirPlane( "biplane.x" )

player1.Player=CreatePlayer( plane,0,0,GraphicsWidth(),GraphicsHeight(),1 )


Include "control.bb"


End
Include "func.bb"