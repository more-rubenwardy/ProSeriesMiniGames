.Menu
Cls
e=0
Graphics3D 640,460,0,2
screen=LoadImage ("Logo.bmp")
DrawImage screen,0,0
WaitKey()
If KeyDown(2) Or KeyDown(11) Or KeyDown(3)
	If KeyDown(2)
		screen=1
		x=1024
		y=768
		f=1
	Else If KeyDown(11)
		screen=0
		x=956
		y=650
		f=2
	Else If KeyDown(3)
		fileout=WriteFile("name.txt")
		WriteString(fileout,"")
		CloseFile(fileout)
		fileout=WriteFile("scr.txt")
		WriteString(fileout,"")
		CloseFile(fileout)
		Cls
		Print "Save slot erased"
		Delay 1000
		Goto start
	End If
	
Else
	Goto Menu
End If
Graphics3D x,y,0,f
FreeImage screen