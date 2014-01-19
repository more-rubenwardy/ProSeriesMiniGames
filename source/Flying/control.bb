period=1000/FPS
time=MilliSecs()-period

detail=2000:morph=True
TerrainDetail terr,detail,morph
wind=PlayMusic("Wind.wav")
While Not KeyHit(1)
	
	If Not ChannelPlaying(wind)
	wind=PlayMusic("Wind.wav")
	End If
	 ; Control
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
	
	; WIRE FRAME
	If KeyHit(17)
		wire=Not wire
		WireFrame wire
	EndIf
	
	; PAUSE
	If KeyDown(25)
		Cls
		Text 300,300,"Game Paused"
		Text 300,315,"Click to Continue"
		Flip
		WaitMouse()
	End If
	
	; DETAIL
	If KeyHit(26)
		detail=detail-100
		If detail<100 Then detail=100
		TerrainDetail terr,detail,morph
	Else If KeyHit(27)
		detail=detail+100
		If detail>10000 Then detail=10000
		TerrainDetail terr,detail,morph
	EndIf
	
	; MORPH
	If KeyHit(50)
		morph=Not morph
		TerrainDetail terr,detail,morph
	EndIf
	
	; SPEED
	If KeyHit(4)
		speed=speed+1
		If speed>50 And warning=0
			Cls
			wrn = LoadImage("wrn.bmp")
			DrawImage wrn,0,0
			warning=1
			warnTime=MilliSecs()
			WaitKey()
		End If
	Else If KeyHit(5)
	speed=speed-1	
	End If
	If MilliSecs()>warnTime+1000
	warning=0
	End If
	If speed>15 And sonic=False 
	speed=15	
	Else If speed<1
	speed=1
	End If
	If speed>tpsp
	tpsp=speed
	End If
	
	If KeyDown(67) And KeyDown(2)
			sonic=True
			Stat$="Speed Unlimited"
	End If
	
	; Cameras
	If KeyHit(59)
	cam=cam+1
	Else If KeyHit(60)
	cam=cam-1	
	End If
	If cam>4
	cam=4
	Else If cam<1
	cam=1
	End If
	
	MoveEntity plane,0,-1,0
	
	; Game Over
	If CountCollisions (terr)=True
		Cls
		Graphics3D 640,460
		ti=MilliSecs()-t
		time=ti/1000
 		gm = LoadImage("gm.bmp")
		DrawImage gm,0,0
		Text 290,250,time
		
		If time>score
		fileout=WriteFile("scr.txt")
		WriteString(fileout,time)
		CloseFile(fileout)
		End If
		
		If time>top
		fileout=WriteFile("scr2.txt")
		WriteString(fileout,time)
		CloseFile(fileout)
		
		fileout=WriteFile("scr2name.txt")
		WriteString(fileout,name$)
		CloseFile(fileout)
		
		fileout=WriteFile("scr2speed.txt")
		WriteString(fileout,tpsp)
		CloseFile(fileout)
		
		End If
		Flip
		Delay 1000
		End
	End If
	
	RenderWorld tween
	
	
	; Text
	Color 255,0,0
	
	Text 0,0,stat
	Text 0,15,"Player: "+name
	Text 0,30,"Max Speed: "+speed
	Text 0,45,"Camera: "+cam
	Text 0,60,"Your Top Time: "+score
	If screen=0
		Text GraphicsWidth()-outset,0,"Top Time"
		Text GraphicsWidth()-outset,15," Time: "+top
		Text GraphicsWidth()-outset,30," By: "+topname
		Text GraphicsWidth()-outset,45," Top Speed: "+topspeed
	Else
		Text GraphicsWidth()-outset,0,"Top Time"
		Text GraphicsWidth()-outset,15," Time: "+top
		Text GraphicsWidth()-outset,30," By: "+topname
		Text GraphicsWidth()-outset,45," Top Speed: "+topspeed
	End If
	

	
	Flip
Wend