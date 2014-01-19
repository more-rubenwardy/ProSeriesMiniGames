Cls
Graphics3D 640,460,0,2
screen=LoadImage ("loading.bmp")
DrawImage screen,0,0


filein = ReadFile("name.txt")
name$ = ReadString$(filein)

filein = ReadFile("scr.txt")
score = ReadString$(filein)

filein = ReadFile("scr2.txt")
top= ReadString$(filein)

filein = ReadFile("scr2name.txt")
topname$ = ReadString$(filein)

filein = ReadFile("scr2speed.txt")
topspeed$ = ReadString$(filein)

Delay 250

If name$=""
.name
FreeImage screen
screen=LoadImage ("welcome.bmp")
DrawImage screen,0,0

Delay 500
Locate 250,250
Color 250,0,0
name$=Input("Enter Your Name: ")
If name$=""
Goto name
End If
fileout=WriteFile("name.txt")
WriteString(fileout,name)
CloseFile(fileout)
Delay 1000

End If
AppTitle "Flying Pro : "+name$, ""

Cls
FreeImage screen