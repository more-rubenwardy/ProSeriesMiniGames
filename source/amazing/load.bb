; Graphics (Screen)
.screen
startmenu
Function startmenu()
	Graphics 640,480
	menu=LoadImage("menu.png")
	DrawImage menu,0,0
	key=WaitKey()
	If KeyDown(2)
		scr=2
	Else If KeyDown(3)
		scr=1
	Else If KeyDown(1)
		End
	Else
		scr=1	
	End If
	FreeImage menu
	While KeyDown(key)
	Wend
	If scr=1
		menu=LoadImage("graphics.png")
		DrawImage menu,0,0
		WaitKey()
		If KeyDown(2); 1
			gr=640
			gr2=480
		Else If KeyDown(3); 2
			gr=800
			gr2=600
		Else If KeyDown(4); 3
			gr=1024
			gr2=768
		Else If KeyDown(5); 4
			gr=1280
			gr2=600
		Else If KeyDown(6); 5
			gr=1280
			gr2=720
		Else If KeyDown(7); 6
			gr=1280
			gr2=768
		Else If KeyDown(8); 7
			gr=1360
			gr2=768
		Else If KeyDown(9); 8
			gr=1366
			gr2=768
		Else If KeyDown(1)
			End
		Else ; default
			gr=800
			gr2=600	
		End If
	Else
		gr=800
		gr2=600
	End If
	Graphics3D gr,gr2,0,scr
	SetBuffer BackBuffer()
End Function

imgLoad=LoadImage("Loading.png")
DrawImage imgload,GraphicsWidth() / 2 - 192,GraphicsHeight() / 2 - 73

; TEXTURE
tx1=LoadTexture("wood.jpg")
tx2=LoadTexture("grass.jpg")
tx3=LoadTexture("pebblewall.jpg")
tx4=LoadTexture("mosiac.jpg")
tx5=LoadTexture("greenwall.jpg")
	
tex=tx1
tx=1
	
ScaleTexture tex, 50,50

; types
type_player=1
type_scene=2

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

; Terrain make
terrain=LoadTerrain("heightmap.jpg")
ScaleEntity terrain,2,50,2
PositionEntity terrain,-500,0,-500
EntityTexture terrain,tex
EntityType terrain,type_scene
EntityPickMode terrain,2

; Plane
tex=tx2
txg=2
ScaleTexture tex, 50,50
plane=CreatePlane()
EntityType plane,type_scene
PositionEntity plane,0,1,0
EntityTexture plane,tex
EntityPickMode plane,2

;create cloud planes
tex=LoadTexture( "cloud.bmp",3 )
ScaleTexture tex,1000,1000
p=CreatePlane()
EntityTexture p,tex
EntityFX p,1
PositionEntity p,0,100,0
p=CopyEntity( p )
RotateEntity p,0,0,180 

;Finish Sprite
start=LoadSprite("finish.jpg")
PositionEntity start,170,3,720
ScaleSprite start,26,3
SpriteViewMode start,1

Collisions type_player,type_scene,2,2