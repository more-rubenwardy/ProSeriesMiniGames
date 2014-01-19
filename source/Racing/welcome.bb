filein=OpenFile("ScrRes.dll")
If Not filein
	Graphics 640,480,0,2
	img=LoadImage("ScrResSetUp.png")
	DrawImage img,0,0
	For a=1 To 20
		Print ""
	Next
	Color 255,0,0
	Print "Note - Do not skip this stage."
	Print "If you get it wrong, Delete the 'ScrRes.dll' File.'"
	Color 255,255,0
	Print "There is 50px (Pixels) in 1.5cm"
	Print ""
	resX=Input("Enter screen resolution X (Normally 1024px): ")
	resY=Input("Enter screen resolution Y (Normally 768px): ")
	fileout=WriteFile("ScrRes.dll")
		WriteLine(fileout,resX)
		WriteLine(fileout,resY)
	CloseFile fileout
	filein=OpenFile("ScrRes.dll")
End If
resX=ReadLine(filein)
resY=ReadLine(filein)
CloseFile filein

;Graphics 315,45,0,2
;.space
;DebugLog "Random position objects"
;Print "RANDOMISING POSITION OF OBJECTS"
;Print "Hold down SPACE for as long as you want"
;WaitKey()
;If KeyDown(57)
	;Print "Release SPACE key to start game"
	;x=-490
	;z=0
	;px=-100
	;pz=100
	;While KeyDown(57)
		; Goal
		;x=x+1
		;z=z+1
		;If z > 729
			;z=-490
		;Else If x > 729
			;x=-490
		;End If
		; Players
		;px=px+1
		;pz=pz+1
		
		;If pz > 729
		;	pz=-490
		;Else If px > 729
		;	px=-490
		;End If
	;Wend
;Else
	;Goto space
;Text 0,0,"Camera  X"+EntityX(camera)+" Y"+EntityY(camera)+" Z"+EntityZ(camera)End If

.menu
DebugLog "Main Menu"
Graphics 640,480,0,2
img=LoadImage("welcome.png")
DrawImage img,0,0
WaitKey()
If KeyDown(2)
	scrtype=1
	Graphics3D resX,resY,0,1
Else If KeyDown(3)
	scrtype=2
	Graphics3D 1024,768,0,2
Else
	Goto menu
End If
FreeImage img