imgLoad=LoadImage("Loading.png")
DrawImage imgload,GraphicsWidth() / 2 - 192,GraphicsHeight() / 2 - 73

DebugLog "Types Loading"
; Types
type_player=1
type_scene=2
type_wall=3
type_goal=4

DebugLog "Buffer Loading"
; Graphics
SetBuffer BackBuffer()

DebugLog "Players Loading"

; Camera
cam=CreateCamera()
PositionEntity cam,-30,6,-730
CameraClsColor cam,86,233,233

; Camera2
cam2=CreateCamera()
PositionEntity cam2,-25,6,-730
CameraClsColor cam2,86,233,233


; Players
camera = LoadMesh("f360.3ds")
EntityRadius camera,1
PositionEntity camera, -30,3,-725
ScaleEntity camera, 1,1,1
tex=LoadTexture("fskin.jpg")
EntityTexture camera,tex
EntityType camera,type_player
EntityParent cam,camera

camera2 = LoadMesh("f360.3ds")
PositionEntity camera2,-25,3,-725
ScaleEntity camera2, 1,1,1
tex=LoadTexture("fskiny.jpg")
EntityTexture camera2,tex
EntityType camera2,type_player
EntityParent cam2,camera2
EntityRadius camera2,1

DebugLog "Cameras Loading"

DebugLog "Split Screen Loading"
; Split
CameraViewport cam,0,0,GraphicsWidth()/2,GraphicsHeight()
CameraViewport cam2,GraphicsWidth()/2,0,GraphicsWidth()/2,GraphicsHeight()

DebugLog "Light"
; Light
l=CreateLight()
RotateEntity l,45,45,0
AmbientLight 32,32,32

DebugLog "Scene"
DebugLog " Terrain"
; Terrain
terr=LoadTerrain ( "ground.jpg" )
ScaleEntity terr,2.5,50,2.5
PositionEntity terr,-1024,0,-1024
grass=LoadTexture( "greenery.jpg" )
ScaleTexture grass, 25,25
EntityTexture terr,grass
EntityType terr,type_scene

DebugLog " Road"
road=CreatePlane ()
PositionEntity road,0,2,0
tex=LoadTexture( "road.bmp" )
ScaleTexture tex, 10,10
EntityTexture road,tex
EntityType road,type_scene

finish=LoadSprite("finishline.jpg")
PositionEntity finish,-30,3,1370
ScaleSprite finish,18,2
RotateEntity finish,0,90,0
SpriteViewMode finish,2
TurnEntity finish,90,90,0

start=LoadSprite("finishline.jpg")
PositionEntity start,-30,3,-720
ScaleSprite start,18,2
RotateEntity start,0,90,0
SpriteViewMode start,2
TurnEntity start,90,90,0

DebugLog " Cloud"
;create cloud planes
tex=LoadTexture( "cloud.bmp",3 )
ScaleTexture tex,1000,1000
p=CreatePlane()
EntityTexture p,tex
EntityFX p,1
PositionEntity p,0,200,0
p=CopyEntity( p )
RotateEntity p,0,0,180 

DebugLog " Goal"

; Create the goal
;gl=CreateSphere()
;ScaleEntity gl,0.5,1,0.5
;PositionEntity gl,x,100,z
;EntityType gl,type_goal
;EntityRadius gl,2.5
;EntityColor gl,255,0,0
;DebugLog "  Goal Start Up"
;Collisions type_goal,type_scene,2,3
;While Not KeyDown(1)
;MoveEntity gl,0,-.1,0
;UpdateWorld
;If CountCollisions (gl)
;Goto glend
;End If
;Wend
;DebugLog "   !!-User Stopped Goal Start Up-!!"
;Stop
;.glend
;MoveEntity gl,0,1,0
;DebugLog "Gl position X:"+EntityX(gl)+" Y:"+EntityY(gl)+" Z:"+EntityZ(gl)

;-75
barrier1=CreatePlane()
PositionEntity barrier1,-75,0,0
RotateEntity barrier1,0,180,90
EntityType barrier1,type_scene
EntityAlpha barrier1,0

barrier2=CreatePlane()
PositionEntity barrier2,15,0,0
RotateEntity barrier2,0,0,90
EntityType barrier2,type_scene
EntityAlpha barrier2,0

DebugLog "Collisions"
; Collisions
Collisions type_player,type_scene,2,3
Collisions type_player,type_player,2,2